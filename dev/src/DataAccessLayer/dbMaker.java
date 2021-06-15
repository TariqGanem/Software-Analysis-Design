package DataAccessLayer;

import BusinessLayer.Response;
import BusinessLayer.ResponseT;
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
    public static String itemsTbl = "ShippedItems";
    public static String shipmentsTbl = "Shipments";
    public static String truckSchedulerTbl = "TruckScheduler";

    public static String employeeTbl = "Employee";
    public static String employeeSkillsTbl = "EmployeeSkills";
    public static String shiftTbl = "Shift";
    public static String shiftPersonnelTbl = "ShiftPersonnel";
    public static String empTimePrefTbl = "EmployeeTimePreferences";
    public static String HRAlertsTbl = "HRAlerts";

    public static String categorytbl = "Category";
    public static String itemspecstbl = "ItemSpecs";
    public static String itemstbl = "Items";
    //    public static String itemsStbl = "ItemsS";
    public static String reportstbl = "Reports";
    public static String defectstbl = "Defects";

    public dbMaker() {
        dbName = "superLee.db";
        path = "jdbc:sqlite:" + dbName;
    }

    public void initialize() {
        if (!new File(dbName).exists()) {
            createNewDatabase();

            //Shipments Tables
            createTrucksTbl();
            createDocumentsTbl();
            createDriversTbl();
            createLocationsTbl();
            createShippedItemsTbl();
            createShipmentsTbl();
            createTruckSchedulerTbl();

            //Employees Tables
            createEmployeeTbl();
            createEmpTimePrefTbl();
            createShiftTbl();
            createShiftPersonnelTbl();
            createEmployeeSkillsTbl();
            createHRAlertsTbl();

            //Stock Tables
            createCategoryTbl();
            createItemSpecsTbl();
            createItemsTbl();
            createDefectsTbl();
            createReportsTbl();

            //Suppliers Tables
            createSuppliersTables1();
            createSuppliersTables2();
            createSuppliersTables3();
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

    private void createSuppliersTables1() {
        try {
            Statement stmt = connect().createStatement();
            String sql = "CREATE TABLE IF NOT EXISTS contracts" +
                    "(companyId INT PRIMARY KEY    NOT NULL," +
                    "selfPickup           INT     NOT NULL," +
                    "FOREIGN KEY(companyId) REFERENCES suppliers(companyId))";
            stmt.executeUpdate(sql);
            //====================================================
            stmt = connect().createStatement();
            sql = "CREATE TABLE IF NOT EXISTS discounts " +
                    "(itemId    INT NOT NULL," +
                    "companyId INT NOT NULL," +
                    "quantity  INT NOT NULL," +
                    "discount  INT NOT NULL," +
                    "FOREIGN KEY(companyId) REFERENCES suppliers(companyId)," +
                    "FOREIGN KEY(itemId) REFERENCES ItemsS(id)," +
                    "PRIMARY KEY (itemId,companyId,quantity))";
            stmt.executeUpdate(sql);
            //====================================================
            stmt = connect().createStatement();
            sql = "CREATE TABLE IF NOT EXISTS ItemsS " +
                    "(companyId INT  NOT NULL," +
                    "itemId    INT  NOT NULL," +
                    "name      TEXT NOT NULL," +
                    "price     REAL NOT NULL," +
                    "weight    REAL NOT NULL," +
                    "PRIMARY KEY (itemId,companyId)," +
                    "FOREIGN KEY(companyId) REFERENCES suppliers(companyId))";
            stmt.executeUpdate(sql);
            //====================================================
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    private void createSuppliersTables2() {
        try {
            Statement statement = connect().createStatement();
            String sql = "CREATE TABLE IF NOT EXISTS Orders " +
                    "(Id INTEGER PRIMARY KEY AUTOINCREMENT," +
                    "Status                 TEXT    NOT NULL, " +
                    "PlacementDate           TEXT    NOT NULL, " +
                    "DueDate          TEXT     NOT NULL, " +
                    "Fixed  INT NOT NULL)";
            statement.executeUpdate(sql);

            statement = connect().createStatement();
            sql = "CREATE TABLE IF NOT EXISTS ItemsInOrders " +
                    "(OrderID INT," +
                    "ItemID INT,  " +
                    "Amount INT NOT NULL," +
                    "PRIMARY KEY(OrderID,ItemID)," +
                    "FOREIGN KEY(OrderID) REFERENCES Orders(Id))";
            statement.executeUpdate(sql);
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    private void createCategoryTbl() {
        String sql = "CREATE TABLE \"Category\" (\n" +
                "\t\"cname\"\tTEXT,\n" +
                "\t\"level\"\tINTEGER,\n" +
                "\t\"discount1\"\tINTEGER,\n" +
                "\t\"uppercat\"\tTEXT,\n" +
                "\tPRIMARY KEY(\"cname\")\n" +
                ");";
        try {
            Statement stmt = connect().createStatement();
            stmt.execute(sql);
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    private void createSuppliersTables3() {
        try {
            Statement stmt = connect().createStatement();
            String sql = "CREATE TABLE IF NOT EXISTS suppliers " +
                    "(companyId INT PRIMARY KEY    NOT NULL, " +
                    "name                 TEXT    NOT NULL, " +
                    "phone                TEXT    NOT NULL, " +
                    "manifactur           TEXT    NOT NULL, " +
                    "bankAccount          INT     NOT NULL, " +
                    "paymentConditions    TEXT    NOT NULL, " +
                    "orderType            TEXT    NOT NULL, " +
                    "selfPickup           INT     NOT NULL)";
            stmt.executeUpdate(sql);
            //====================================================
            stmt = connect().createStatement();
            sql = "CREATE TABLE IF NOT EXISTS contacts " +
                    "(companyId INT  NOT NULL," +
                    "name           TEXT        NOT NULL," +
                    "method         TEXT        NOT NULL," +
                    "data           TEXT        NOT NULL," +
                    "PRIMARY KEY (companyId,name,method)," +
                    "FOREIGN KEY(companyId) REFERENCES suppliers(companyId))";
            stmt.executeUpdate(sql);
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    private void createItemSpecsTbl() {
        String sql = "CREATE TABLE \"ItemSpecs\" (\n" +
                "\t\"iname\"\tTEXT,\n" +
                "\t\"cname\"\tTEXT,\n" +
                "\t\"minamount\"\tINTEGER,\n" +
                "\t\"totalamount\"\tINTEGER,\n" +
                "\t\"manufacture\"\tTEXT,\n" +
                "\t\"companyprice\"\tINTEGER,\n" +
                "\t\"storeprice\"\tINTEGER,\n" +
                "\t\"discount\"\tINTEGER,\n" +
                "\t\"finalprice\"\tINTEGER,\n" +
                "\tPRIMARY KEY(\"iname\")\n" +
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
                "\t\"iname\"\tTEXT,\n" +
                "\t\"id\"\tINTEGER,\n" +
                "\t\"expdate\"\tTEXT,\n" +
                "\t\"shelveamount\"\tINTEGER,\n" +
                "\t\"storageamount\"\tINTEGER,\n" +
                "\t\"defectamount\"\tINTEGER,\n" +
                "\t\"defectreason\"\tTEXT,\n" +
                "\tPRIMARY KEY(\"id\")\n" +
                ");";
        try {
            Statement stmt = connect().createStatement();
            stmt.execute(sql);
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    private void createDefectsTbl() {
        String sql = "CREATE TABLE \"Defects\" (\n" +
                "\t\"id\"\tINTEGER,\n" +
                "\t\"iname\"\tTEXT,\n" +
                "\t\"cname\"\tTEXT,\n" +
                "\t\"defectreason\"\tTEXT,\n" +
                "\t\"amount\"\tINTEGER,\n" +
                "\tPRIMARY KEY(\"id\")\n" +
                ");";
        try {
            Statement stmt = connect().createStatement();
            stmt.execute(sql);
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    private void createReportsTbl() {
        String sql = "CREATE TABLE \"Reports\" (\n" +
                "\t\"id\"\tINTEGER,\n" +
                "\t\"title\"\tTEXT,\n" +
                "\t\"description\"\tTEXT,\n" +
                "\t\"cname\"\tTEXT,\n" +
                "\t\"date\"\tTEXT,\n" +
                "\t\"reportbudy\"\tTEXT,\n" +
                "\tPRIMARY KEY(\"id\")\n" +
                ");";
        try {
            Statement stmt = connect().createStatement();
            stmt.execute(sql);
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
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

    private void createShippedItemsTbl() {
        String sql = "CREATE TABLE \"ShippedItems\" (\n" +
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
                "\t\"approved\"\tINTEGER,\n" +
                "\t\"delivered\"\tINTEGER,\n" +
                "\tPRIMARY KEY(\"id\"),\n" +
                "\tFOREIGN KEY(\"truckPlateNumber\") REFERENCES \"Trucks\"(\"plateNumber\"),\n" +
                "\tFOREIGN KEY(\"sourceId\") REFERENCES \"Locations\"(\"id\"),\n" +
                "\tFOREIGN KEY(\"driverId\") REFERENCES \"Drivers\"(\"id\")\n" +
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
                "\t\"driver\"\tTEXT,\n" +
                "\tFOREIGN KEY(\"plateNumber\") REFERENCES \"Trucks\"(\"plateNumber\"),\n" +
                "\tFOREIGN KEY(\"driver\") REFERENCES \"Drivers\"(\"id\") ON DELETE CASCADE\n" +
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
                "\"message\" TEXT UNIQUE" +
                ");";
        try {
            Statement stmt = connect().createStatement();
            stmt.execute(sql);
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }


}
