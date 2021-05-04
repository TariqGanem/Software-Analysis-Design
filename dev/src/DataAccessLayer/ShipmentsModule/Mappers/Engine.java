package DataAccessLayer.Mappers;

import ShipmentsModule.Response;
import ShipmentsModule.ResponseT;

import java.io.File;
import java.sql.*;

public class Engine {
    private String dbName;
    private String path;

    //TABLES NAMES
    protected final String trucksTbl = "Trucks";
    protected final String locationsTbl = "Locations";
    protected final String driversTbl = "Drivers";
    protected final String documentsTbl = "Documents";
    protected final String itemsTbl = "Items";

    protected Connection conn = null;

    public Engine() {
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
        }
    }

    private Response createNewDatabase() {
        try {
            Class.forName("org.sqlite.JDBC");
            Connection conn = DriverManager.getConnection(path);
            if (conn != null) {
                DatabaseMetaData meta = conn.getMetaData();
                System.out.println("The driver name is " + meta.getDriverName());
                System.out.println("A new database has been created.");
                return new ResponseT<>("A new database has been created.");
            }
            return new ResponseT<>("Connection is null!");
        } catch (SQLException | ClassNotFoundException e) {
            return new Response(e.getMessage());
        }
    }

    protected Connection connect() {
        try {
            conn = DriverManager.getConnection(path);
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return conn;
    }

    protected void close() {
        try {
            conn.close();
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
                "\t\"available\"\tINTEGER,\n" +
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
                "\tPRIMARY KEY(\"id\")\n" +
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
                "\t\"weight\"\tREAL\n" +
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
                "\tFOREIGN KEY(\"sourceId\") REFERENCES \"Locations\"(\"address\"),\n" +
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

    private void createDocumentsTbl() {
        String sql = "CREATE TABLE \"Documents\" (\n" +
                "\t\"trackingNumber\"\tINTEGER,\n" +
                "\t\"destinationId\"\tINTEGER,\n" +
                "\t\"shipmentId\"\tINTEGER,\n" +
                "\tPRIMARY KEY(\"trackingNumber\")\n" +
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

}
