package DataAccessLayer.EmployeesModule;

import BusinessLayer.EmployeesModule.EmployeePackage.Employee;
import BusinessLayer.Response;
import BusinessLayer.ResponseT;
import DataAccessLayer.dbMaker;
import Resources.Preference;
import Resources.Role;
import org.sqlite.SQLiteConfig;

import java.sql.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class EmployeeMapper {
    private static EmployeeMapper instance = null;
    private String url;

    public static EmployeeMapper getInstance(String url) {
        if (instance == null)
            instance = new EmployeeMapper(url);
        return instance;
    }

    private EmployeeMapper(String url) {
        this.url = url;
    }

    public ResponseT<Employee> getEmployee(String ID) {
        try (Connection con = DriverManager.getConnection(url)) {

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
        } catch (Exception ex) {
            return new ResponseT<>(ex.getMessage());
        }
    }

    private List<Role> getSkills(String ID) throws SQLException {
        try (Connection con = DriverManager.getConnection(url)) {

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
        Connection con = null;
        Preference[][] preferences = new Preference[7][2];

        try {
            con = DriverManager.getConnection(url);

            String sqlStatement = "select * from EmployeeTimePreferences where ID = ?";
            PreparedStatement p = con.prepareStatement(sqlStatement);
            p.setString(1, ID);
            ResultSet rs = p.executeQuery();

            preferences = new Preference[7][2];

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
        } catch (SQLException ex) {
            throw ex;
        } finally {
            if (con != null)
                con.close();
        }
        return preferences;
    }

    public Response setEmployee(Employee emp) {
        try (Connection con = DriverManager.getConnection(url)) {

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
            for (PreparedStatement pEmployeeSkill : pEmployeeSkills) {
                pEmployeeSkill.executeUpdate();
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

    public Response updateEmployee(Employee emp, String oldID) {
        Connection con = null;

        try {
            SQLiteConfig config = new SQLiteConfig(); //I add this configuration
            config.enforceForeignKeys(true);
            con = DriverManager.getConnection(url, config.toProperties());

            // Update Employee
            String sqlStatement = "update Employee set ID = ?, name = ?, bankID = ?, branchID = ?, accountNumber = ?, salary = ?, startDate = ?, trustFund = ?, freeDays = ?, sickDays = ? where ID = ?";
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
            pEmployee.setString(11, oldID);

            // Update EmployeeSkills
            PreparedStatement pDeleteSkillsEmployees = con.prepareStatement("delete from EmployeeSkills where ID = ?");
            pDeleteSkillsEmployees.setString(1, oldID);
            List<Role> empSkills = emp.getSkills();
            PreparedStatement[] pEmployeeSkills = new PreparedStatement[empSkills.size()];
            for (int i = 0; i < pEmployeeSkills.length; i++) {
                pEmployeeSkills[i] = con.prepareStatement("insert into EmployeeSkills values(?, ?)");
                pEmployeeSkills[i].setString(1, emp.getID());
                pEmployeeSkills[i].setString(2, empSkills.get(i).name());
            }

            // Update EmployeeTimeFrames
            PreparedStatement pDeleteTimePreferencesEmployees = con.prepareStatement("delete from EmployeeTimePreferences where ID = ?");
            pDeleteTimePreferencesEmployees.setString(1, oldID);
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
            pDeleteSkillsEmployees.executeUpdate();
            for (PreparedStatement pEmployeeSkill : pEmployeeSkills) {
                pEmployeeSkill.executeUpdate();
            }
            pDeleteTimePreferencesEmployees.executeUpdate();
            for (int i = 0; i < 7; i++) {
                for (int j = 0; j < 2; j++) {
                    pEmployeeTimePreferences[i][j].executeUpdate();
                }
            }

            return new Response();
        } catch (SQLException ex) {
            return new Response(ex.getMessage());
        } finally {
            if (con != null) {
                try {
                    con.close();
                } catch (SQLException ignored) {
                }
            }
        }
    }

    public ResponseT<List<String>> getEmployeeIDs() {
        try (Connection con = DriverManager.getConnection(url)) {

            String sqlStatement = "select ID from Employee";
            PreparedStatement pEmployee = con.prepareStatement(sqlStatement);
            ResultSet rs = pEmployee.executeQuery();

            List<String> IDs = new ArrayList<>();
            while (rs.next()) {
                IDs.add(rs.getString("ID"));
            }

            return new ResponseT(IDs);
        } catch (SQLException ex) {
            return new ResponseT(ex.getMessage());
        }
    }

    public ResponseT<List<Employee>> getAvailableEmployees(int day, boolean isMorning, Role skill, boolean getAvailable) {
        try (Connection con = DriverManager.getConnection(url)) {
            String sqlStatement;
            if (getAvailable)
                sqlStatement = "select E.ID from ((" + dbMaker.employeeTbl + " as E join " + dbMaker.employeeSkillsTbl + " as ES on E.ID = ES.ID) as EES join " + dbMaker.empTimePrefTbl + " AS ETP on EES.ID = ETP.ID) where ES.skill = ? and (ETP.preference = \"WANT\" or ETP.preference = \"CAN\") and ETP.dayIndex = ? and ETP.isMorning = ? GROUP by E.ID";
            else
                sqlStatement = "select E.ID from ((" + dbMaker.employeeTbl + " as E join " + dbMaker.employeeSkillsTbl + " as ES on E.ID = ES.ID) as EES join " + dbMaker.empTimePrefTbl + " AS ETP on EES.ID = ETP.ID) where ES.skill = ? and ETP.preference = \"CANT\" and ETP.dayIndex = ? and ETP.isMorning = ? GROUP by E.ID";

            PreparedStatement p = con.prepareStatement(sqlStatement);
            p.setString(1, skill.name());
            p.setInt(2, day);
            p.setString(3, String.valueOf(isMorning));
            ResultSet rs = p.executeQuery();

            List<Employee> employees = new ArrayList<>();
            while (rs.next()) {
                employees.add(getEmployee(rs.getString("ID")).getValue());
            }

            return new ResponseT(employees);
        } catch (SQLException ex) {
            return new ResponseT(ex.getMessage());
        }
    }
}
