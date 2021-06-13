package DataAccessLayer.ShipmentsModule.Mappers;

import DTOPackage.DocumentDTO;
import DTOPackage.ShippedItemDTO;
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

    public DocumentDTO addDocument(int tracking, int destinationId, int shipmentId, List<ShippedItemDTO> items) throws Exception {
        DocumentDTO document;
        for (DocumentDTO d : memory.getDocuments()) {
            if (d.getTrackingNumber() == tracking)
                throw new Exception("Document already exists!");
        }
        document = new DocumentDTO(tracking, items, LocationMapper.getInstance().getLocation(destinationId));
        if (documentExists(tracking)) {
            memory.getDocuments().add(document);
            throw new Exception("Document already exists in the database!");
        }
        insertDocument(tracking, destinationId, shipmentId, items);
        memory.getDocuments().add(document);
        return document;
    }

    public List<DocumentDTO> getShipmentDocuments(int shipmentId) throws Exception {
        List<DocumentDTO> docs = selectShipmentDocuments(shipmentId);
        if (docs != null) {
            for (DocumentDTO doc : docs)
                memory.getDocuments().add(doc);
        }
        return docs;
    }

    private void insertDocument(int tracking, int destinationId, int shipmentId, List<ShippedItemDTO> items) throws Exception {
        String sql = "INSERT INTO " + dbMaker.documentsTbl + "(trackingNumber, destinationId, shipmentId) VALUES (?,?,?)";
        try (Connection conn = dbMaker.connect();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, tracking);
            pstmt.setInt(2, destinationId);
            pstmt.setInt(3, shipmentId);
            pstmt.executeUpdate();
            for (ShippedItemDTO item : items) {
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
                        LocationMapper.getInstance().getLocation(rs.getInt(2))
                );
            }
        } catch (Exception e) {
            throw new Exception(e.getMessage());
        }
        return null;
    }

    private List<DocumentDTO> selectShipmentDocuments(int shipmentId) throws Exception {
        String sql = "SELECT * FROM " + dbMaker.documentsTbl + " WHERE shipmentId=" + shipmentId;
        List<DocumentDTO> docs = new LinkedList<>();
        try (Connection conn = dbMaker.connect();
             Statement stmt = conn.createStatement()) {
            ResultSet rs = stmt.executeQuery(sql);
            while (rs.next()) {
                docs.add(new DocumentDTO(rs.getInt(1),
                        selectItems(rs.getInt(1)),
                        LocationMapper.getInstance().getLocation(rs.getInt(2))
                ));
            }
        } catch (Exception e) {
            throw new Exception(e.getMessage());
        }
        return docs;
    }

    private List<ShippedItemDTO> selectItems(int tracking) throws Exception {
        List<ShippedItemDTO> items = new LinkedList<>();
        String sql = "SELECT * FROM " + dbMaker.itemsTbl + " WHERE documentId=" + tracking;
        try (Connection conn = dbMaker.connect();
             Statement stmt = conn.createStatement()) {
            ResultSet rs = stmt.executeQuery(sql);
            while (rs.next()) {
                ShippedItemDTO item = new ShippedItemDTO(
                        rs.getString(2),
                        rs.getDouble(3),
                        rs.getDouble(4)
                );
                item.setDocumentId(rs.getInt(1));
                items.add(item);
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

    public int getMaxID() {
        String sql = "SELECT MAX(trackingNumber) FROM " + dbMaker.documentsTbl;
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
