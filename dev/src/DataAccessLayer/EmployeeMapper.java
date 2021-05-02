package DataAccessLayer;

import BusinessLayer.EmployeePackage.Employee;
import BusinessLayer.Response;
import BusinessLayer.ResponseT;
import Resources.Preference;
import Resources.Role;

import java.sql.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class EmployeeMapper {
    private static EmployeeMapper instance = null;
    private Connection con;

    public static EmployeeMapper getInstance(Connection con) {
        if (instance == null)
            instance = new EmployeeMapper(con);
        return instance;
    }

    private EmployeeMapper(Connection con) {
        this.con = con;
    }

    public ResponseT<Employee> getEmployee(String ID) {
        try {
            String sqlStatement = "select * from Employee where ID = ?";
            PreparedStatement p = con.prepareStatement(sqlStatement);
            p.setString(1, ID);
            ResultSet rs = p.executeQuery();

            if (!rs.next())
                return new ResponseT<>("Employee not found in the system.");

            String name = rs.getString("name");
            int bankID = rs.getInt("bankID");
            int branchID = rs.getInt("branchID");
            int accountNumber = rs.getInt("accountNumber");
            float salary = rs.getFloat("salary");
            LocalDate startDate = LocalDate.parse(rs.getString("startDate"));
            String trustFund = rs.getString("trustFund");
            int freeDays = rs.getInt("freeDays");
            int sickDays = rs.getInt("sickDays");
            List<Role> skills = getSkills(ID);
            Preference[][] timeFrames = getTimePreferences(ID);

            Employee emp = new Employee(name, ID, bankID, branchID, accountNumber, salary, startDate,
                    trustFund, freeDays, sickDays, skills, timeFrames);

            return new ResponseT<>(emp);
        } catch (SQLException ex) {
            return new ResponseT<>(ex.getMessage());
        }
    }

    private List<Role> getSkills(String ID) throws SQLException {
        try {
            String sqlStatement = "select * from EmployeeSkills where ID = ?";
            PreparedStatement p = con.prepareStatement(sqlStatement);
            p.setString(1, ID);
            ResultSet rs = p.executeQuery();

            List<Role> skills = new ArrayList<>();

            while (rs.next()) {
                String role = rs.getString("skill");
                skills.add(Role.valueOf(role));
            }

            return skills;
        } catch (SQLException ex) {
            throw ex;
        }
    }

    private Preference[][] getTimePreferences(String ID) throws SQLException {
        String sqlStatement = "select * from EmployeeTimePreferences where ID = ?";
        PreparedStatement p = con.prepareStatement(sqlStatement);
        p.setString(1, ID);
        ResultSet rs = p.executeQuery();

        Preference[][] preferences = new Preference[7][2];

        while (rs.next()) {
            int dayIndex = rs.getInt("dayIndex");
            boolean isMorning = Boolean.parseBoolean(rs.getString("isMorning"));
            int morningIndex = isMorning ? 0 : 1;

            String preference = rs.getString("preference");
            if (preference.toLowerCase().equals("null"))
                preferences[dayIndex][morningIndex] = null;
            else
                preferences[dayIndex][morningIndex] = Preference.valueOf(preference);
        }

        return preferences;
    }

    public Response setEmployee(Employee emp) {
        try {
            String sqlStatement = "insert into Employee values(?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
            PreparedStatement pEmployee = con.prepareStatement(sqlStatement);
            pEmployee.setString(1, emp.getID());
            pEmployee.setString(2, emp.getName());
            pEmployee.setInt(3, emp.getBankId());
            pEmployee.setInt(4, emp.getBranchId());
            pEmployee.setInt(5, emp.getAccountNumber());
            pEmployee.setFloat(6, emp.getSalary());
            pEmployee.setString(7, emp.getStartDate().toString());
            pEmployee.setString(8, emp.getTrustFund());
            pEmployee.setInt(9, emp.getFreeDays());
            pEmployee.setInt(10, emp.getSickDays());

            List<Role> empSkills = emp.getSkills();
            PreparedStatement[] pEmployeeSkills = new PreparedStatement[empSkills.size()];
            for (int i = 0; i < pEmployeeSkills.length; i++) {
                pEmployeeSkills[i] = con.prepareStatement("insert into EmployeeSkills values(?, ?)");
                pEmployeeSkills[i].setString(1, emp.getID());
                pEmployeeSkills[i].setString(2, empSkills.get(i).name());
            }

            Preference[][] empTimePreferences = emp.getTimeFrames();
            PreparedStatement[][] pEmployeeTimePreferences = new PreparedStatement[7][2];
            for (int i = 0; i < 7; i++) {
                for (int j = 0; j < 2; j++) {
                    pEmployeeTimePreferences[i][j] = con.prepareStatement("insert into EmployeeTimePreferences values(?, ?, ?, ?)");
                    pEmployeeTimePreferences[i][j].setString(1, emp.getID());
                    pEmployeeTimePreferences[i][j].setInt(2, i);
                    pEmployeeTimePreferences[i][j].setString(3, j == 0 ? "true" : "false");
                    pEmployeeTimePreferences[i][j].setString(4, empTimePreferences[i][j].name());
                }
            }

            pEmployee.executeUpdate();
            for (int i = 0; i < pEmployeeSkills.length; i++) {
                pEmployeeSkills[i].executeUpdate();
            }
            for (int i = 0; i < 7; i++) {
                for (int j = 0; j < 2; j++) {
                    pEmployeeTimePreferences[i][j].executeUpdate();
                }
            }

            return new Response();
        } catch (SQLException ex) {
            return new Response(ex.getMessage());
        }
    }
}
