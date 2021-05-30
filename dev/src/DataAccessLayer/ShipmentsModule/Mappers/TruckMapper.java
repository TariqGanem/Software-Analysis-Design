package DataAccessLayer.ShipmentsModule.Mappers;

import DTOPackage.TruckDTO;
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

    public TruckDTO addTruck(String plateNumber, String model, Double natoWeight, Double maxWeight) throws Exception {
        TruckDTO truck;
        for (TruckDTO t : memory.getTrucks()) {
            if (t.getTruckPlateNumber().equals(plateNumber))
                throw new Exception("Truck already exists!");
        }
        truck = new TruckDTO(plateNumber, model, natoWeight, maxWeight);
        if (truckExists(plateNumber)) {
            memory.getTrucks().add(truck);
            throw new Exception("Truck already exists in the database!");
        }
        insertTruck(plateNumber, model, natoWeight, maxWeight);
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

    public List<TruckDTO> getAllTrucks() throws Exception {
        List<TruckDTO> trucks = selectAllTrucks();
        memory.setTrucks(trucks);
        return memory.getTrucks();
    }

    public boolean isAvailableTruck(String plateNumber, double weight, Date date, boolean isMorning) throws Exception {
        List<TruckDTO> trucks = getAvailableTrucks(weight, date, isMorning);
        for (TruckDTO truck : trucks) {
            if (truck.getTruckPlateNumber().equals(plateNumber))
                return true;
        }
        return false;
    }


    public List<TruckDTO> getAvailableTrucks(double weight, Date date, boolean isMorning) throws Exception {
        List<TruckDTO> trucks = _getAvailableTrucks(weight, date, isMorning);
        for (TruckDTO truck : trucks)
            memory.getTrucks().add(truck);
        if (trucks.isEmpty())
            throw new Exception("There is no such available truck in the database!");
        return trucks;
    }

    public void insertTruckScheduler(String plateNumber, Date date, boolean isMorning) throws Exception {
        String sql = "INSERT INTO " + dbMaker.truckSchedulerTbl + " (plateNumber, shipmentDate, isMorning) VALUES (?,?,?)";
        try (Connection conn = dbMaker.connect();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, plateNumber);
            pstmt.setString(2, new SimpleDateFormat("dd/MM/yyyy").format(date));
            pstmt.setBoolean(3, isMorning);
            pstmt.executeUpdate();
        } catch (Exception e) {
            throw new Exception(e.getMessage());
        }
    }

    private List<TruckDTO> _getAvailableTrucks(double weight, Date date, boolean isMorning) throws Exception {
        List<TruckDTO> trucks = new LinkedList<>();
        String sql = "SELECT * FROM\n" +
                "((SELECT t.plateNumber, t.model, t.natoWeight, t.maxWeight FROM Trucks as t WHERE t.maxWeight>= "
                + weight + " EXCEPT " + "SELECT t.plateNumber, t.model, t.natoWeight, t.maxWeight FROM TruckScheduler AS ts\n" +
                "JOIN Trucks as t ON ts.plateNumber=t.plateNumber) as notyetscheduled)\n" +
                "UNION\n" + "SELECT t.plateNumber, t.model, t.natoWeight, t.maxWeight FROM TruckScheduler AS ts\n" +
                "JOIN Trucks as t ON ts.plateNumber=t.plateNumber WHERE t.maxWeight>= " + weight + " AND ts.shipmentDate != '" +
                new SimpleDateFormat("dd/MM/yyyy").format(date) + "' OR ts.isMorning!=" + (isMorning ? 1 : 0);
        try (Connection conn = dbMaker.connect();
             Statement stmt = conn.createStatement()) {
            ResultSet rs = stmt.executeQuery(sql);
            while (rs.next()) {
                trucks.add(new TruckDTO(rs.getString(1),
                        rs.getString(2),
                        rs.getDouble(3),
                        rs.getDouble(4)
                ));
            }
        } catch (Exception e) {
            throw new Exception(e.getMessage());
        }
        return trucks;
    }

    private void insertTruck(String plateNumber, String model, Double natoWeight, Double maxWeight) throws Exception {
        String sql = "INSERT INTO " + dbMaker.trucksTbl + " (plateNumber, model, natoWeight, maxWeight) VALUES (?,?,?,?)";
        try (Connection conn = dbMaker.connect();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, plateNumber);
            pstmt.setString(2, model);
            pstmt.setDouble(3, natoWeight);
            pstmt.setDouble(4, maxWeight);
            ;
            pstmt.executeUpdate();
        } catch (Exception e) {
            throw new Exception(e.getMessage());
        }
    }

    private TruckDTO selectTruck(String plateNumber) throws Exception {
        String sql = "SELECT * FROM " + dbMaker.trucksTbl + " WHERE plateNumber= '" + plateNumber + "'";
        try (Connection conn = dbMaker.connect();
             Statement stmt = conn.createStatement()) {
            ResultSet rs = stmt.executeQuery(sql);
            if (rs.next()) {
                return new TruckDTO(rs.getString(1),
                        rs.getString(2),
                        rs.getDouble(3),
                        rs.getDouble(4)
                );
            }
        } catch (Exception e) {
            throw new Exception(e.getMessage());
        }
        return null;
    }

    private List<TruckDTO> selectAllTrucks() throws Exception {
        String sql = "SELECT * FROM " + dbMaker.trucksTbl;
        List<TruckDTO> trucks = new LinkedList<>();
        try (Connection conn = dbMaker.connect();
             Statement stmt = conn.createStatement()) {
            ResultSet rs = stmt.executeQuery(sql);
            while (rs.next()) {
                trucks.add(new TruckDTO(rs.getString(1),
                        rs.getString(2),
                        rs.getDouble(3),
                        rs.getDouble(4)
                ));
            }
            return trucks;
        } catch (Exception e) {
            throw new Exception(e.getMessage());
        }
    }

    private boolean truckExists(String plateNumber) throws Exception {
        TruckDTO truck = selectTruck(plateNumber);
        if (truck != null)
            return true;
        return false;
    }
}
