package DataAccessLayer.SuppliersModule.Mappers;

import DTOPackage.ContractDTO;
import DataAccessLayer.SuppliersModule.Objects.Contract;
import DataAccessLayer.SuppliersModule.Objects.Item;
import DataAccessLayer.SuppliersModule.Objects.QuantityReport;
import DataAccessLayer.dbMaker;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.HashMap;
import java.util.Map;

public class ContractsMapper extends Mapper {
    private Map<Integer, Contract> contracts;

    public ContractsMapper() {
        super();
        contracts = new HashMap<>();
    }

    //==================================================================================================================
    private Connection connect() {
        try {
            return dbMaker.connect();
        } catch (Exception e) {
            return null;
        }
    }
    //==================================================================================================================

    public ContractDTO getContract(int companyid) {
        Contract contract = null;
        Map<Integer, Map<Integer, Double>> discounts = new HashMap<>();
        Map<Integer, Item> items = new HashMap<>();
        if (!contracts.containsKey(companyid)) {
            try {
                connection = connect();
                String sql_discounts = "SELECT * FROM discounts WHERE companyId = " + companyid;
                Statement stmt_discounts = connection.createStatement();
                ResultSet rs_discounts = stmt_discounts.executeQuery(sql_discounts);
                while (rs_discounts.next()) {
                    if (discounts.containsKey(rs_discounts.getInt("itemId"))) {
                        discounts.get(rs_discounts.getInt("itemId"))
                                .putIfAbsent(rs_discounts.getInt("quantity"),
                                        rs_discounts.getDouble("discount"));
                    } else {
                        discounts.putIfAbsent(rs_discounts.getInt("itemId"), new HashMap<>());
                        discounts.get(rs_discounts.getInt("itemId"))
                                .putIfAbsent(rs_discounts.getInt("quantity"),
                                        rs_discounts.getDouble("discount"));
                    }
                }
                QuantityReport report = new QuantityReport(discounts);
                //===============================================================
                String sql_items = "SELECT * FROM ItemsS WHERE companyId = " + companyid;
                Statement stmt_items = connection.createStatement();
                ResultSet rs_items = stmt_items.executeQuery(sql_items);
                while (rs_items.next()) {
                    items.putIfAbsent(rs_items.getInt("itemId"),
                            new Item(rs_items.getInt("itemId"),
                                    rs_items.getString("name"),
                                    rs_items.getDouble("price"),
                                    rs_items.getDouble("weight")));
                }
                //===============================================================
                String sql = "SELECT * FROM contracts WHERE companyId = " + companyid;
                Statement stmt = connection.createStatement();
                ResultSet rs = stmt.executeQuery(sql);
                while (rs.next()) {
                    contracts.putIfAbsent(companyid,
                            new Contract(items, report, rs.getInt("selfPickup") == 1));
                }
                contract = contracts.get(companyid);
                //===============================================================
                stmt_discounts.close();
                stmt_items.close();
                stmt.close();
                connection.close();
                connection = null;
            } catch (Exception e) {
                contract = null;
            }
        } else {
            contract = contracts.get(companyid);
        }
        if (contract != null)
            return new ContractDTO(contract);
        else
            return null;
    }

    //==================================================================================================================

    public void addContract(int companyid, boolean selfPickup) {
        try {
            String sql = "INSERT INTO contracts (companyId,selfPickup) " +
                    "VALUES (?,?)";
            connection = connect();
            //====================================================
            //entering values
            PreparedStatement pstmt = connection.prepareStatement(sql);
            pstmt.setInt(1, companyid);
            pstmt.setInt(2, selfPickup ? 1 : 0);
            pstmt.executeUpdate();
            //====================================================
            //updating in the HashMap
            contracts.putIfAbsent(companyid, new Contract(new HashMap<>(), new QuantityReport(), selfPickup));
            //====================================================
            pstmt.close();
            connection.close();
            connection = null;
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    //==================================================================================================================

    public void addDiscount(int companyid, int itemid, int quant, double disc) {
        try {
            String sql = "INSERT INTO discounts (companyId,itemid,quantity,discount) " +
                    "VALUES (?,?,?,?)";
            connection = connect();
            //====================================================
            //entering values
            PreparedStatement pstmt = connection.prepareStatement(sql);
            pstmt.setInt(1, itemid);
            pstmt.setInt(2, companyid);
            pstmt.setInt(3, quant);
            pstmt.setDouble(4, disc);
            pstmt.executeUpdate();
            //====================================================
            //updating in the HashMap
            if (contracts.containsKey(companyid)) {
                contracts.get(companyid).addDiscount(itemid, quant, disc);
            }
            //====================================================
            pstmt.close();
            connection.close();
            connection = null;
        } catch (Exception ignored) {
//            e.printStackTrace();
        }
    }

    public void addItem(int companyid, int itemid, String name, double price, double weight) {
        try {
            String sql = "INSERT INTO ItemsS (companyId,itemid,name,price,weight) " +
                    "VALUES (?,?,?,?,?)";
            connection = connect();
            //====================================================
            //entering values
            PreparedStatement pstmt = connection.prepareStatement(sql);
            pstmt.setInt(1, companyid);
            pstmt.setInt(2, itemid);
            pstmt.setString(3, name);
            pstmt.setDouble(4, price);
            pstmt.setDouble(5, weight);
            pstmt.executeUpdate();
            //====================================================
            //updating in the HashMap
            if (contracts.containsKey(companyid)) {
                contracts.get(companyid).addItem(itemid, name, price, weight);
            }
            //====================================================
            pstmt.close();
            connection.close();
            connection = null;
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    //==================================================================================================================

    public void removeContract(int companyid) {
        try {
            String sql = "DELETE FROM contracts WHERE companyId = ?";
            connection = connect();
            //====================================================
            //entering values
            PreparedStatement pstmt = connection.prepareStatement(sql);
            pstmt.setInt(1, companyid);
            pstmt.executeUpdate();
            //====================================================
            //updating in the HashMap
            if (contracts.containsKey(companyid)) {
                contracts.remove(companyid);
            }
            //====================================================
            pstmt.close();
            connection.close();
            connection = null;
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void removeDiscount(int companyid, int itemid, int quant) {
        try {
            String sql = "DELETE FROM discounts WHERE companyId = ? AND itemId = ? AND quantity = ?";
            connection = connect();
            //====================================================
            //entering values
            PreparedStatement pstmt = connection.prepareStatement(sql);
            pstmt.setInt(1, companyid);
            pstmt.setInt(2, itemid);
            pstmt.setInt(3, quant);
            pstmt.executeUpdate();
            //====================================================
            //updating in the HashMap
            if (contracts.containsKey(companyid)) {
                contracts.get(companyid).removeDiscount(itemid, quant);
            }
            //====================================================
            pstmt.close();
            connection.close();
            connection = null;
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void removeItem(int companyid, int itemid) {
        try {
            String sql = "DELETE FROM ItemsS WHERE companyId = ? AND itemId = ?";
            connection = connect();
            //====================================================
            //entering values
            PreparedStatement pstmt = connection.prepareStatement(sql);
            pstmt.setInt(1, companyid);
            pstmt.setInt(2, itemid);
            pstmt.executeUpdate();
            //====================================================
            //updating in the HashMap
            if (contracts.containsKey(companyid)) {
                contracts.get(companyid).removeItem(itemid);
            }
            //====================================================
            pstmt.close();
            connection.close();
            connection = null;
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    //==================================================================================================================

    public void updatePrice(int companyid, int itemid, double price) {
        try {
            String sql = "UPDATE ItemsS SET price = ?"
                    + "WHERE companyId = ? AND itemId = ?";

            connection = connect();
            //====================================================
            //entering values
            PreparedStatement pstmt = connection.prepareStatement(sql);
            pstmt.setDouble(1, price);
            pstmt.setInt(2, companyid);
            pstmt.setInt(3, itemid);
            pstmt.executeUpdate();
            //====================================================
            //updating in the HashMap
            if (contracts.containsKey(companyid)) {
                contracts.get(companyid).updatePrice(itemid, price);
            }
            //====================================================
            pstmt.close();
            connection.close();
            connection = null;
        } catch (Exception e) {
            //System.out.println(e.getMessage());
        }
    }

    public Map<Integer, ContractDTO> ContractsOfItem(String name) {
        try (Connection connection = connect();) {
            Map<Integer, ContractDTO> newContracts = new HashMap<>();
            String sql = "SELECT companyId FROM ItemsS WHERE name = '" + name + "'";
            Statement stmt = connection.createStatement();
            ResultSet rs = stmt.executeQuery(sql);
            while (rs.next()) {
                newContracts.putIfAbsent(rs.getInt("companyId"), getContract(rs.getInt("companyId")));
            }
            //===============================================================
            stmt.close();
            //connection.close();
            return newContracts;
        } catch (Exception e) {
            //System.out.println(e.getMessage());
            return null;
        }
    }
}