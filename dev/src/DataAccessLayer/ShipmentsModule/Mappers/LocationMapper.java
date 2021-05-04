package DataAccessLayer.Mappers;

import DTOs.LocationDTO;
import DataAccessLayer.IdentityMap;

import java.sql.*;

public class LocationMapper extends Engine {
    private static LocationMapper instance = null;
    private IdentityMap memory;


    private LocationMapper() {
        memory = IdentityMap.getInstance();
    }

    public static LocationMapper getInstance() {
        if (instance == null)
            instance = new LocationMapper();
        return instance;
    }

    public LocationDTO getLocation(int id) throws Exception {
        for (LocationDTO loc : memory.getLocations()) {
            if (loc.getId() == id)
                return loc;
        }
        LocationDTO location = selectLocation(id);
        if (location != null) {
            memory.getLocations().add(location);
            return location;
        }
        throw new Exception("There is no such location in the database!");
    }

    public LocationDTO addLocation(int id, String address, String phone, String contactName) throws Exception {
        LocationDTO location;
        for (LocationDTO loc : memory.getLocations()) {
            if (loc.getId() == id)
                throw new Exception("Location already exists!");
        }
        location = new LocationDTO(id, address, phone, contactName);
        if (locationExists(id)) {
            memory.getLocations().add(location);
            throw new Exception("Location already exists in the database!");
        }
        insertLocation(id, address, phone, contactName);
        memory.getLocations().add(location);
        return location;
    }

    private void insertLocation(int id, String address, String phone, String contactName) throws Exception {
        String sql = "INSERT INTO " + locationsTbl + " (id, address, phone, contactName) VALUES (?,?,?,?)";
        try (Connection conn = this.connect();
             PreparedStatement pStmt = conn.prepareStatement(sql)) {
            pStmt.setInt(1, id);
            pStmt.setString(2, address);
            pStmt.setString(3, phone);
            pStmt.setString(4, contactName);
            pStmt.executeUpdate();
        } catch (Exception e) {
            throw new Exception(e.getMessage());
        }
    }

    private LocationDTO selectLocation(int id) throws Exception {
        String sql = "SELECT * FROM " + locationsTbl + " WHERE id=" + id;
        try (Connection conn = this.connect();
             Statement stmt = conn.createStatement()) {
            ResultSet rs = stmt.executeQuery(sql);
            if (rs.next()) {
                return new LocationDTO(rs.getInt(1),
                        rs.getString(2),
                        rs.getString(3),
                        rs.getString(4)
                );
            }
        } catch (Exception e) {
            throw new Exception(e.getMessage());
        }
        return null;
    }

    private boolean locationExists(int id) throws Exception {
        LocationDTO location = selectLocation(id);
        if (location != null)
            return true;
        return false;
    }
}
