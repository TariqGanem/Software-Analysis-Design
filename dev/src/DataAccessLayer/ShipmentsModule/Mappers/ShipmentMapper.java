package DataAccessLayer.Mappers;

import DTOs.LocationDTO;
import DTOs.ShipmentDTO;
import DTOs.TruckDTO;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.Date;

public class ShipmentMapper extends Engine {

//    private static ShipmentMapper instance = null;
//    private IdentityMap memory;
//
//
//    private ShipmentMapper() {
//        memory = IdentityMap.getInstance();
//    }
//
//    public static ShipmentMapper getInstance() {
//        if (instance == null)
//            instance = new ShipmentMapper();
//        return instance;
//    }
//
////    public ShipmentDTO addShipment(Date date, String depHour, String truckPlateNumber, String driverId, int sourceId) throws Exception {
////        ShipmentDTO shipment;
////        for (ShipmentDTO sh : memory.getShipments()) {
////            if (sh.getDate().equals(date)
////                    && sh.getDepartureHour().equals(depHour)
////                    && sh.getDriverId() == driverId)
////                throw new Exception("Shipment already exists!");
////        }
////        shipment = new ShipmentDTO(date, depHour, truckPlateNumber, driverId, sourceId);
////        if (truckExists(plateNumber)) {
////            memory.getTrucks().add(truck);
////            throw new Exception("Truck already exists in the database!");
////        }
////        insertTruck(plateNumber, model, natoWeight, maxWeight, available);
////        memory.getTrucks().add(truck);
////        return truck;
////    }
//
//    public TruckDTO getTruck(String plateNumber) throws Exception {
//        for (TruckDTO t : memory.getTrucks()) {
//            if (t.getTruckPlateNumber().equals(plateNumber))
//                return t;
//        }
//        TruckDTO truck = selectTruck(plateNumber);
//        if (truck != null) {
//            memory.getTrucks().add(truck);
//            return truck;
//        }
//        throw new Exception("There is no such truck in the database!");
//    }
//
//    public TruckDTO updateTruck(String plateNumber, boolean available) throws Exception {
//        TruckDTO truck = getTruck(plateNumber);
//        truck.setAvailable(available);
//        _updateTruck(plateNumber, available);
//        return truck;
//    }
//
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
//
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
//
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
//
//    private void insertTruck(String plateNumber, String model, Double natoWeight, Double maxWeight, boolean available) throws Exception {
//        String sql = "INSERT INTO " + trucksTbl + "(plateNumber, model, natoWeight, maxWeight, available) VALUES (?,?,?,?,?)";
//        try (Connection conn = this.connect();
//             PreparedStatement pstmt = conn.prepareStatement(sql)) {
//            pstmt.setString(1, plateNumber);
//            pstmt.setString(2, model);
//            pstmt.setDouble(3, natoWeight);
//            pstmt.setDouble(4, maxWeight);
//            pstmt.setBoolean(5, available);
//            pstmt.executeUpdate();
//        } catch (Exception e) {
//            throw new Exception(e.getMessage());
//        }
//    }
//
//    private TruckDTO selectTruck(String plateNumber) throws Exception {
//        String sql = "SELECT * FROM " + trucksTbl + " WHERE plateNumber=" + plateNumber;
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
//
//    private boolean truckExists(String plateNumber) throws Exception {
//        TruckDTO truck = selectTruck(plateNumber);
//        if (truck != null)
//            return true;
//        return false;
//    }
}
