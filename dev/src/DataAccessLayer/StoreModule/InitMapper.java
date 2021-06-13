package DataAccessLayer.StoreModule;

import java.io.File;
import java.sql.*;

public class InitMapper {

    private static String dbName = "superLee.db";
    public static String path = "jdbc:sqlite:superLee.db";
    //tabels
    public static String categorytbl = "Category";
    public static String itemspecstbl = "ItemSpecs";
    public static String itemstbl = "Items_Store";
    public static String reportstbl = "Reports";
    public static String defectstbl = "Defects";


    public InitMapper() {
        dbName = "superLee.db";
        path = "jdbc:sqlite:superLee.db";
    }

    public void initialize() {
        createNewDatabase();
        createCategoryTbl();
        createItemSpecsTbl();
        createItemsTbl();
        createDefectsTbl();
        createReportsTbl();
    }

    private void createNewDatabase() {
        try {
            Class.forName("org.sqlite.JDBC");
            Connection conn = DriverManager.getConnection("jdbc:sqlite:superLee.db");
            if (conn != null) {
                DatabaseMetaData meta = conn.getMetaData();
                System.out.println("A new database has been created.");
            } else System.out.println("Connection is null!");
        } catch (SQLException | ClassNotFoundException e) {
            System.out.println(e.getMessage());
        }
    }

    public static Connection connect() {
        Connection conn = null;
        try {
            Class.forName("org.sqlite.JDBC");
            conn = DriverManager.getConnection(path);
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        return conn;
    }

    private void createCategoryTbl() {
        String sql = "CREATE TABLE IF NOT EXISTS Category (" +
                "cname  TEXT," +
                "level  INTEGER," +
                "discount1 INTEGER," +
                "uppercat TEXT," +
                "PRIMARY KEY(cname))";
        try {
            try {
                Class.forName("org.sqlite.JDBC");
            } catch (ClassNotFoundException e) {
                e.printStackTrace();
            }
            Statement stmt = connect().createStatement();
            stmt.execute(sql);
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    private void createItemSpecsTbl() {
        String sql = "CREATE TABLE IF NOT EXISTS ItemSpecs (" +
                "iname TEXT," +
                "cname TEXT," +
                "minamount INTEGER," +
                "totalamount INTEGER," +
                "manufacture TEXT," +
                "companyprice INTEGER," +
                "storeprice INTEGER," +
                "discount INTEGER," +
                "finalprice INTEGER," +
                "PRIMARY KEY(iname))";
        try {
            Statement stmt = connect().createStatement();
            stmt.execute(sql);
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    private void createItemsTbl() {
        String sql = "CREATE TABLE IF NOT EXISTS Items_Store(" +
                "iname TEXT," +
                "id INTEGER," +
                "expdate TEXT," +
                "shelveamount INTEGER," +
                "storageamount INTEGER," +
                "defectamount INTEGER," +
                "defectreason TEXT," +
                "PRIMARY KEY(id))";
        try {
            Statement stmt = connect().createStatement();
            stmt.execute(sql);
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    private void createDefectsTbl() {
        String sql = "CREATE TABLE IF NOT EXISTS Defects(" +
                "id INTEGER," +
                "iname TEXT," +
                "cname TEXT," +
                "defectreason TEXT," +
                "amount INTEGER," +
                "PRIMARY KEY(id))";
        try {
            Statement stmt = connect().createStatement();
            stmt.execute(sql);
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    private void createReportsTbl() {
        String sql = "CREATE TABLE IF NOT EXISTS Reports(" +
                "id INTEGER," +
                "title TEXT," +
                "description TEXT," +
                "cname TEXT," +
                "date TEXT,\n" +
                "reportbudy TEXT," +
                "PRIMARY KEY(id))";
        try {
            Statement stmt = connect().createStatement();
            stmt.execute(sql);
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }
}