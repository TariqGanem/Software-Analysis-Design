package DataAccessLayer.Mappers;

import DataAccessLayer.DAO.Order;

import java.sql.*;
import java.util.HashMap;
import java.util.Map;

public class OrdersMapper extends Mapper{
    private Map<Integer, Order> orders;

    public OrdersMapper(){
        super();
        orders = new HashMap<>();
        try {
            Statement statement = connection.createStatement();
            String sql = "CREATE TABLE IF NOT EXISTS FixedOrders"+
                    ""+""+
                    ""+""+
                    ""+""+
                    ""+"";


            statement.close();
            connection.close();
        }
        catch (SQLException exception) {
            exception.printStackTrace();
        }

    }

    public void storeFixedOrder(){
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
    public void storeSingleOrder(){
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
        try {
            Statement statement = connection.createStatement();
            String sql = "";


            statement.close();
            connection.close();
        }
        catch (SQLException exception) {
            exception.printStackTrace();
        }
        return output;
    }
    public void updateFixedOrder(){
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
    public void updateSingleOrder(){
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


    private Connection connect() throws Exception {
        try {
            return DriverManager.getConnection("jdbc:sqlite:database.db");
        } catch (Exception e) {
            throw new Exception(e.getMessage());
        }
    }
}
