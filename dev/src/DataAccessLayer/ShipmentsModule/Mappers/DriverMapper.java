package DataAccessLayer.ShipmentsModule.Mappers;

import DTOPackage.DriverDTO;
import DataAccessLayer.ShipmentsModule.IdentityMap;
import DataAccessLayer.dbMaker;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.LinkedList;
import java.util.List;

public class DriverMapper {

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

    public DriverDTO addDriver(String id, Double allowedWeight, boolean available) throws Exception {
        DriverDTO driver;
        for (DriverDTO d : memory.getDrivers()) {
            if (d.getId().equals(id))
                throw new Exception("Driver already exists!");
        }
        String driverName = getDriverName(id);
        if (getDriverName(id).equals(null))
            throw new Exception("No such driver in employees");
        driver = new DriverDTO(id, driverName, allowedWeight, true);
        if (driverExists(id)) {
            memory.getDrivers().add(driver);
            throw new Exception("Driver already exists in the database!");
        }
        insertDriver(id, allowedWeight, available);
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

    public List<DriverDTO> getAllDrivers() throws Exception {
        List<DriverDTO> drivers = selectAllDrivers();
        memory.setDrivers(drivers);
        return memory.getDrivers();
    }

    public DriverDTO updateDriver(String id, boolean available) throws Exception {
        DriverDTO driver = getDriver(id);
        driver.setAvailable(available);
        _updateDriver(id, available);
        return driver;
    }

    private void insertDriver(String id, Double allowedWeight, boolean available) throws Exception {
        String sql = "INSERT INTO " + dbMaker.driversTbl + "(id, allowedWeight) VALUES (?,?,?)";
        try (Connection conn = dbMaker.connect();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, id);
            pstmt.setDouble(2, allowedWeight);
            pstmt.setBoolean(3, available);
            pstmt.executeUpdate();
        } catch (Exception e) {
            throw new Exception(e.getMessage());
        }
    }

    private DriverDTO selectDriver(String id) throws Exception {
        String sql = "SELECT * FROM " + dbMaker.driversTbl + " WHERE id=" + id;
        try (Connection conn = dbMaker.connect();
             Statement stmt = conn.createStatement()) {
            ResultSet rs = stmt.executeQuery(sql);
            if (rs.next()) {
                String driverName = getDriverName(id);
                if (getDriverName(id).equals(null))
                    throw new Exception("No such driver in employees");
                return new DriverDTO(rs.getString(1),
                        driverName,
                        rs.getDouble(2),
                        rs.getBoolean(3)
                );
            }
        } catch (Exception e) {
            throw new Exception(e.getMessage());
        }
        return null;
    }

    private List<DriverDTO> selectAllDrivers() throws Exception {
        String sql = "SELECT * FROM " + dbMaker.driversTbl;
        List<DriverDTO> drivers = new LinkedList<>();
        try (Connection conn = dbMaker.connect();
             Statement stmt = conn.createStatement()) {
            ResultSet rs = stmt.executeQuery(sql);
            while (rs.next()) {
                String driverName = getDriverName(rs.getString(1));
                drivers.add(new DriverDTO(rs.getString(1),
                        driverName,
                        rs.getDouble(2),
                        rs.getBoolean(3)
                ));
            }
        } catch (Exception e) {
            throw new Exception(e.getMessage());
        }
        return null;
    }

    private void _updateDriver(String id, boolean available) throws Exception {
        String sql = "UPDATE " + dbMaker.driversTbl + " SET available = ? ";
        try (Connection conn = dbMaker.connect();
             PreparedStatement pStmt = conn.prepareStatement(sql)) {
            pStmt.setBoolean(1, available);
            pStmt.executeUpdate();
        } catch (Exception e) {
            throw new Exception(e.getMessage());
        }
    }

    private String getDriverName(String id) throws Exception {
        String sql = "SELECT name FROM " + dbMaker.employeeTbl + " WHERE ID=" + id;
        try (Connection conn = dbMaker.connect();
             Statement stmt = conn.createStatement()) {
            ResultSet rs = stmt.executeQuery(sql);
            if (rs.next()) {
                return rs.getString(1);
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
