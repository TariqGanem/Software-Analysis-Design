package DataAccessLayer.ShipmentsModule.Mappers;

import DTOPackage.DocumentDTO;
import DTOPackage.ShipmentDTO;
import DataAccessLayer.ShipmentsModule.IdentityMap;
import DataAccessLayer.dbMaker;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;

public class ShipmentMapper {

    private static ShipmentMapper instance = null;
    private IdentityMap memory;


    private ShipmentMapper() {
        memory = IdentityMap.getInstance();
    }

    public static ShipmentMapper getInstance() {
        if (instance == null)
            instance = new ShipmentMapper();
        return instance;
    }

    public ShipmentDTO addShipment(int id, Date date, String depHour, String truckPlateNumber, String driverId, int sourceId) throws Exception {
        ShipmentDTO shipment;
        for (ShipmentDTO sh : memory.getShipments()) {
            if (sh.getShipmentId() == id)
                throw new Exception("Shipment already exists!");
        }
        shipment = new ShipmentDTO(id, date, depHour, truckPlateNumber, driverId, LocationMapper.getInstance().getLocation(sourceId));
        if (shipmentExist(id)) {
            memory.getShipments().add(shipment);
            throw new Exception("Shipment already exists in the database!");
        }
        insertShipment(id, date, depHour, truckPlateNumber, driverId, sourceId);
        memory.getShipments().add(shipment);
        return shipment;
    }


    public ShipmentDTO getShipment(Date date, String departureHour, String driverId) throws Exception {
        for (ShipmentDTO shipment : memory.getShipments()) {
            if (shipment.getDate().equals(date) && shipment.getDepartureHour().equals(departureHour)
                    && shipment.getDriverId().equals(driverId))
                return shipment;
        }
        ShipmentDTO shipment = selectShipment(date, departureHour, driverId);
        if (shipment != null) {
            for (DocumentDTO doc : DocumentMapper.getInstance().getShipmentDocuments(shipment.getShipmentId())) {
                if (doc != null)
                    shipment.addDocument(doc.getTrackingNumber(), doc.getProducts(), doc.getDestination());
            }
            memory.getShipments().add(shipment);
            return shipment;
        }
        return null;
    }

    public ShipmentDTO trackShipment(int trackingId) throws Exception {
        for (ShipmentDTO shipment : memory.getShipments()) {
            for (DocumentDTO doc : shipment.getDocuments().values())
                if (doc.getTrackingNumber() == trackingId)
                    return shipment;
        }
        List<ShipmentDTO> shipments = selectAllShipments();
        for (ShipmentDTO shipment : shipments) {
            for (DocumentDTO doc : shipment.getDocuments().values())
                if (doc.getTrackingNumber() == trackingId) {
                    return shipment;
                }
        }
        throw new Exception("No such tracking number in the database!");
    }

    public List<ShipmentDTO> getAllShipments() throws Exception {
        List<ShipmentDTO> shipments = selectAllShipments();
        memory.setShipments(shipments);
        return shipments;
    }

    public void deleteShipment(int shipmentId) throws Exception {
        _deleteShipment(shipmentId);
        for (ShipmentDTO shipment : memory.getShipments()) {
            if (shipment.getShipmentId() == shipmentId) {
                memory.getShipments().remove(shipment);
                return;
            }
        }
        throw new Exception("No such shipment in the system!");
    }

    private void insertShipment(int id, Date date, String depHour, String truckPlateNumber, String driverId, int sourceId) throws Exception {
        String sql = "INSERT INTO " + dbMaker.shipmentsTbl + " (id, Date, departureHour, truckPlateNumber, driverId, sourceId) VALUES (?,?,?,?,?,?)";
        try (Connection conn = dbMaker.connect();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, id);
            pstmt.setString(2, new SimpleDateFormat("dd/MM/yyyy").format(date));
            pstmt.setString(3, depHour);
            pstmt.setString(4, truckPlateNumber);
            pstmt.setString(5, driverId);
            pstmt.setInt(6, sourceId);
            pstmt.executeUpdate();
        } catch (Exception e) {
            throw new Exception(e.getMessage());
        }
    }

    private void _deleteShipment(int shipmentId) throws Exception {
        String sql = "DELETE FROM " + dbMaker.shipmentsTbl + " WHERE id = ?";
        try (Connection conn = dbMaker.connect();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, shipmentId);
            pstmt.executeUpdate();
        } catch (Exception e) {
            throw new Exception(e.getMessage());
        }
    }

    private ShipmentDTO selectShipment(int id) throws Exception {
        String sql = "SELECT * FROM " + dbMaker.shipmentsTbl + " WHERE id=" + id;
        try (Connection conn = dbMaker.connect();
             Statement stmt = conn.createStatement()) {
            ResultSet rs = stmt.executeQuery(sql);
            if (rs.next()) {
                ShipmentDTO shipment = new ShipmentDTO(rs.getInt(1),
                        new SimpleDateFormat("dd/MM/yyyy").parse(rs.getString(2)),
                        rs.getString(3),
                        rs.getString(4),
                        rs.getString(5),
                        LocationMapper.getInstance().getLocation(rs.getInt(6)));
                List<DocumentDTO> docs = DocumentMapper.getInstance().getShipmentDocuments(shipment.getShipmentId());
                for (DocumentDTO doc : docs) {
                    shipment.addDocument(doc.getTrackingNumber(), doc.getProducts(), doc.getDestination());
                }
                return shipment;
            }
        } catch (Exception e) {
            throw new Exception(e.getMessage());
        }
        return null;
    }

    private ShipmentDTO selectShipment(Date date, String departureHour, String driverId) throws Exception {
        String sql = "SELECT * FROM " + dbMaker.shipmentsTbl + " WHERE date='" +
                new SimpleDateFormat("dd/MM/yyyy").format(date) + "' AND departureHour='"
                + departureHour + "' AND driverId='" + driverId + "'";
        try (Connection conn = dbMaker.connect();
             Statement stmt = conn.createStatement()) {
            ResultSet rs = stmt.executeQuery(sql);
            if (rs.next()) {
                ShipmentDTO shipment = new ShipmentDTO(rs.getInt(1),
                        new SimpleDateFormat("dd/MM/yyyy").parse(rs.getString(2)),
                        rs.getString(3),
                        rs.getString(4),
                        rs.getString(5),
                        LocationMapper.getInstance().getLocation(rs.getInt(6)));
                List<DocumentDTO> docs = DocumentMapper.getInstance().getShipmentDocuments(shipment.getShipmentId());
                for (DocumentDTO doc : docs) {
                    shipment.addDocument(doc.getTrackingNumber(), doc.getProducts(), doc.getDestination());
                }
                return shipment;
            }
        } catch (Exception e) {
            throw new Exception(e.getMessage());
        }
        throw new Exception("There is no such shipment in the database!");
    }

    private List<ShipmentDTO> selectAllShipments() throws Exception {
        String sql = "SELECT * FROM " + dbMaker.shipmentsTbl;
        List<ShipmentDTO> shipments = new LinkedList<>();
        try (Connection conn = dbMaker.connect();
             Statement stmt = conn.createStatement()) {
            ResultSet rs = stmt.executeQuery(sql);
            while (rs.next()) {
                ShipmentDTO shipment = new ShipmentDTO(rs.getInt(1),
                        new SimpleDateFormat("dd/MM/yyyy").parse(rs.getString(2)),
                        rs.getString(3),
                        rs.getString(4),
                        rs.getString(5),
                        LocationMapper.getInstance().getLocation(rs.getInt(6)));
                List<DocumentDTO> docs = DocumentMapper.getInstance().getShipmentDocuments(shipment.getShipmentId());
                for (DocumentDTO doc : docs) {
                    shipment.addDocument(doc.getTrackingNumber(), doc.getProducts(), doc.getDestination());
                }
                shipments.add(shipment);
            }
        } catch (Exception e) {
            throw new Exception(e.getMessage());
        }
        return shipments;
    }

    private boolean shipmentExist(int id) throws Exception {
        ShipmentDTO shipment = selectShipment(id);
        if (shipment != null)
            return true;
        return false;
    }

    public int getMaxID() {
        String sql = "SELECT MAX(id) FROM " + dbMaker.shipmentsTbl;
        try (Connection conn = dbMaker.connect();
             Statement stmt = conn.createStatement()) {
            ResultSet rs = stmt.executeQuery(sql);
            if (rs.next()) {
                return rs.getInt(1);
            }
        } catch (Exception e) {
            //throw new Exception(e.getMessage());
        }
        return -1;
        //throw new Exception("Error in indexing!");
    }
}
