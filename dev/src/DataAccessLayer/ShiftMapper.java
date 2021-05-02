package DataAccessLayer;

import BusinessLayer.Response;
import BusinessLayer.ResponseT;
import BusinessLayer.ShiftPackage.Shift;
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

    public Response removeFromShift(Shift shift, Role role, String ID){
        try {
            String sqlStatement = "delete from Shift where date = ? and isMorning = ? and role = ?";
            PreparedStatement p = con.prepareStatement(sqlStatement);
            p.setString(1, shift.getDate().toString());
            p.setBoolean(2, shift.isMorning());
            p.setString(3, role.toString());
            p.executeQuery(sqlStatement);
            return new Response();
        } catch (SQLException ex) {
            return new Response(ex.getMessage());
        }
    }
}
