package DataAccessLayer.SuppliersModule.Mappers;

import DTOPackage.ItemDTO;
import DTOPackage.OrderDTO;
import DataAccessLayer.SuppliersModule.Objects.Order;
import Resources.Status;

import java.sql.*;
import java.time.LocalDate;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

public class OrdersMapper extends Mapper{
    private Map<Integer, Order> orders;

    public OrdersMapper(){
        super();
        orders = new HashMap<>();
        try {
            Statement statement = connection.createStatement();
            String sql = "CREATE TABLE IF NOT EXISTS Orders " +
                    "(Id INTEGER PRIMARY KEY AUTOINCREMENT," +
                    "Status                 TEXT    NOT NULL, " +
                    "PlacementDate           TEXT    NOT NULL, " +
                    "DueDate          TEXT     NOT NULL, "+
                    "Fixed  INT NOT NULL)";
            statement.executeUpdate(sql);

            statement = connection.createStatement();
            sql = "CREATE TABLE IF NOT EXISTS ItemsInOrders "+
                    "(OrderID INT,"+
                    "ItemID INT,  " +
                    "Amount INT NOT NULL,"+
                    "PRIMARY KEY(OrderID,ItemID)," +
                    "FOREIGN KEY(OrderID) REFERENCES Orders(Id))";
            statement.executeUpdate(sql);
            

            statement.close();
            connection.close();
        }
        catch (SQLException exception) {
            exception.printStackTrace();
        }
    }

    public int storeFixedOrder(OrderDTO orderDTO){  // done
        int output = -1;
        try {
            String sql = "INSERT INTO Orders (Status,PlacementDate,DueDate,Fixed) \" +\n" +
                    "                    \"VALUES (?,?,?,?)";
            connection = connect();

            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setString(1,orderDTO.getStatus().toString());
            preparedStatement.setString(2, orderDTO.getPlacementDate().toString());
            preparedStatement.setString(3, orderDTO.getDueDate().toString());
            preparedStatement.setInt(4, 1);
            preparedStatement.executeUpdate();


            output = getNewOrderID();
            orderDTO.setId(output);
            orders.putIfAbsent(orderDTO.getId(), new Order(orderDTO));

            for (Map.Entry<Integer, ItemDTO> item: orderDTO.getItems().entrySet()) {
                storeItemInOrder(output, item.getValue().getId(), orderDTO.getAmounts().get(item.getKey()));
            }

            preparedStatement.close();
            connection.close();
        }
        catch (Exception exception) {
            exception.printStackTrace();
        }
        return output;
    }


    public int storeSingleOrder(OrderDTO orderDTO){  // done
        int output = -1;
        try {
            String sql = "INSERT INTO Orders (Status,PlacementDate,DueDate,Fixed) \" +\n" +
                    "                    \"VALUES (?,?,?,?)";
            connection = connect();

            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setString(1,orderDTO.getStatus().toString());
            preparedStatement.setString(2, orderDTO.getPlacementDate().toString());
            preparedStatement.setString(3, orderDTO.getDueDate().toString());
            preparedStatement.setInt(4, 0);
            preparedStatement.executeUpdate();


            output = getNewOrderID();
            orderDTO.setId(output);
            orders.putIfAbsent(orderDTO.getId(), new Order(orderDTO));

            for (Map.Entry<Integer, ItemDTO> item: orderDTO.getItems().entrySet()) {
                storeItemInOrder(output, item.getValue().getId(), orderDTO.getAmounts().get(item.getKey()));
            }

            preparedStatement.close();
            connection.close();
        }
        catch (Exception exception) {
            exception.printStackTrace();
        }
        return output;
    }


    public void LoadOrders(){
        try {
            Statement statement = connection.createStatement();
            String sql = "";


            statement.close();
            connection.close();
        }
        catch (SQLException exception) {
            exception.printStackTrace();
        }
    }
    public int getNewOrderID(){
        int output = -1;
       /* try {
            Statement statement = connection.createStatement();
            String sql = "";


            statement.close();
            connection.close();
        }
        catch (SQLException exception) {
            exception.printStackTrace();
        }*/
        return 1;
    }
    public void updateStatus(int orderID, Status newStatus){   // done
        try{
            String sql = "UPDATE Orders " +
                    "SET    Status = ? " +
                    "WHERE  Id = ?";
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setString(1, newStatus.toString());
            preparedStatement.setInt(2, orderID);
            preparedStatement.executeUpdate();

            preparedStatement.close();
            connection.close();
        }
        catch (SQLException exception){
            exception.printStackTrace();
        }
    }
    public void updateDueDate(int orderID, LocalDate newDueDate){  // done
        try{
            String sql = "UPDATE Orders " +
                    "SET    DueDate = ? " +
                    "WHERE  Id = ?";
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setString(1, newDueDate.toString());
            preparedStatement.setInt(2, orderID);
            preparedStatement.executeUpdate();

            preparedStatement.close();
            connection.close();
        }
        catch (SQLException exception){
            exception.printStackTrace();
        }
    }

    public void updateAmount(int orderID, int itemID, int newAmount){  // done
        try{
            String sql = "UPDATE ItemsInOrders " +
                    "SET    Amount = ? " +
                    "WHERE  OrderID = ? AND ItemID = ?";
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setInt(1, newAmount);
            preparedStatement.setInt(2, orderID);
            preparedStatement.setInt(3, itemID);
            preparedStatement.executeUpdate();

            preparedStatement.close();
            connection.close();
        }
        catch (SQLException exception){
            exception.printStackTrace();
        }
    }


    public void storeItemInOrder(int OrderID, int ItemID, int Amount){  // done
        try{
            String sql = "INSERT INTO ItemsInOrders (OrderID, ItemID, Amount) " +
                         "VALUES(?, ?, ?)";
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setInt(1, OrderID);
            preparedStatement.setInt(2, ItemID);
            preparedStatement.setInt(3, Amount);
            preparedStatement.executeUpdate();

            preparedStatement.close();
            connection.close();
        }
        catch (Exception exception){
            exception.printStackTrace();
        }
    }


    public void removeItem(int orderID, int itemID){   // done
        try{
            String sql = "DELETE FROM ItemsInOrders " +
                    "WHERE OrderID = ? AND ItemID = ?";
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setInt(1, orderID);
            preparedStatement.setInt(2, itemID);

            preparedStatement.executeUpdate();

            preparedStatement.close();
            connection.close();

        }
        catch (SQLException exception){
            exception.printStackTrace();
        }

    }


    private Connection connect() throws Exception {
        try {
            Class.forName("org.sqlite.JDBC");
            return DriverManager.getConnection("jdbc:sqlite:superLee.db");
        } catch (Exception e) {
            throw new Exception(e.getMessage());
        }
    }

    public List<String> getTodayOrders(LocalDate now) throws Exception {
        System.out.println("Section 2\n");
        try{
            List<String> names = new LinkedList<>();
            String sql = "SELECT name FROM (SELECT * FROM ItemsInOrders Natural JOIN ItemsS WHERE ItemsInOrders.ItemId = ItemsS.itemId)" +
                    " Natural JOIN Orders " +
                    "WHERE Orders.ID = ItemsInOrders.OrderID";
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            ResultSet r = preparedStatement.executeQuery();
            while(r.next()){
                names.add(r.getString("name"));
            }
            preparedStatement.close();
            connection.close();
            return names;
        }catch (Exception e){
            return null;
        }
    }
}
