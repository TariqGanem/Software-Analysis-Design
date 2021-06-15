package DataAccessLayer.StoreModule.Mappers;

//import BusinessLayer.ItemSpecs;

import DataAccessLayer.StoreModule.objects.ItemSpecsDl;
import DataAccessLayer.dbMaker;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.LinkedList;
import java.util.List;

public class ItemSpecsMapper {

    private static ItemSpecsMapper instance = null;
    private Mapper memory;


    private ItemSpecsMapper() {
        memory = Mapper.getInstance();
    }

    public static ItemSpecsMapper getInstance() {
        if (instance == null)
            instance = new ItemSpecsMapper();
        return instance;
    }

    public void insertItemSpecs(String iname, String cname, int minamount, int totalamount, String manufacture, int companyprice, int storeprice, int discount, int finalprice) throws Exception {
        String sql = "INSERT INTO " + dbMaker.itemspecstbl + "(iname,cname, minamount,totalamount,manufacture,companyprice,storeprice ,discount,finalprice) VALUES (?,?,?,?,?,?,?,?,?)";
        try (PreparedStatement pstmt = dbMaker.connect().prepareStatement(sql)) {
            pstmt.setString(1, iname);
            pstmt.setString(2, cname);
            pstmt.setInt(3, minamount);
            pstmt.setInt(4, totalamount);
            pstmt.setString(5, manufacture);
            pstmt.setInt(6, companyprice);
            pstmt.setInt(7, storeprice);
            pstmt.setInt(8, discount);
            pstmt.setInt(9, finalprice);
            pstmt.executeUpdate();
        } catch (Exception e) {
            //throw new Exception(e.getMessage());
        }
    }

/// will be affected here : Total amount , final price , discount

    public void updateItemSpecsDiscount(String iname, int discount) throws Exception { //add&remove Discount!
        String sql = "UPDATE " + dbMaker.itemspecstbl + " SET discount = ? WHERE iname= ?";
        try (PreparedStatement pStmt = dbMaker.connect().prepareStatement(sql)) {
            pStmt.setInt(1, discount);
            pStmt.setString(2, iname);
            pStmt.executeUpdate();
        } catch (Exception e) {
//            throw new Exception(e.getMessage());
        }
    }

    public void updateItemSpecsTotalAmount(String iname, int amount) throws Exception {
        String sql = "UPDATE " + dbMaker.itemspecstbl + " SET totalamount = ? WHERE iname= ?";
        try (PreparedStatement pStmt = dbMaker.connect().prepareStatement(sql)) {
            pStmt.setInt(1, amount);
            pStmt.setString(2, iname);
            pStmt.executeUpdate();
        } catch (Exception e) {
//            throw new Exception(e.getMessage());
        }
    }

    public void updateItemSpecsFinalPrice(String iname, int amount) throws Exception {
        String sql = "UPDATE " + dbMaker.itemspecstbl + " SET finalprice = ? WHERE iname= ?";
        try (PreparedStatement pStmt = dbMaker.connect().prepareStatement(sql)) {
            pStmt.setInt(1, amount);
            pStmt.setString(2, iname);
            pStmt.executeUpdate();
        } catch (Exception e) {
//            throw new Exception(e.getMessage());
        }
    }

    public void deleteItemSpecs(String iname) throws Exception {
        String sql = "DELETE FROM " + dbMaker.itemspecstbl + " WHERE iname = ?";
        try (PreparedStatement pstmt = dbMaker.connect().prepareStatement(sql)) {
            pstmt.setString(1, iname);
            pstmt.executeUpdate();
        } catch (Exception e) {
//            throw new Exception(e.getMessage());
        }
    }

    public void restoreAllItemSpecs() throws Exception {
        String sql = "SELECT * FROM " + dbMaker.itemspecstbl;
        List<ItemSpecsDl> it = new LinkedList<>();
        try (
                Statement stmt = dbMaker.connect().createStatement()) {
            ResultSet rs = stmt.executeQuery(sql);
            while (rs.next()) {
                ItemSpecsDl items = new ItemSpecsDl(rs.getString(1),
                        rs.getString(2),
                        rs.getInt(3),
                        rs.getInt(4),
                        rs.getString(5),
                        rs.getInt(6),
                        rs.getInt(7),
                        rs.getInt(8),
                        rs.getInt(9));
                it.add(items);
            }
            memory.setItemspecs(it);
        } catch (Exception e) {
//            throw new Exception(e.getMessage());
        }
    }

}
