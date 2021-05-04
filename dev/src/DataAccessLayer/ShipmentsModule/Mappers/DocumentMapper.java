package DataAccessLayer.ShipmentsModule.Mappers;

import DTOPackage.DocumentDTO;
import DTOPackage.ItemDTO;
import DataAccessLayer.ShipmentsModule.IdentityMap;
import DataAccessLayer.dbMaker;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.LinkedList;
import java.util.List;

public class DocumentMapper {
    private static DocumentMapper instance = null;
    private IdentityMap memory;


    private DocumentMapper() {
        memory = IdentityMap.getInstance();
    }

    public static DocumentMapper getInstance() {
        if (instance == null)
            instance = new DocumentMapper();
        return instance;
    }

    public DocumentDTO addDocument(int tracking, int destinationId, int shipmentId, List<ItemDTO> items) throws Exception {
        DocumentDTO document;
        for (DocumentDTO d : memory.getDocuments()) {
            if (d.getTrackingNumber() == tracking)
                throw new Exception("Document already exists!");
        }
        document = new DocumentDTO(tracking, items, null, 0); //TODO finding destination
        if (documentExists(tracking)) {
            memory.getDocuments().add(document);
            throw new Exception("Document already exists in the database!");
        }
        insertDocument(tracking, destinationId, shipmentId, items);
        memory.getDocuments().add(document);
        return document;
    }

    public DocumentDTO getDocument(int tracking) throws Exception {
        for (DocumentDTO d : memory.getDocuments()) {
            if (d.getTrackingNumber() == tracking)
                return d;
        }
        DocumentDTO document = selectDocument(tracking);
        if (document != null) {
            memory.getDocuments().add(document);
            return document;
        }
        throw new Exception("There is no such document in the database!");
    }

//    public TruckDTO updateTruck(String plateNumber, boolean available) throws Exception {
//        TruckDTO truck = getTruck(plateNumber);
//        truck.setAvailable(available);
//        _updateTruck(plateNumber, available);
//        return truck;
//    }

//    public TruckDTO getAvailableTruck(double weight) throws Exception {
//        for (TruckDTO t : memory.getTrucks()) {
//            if (t.isAvailable() && t.getMaxWeight() >= weight)
//                return t;
//        }
//        TruckDTO truck = _getAvailableTruck(weight);
//        if (truck != null) {
//            memory.getTrucks().add(truck);
//            return truck;
//        }
//        throw new Exception("There is no such available truck in the database!");
//    }

//    private TruckDTO _getAvailableTruck(double weight) throws Exception {
//        String sql = "SELECT * FROM " + trucksTbl + " WHERE maxWeight>=" + weight + " AND available=TRUE";
//        try (Connection conn = this.connect();
//             Statement stmt = conn.createStatement()) {
//            ResultSet rs = stmt.executeQuery(sql);
//            if (rs.next()) {
//                return new TruckDTO(rs.getString(1),
//                        rs.getString(2),
//                        rs.getDouble(3),
//                        rs.getDouble(4),
//                        rs.getBoolean(5)
//                );
//            }
//        } catch (Exception e) {
//            throw new Exception(e.getMessage());
//        }
//        return null;
//    }

//    private void _updateTruck(String plateNumber, boolean available) throws Exception {
//        String sql = "UPDATE " + trucksTbl + " SET available = ? "
//                + "WHERE plateNumber = ?";
//        try (Connection conn = this.connect();
//             PreparedStatement pStmt = conn.prepareStatement(sql)) {
//            pStmt.setBoolean(1, available);
//            pStmt.setString(2, plateNumber);
//            pStmt.executeUpdate();
//        } catch (Exception e) {
//            throw new Exception(e.getMessage());
//        }
//    }

    private void insertDocument(int tracking, int destinationId, int shipmentId, List<ItemDTO> items) throws Exception {
        String sql = "INSERT INTO " + dbMaker.documentsTbl + "(trackingNumber, destinationId, shipmentId) VALUES (?,?,?)";
        try (Connection conn = dbMaker.connect();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, tracking);
            pstmt.setInt(2, destinationId);
            pstmt.setInt(3, shipmentId);
            pstmt.executeUpdate();
            for (ItemDTO item : items) {
                insertItem(tracking, item.getName(), item.getAmount(), item.getWeight());
            }
        } catch (Exception e) {
            throw new Exception(e.getMessage());
        }
    }

    private void insertItem(int tracking, String name, double amount, double weight) throws Exception {
        String sql = "INSERT INTO " + dbMaker.itemsTbl + "(documentId, name, amount, weight) VALUES (?,?,?,?)";
        try (Connection conn = dbMaker.connect();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, tracking);
            pstmt.setString(2, name);
            pstmt.setDouble(3, amount);
            pstmt.setDouble(4, weight);
            pstmt.executeUpdate();
        } catch (Exception e) {
            throw new Exception(e.getMessage());
        }
    }

    private DocumentDTO selectDocument(int tracking) throws Exception {
        String sql = "SELECT * FROM " + dbMaker.documentsTbl + " WHERE trackingNumber=" + tracking;
        try (Connection conn = dbMaker.connect();
             Statement stmt = conn.createStatement()) {
            ResultSet rs = stmt.executeQuery(sql);
            if (rs.next()) {
                return new DocumentDTO(rs.getInt(1),
                        selectItems(tracking),
                        null, //TODO - locationID
                        rs.getInt(3)
                );
            }
        } catch (Exception e) {
            throw new Exception(e.getMessage());
        }
        return null;
    }

    private List<ItemDTO> selectItems(int tracking) throws Exception {
        List<ItemDTO> items = new LinkedList<>();
        String sql = "SELECT * FROM " + dbMaker.itemsTbl + " WHERE documentId=" + tracking;
        try (Connection conn = dbMaker.connect();
             Statement stmt = conn.createStatement()) {
            ResultSet rs = stmt.executeQuery(sql);
            while (rs.next()) {
                items.add(new ItemDTO(rs.getInt(1),
                        rs.getString(2),
                        rs.getDouble(3),
                        rs.getDouble(4)
                ));
            }
        } catch (Exception e) {
            throw new Exception(e.getMessage());
        }
        return items;
    }

    private boolean documentExists(int tracking) throws Exception {
        DocumentDTO document = selectDocument(tracking);
        if (document != null)
            return true;
        return false;
    }
}
