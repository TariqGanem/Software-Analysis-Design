package DataAccessLayer.EmployeeModule;

import BusinessLayer.EmployeeModule.Response;
import BusinessLayer.EmployeeModule.ResponseT;
import DataAccessLayer.dbMaker;

import java.sql.*;
import java.time.LocalDate;
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

    public ResponseT<List<LocalDate>> getAlerts() {
        List<LocalDate> dates = new ArrayList<>();
        try (Connection con = DriverManager.getConnection(url)) {
            String sqlStatement = "select * from " + dbMaker.HRAlertsTbl;
            Statement p = con.createStatement();
            ResultSet rs = p.executeQuery(sqlStatement);

            while (rs.next()) {
                dates.add(LocalDate.parse(rs.getString("orderDate")));
            }

            return new ResponseT<>(dates);
        } catch (Exception ex) {
            return new ResponseT<>(ex.getMessage());
        }
    }

    public Response addAlert(LocalDate date) {
        try (Connection con = DriverManager.getConnection(url)) {
            String sqlStatement = "insert or ignore into HRAlerts values(?)";
            PreparedStatement p = con.prepareStatement(sqlStatement);
            p.setString(1, date.toString());
            p.executeUpdate();

            return new Response();
        } catch (Exception ex) {
            return new Response(ex.getMessage());
        }
    }
}
