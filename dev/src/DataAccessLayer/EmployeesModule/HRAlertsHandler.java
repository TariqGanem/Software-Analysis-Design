package DataAccessLayer.EmployeesModule;

import BusinessLayer.Response;
import BusinessLayer.ResponseT;
import DataAccessLayer.dbMaker;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class HRAlertsHandler {
    private static HRAlertsHandler instance = null;
    private String url;

    public static HRAlertsHandler getInstance(String url) {
        if (instance == null)
            instance = new HRAlertsHandler(url);
        return instance;
    }

    private HRAlertsHandler(String url) {
        this.url = url;
    }

    public ResponseT<List<String>> getAlerts() {
        List<String> messages = new ArrayList<>();
        try (Connection con = DriverManager.getConnection(url)) {
            String sqlStatement = "select * from " + dbMaker.HRAlertsTbl;
            Statement p = con.createStatement();
            ResultSet rs = p.executeQuery(sqlStatement);

            while (rs.next()) {
                messages.add(rs.getString("message"));
            }

            return new ResponseT<>(messages);
        } catch (Exception ex) {
            return new ResponseT<>(ex.getMessage());
        } finally {
            deleteAllAlerts();
        }
    }

    public Response addAlert(String msg) {
        try (Connection con = DriverManager.getConnection(url)) {
            String sqlStatement = "insert or ignore into HRAlerts values(?)";
            PreparedStatement p = con.prepareStatement(sqlStatement);
            p.setString(1, msg);
            p.executeUpdate();

            return new Response();
        } catch (Exception ex) {
            return new Response(ex.getMessage());
        }
    }

    public void deleteAllAlerts() {
        try (Connection con = DriverManager.getConnection(url)) {
            String sqlStatement = "delete from " + dbMaker.HRAlertsTbl;
            Statement p = con.createStatement();
            p.executeUpdate(sqlStatement);

        } catch (Exception ignored) {
        }
    }
}
