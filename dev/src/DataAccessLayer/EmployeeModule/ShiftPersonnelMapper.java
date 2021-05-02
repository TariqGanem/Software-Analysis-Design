package DataAccessLayer.EmployeeModule;

import BusinessLayer.Response;
import BusinessLayer.ResponseT;
import Resources.Role;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;

public class ShiftPersonnelMapper {
    private static ShiftPersonnelMapper instance = null;
    private Connection con;

    public static ShiftPersonnelMapper getInstance(Connection con) {
        if (instance == null)
            instance = new ShiftPersonnelMapper(con);
        return instance;
    }

    private ShiftPersonnelMapper(Connection con) {
        this.con = con;
    }

    public ResponseT<Map<Role, Integer>[]> getPersonnelMapper() {
        try{
            String sqlStatement = "select * from ShiftPersonnel";
            PreparedStatement stat = con.prepareStatement(sqlStatement);
            ResultSet rs = stat.executeQuery();
            Map<Role, Integer>[] mapArray = new Map[14];
            for(int i = 0; i < 14; i++){
                mapArray[i] = new HashMap<>();
            }
            while(rs.next()){
                Role role = Role.valueOf(rs.getString("role"));
                int dayIndex = rs.getInt("dayIndex");
                mapArray[dayIndex].put(role,rs.getInt("amount"));
            }
            return new ResponseT(mapArray);
        }catch(SQLException ex) {
            return new ResponseT(ex.getMessage());
        }
    }

    public Response updatePersonnelMapper(int dayIndex,Role role, int qtty) {
        try{
            String sqlStatement = "update ShiftPersonnel set amount = ? where dayIndex = ? and role = ?";
            PreparedStatement stat = con.prepareStatement(sqlStatement);
            stat.setInt(1,qtty);
            stat.setInt(2, dayIndex);
            stat.setString(3, role.toString());
            stat.executeQuery();
            return new Response();
        }catch(SQLException ex) {
            return new Response(ex.getMessage());
        }
    }

    public Response setPersonnelMapper(int dayIndex,Role role, int qtty) {
        try{
            String sqlStatement = "insert into ShiftPersonnel (dayIndex, role, amount) values(?,?,?)";
            PreparedStatement stat = con.prepareStatement(sqlStatement);
            stat.setInt(1,qtty);
            stat.setInt(2, dayIndex);
            stat.setString(3, role.toString());
            stat.executeQuery();
            return new Response();
        }catch(SQLException ex) {
            return new Response(ex.getMessage());
        }
    }

}
