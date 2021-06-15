package DataAccessLayer.ShipmentsModule.Mappers;

import DTOPackage.LocationDTO;
import DataAccessLayer.ShipmentsModule.IdentityMap;
import DataAccessLayer.dbMaker;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.LinkedList;
import java.util.List;

public class LocationMapper {
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

    public List<LocationDTO> getAllLocations() throws Exception {
        List<LocationDTO> locations = selectAllLocations();
        memory.setLocations(locations);
        return memory.getLocations();
    }

    public LocationDTO addLocation(int id, String address, String phone, String contactName) throws Exception {
        LocationDTO location;
//        for (LocationDTO loc : memory.getLocations()) {
//            if (loc.getAddress().equals(address) && loc.getPhoneNumber().equals(phone))
//                throw new Exception("Location already exists!");
//        }
        location = new LocationDTO(id, address, phone, contactName);
        if (locationExists(address, phone, contactName)) {
            memory.getLocations().add(location);
            //throw new Exception("Location already exists in the database!");
        } else {
            insertLocation(id, address, phone, contactName);
            memory.getLocations().add(location);
        }

        return location;
    }

    private void insertLocation(int id, String address, String phone, String contactName) throws Exception {
        String sql = "INSERT INTO " + dbMaker.locationsTbl + " (id, address, phone, contactName) VALUES (?,?,?,?)";
        try (Connection conn = dbMaker.connect();
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
        String sql = "SELECT * FROM " + dbMaker.locationsTbl + " WHERE id= " + id;
        try (Connection conn = dbMaker.connect();
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

    private List<LocationDTO> selectAllLocations() throws Exception {
        String sql = "SELECT * FROM " + dbMaker.locationsTbl;
        List<LocationDTO> locations = new LinkedList<>();
        try (Connection conn = dbMaker.connect();
             Statement stmt = conn.createStatement()) {
            ResultSet rs = stmt.executeQuery(sql);
            while (rs.next()) {
                locations.add(new LocationDTO(rs.getInt(1),
                        rs.getString(2),
                        rs.getString(3),
                        rs.getString(4)
                ));
            }
            return locations;
        } catch (Exception e) {
            throw new Exception(e.getMessage());
        }
    }

    public boolean locationExists(String address, String phone, String contactName) throws Exception {
        LocationDTO location = _selectLocationE(address, phone, contactName);
        if (location != null)
            return true;
        return false;
    }

    public LocationDTO _selectLocationE(String address, String phone, String contactName) throws Exception {
        String sql = "SELECT * FROM " + dbMaker.locationsTbl + " WHERE address='" + address + "' AND phone='" + phone + "' AND contactName='" + contactName + "'";
        try (Connection conn = dbMaker.connect();
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

    public int getMaxID() {
        String sql = "SELECT MAX(id) FROM " + dbMaker.locationsTbl;
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
