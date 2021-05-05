package DataAccessLayer.ShipmentsModule.Mappers;

import DTOPackage.TruckDTO;
import DataAccessLayer.ShipmentsModule.IdentityMap;
import DataAccessLayer.dbMaker;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;

public class TruckMapper {
    private static TruckMapper instance = null;
    private IdentityMap memory;


    private TruckMapper() {
        memory = IdentityMap.getInstance();
    }

    public static TruckMapper getInstance() {
        if (instance == null)
            instance = new TruckMapper();
        return instance;
    }

    public TruckDTO addTruck(String plateNumber, String model, Double natoWeight, Double maxWeight, boolean available) throws Exception {
        TruckDTO truck;
        for (TruckDTO t : memory.getTrucks()) {
            if (t.getTruckPlateNumber().equals(plateNumber))
                throw new Exception("Truck already exists!");
        }
        truck = new TruckDTO(plateNumber, model, natoWeight, maxWeight, available);
        if (truckExists(plateNumber)) {
            memory.getTrucks().add(truck);
            throw new Exception("Truck already exists in the database!");
        }
        insertTruck(plateNumber, model, natoWeight, maxWeight, available);
        memory.getTrucks().add(truck);
        return truck;
    }

    public TruckDTO getTruck(String plateNumber) throws Exception {
        for (TruckDTO t : memory.getTrucks()) {
            if (t.getTruckPlateNumber().equals(plateNumber))
                return t;
        }
        TruckDTO truck = selectTruck(plateNumber);
        if (truck != null) {
            memory.getTrucks().add(truck);
            return truck;
        }
        throw new Exception("There is no such truck in the database!");
    }

    public TruckDTO updateTruck(String plateNumber, boolean available) throws Exception {
        TruckDTO truck = getTruck(plateNumber);
        truck.setAvailable(available);
        _updateTruck(plateNumber, available);
        return truck;
    }

    public TruckDTO getAvailableTruck(double weight) throws Exception {
        for (TruckDTO t : memory.getTrucks()) {
            if (t.isAvailable() && t.getMaxWeight() >= weight)
                return t;
        }
        TruckDTO truck = _getAvailableTruck(weight);
        if (truck != null) {
            memory.getTrucks().add(truck);
            return truck;
        }
        throw new Exception("There is no such available truck in the database!");
    }

    private TruckDTO _getAvailableTruck(double weight) throws Exception {
        String sql = "SELECT * FROM " + dbMaker.trucksTbl + " WHERE maxWeight>=" + weight + " AND available=TRUE";
        try (Connection conn = dbMaker.connect();
             Statement stmt = conn.createStatement()) {
            ResultSet rs = stmt.executeQuery(sql);
            if (rs.next()) {
                return new TruckDTO(rs.getString(1),
                        rs.getString(2),
                        rs.getDouble(3),
                        rs.getDouble(4),
                        rs.getBoolean(5)
                );
            }
        } catch (Exception e) {
            throw new Exception(e.getMessage());
        }
        return null;
    }

    private void _updateTruck(String plateNumber, boolean available) throws Exception {
        String sql = "UPDATE " + dbMaker.trucksTbl + " SET available = ? "
                + "WHERE plateNumber = ?";
        try (Connection conn = dbMaker.connect();
             PreparedStatement pStmt = conn.prepareStatement(sql)) {
            pStmt.setBoolean(1, available);
            pStmt.setString(2, plateNumber);
            pStmt.executeUpdate();
        } catch (Exception e) {
            throw new Exception(e.getMessage());
        }
    }

    private void insertTruck(String plateNumber, String model, Double natoWeight, Double maxWeight, boolean available) throws Exception {
        String sql = "INSERT INTO " + dbMaker.trucksTbl + "(plateNumber, model, natoWeight, maxWeight, available) VALUES (?,?,?,?,?)";
        try (Connection conn = dbMaker.connect();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, plateNumber);
            pstmt.setString(2, model);
            pstmt.setDouble(3, natoWeight);
            pstmt.setDouble(4, maxWeight);
            pstmt.setBoolean(5, available);
            pstmt.executeUpdate();
        } catch (Exception e) {
            throw new Exception(e.getMessage());
        }
    }

    private TruckDTO selectTruck(String plateNumber) throws Exception {
        String sql = "SELECT * FROM " + dbMaker.trucksTbl + " WHERE plateNumber=" + plateNumber;
        try (Connection conn = dbMaker.connect();
             Statement stmt = conn.createStatement()) {
            ResultSet rs = stmt.executeQuery(sql);
            if (rs.next()) {
                return new TruckDTO(rs.getString(1),
                        rs.getString(2),
                        rs.getDouble(3),
                        rs.getDouble(4),
                        rs.getBoolean(5)
                );
            }
        } catch (Exception e) {
            throw new Exception(e.getMessage());
        }
        return null;
    }

    private boolean truckExists(String plateNumber) throws Exception {
        TruckDTO truck = selectTruck(plateNumber);
        if (truck != null)
            return true;
        return false;
    }
}
