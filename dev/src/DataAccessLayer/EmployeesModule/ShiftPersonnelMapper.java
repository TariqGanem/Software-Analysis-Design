package DataAccessLayer.EmployeesModule;

import BusinessLayer.Response;
import BusinessLayer.ResponseT;
import Resources.Role;

import java.sql.*;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

public class ShiftPersonnelMapper {
    private static ShiftPersonnelMapper instance = null;
    private String url;

    public static ShiftPersonnelMapper getInstance(String url) {
        if (instance == null)
            instance = new ShiftPersonnelMapper(url);
        return instance;
    }

    private ShiftPersonnelMapper(String url) {
        this.url = url;
    }

    public ResponseT<Map<Role, Integer>[]> getShiftPersonnel() {

        try (Connection con = DriverManager.getConnection(url)) {

            String sqlStatement = "select * from ShiftPersonnel";
            PreparedStatement stat = con.prepareStatement(sqlStatement);
            ResultSet rs = stat.executeQuery();
            Map<Role, Integer>[] mapArray = new HashMap[14];
            for (int i = 0; i < 14; i++) {
                mapArray[i] = new HashMap<>();
            }
            while (rs.next()) {
                String roleFromDB = rs.getString("role");
                if (Arrays.stream(Role.values()).noneMatch(x -> x.name().equals(roleFromDB))) { //Deleting a role for the db that was deleted here
                    deleteRowsForDeletedRole(roleFromDB);
                    continue;
                }
                Role role = Role.valueOf(roleFromDB);
                int dayIndex = rs.getInt("weekIndex");
                mapArray[dayIndex].put(role, rs.getInt("amount"));
            }

            for (Role r1 : Role.values()) { //There exists a role here that isn't in the DB
                if (mapArray[0].keySet().stream().noneMatch(x -> x.name().equals(r1.name()))) {
                    for (int i = 0; i < 14; i++) {
                        mapArray[i].put(r1, 1); //default value = 1
                        setShiftPersonnel(i, r1, 1);
                    }
                }
            }

            return new ResponseT(mapArray);
        } catch (SQLException ex) {
            return new ResponseT(ex.getMessage());
        }
    }

    public Response updateShiftPersonnel(int dayIndex, Role role, int qtty) {

        try (Connection con = DriverManager.getConnection(url)) {

            String sqlStatement = "update ShiftPersonnel set amount = ? where weekIndex = ? and role = ?";
            PreparedStatement stat = con.prepareStatement(sqlStatement);
            stat.setInt(1, qtty);
            stat.setInt(2, dayIndex);
            stat.setString(3, role.name());
            stat.executeUpdate();
            return new Response();
        } catch (SQLException ex) {
            return new Response(ex.getMessage());
        }
    }

    public Response setShiftPersonnel(int dayIndex, Role role, int qtty) {

        try (Connection con = DriverManager.getConnection(url)) {

            String sqlStatement = "insert into ShiftPersonnel values(?,?,?)";
            PreparedStatement stat = con.prepareStatement(sqlStatement);
            stat.setInt(1, dayIndex);
            stat.setString(2, role.name());
            stat.setInt(3, qtty);
            stat.executeUpdate();
            return new Response();
        } catch (SQLException ex) {
            return new Response(ex.getMessage());
        }
    }

    private void deleteRowsForDeletedRole(String role) {

        try (Connection con = DriverManager.getConnection(url)) {

            String sqlStatement = "delete from ShiftPersonnel where role = ?";
            PreparedStatement stat = con.prepareStatement(sqlStatement);
            stat.setString(1, role);
            stat.executeUpdate();
        } catch (Exception ignored) {
        }
    }
}


