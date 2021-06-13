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

//    public boolean isAvailableTruck(String plateNumber, double weight, Date date, boolean isMorning) throws Exception {
//        List<TruckDTO> trucks = getAvailableTruck(weight, date, isMorning);
//        for (TruckDTO truck : trucks) {
//            if (truck.getTruckPlateNumber().equals(plateNumber))
//                return true;
//        }
//        return false;
//    }


    public TruckDTO getAvailableTruck(double weight, Date date, boolean isMorning) throws Exception {
        TruckDTO truck = _getAvailableTruck(weight, date, isMorning);
        return truck;
    }

    public void insertTruckScheduler(String plateNumber, Date date, boolean isMorning, String driverId) throws Exception {
        String sql = "INSERT INTO " + dbMaker.truckSchedulerTbl + " (plateNumber, shipmentDate, isMorning, driver) VALUES (?,?,?, ?)";
        try (Connection conn = dbMaker.connect();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, plateNumber);
            pstmt.setString(2, new SimpleDateFormat("dd/MM/yyyy").format(date));
            pstmt.setBoolean(3, isMorning);
            pstmt.setString(4, driverId);
            pstmt.executeUpdate();
        } catch (Exception e) {
            throw new Exception(e.getMessage());
        }
    }

    private TruckDTO _getAvailableTruck(double weight, Date date, boolean isMorning) throws Exception {
        TruckDTO truck = null;
        String sql = "SELECT * FROM(\n" +
                "SELECT plateNumber, model, natoWeight, maxWeight FROM Trucks\n" +
                "EXCEPT\n" +
                "SELECT DISTINCT(t.plateNumber), t.model, t.natoWeight, t.maxWeight \n" +
                "FROM TruckScheduler AS ts JOIN Trucks AS t ON ts.plateNumber=t.plateNumber /*Trucks not yet scheduled*/\n" +
                "\n" +
                "UNION\n" +
                "\n" +
                "SELECT DISTINCT(t.plateNumber), t.model, t.natoWeight, t.maxWeight \n" +
                "FROM TruckScheduler AS ts JOIN Trucks AS t ON ts.plateNumber=t.plateNumber /*All scheduled trucks*/\n" +
                "\n" +
                "EXCEPT\n" +
                "\n" +
                "SELECT DISTINCT(t.plateNumber), t.model, t.natoWeight, t.maxWeight \n" +
                "FROM TruckScheduler AS ts JOIN Trucks AS t ON ts.plateNumber=t.plateNumber\n" +
                "WHERE (ts.shipmentDate='" + new SimpleDateFormat("dd/MM/yyyy").format(date) +
                "' AND ts.isMorning=" + (isMorning ? 1 : 0) + ") /*Scheduled in the same date and shift*/\n" +
                ")\n" +
                "WHERE maxWeight>=" + weight + " ORDER BY (natoWeight+maxWeight) ASC LIMIT 1";
        try (Connection conn = dbMaker.connect();
             Statement stmt = conn.createStatement()) {
            ResultSet rs = stmt.executeQuery(sql);
            if (rs.next()) {
                truck = new TruckDTO(rs.getString(1),
                        rs.getString(2),
                        rs.getDouble(3),
                        rs.getDouble(4));
            }
        } catch (Exception e) {
            throw new Exception(e.getMessage());
        }
        return truck;
    }

    private void insertTruck(String plateNumber, String model, Double natoWeight, Double maxWeight) throws Exception {
        String sql = "INSERT INTO " + dbMaker.trucksTbl + " (plateNumber, model, natoWeight, maxWeight) VALUES (?,?,?,?)";
        try (Connection conn = dbMaker.connect();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, plateNumber);
            pstmt.setString(2, model);
            pstmt.setDouble(3, natoWeight);
            pstmt.setDouble(4, maxWeight);
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
