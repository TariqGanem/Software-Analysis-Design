package DataAccessLayer.EmployeeModule;

import BusinessLayer.EmployeeModule.Response;
import BusinessLayer.EmployeeModule.ResponseT;
import BusinessLayer.EmployeeModule.ShiftPackage.Shift;
import Resources.Role;

import java.sql.*;
import java.time.LocalDate;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ShiftMapper {
    private static ShiftMapper instance = null;
    private Connection con;

    public static ShiftMapper getInstance(Connection con) {
        if (instance == null)
            instance = new ShiftMapper(con);
        return instance;
    }

    private ShiftMapper(Connection con) {
        this.con = con;
    }

    public ResponseT<Shift> getShift(LocalDate date, boolean isMorning) {
        try {
            String sqlStatement = "select * from Shift where date = ? and isMorning = ?";
            PreparedStatement p = con.prepareStatement(sqlStatement);
            p.setString(1, date.toString());
            p.setString(2, String.valueOf(isMorning));
            ResultSet rs = p.executeQuery(sqlStatement);
            Map<Role, List<String>> positions = new HashMap<>();
            while(rs.next()){
                Role role = Role.valueOf(rs.getString("role"));
                if(!positions.containsKey(role))
                    positions.put(role,new ArrayList<>());
                positions.get(role).add(rs.getString("ID"));
            }
            return new ResponseT<>(new Shift(date, isMorning, positions));
        }catch (SQLException ex){
            return new ResponseT<>(ex.getMessage());
        }
    }

    public Response setShift(Shift shift) {
        try {
            Map<Role, List<String>> positions = shift.getPositions();

            for (Role role : positions.keySet()) {
                List<String> ids = positions.get(role);
                for (String id : ids) {
                    String sqlStatement = "insert into Shift (date, isMorning, role, ID) values(?,?,?,?)";
                    PreparedStatement p = con.prepareStatement(sqlStatement);
                    p.setString(1, shift.getDate().toString());
                    p.setBoolean(2, shift.isMorning());
                    p.setString(3, role.toString());
                    p.setString(4, id);
                    p.executeQuery(sqlStatement);
                }
            }
            return new Response();
        } catch (SQLException ex) {
            return new Response(ex.getMessage());
        }
    }


    public Response insertToShift(Shift shift, Role role, String ID){
        try {
            String sqlStatement = "insert into Shift (date, isMorning, role, ID) values(?,?,?,?)";
            PreparedStatement p = con.prepareStatement(sqlStatement);
            p.setString(1, shift.getDate().toString());
            p.setBoolean(2, shift.isMorning());
            p.setString(1, role.toString());
            p.setString(2, ID);
            p.executeQuery(sqlStatement);
            return new Response();
        } catch (SQLException ex) {
            return new Response(ex.getMessage());
        }
    }

    public Response removeFromShift(Shift shift, String ID){
        try {
            String sqlStatement = "delete from Shift where date = ? and isMorning = ? and ID = ?";
            PreparedStatement p = con.prepareStatement(sqlStatement);
            p.setString(1, shift.getDate().toString());
            p.setBoolean(2, shift.isMorning());
            p.setString(3, ID);
            p.executeQuery(sqlStatement);
            return new Response();
        } catch (SQLException ex) {
            return new Response(ex.getMessage());
        }
    }

    public ResponseT<List<Shift>> getShifts(int daysFromToday) {
        try {
            String sqlStatement = "select date, isMorning from Shift where date(date) BETWEEN date('now') and date('now','+? days') ";
            PreparedStatement p = con.prepareStatement(sqlStatement);
            p.setInt(1, daysFromToday);
            ResultSet rs = p.executeQuery(sqlStatement);
            List<Shift> lst = new ArrayList<>();
            while(rs.next()){
                ResponseT<Shift> res = getShift(LocalDate.parse(rs.getString("date")),rs.getBoolean("isMorning"));
                if(res.getErrorOccurred())
                    throw new SQLException(res.getErrorMessage());
                lst.add(res.getValue());
            }
            return new ResponseT<>(lst);
        }catch (SQLException ex){
            return new ResponseT<>(ex.getMessage());
        }
    }

    public void deleteShift(LocalDate date, boolean isMorning) {
        try {
            String sqlStatement = "delete from Shift where date = ? and isMorning = ?";
            PreparedStatement p = con.prepareStatement(sqlStatement);
            p.setString(1, date.toString());
            p.setBoolean(2, isMorning);
            p.executeQuery(sqlStatement);
        } catch (SQLException ignored) {}
    }

    public ResponseT<Map<Shift, Role>> getEmpShifts(String id) {
        try {
            String sqlStatement = "select date, isMorning, role from Shift where ID = ?";
            PreparedStatement p = con.prepareStatement(sqlStatement);
            p.setString(1, id);
            ResultSet rs = p.executeQuery(sqlStatement);
            Map<Shift, Role> map = new HashMap<>();
            while(rs.next()){
                ResponseT<Shift> res = getShift(LocalDate.parse(rs.getString("date")),rs.getBoolean("isMorning"));
                if(res.getErrorOccurred())
                    throw new SQLException(res.getErrorMessage());
                map.put(res.getValue(), Role.valueOf(rs.getString("role")));
            }
            return new ResponseT<>(map);
        }catch (SQLException ex){
            return new ResponseT<>(ex.getMessage());
        }
    }
}
