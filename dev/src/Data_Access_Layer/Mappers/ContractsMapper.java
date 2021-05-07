package Data_Access_Layer.Mappers;

import DTO.ContractDTO;
import DTO.ItemDTO;
import DTO.SupplierDTO;
import Data_Access_Layer.Objects.ContractDAL;
import Data_Access_Layer.Objects.ItemDAL;
import Data_Access_Layer.Objects.QuantityReportDAL;

import java.sql.*;
import java.util.HashMap;
import java.util.Map;

public class ContractsMapper extends Mapper{
    private Map<Integer, ContractDAL> contracts;

    public ContractsMapper(){
        super();
        contracts = new HashMap<>();
        try {
            Statement stmt = c.createStatement();
            String sql = "CREATE TABLE IF NOT EXISTS contracts" +
                    "(companyId INT PRIMARY KEY    NOT NULL," +
                    "selfPickup           INT     NOT NULL," +
                    "FOREIGN KEY(companyId) REFERENCES suppliers(companyId))";
            stmt.executeUpdate(sql);
            //====================================================
            stmt = c.createStatement();
            sql = "CREATE TABLE IF NOT EXISTS discounts " +
                    "(itemId    INT NOT NULL," +
                    "companyId INT NOT NULL," +
                    "quantity  INT NOT NULL," +
                    "discount  INT NOT NULL," +
                    "FOREIGN KEY(companyId) REFERENCES suppliers(companyId)," +
                    "FOREIGN KEY(itemId) REFERENCES items(id)," +
                    "PRIMARY KEY (itemId,companyId,quantity))";
            stmt.executeUpdate(sql);
            //====================================================
            stmt = c.createStatement();
            sql = "CREATE TABLE IF NOT EXISTS items " +
                    "(companyId INT  NOT NULL," +
                    "itemId    INT  NOT NULL," +
                    "name      TEXT NOT NULL," +
                    "price     REAL NOT NULL," +
                    "PRIMARY KEY (itemId,companyId)," +
                    "FOREIGN KEY(companyId) REFERENCES suppliers(companyId))";
            stmt.executeUpdate(sql);
            //====================================================
            stmt.close();
            c.close();
            stmt = null;
            c = null;
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    //==================================================================================================================
    private Connection connect(){
        try {
            return DriverManager.getConnection("jdbc:sqlite:database.db");
        } catch (Exception e) {
            return null;
        }
    }
    //==================================================================================================================

    public ContractDTO getContract(int companyid){
        ContractDAL contract = null;
        Map<Integer, Map<Integer, Double>> discounts = new HashMap<>();
        Map<Integer, ItemDAL> items = new HashMap<>();
        if (!contracts.containsKey(companyid)){
            try {
                c = connect();
                String sql_discounts = "SELECT * FROM discounts WHERE companyId = " + companyid;
                Statement stmt_discounts = c.createStatement();
                ResultSet rs_discounts = stmt_discounts.executeQuery(sql_discounts);
                while(rs_discounts.next()){
                    if(discounts.containsKey(rs_discounts.getInt("itemId"))){
                        discounts.get(rs_discounts.getInt("itemId"))
                                .putIfAbsent(rs_discounts.getInt("quantity"),
                                        rs_discounts.getDouble("discount"));
                    }else{
                        discounts.putIfAbsent(rs_discounts.getInt("itemId"),new HashMap<>());
                        discounts.get(rs_discounts.getInt("itemId"))
                                .putIfAbsent(rs_discounts.getInt("quantity"),
                                        rs_discounts.getDouble("discount"));
                    }
                }
                QuantityReportDAL report = new QuantityReportDAL(discounts);
                //===============================================================
                String sql_items = "SELECT * FROM items WHERE companyId = " + companyid;
                Statement stmt_items = c.createStatement();
                ResultSet rs_items = stmt_items.executeQuery(sql_items);
                while(rs_items.next()){
                    items.putIfAbsent(rs_items.getInt("itemId"),
                            new ItemDAL(rs_items.getInt("itemId"),
                                        rs_items.getString("name"),
                                        rs_items.getDouble("price")));
                }
                //===============================================================
                String sql = "SELECT * FROM contracts WHERE companyId = " + companyid;
                Statement stmt = c.createStatement();
                ResultSet rs = stmt.executeQuery(sql);
                while(rs.next()){
                    contracts.putIfAbsent(companyid,
                            new ContractDAL(items,report,rs.getInt("selfPickup") == 1? true:false));
                }
                contract = contracts.get(companyid);
                //===============================================================
                stmt_discounts.close();
                stmt_items.close();
                stmt.close();
                c.close();
                c = null;
            } catch (Exception e) {
                contract = null;
            }
        }else{
            contract = contracts.get(companyid);
        }
        if(contract!=null)
            return contract.DTO();
        else
            return null;
    }

    //==================================================================================================================

    public void addContract(int companyid, boolean selfPickup){
        try {
            String sql = "INSERT INTO contracts (companyId,selfPickup) " +
                    "VALUES (?,?)";
            c = connect();
            //====================================================
            //entering values
            PreparedStatement pstmt = c.prepareStatement(sql);
            pstmt.setInt(1,companyid);
            pstmt.setInt(2,selfPickup?1:0);
            pstmt.executeUpdate();
            //====================================================
            //updating in the HashMap
            contracts.putIfAbsent(companyid,new ContractDAL(new HashMap<>(),new QuantityReportDAL(),selfPickup));
            //====================================================
            pstmt.close();
            c.close();
            c = null;
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    //==================================================================================================================

    public void addDiscount(int companyid, int itemid, int quant, double disc){
        try {
            String sql = "INSERT INTO discounts (companyId,itemid,quantity,discount) " +
                    "VALUES (?,?,?,?)";
            c = connect();
            //====================================================
            //entering values
            PreparedStatement pstmt = c.prepareStatement(sql);
            pstmt.setInt(1,itemid);
            pstmt.setInt(2,companyid);
            pstmt.setInt(3,quant);
            pstmt.setDouble(4,disc);
            pstmt.executeUpdate();
            //====================================================
            //updating in the HashMap
            if(contracts.containsKey(companyid)){
                contracts.get(companyid).addDiscount(itemid,quant,disc);
            }
            //====================================================
            pstmt.close();
            c.close();
            c = null;
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void addItem(int companyid, int itemid, String name, double price){
        try {
            String sql = "INSERT INTO items (companyId,itemid,name,price) " +
                    "VALUES (?,?,?,?)";
            c = connect();
            //====================================================
            //entering values
            PreparedStatement pstmt = c.prepareStatement(sql);
            pstmt.setInt(1,companyid);
            pstmt.setInt(2,itemid);
            pstmt.setString(3,name);
            pstmt.setDouble(4,price);
            pstmt.executeUpdate();
            //====================================================
            //updating in the HashMap
            if(contracts.containsKey(companyid)){
                contracts.get(companyid).addItem(itemid,name,price);
            }
            //====================================================
            pstmt.close();
            c.close();
            c = null;
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    //==================================================================================================================

    public void removeContract(int companyid){
        try {
            String sql = "DELETE FROM contracts WHERE companyId = ?";
            c = connect();
            //====================================================
            //entering values
            PreparedStatement pstmt = c.prepareStatement(sql);
            pstmt.setInt(1,companyid);
            pstmt.executeUpdate();
            //====================================================
            //updating in the HashMap
            if(contracts.containsKey(companyid)){
                contracts.remove(companyid);
            }
            //====================================================
            pstmt.close();
            c.close();
            c = null;
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void removeDiscount(int companyid, int itemid, int quant){
        try {
            String sql = "DELETE FROM discounts WHERE companyId = ? AND itemId = ? AND quantity = ?";
            c = connect();
            //====================================================
            //entering values
            PreparedStatement pstmt = c.prepareStatement(sql);
            pstmt.setInt(1,companyid);
            pstmt.setInt(2,itemid);
            pstmt.setInt(3,quant);
            pstmt.executeUpdate();
            //====================================================
            //updating in the HashMap
            if(contracts.containsKey(companyid)){
                contracts.get(companyid).removeDiscount(itemid,quant);
            }
            //====================================================
            pstmt.close();
            c.close();
            c = null;
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void removeItem(int companyid, int itemid){
        try {
            String sql = "DELETE FROM items WHERE companyId = ? AND itemId = ?";
            c = connect();
            //====================================================
            //entering values
            PreparedStatement pstmt = c.prepareStatement(sql);
            pstmt.setInt(1,companyid);
            pstmt.setInt(2,itemid);
            pstmt.executeUpdate();
            //====================================================
            //updating in the HashMap
            if(contracts.containsKey(companyid)){
                contracts.get(companyid).removeItem(itemid);
            }
            //====================================================
            pstmt.close();
            c.close();
            c = null;
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    //==================================================================================================================

    public void updatePrice(int companyid, int itemid,double price){
        try{
            String sql = "UPDATE items SET price = ?"
                    + "WHERE companyId = ? AND itemId = ?";

            c = connect();
            //====================================================
            //entering values
            PreparedStatement pstmt = c.prepareStatement(sql);
            pstmt.setDouble(1, price);
            pstmt.setInt(2, companyid);
            pstmt.setInt(3, itemid);
            pstmt.executeUpdate();
            //====================================================
            //updating in the HashMap
            if(contracts.containsKey(companyid)){
                contracts.get(companyid).updatePrice(itemid,price);
            }
            //====================================================
            pstmt.close();
            c.close();
            c = null;
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }
}