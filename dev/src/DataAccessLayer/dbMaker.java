package DataAccessLayer;

import BusinessLayer.ShipmentsModule.Response;
import BusinessLayer.ShipmentsModule.ResponseT;
import org.sqlite.SQLiteConfig;

import java.io.File;
import java.sql.*;

public class dbMaker {

    private static String dbName = "superLee.db";
    public static String path = "jdbc:sqlite:" + dbName;

    //TABLES NAMES
    public static String trucksTbl = "Trucks";
    public static String locationsTbl = "Locations";
    public static String driversTbl = "Drivers";
    public static String documentsTbl = "Documents";
    public static String itemsTbl = "Items";
    public static String shipmentsTbl = "Shipments";
    public static String truckSchedulerTbl = "TruckScheduler";

    public static String employeeTbl = "Employee";
    public static String employeeSkillsTbl = "EmployeeSkills";
    public static String shiftTbl = "Shift";
    public static String shiftPersonnelTbl = "ShiftPersonnel";
    public static String empTimePrefTbl = "EmployeeTimePreferences";
    public static String HRAlertsTbl = "HRAlerts";

    public dbMaker() {
        dbName = "superLee.db";
        path = "jdbc:sqlite:" + dbName;
    }

    public void initialize() {
        if (!new File(dbName).exists()) {
            createNewDatabase();
            createTrucksTbl();
            createDocumentsTbl();
            createDriversTbl();
            createLocationsTbl();
            createItemsTbl();
            createShipmentsTbl();
            createTruckSchedulerTbl();

            createEmployeeTbl();
            createEmpTimePrefTbl();
            createShiftTbl();
            createShiftPersonnelTbl();
            createEmployeeSkillsTbl();
            createHRAlertsTbl();
        }
    }

    private Response createNewDatabase() {
        try {
            Class.forName("org.sqlite.JDBC");
            Connection conn = DriverManager.getConnection(path);
            if (conn != null) {
                DatabaseMetaData meta = conn.getMetaData();
                return new ResponseT<>("A new database has been created.");
            }
            return new ResponseT<>("Connection is null!");
        } catch (SQLException | ClassNotFoundException e) {
            return new Response(e.getMessage());
        }
    }

    public static Connection connect() {
        Connection conn = null;
        try {
            SQLiteConfig config = new SQLiteConfig();
            config.enforceForeignKeys(true);
            conn = DriverManager.getConnection(path, config.toProperties());
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return conn;
    }

    private void createTrucksTbl() {
        String sql = "CREATE TABLE \"Trucks\" (\n" +
                "\t\"plateNumber\"\tTEXT,\n" +
                "\t\"model\"\tTEXT,\n" +
                "\t\"natoWeight\"\tREAL,\n" +
                "\t\"maxWeight\"\tREAL,\n" +
                "\tPRIMARY KEY(\"plateNumber\")\n" +
                ");";
        try {
            Statement stmt = connect().createStatement();
            stmt.execute(sql);
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    private void createDriversTbl() {
        String sql = "CREATE TABLE \"Drivers\" (\n" +
                "\t\"id\"\tTEXT,\n" +
                "\t\"allowedWeight\"\tREAL,\n" +
                "\tPRIMARY KEY(\"id\"),\n" +
                "\tFOREIGN KEY(\"id\") REFERENCES \"Employee\"(\"ID\")\n" +
                ");";
        try {
            Statement stmt = connect().createStatement();
            stmt.execute(sql);
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    private void createItemsTbl() {
        String sql = "CREATE TABLE \"Items\" (\n" +
                "\t\"documentId\"\tINTEGER,\n" +
                "\t\"name\"\tTEXT,\n" +
                "\t\"amount\"\tREAL,\n" +
                "\t\"weight\"\tREAL,\n" +
                "\tFOREIGN KEY(\"documentId\") REFERENCES \"Documents\"(\"trackingNumber\") ON DELETE CASCADE\n" +
                ");";
        try {
            Statement stmt = connect().createStatement();
            stmt.execute(sql);
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    private void createShipmentsTbl() {
        String sql = "CREATE TABLE \"Shipments\" (\n" +
                "\t\"id\"\tINTEGER,\n" +
                "\t\"Date\"\tDate,\n" +
                "\t\"departureHour\"\tTEXT,\n" +
                "\t\"truckPlateNumber\"\tTEXT,\n" +
                "\t\"driverId\"\tINTEGER,\n" +
                "\t\"sourceId\"\tINTEGER,\n" +
                "\tFOREIGN KEY(\"driverId\") REFERENCES \"Drivers\"(\"id\"),\n" +
                "\tFOREIGN KEY(\"sourceId\") REFERENCES \"Locations\"(\"id\"),\n" +
                "\tPRIMARY KEY(\"id\"),\n" +
                "\tFOREIGN KEY(\"truckPlateNumber\") REFERENCES \"Trucks\"(\"plateNumber\")\n" +
                ");";
        try {
            Statement stmt = connect().createStatement();
            stmt.execute(sql);
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    private void createTruckSchedulerTbl() {
        String sql = "CREATE TABLE \"TruckScheduler\" (\n" +
                "\t\"plateNumber\"\tTEXT,\n" +
                "\t\"shipmentDate\"\tDate,\n" +
                "\t\"isMorning\"\tINTEGER,\n" +
                "\tFOREIGN KEY(\"plateNumber\") REFERENCES \"Trucks\"(\"plateNumber\")\n" +
                ");";
        try {
            Statement stmt = connect().createStatement();
            stmt.execute(sql);
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    private void createDocumentsTbl() {
        String sql = "CREATE TABLE \"Documents\" (\n" +
                "\t\"trackingNumber\"\tINTEGER,\n" +
                "\t\"destinationId\"\tINTEGER,\n" +
                "\t\"shipmentId\"\tINTEGER,\n" +
                "\tPRIMARY KEY(\"trackingNumber\"),\n" +
                "\tFOREIGN KEY(\"destinationId\") REFERENCES \"Locations\"(\"id\"),\n" +
                "\tFOREIGN KEY(\"shipmentId\") REFERENCES \"Shipments\"(\"id\") ON DELETE CASCADE\n" +
                ");";
        try {
            Statement stmt = connect().createStatement();
            stmt.execute(sql);
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    private void createLocationsTbl() {
        String sql = "CREATE TABLE \"Locations\" (\n" +
                "\t\"id\"\tINTEGER,\n" +
                "\t\"address\"\tTEXT,\n" +
                "\t\"phone\"\tTEXT,\n" +
                "\t\"contactName\"\tTEXT,\n" +
                "\tPRIMARY KEY(\"id\")\n" +
                ");";
        try {
            Statement stmt = connect().createStatement();
            stmt.execute(sql);
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    private void createEmployeeTbl() {
        String sql = "CREATE TABLE \"Employee\" (\n" +
                "\t\"ID\"\tTEXT,\n" +
                "\t\"name\"\tTEXT,\n" +
                "\t\"bankID\"\tINTEGER,\n" +
                "\t\"branchID\"\tINTEGER,\n" +
                "\t\"accountNumber\"\tINTEGER,\n" +
                "\t\"salary\"\tREAL,\n" +
                "\t\"startDate\"\tTEXT,\n" +
                "\t\"trustFund\"\tTEXT,\n" +
                "\t\"freeDays\"\tINTEGER,\n" +
                "\t\"sickDays\"\tINTEGER,\n" +
                "\tPRIMARY KEY(\"ID\")\n" +
                ");";
        try {
            Statement stmt = connect().createStatement();
            stmt.execute(sql);
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    private void createEmployeeSkillsTbl() {
        String sql = "CREATE TABLE \"EmployeeSkills\" (\n" +
                "\t\"ID\"\tTEXT,\n" +
                "\t\"skill\"\tTEXT,\n" +
                "\tPRIMARY KEY(\"ID\",\"skill\")\n" +
                ");";
        try {
            Statement stmt = connect().createStatement();
            stmt.execute(sql);
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    private void createEmpTimePrefTbl() {
        String sql = "CREATE TABLE \"EmployeeTimePreferences\" (\n" +
                "\t\"ID\"\tTEXT,\n" +
                "\t\"dayIndex\"\tINTEGER,\n" +
                "\t\"isMorning\"\tTEXT,\n" +
                "\t\"preference\"\tTEXT,\n" +
                "\tPRIMARY KEY(\"ID\",\"dayIndex\",\"isMorning\")\n" +
                ");";
        try {
            Statement stmt = connect().createStatement();
            stmt.execute(sql);
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    private void createShiftTbl() {
        String sql = "CREATE TABLE \"Shift\" (\n" +
                "\t\"date\"\tTEXT,\n" +
                "\t\"isMorning\"\tTEXT,\n" +
                "\t\"role\"\tTEXT,\n" +
                "\t\"ID\"\tTEXT,\n" +
                "\tPRIMARY KEY(\"date\",\"isMorning\",\"ID\"),\n" +
                "\tFOREIGN KEY(\"ID\") REFERENCES \"Employee\"(\"ID\") ON UPDATE CASCADE\n" +
                ");";
        try {
            Statement stmt = connect().createStatement();
            stmt.execute(sql);
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    private void createShiftPersonnelTbl() {
        String sql = "CREATE TABLE \"ShiftPersonnel\" (\n" +
                "\t\"weekIndex\"\tINTEGER,\n" +
                "\t\"role\"\tTEXT,\n" +
                "\t\"amount\"\tINTEGER,\n" +
                "\tPRIMARY KEY(\"weekIndex\",\"role\")\n" +
                ");";
        try {
            Statement stmt = connect().createStatement();
            stmt.execute(sql);
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    private void createHRAlertsTbl() {
        String sql = "CREATE TABLE \"HRAlerts\" ( " +
                    "\"orderDate\" INTEGER UNIQUE" +
                    ");";
        try {
            Statement stmt = connect().createStatement();
            stmt.execute(sql);
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }


}
