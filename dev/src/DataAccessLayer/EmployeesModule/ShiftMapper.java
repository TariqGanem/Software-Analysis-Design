package DataAccessLayer.EmployeesModule;

import BusinessLayer.EmployeesModule.ShiftPackage.Shift;
import BusinessLayer.Response;
import BusinessLayer.ResponseT;
import DataAccessLayer.dbMaker;
import Resources.Role;

import java.sql.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ShiftMapper {
    private static ShiftMapper instance = null;
    private String url;

    public static ShiftMapper getInstance(String url) {
        if (instance == null)
            instance = new ShiftMapper(url);
        return instance;
    }

    private ShiftMapper(String url) {
        this.url = url;
    }

    public ResponseT<Shift> getShift(LocalDate date, boolean isMorning) {

        try (Connection con = DriverManager.getConnection(url)) {

            String sqlStatement = "select * from Shift where date = ? and isMorning = ?";
            PreparedStatement p = con.prepareStatement(sqlStatement);
            p.setString(1, date.toString());
            p.setString(2, String.valueOf(isMorning));
            ResultSet rs = p.executeQuery();


            Map<Role, List<String>> positions = new HashMap<>();
            while (rs.next()) {
                Role role = Role.valueOf(rs.getString("role"));
                if (!positions.containsKey(role))
                    positions.put(role, new ArrayList<>());
                positions.get(role).add(rs.getString("ID"));
            }

            if (positions.isEmpty())
                return new ResponseT<>("this shift does not exists.");
            return new ResponseT<>(new Shift(date, isMorning, positions));
        } catch (SQLException ex) {
            return new ResponseT<>(ex.getMessage());
        }
    }

    public Response setShift(Shift shift) {

        try (Connection con = DriverManager.getConnection(url)) {
            Map<Role, List<String>> positions = shift.getPositions();

            for (Role role : positions.keySet()) {
                List<String> ids = positions.get(role);
                for (String id : ids) {
                    String sqlStatement = "insert into Shift values(?,?,?,?)";
                    PreparedStatement p = con.prepareStatement(sqlStatement);
                    p.setString(1, shift.getDate().toString());
                    p.setString(2, String.valueOf(shift.isMorning()));
                    p.setString(3, role.toString());
                    p.setString(4, id);
                    p.executeUpdate();
                }
            }
            return new Response();
        } catch (SQLException ex) {
            return new Response(ex.getMessage());
        }
    }

    public Response insertToShift(Shift shift, Role role, String ID) {

        try (Connection con = DriverManager.getConnection(url)) {

            String sqlStatement = "insert into Shift values(?, ?, ?, ?)";
            PreparedStatement p = con.prepareStatement(sqlStatement);
            p.setString(1, shift.getDate().toString());
            p.setString(2, String.valueOf(shift.isMorning()));
            p.setString(3, role.toString());
            p.setString(4, ID);
            p.executeUpdate();
            return new Response();
        } catch (SQLException ex) {
            return new Response(ex.getMessage());
        }
    }

    public Response removeFromShift(Shift shift, String ID) {

        try (Connection con = DriverManager.getConnection(url)) {

            String sqlStatement = "delete from Shift where date = ? and isMorning = ? and ID = ?";
            PreparedStatement p = con.prepareStatement(sqlStatement);
            p.setString(1, shift.getDate().toString());
            p.setString(2, String.valueOf(shift.isMorning()));
            p.setString(3, ID);
            p.executeUpdate();
            return new Response();
        } catch (SQLException ex) {
            return new Response(ex.getMessage());
        }
    }

    public ResponseT<List<Shift>> getShifts(int daysFromToday) {
        try (Connection con = DriverManager.getConnection(url)) {
            String sqlStatement = "select date, isMorning from Shift where date(date) BETWEEN date('now') and date('now','+" + daysFromToday + " days') group by date, isMorning";
            Statement p = con.createStatement();
            ResultSet rs = p.executeQuery(sqlStatement);
            List<Shift> lst = new ArrayList<>();
            while (rs.next()) {
                ResponseT<Shift> res = getShift(LocalDate.parse(rs.getString("date")), Boolean.parseBoolean(rs.getString("isMorning")));
                if (res.getErrorOccurred()) {
                    throw new SQLException(res.getErrorMessage());
                }
                lst.add(res.getValue());
            }
            return new ResponseT<>(lst);
        } catch (SQLException ex) {
            return new ResponseT<>(ex.getMessage());
        }
    }

    public Response deleteShift(LocalDate date, boolean isMorning) {

        try (Connection con = DriverManager.getConnection(url)) {

            String sqlStatement = "delete from Shift where date = ? and isMorning = ?";
            PreparedStatement p = con.prepareStatement(sqlStatement);
            p.setString(1, date.toString());
            p.setString(2, String.valueOf(isMorning));
            int rows = p.executeUpdate();

            if (rows == 0)
                return new Response("Didn't find the shift specified");

            return new Response();
        } catch (SQLException ex) {
            return new Response(ex.getMessage());
        }
    }

    public ResponseT<Map<Shift, Role>> getEmpShifts(String id) {
        try (Connection con = DriverManager.getConnection(url)) {
            String sqlStatement = "select date, isMorning, role from Shift where ID = ?";
            PreparedStatement p = con.prepareStatement(sqlStatement);
            p.setString(1, id);
            ResultSet rs = p.executeQuery();
            Map<Shift, Role> map = new HashMap<>();
            while (rs.next()) {
                ResponseT<Shift> res = getShift(LocalDate.parse(rs.getString("date")), Boolean.parseBoolean(rs.getString("isMorning")));
                if (res.getErrorOccurred())
                    throw new SQLException(res.getErrorMessage());
                map.put(res.getValue(), Role.valueOf(rs.getString("role")));
            }
            return new ResponseT<>(map);
        } catch (SQLException ex) {
            return new ResponseT<>(ex.getMessage());
        }
    }

    public ResponseT<List<String>> getAvailableDrivers(LocalDate date, boolean isMorning) {
        try (Connection con = DriverManager.getConnection(url)) {
            String sqlStatement = "select ID from " + dbMaker.shiftTbl + " where role = ?"
                    + " AND isMorning=? AND date=?";
            PreparedStatement p = con.prepareStatement(sqlStatement);
            p.setString(1, Role.Driver.name());
            p.setString(2, String.valueOf(isMorning));
            p.setString(3, date.toString());
            ResultSet rs = p.executeQuery();
            List<String> IDs = new ArrayList<>();
            while (rs.next()) {
                IDs.add(rs.getString("ID"));
            }
            return new ResponseT<>(IDs);
        } catch (SQLException ex) {
            return new ResponseT<>(ex.getMessage());
        }
    }
}
