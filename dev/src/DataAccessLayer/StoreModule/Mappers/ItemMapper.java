package DataAccessLayer.StoreModule.Mappers;

import DataAccessLayer.StoreModule.objects.ItemsDl;
import DataAccessLayer.dbMaker;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.LinkedList;
import java.util.List;

public class ItemMapper {

    private static ItemMapper instance = null;
    private Mapper memory;


    private ItemMapper() {
        memory = Mapper.getInstance();
    }

    public static ItemMapper getInstance() {
        if (instance == null)
            instance = new ItemMapper();
        return instance;
    }

    public void insertItem(String iname, int id, String expdate, int shelveamount, int storageamount, int defectamount, String defectreason) throws Exception {
        String sql = "INSERT INTO " + dbMaker.itemstbl + "(iname,id, expdate,shelveamount,storageamount,defectamount,defectreason) VALUES (?,?,?,?,?,?,?)";
        try (PreparedStatement pstmt = dbMaker.connect().prepareStatement(sql)) {
            pstmt.setString(1, iname);
            pstmt.setInt(2, id);
            pstmt.setString(3, expdate);
            pstmt.setInt(4, shelveamount);
            pstmt.setInt(5, storageamount);
            pstmt.setInt(6, defectamount);
            pstmt.setString(7, defectreason);
            pstmt.executeUpdate();
        } catch (Exception e) {
            //throw new Exception(e.getMessage());
        }


    }

/// will be affected here : shelve amount , storage amount , defected amount , defect reason

    public void updateItemSpecsDiscount(String iname, int discount) throws Exception { //add&remove Discount!
        String sql = "UPDATE " + dbMaker.itemstbl + " SET discount = ? WHERE iname= ?";
        try (PreparedStatement pStmt = dbMaker.connect().prepareStatement(sql)) {
            pStmt.setInt(1, discount);
            pStmt.setString(2, iname);
            pStmt.executeUpdate();
        } catch (Exception e) {
            //throw new Exception(e.getMessage());
        }
    }

    public void updateItemStorageAmount(int id, int amount) throws Exception {
        String sql = "UPDATE " + dbMaker.itemstbl + " SET storageamount = ? WHERE id= ?";
        try (PreparedStatement pStmt = dbMaker.connect().prepareStatement(sql)) {
            pStmt.setInt(1, amount);
            pStmt.setInt(2, id);
            pStmt.executeUpdate();
        } catch (Exception e) {
            //throw new Exception(e.getMessage());
        }
    }

    public void updateItemShelveamount(int id, int amount) throws Exception {
        String sql = "UPDATE " + dbMaker.itemstbl + " SET shelveamount = ? WHERE id= ?";
        try (PreparedStatement pStmt = dbMaker.connect().prepareStatement(sql)) {
            pStmt.setInt(1, amount);
            pStmt.setInt(2, id);
            pStmt.executeUpdate();
        } catch (Exception e) {
            //throw new Exception(e.getMessage());
        }
    }

    public void updateItemDefecedamount(int id, int amount) throws Exception {
        String sql = "UPDATE " + dbMaker.itemstbl + " SET defectamount = ? WHERE id= ?";
        try (PreparedStatement pStmt = dbMaker.connect().prepareStatement(sql)) {
            pStmt.setInt(1, amount);
            pStmt.setInt(2, id);
            pStmt.executeUpdate();
        } catch (Exception e) {
            //throw new Exception(e.getMessage());
        }
    }

    public void updateItemSdefectreason(int id, String amount) throws Exception {
        String sql = "UPDATE " + dbMaker.itemstbl + " SET defectreason = ? WHERE id= ?";
        try (PreparedStatement pStmt = dbMaker.connect().prepareStatement(sql)) {
            pStmt.setString(1, amount);
            pStmt.setInt(2, id);
            pStmt.executeUpdate();
        } catch (Exception e) {
            //throw new Exception(e.getMessage());
        }
    }

    public void deleteItems(String iname) throws Exception {
        String sql = "DELETE FROM " + dbMaker.itemstbl + " WHERE iname = ?";
        try (PreparedStatement pstmt = dbMaker.connect().prepareStatement(sql)) {
            pstmt.setString(1, iname);
            pstmt.executeUpdate();
        } catch (Exception e) {
            //throw new Exception(e.getMessage());
        }
    }

    public void restoreAllItems() throws Exception {
        String sql = "SELECT * FROM " + dbMaker.itemstbl;
        List<ItemsDl> it = new LinkedList<>();
        try (Statement stmt = dbMaker.connect().createStatement()) {
            ResultSet rs = stmt.executeQuery(sql);
            while (rs.next()) {
                ItemsDl items = new ItemsDl(rs.getString(1),
                        rs.getInt(2),
                        rs.getString(3),
                        rs.getInt(4),
                        rs.getInt(5),
                        rs.getInt(6),
                        rs.getString(7));
                it.add(items);
            }
            memory.setItems(it);
        } catch (Exception e) {
            //throw new Exception(e.getMessage());
        }
    }

}
