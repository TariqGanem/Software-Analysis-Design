package DataAccessLayer.Mappers;

import DTOs.DriverDTO;
import DataAccessLayer.IdentityMap;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;

public class DriverMapper extends Engine {

    private static DriverMapper instance = null;
    private IdentityMap memory;


    private DriverMapper() {
        memory = IdentityMap.getInstance();
    }

    public static DriverMapper getInstance() {
        if (instance == null)
            instance = new DriverMapper();
        return instance;
    }

    public DriverDTO addDriver(String id, Double allowedWeight) throws Exception {
        DriverDTO driver;
        for (DriverDTO d : memory.getDrivers()) {
            if (d.getId().equals(id))
                throw new Exception("Driver already exists!");
        }
        driver = new DriverDTO(id, "Yazan", allowedWeight, true); //TODO - Replace when employees db is available with us.
        if (driverExists(id)) {
            memory.getDrivers().add(driver);
            throw new Exception("Driver already exists in the database!");
        }
        insertDriver(id, allowedWeight);
        memory.getDrivers().add(driver);
        return driver;
    }

    public DriverDTO getDriver(String id) throws Exception {
        for (DriverDTO d : memory.getDrivers()) {
            if (d.getId().equals(id))
                return d;
        }
        DriverDTO driver = selectDriver(id);
        if (driver != null) {
            memory.getDrivers().add(driver);
            return driver;
        }
        throw new Exception("There is no such driver in the database!");
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

    private void insertDriver(String id, Double allowedWeight) throws Exception {
        String sql = "INSERT INTO " + driversTbl + "(id, allowedWeight) VALUES (?,?)";
        try (Connection conn = this.connect();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, id);
            pstmt.setDouble(2, allowedWeight);
            pstmt.executeUpdate();
        } catch (Exception e) {
            throw new Exception(e.getMessage());
        }
    }

    private DriverDTO selectDriver(String id) throws Exception {
        String sql = "SELECT * FROM " + driversTbl + " WHERE id=" + id;
        try (Connection conn = this.connect();
             Statement stmt = conn.createStatement()) {
            ResultSet rs = stmt.executeQuery(sql);
            if (rs.next()) {
                return new DriverDTO(rs.getString(1),
                        "Yazan",
                        rs.getDouble(2),
                        true
                );
            }
        } catch (Exception e) {
            throw new Exception(e.getMessage());
        }
        return null;
    }

    private boolean driverExists(String id) throws Exception {
        DriverDTO driver = selectDriver(id);
        if (driver != null)
            return true;
        return false;
    }
}
