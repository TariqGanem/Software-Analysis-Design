package DataAccessLayer;
import java.io.File;
import java.sql.*;

public class InitMapper {

    private static String dbName= "superLee.db";
    public static String path= "jdbc:sqlite:superLee.db";
    //tabels
    public static String categorytbl = "Category";
    public static String itemspecstbl = "ItemSpecs";
    public static String itemstbl = "Items";
    public static String reportstbl = "Reports";
    public static String defectstbl = "Defects";


    public InitMapper(){
        dbName = "superLee.db";
        path = "jdbc:sqlite:superLee.db";
    }
    public void initialize() {
        if (!new File(dbName).exists()) {
            createNewDatabase();
            createCategoryTbl();
            createItemSpecsTbl();
            createItemsTbl();
            createDefectsTbl();
            createReportsTbl();
        }
    }

    private void createNewDatabase() {
        try {
            Class.forName("org.sqlite.JDBC");
            Connection conn = DriverManager.getConnection("jdbc:sqlite:superLee.db");
            if (conn != null) {
                DatabaseMetaData meta = conn.getMetaData();
                System.out.println("A new database has been created.");
            }
            else System.out.println("Connection is null!");
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

    private void createCategoryTbl(){
        String sql ="CREATE TABLE IF NOT EXISTS \"Category\" (\n" +
                "\t\"cname\"\tTEXT,\n" +
                "\t\"level\"\tINTEGER,\n" +
                "\t\"discount1\"\tINTEGER,\n" +
                "\t\"uppercat\"\tTEXT,\n" +
                "\tPRIMARY KEY(\"cname\")\n" +
                ");";
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
    private void createItemSpecsTbl(){
        String sql ="CREATE TABLE IF NOT EXISTS \"ItemSpecs\" (\n" +
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
    private void createItemsTbl(){
        String sql ="CREATE TABLE IF NOT EXISTS\"Items\" (\n" +
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
    private void createDefectsTbl(){
        String sql ="CREATE TABLE IF NOT EXISTS \"Defects\" (\n" +
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
    private void createReportsTbl(){
        String sql ="CREATE TABLE IF NOT EXISTS \"Reports\" (\n" +
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


}