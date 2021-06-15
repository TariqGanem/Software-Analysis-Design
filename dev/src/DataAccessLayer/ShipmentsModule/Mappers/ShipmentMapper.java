package DataAccessLayer.ShipmentsModule.Mappers;

import BusinessLayer.StoreModule.Objects.ItemSpecs;
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
        if (shipmentExist(date, depHour, driverId)) {
            memory.getShipments().add(shipment);
            throw new Exception("Shipment already exists in the database!");
        } else {
            insertShipment(id, date, depHour, truckPlateNumber, driverId, sourceId);
            memory.getShipments().add(shipment);
        }
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

    public void deleteDriverTruck(String driverId, Date date, boolean isMorning) throws Exception {
        String sql = "DELETE FROM " + dbMaker.truckSchedulerTbl + " WHERE shipmentDate = ?  AND isMorning=? AND driver = ?";
        try (Connection conn = dbMaker.connect();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, new SimpleDateFormat("dd/MM/yyyy").format(date));
            pstmt.setBoolean(2, isMorning);
            pstmt.setString(3, driverId);
            pstmt.executeUpdate();
        } catch (Exception e) {
            throw new Exception(e.getMessage());
        }
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
        String sql = "INSERT INTO " + dbMaker.shipmentsTbl + " (id, Date, departureHour, truckPlateNumber, driverId, sourceId, approved, delivered) VALUES (?,?,?,?,?,?,?,?)";
        try (Connection conn = dbMaker.connect();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, id);
            pstmt.setString(2, new SimpleDateFormat("dd/MM/yyyy").format(date));
            pstmt.setString(3, depHour);
            pstmt.setString(4, truckPlateNumber);
            pstmt.setString(5, driverId);
            pstmt.setInt(6, sourceId);
            pstmt.setBoolean(7, false);
            pstmt.setBoolean(8, false);
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

//    private void _deleteShipmentDriver(Date date, boolean isMorning, String driverId) throws Exception {
//        String sql = "DELETE FROM " + dbMaker.shipmentsTbl + " WHERE driverId = ? AND Date=? AND departureHour";
//        try (Connection conn = dbMaker.connect();
//             PreparedStatement pstmt = conn.prepareStatement(sql)) {
//            pstmt.setString(1, driverId);
//            pstmt.setString(2,new SimpleDateFormat("dd/MM/yyyy").format(date));
//            pstmt.executeUpdate();
//        } catch (Exception e) {
//            throw new Exception(e.getMessage());
//        }
//    }

    private ShipmentDTO selectShipment(Date date, String departureHour, String driverId) throws Exception {
        String sql = "SELECT * FROM " + dbMaker.shipmentsTbl + " WHERE Date='" +
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
                shipment.setApproved(rs.getBoolean(7));
                shipment.setDelivered(rs.getBoolean(8));
                return shipment;
            }
        } catch (Exception e) {
            throw new Exception(e.getMessage());
        }
        throw new Exception("There is no such shipment in the database!");
    }

    public List<ItemSpecs> getReceivedItems(int shipmentId) throws Exception {
        String sql = "SELECT isp.iname, isp.cname, isp.minamount, isp.totalamount, isp.manufacture, isp.companyprice, isp.storeprice, isp.discount, isp.finalprice, si.amount FROM \n" +
                "Shipments AS s JOIN Documents AS d ON s.id=d.shipmentId\n" +
                "JOIN ShippedItems AS si ON d.trackingNumber=si.documentId\n" +
                "JOIN ItemSpecs AS isp ON si.name=isp.iname WHERE s.id=" + shipmentId;
        List<ItemSpecs> itemSpecsList = new LinkedList<>();
        try (Connection conn = dbMaker.connect();
             Statement stmt = conn.createStatement()) {
            ResultSet rs = stmt.executeQuery(sql);
            while (rs.next()) {
                ItemSpecs itemSpecs = new ItemSpecs(rs.getString(1),
                        rs.getString(2),
                        rs.getInt(10),
                        rs.getInt(4),
                        rs.getString(5),
                        rs.getInt(6),
                        rs.getInt(7),
                        rs.getInt(8));

                itemSpecsList.add(itemSpecs);
            }
        } catch (Exception e) {
            throw new Exception(e.getMessage());
        }
        // throw new Exception("There is no such shipment in the database!");
        return itemSpecsList;
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
                shipment.setApproved(rs.getBoolean(7));
                shipment.setDelivered(rs.getBoolean(8));
                shipments.add(shipment);
            }
        } catch (Exception e) {
            throw new Exception(e.getMessage());
        }
        return shipments;
    }

    public List<ShipmentDTO> getNotApprovedShipments() throws Exception {
        String sql = "SELECT * FROM " + dbMaker.shipmentsTbl + " WHERE approved=0";
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
                shipment.setApproved(rs.getBoolean(7));
                shipment.setDelivered(rs.getBoolean(8));
                shipments.add(shipment);
            }
        } catch (Exception e) {
            throw new Exception(e.getMessage());
        }
        return shipments;
    }

    public List<ShipmentDTO> getNotDeliveredShipments() throws Exception {
        String sql = "SELECT * FROM " + dbMaker.shipmentsTbl + " WHERE delivered=0";
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
                shipment.setApproved(rs.getBoolean(7));
                shipment.setDelivered(rs.getBoolean(8));
                shipments.add(shipment);
            }
        } catch (Exception e) {
            throw new Exception(e.getMessage());
        }
        return shipments;
    }

    public List<ShipmentDTO> getApprovedShipments() throws Exception {
        String sql = "SELECT * FROM " + dbMaker.shipmentsTbl + " WHERE approved=1";
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
                shipment.setApproved(rs.getBoolean(7));
                shipment.setDelivered(rs.getBoolean(8));
                shipments.add(shipment);
            }
        } catch (Exception e) {
            throw new Exception(e.getMessage());
        }
        return shipments;
    }

    private ShipmentDTO _selectShipmentE(Date date, String departureHour, String driverId) throws Exception {
        String sql = "SELECT * FROM " + dbMaker.shipmentsTbl + " WHERE Date='" +
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
                shipment.setApproved(rs.getBoolean(7));
                shipment.setDelivered(rs.getBoolean(8));
                return shipment;
            }
        } catch (Exception e) {
            throw new Exception(e.getMessage());
        }
        return null;
    }

    public void approveShipment(Date date, String departureHour, String driverId) throws Exception {
        String sql = "UPDATE Shipments SET approved=1 \n" +
                "WHERE Date='" +
                new SimpleDateFormat("dd/MM/yyyy").format(date)
                + "' AND departureHour='" + departureHour + "' AND driverId='" + driverId + "'";
        try (Connection conn = dbMaker.connect();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.executeUpdate();
        } catch (Exception e) {
            throw new Exception(e.getMessage());
        }
    }

    public void markAsDeliveredShipment(Date date, String departureHour, String driverId) throws Exception {
        String sql = "UPDATE Shipments SET delivered=1 \n" +
                "WHERE Date='" +
                new SimpleDateFormat("dd/MM/yyyy").format(date)
                + "' AND departureHour='" + departureHour + "' AND driverId='" + driverId + "' ";
        try (Connection conn = dbMaker.connect();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.executeUpdate();
        } catch (Exception e) {
            throw new Exception(e.getMessage());
        }
    }

    private boolean shipmentExist(Date date, String departureHour, String driverId) throws Exception {
        ShipmentDTO shipment = _selectShipmentE(date, departureHour, driverId);
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
