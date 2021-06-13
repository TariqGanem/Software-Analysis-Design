package EmployeesTests;

import BusinessLayer.EmployeesModule.EmployeePackage.Employee;
import BusinessLayer.EmployeesModule.EmployeePackage.EmployeeController;
import DataAccessLayer.EmployeesModule.DALController;
import DataAccessLayer.dbMaker;
import Resources.Preference;
import Resources.Role;
import org.junit.Before;
import org.junit.Test;

import javax.naming.NoPermissionException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import static org.junit.Assert.*;

public class EmployeeControllerTest {
    private EmployeeController employeeController;

    @Before
    public void setUp() {
        employeeController = new EmployeeController(new DALController());
        try (Connection con = DriverManager.getConnection(dbMaker.path)) {
            String sqlStatement = "delete from EmployeeSkills;";
            PreparedStatement p = con.prepareStatement(sqlStatement);
            p.executeUpdate();
            sqlStatement = "delete from EmployeeTimePreferences;";
            p = con.prepareStatement(sqlStatement);
            p.executeUpdate();
            sqlStatement = "delete from Employee;";
            p = con.prepareStatement(sqlStatement);
            p.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void testLoginWithNoEmployee() {
        try {
            employeeController.login("1");
            fail("Expected to get an exception because there is no employee in the system.");
        } catch (Exception e) {
            assertTrue(e instanceof IllegalArgumentException);
        }
    }

    @Test
    public void testLogin() {
        List roles = new ArrayList<Role>();
        roles.add(Role.StoreManager);
        String name = "Sponge Bob";
        String ID = "123456789";
        int bankId = 10;
        int branchId = 100;
        int accountNumber = 1000;
        float salary = 5000;
        LocalDate date = LocalDate.now();
        String trustFund = "Trust Fund";
        int freeDays = 10;
        int sickDays = 15;
        Preference[][] timeFrames = new Preference[7][2];
        for (int i = 0; i < 7; i++)
            for (int j = 0; j < 2; j++)
                timeFrames[i][j] = Preference.WANT;

        employeeController.addEmployee(name, ID, bankId, branchId, accountNumber, salary, date, trustFund, freeDays, sickDays, roles, timeFrames);

        try {
            employeeController.login("123456789");
            assertTrue(employeeController.isManager());
        } catch (Exception e) {
            fail("Got an Exception.");
        }
    }

    @Test
    public void testAddEmployee() {
        List roles = new ArrayList<Role>();
        roles.add(Role.StoreManager);
        String name = "Sponge Bob";
        String ID = "123456789";
        int bankId = 10;
        int branchId = 100;
        int accountNumber = 1000;
        float salary = 5000;
        LocalDate date = LocalDate.now();
        String trustFund = "Trust Fund";
        int freeDays = 10;
        int sickDays = 15;
        Preference[][] timeFrames = new Preference[7][2];
        for (int i = 0; i < 7; i++)
            for (int j = 0; j < 2; j++)
                timeFrames[i][j] = Preference.WANT;

        employeeController.addEmployee(name, ID, bankId, branchId, accountNumber, salary, date, trustFund, freeDays, sickDays, roles, timeFrames);
        try {
            employeeController.login("123456789");
        } catch (Exception e) {
            e.printStackTrace();
        }

        try {
            Employee employee = employeeController.getEmployee("123456789");
            assertEquals(name, employee.getName());
            assertEquals(ID, employee.getID());
            assertEquals(bankId, employee.getBankId());
            assertEquals(branchId, employee.getBranchId());
            assertEquals(accountNumber, employee.getAccountNumber());
            assertEquals(0, salary - employee.getSalary(), 0.0);
            assertEquals(date, employee.getStartDate());
            assertEquals(trustFund, employee.getTrustFund());
            assertEquals(freeDays, employee.getFreeDays());
            assertEquals(sickDays, employee.getSickDays());
            assertEquals(roles, employee.getSkills());
            assertArrayEquals(timeFrames, employee.getTimeFrames());
        } catch (Exception e) {
            fail("Got an exception.");
        }
    }

    @Test
    public void testAddEmployeeWithSameID() {
        List roles = new ArrayList<Role>();
        roles.add(Role.StoreManager);
        String name = "Sponge Bob";
        String ID = "123456789";
        int bankId = 10;
        int branchId = 100;
        int accountNumber = 1000;
        float salary = 5000;
        LocalDate date = LocalDate.now();
        String trustFund = "Trust Fund";
        int freeDays = 10;
        int sickDays = 15;
        Preference[][] timeFrames = new Preference[7][2];
        for (int i = 0; i < 7; i++)
            for (int j = 0; j < 2; j++)
                timeFrames[i][j] = Preference.WANT;

        employeeController.addEmployee(name, ID, bankId, branchId, accountNumber, salary, date, trustFund, freeDays, sickDays, roles, timeFrames);

        try {
            employeeController.addEmployee(name, ID, bankId, branchId, accountNumber, salary, date, trustFund, freeDays, sickDays, roles, timeFrames);
            fail("Allowed to add another employee with the same ID.");
        } catch (Exception ignored) {
        }
    }

    @Test
    public void testGetEmployee_NotManager() {
        List roles = new ArrayList<Role>();
        roles.add(Role.Cashier);
        String name = "Sponge Bob";
        String ID = "123456789";
        int bankId = 10;
        int branchId = 100;
        int accountNumber = 1000;
        float salary = 5000;
        LocalDate date = LocalDate.now();
        String trustFund = "Trust Fund";
        int freeDays = 10;
        int sickDays = 15;
        Preference[][] timeFrames = new Preference[7][2];
        for (int i = 0; i < 7; i++)
            for (int j = 0; j < 2; j++)
                timeFrames[i][j] = Preference.WANT;

        employeeController.addEmployee(name, ID, bankId, branchId, accountNumber, salary, date, trustFund, freeDays, sickDays, roles, timeFrames);
        try {
            employeeController.login("123456789");
        } catch (Exception e) {
            e.printStackTrace();
        }

        try {
            employeeController.getEmployee("777777");
            fail("Got an employee back without being a manager.");
        } catch (Exception e) {
            assertTrue(e instanceof NoPermissionException);
        }
    }

    @Test
    public void testGetEmployee_Manager() {
        List roles = new ArrayList<Role>();
        roles.add(Role.StoreManager);
        String name = "Sponge Bob";
        String ID = "123456789";
        int bankId = 10;
        int branchId = 100;
        int accountNumber = 1000;
        float salary = 5000;
        LocalDate date = LocalDate.now();
        String trustFund = "Trust Fund";
        int freeDays = 10;
        int sickDays = 15;
        Preference[][] timeFrames = new Preference[7][2];
        for (int i = 0; i < 7; i++)
            for (int j = 0; j < 2; j++)
                timeFrames[i][j] = Preference.WANT;

        employeeController.addEmployee(name, ID, bankId, branchId, accountNumber, salary, date, trustFund, freeDays, sickDays, roles, timeFrames);
        try {
            employeeController.login("123456789");
        } catch (Exception e) {
            e.printStackTrace();
        }

        try {
            employeeController.getEmployee("123456789");
        } catch (Exception e) {
            fail("Active user is a manager and got an exception.");
        }
    }

    @Test
    public void testGetEmployee_Manager_NoEmployee() {
        List roles = new ArrayList<Role>();
        roles.add(Role.StoreManager);
        String name = "Sponge Bob";
        String ID = "123456789";
        int bankId = 10;
        int branchId = 100;
        int accountNumber = 1000;
        float salary = 5000;
        LocalDate date = LocalDate.now();
        String trustFund = "Trust Fund";
        int freeDays = 10;
        int sickDays = 15;
        Preference[][] timeFrames = new Preference[7][2];
        for (int i = 0; i < 7; i++)
            for (int j = 0; j < 2; j++)
                timeFrames[i][j] = Preference.WANT;

        employeeController.addEmployee(name, ID, bankId, branchId, accountNumber, salary, date, trustFund, freeDays, sickDays, roles, timeFrames);
        try {
            employeeController.login("123456789");
        } catch (Exception e) {
            e.printStackTrace();
        }

        try {
            employeeController.getEmployee("7777777");
            fail("There is no employee with ID 7777777, but didn't get an exception.");
        } catch (Exception e) {
            assertTrue(e instanceof IllegalArgumentException);
        }
    }

    @Test
    public void testUpdateEmployee_ChangeID() {
        List roles = new ArrayList<Role>();
        roles.add(Role.StoreManager);
        String name = "Sponge Bob";
        String ID = "123456789";
        int bankId = 10;
        int branchId = 100;
        int accountNumber = 1000;
        float salary = 5000;
        LocalDate date = LocalDate.now();
        String trustFund = "Trust Fund";
        int freeDays = 10;
        int sickDays = 15;
        Preference[][] timeFrames = new Preference[7][2];
        for (int i = 0; i < 7; i++)
            for (int j = 0; j < 2; j++)
                timeFrames[i][j] = Preference.WANT;

        employeeController.addEmployee(name, ID, bankId, branchId, accountNumber, salary, date, trustFund, freeDays, sickDays, roles, timeFrames);
        try {
            employeeController.login("123456789");
        } catch (Exception e) {
            e.printStackTrace();
        }

        try {
            employeeController.updateEmployee("123456789", name, "987654321", bankId, branchId, accountNumber, salary, date, trustFund, freeDays, sickDays, roles, timeFrames);
        } catch (Exception e) {
            fail("Tried to change an employee ID but failed.");
        }
    }

    @Test
    public void testViewAvailableEmployees() {
        List roles = new ArrayList<Role>();
        roles.add(Role.Cashier);
        String name = "Sponge Bob";
        String ID = "123456789";
        int bankId = 10;
        int branchId = 100;
        int accountNumber = 1000;
        float salary = 5000;
        LocalDate date = LocalDate.now();
        String trustFund = "Trust Fund";
        int freeDays = 10;
        int sickDays = 15;
        Preference[][] timeFrames1 = new Preference[7][2];
        for (int i = 0; i < 7; i++)
            for (int j = 0; j < 2; j++)
                timeFrames1[i][j] = Preference.WANT;
        Preference[][] timeFrames2 = new Preference[7][2];
        for (int i = 0; i < 7; i++)
            for (int j = 0; j < 2; j++)
                timeFrames2[i][j] = Preference.CAN;
        Preference[][] timeFrames3 = new Preference[7][2];
        for (int i = 0; i < 7; i++)
            for (int j = 0; j < 2; j++)
                timeFrames3[i][j] = Preference.CANT;

        employeeController.addEmployee("Sandy Cheeks", ID, bankId, branchId, accountNumber, salary, date, trustFund, freeDays, sickDays, roles, timeFrames1);
        employeeController.addEmployee("Sponge Bob", "1", bankId, branchId, accountNumber, salary, date, trustFund, freeDays, sickDays, roles, timeFrames2);
        employeeController.addEmployee("Patrick Star", "2", bankId, branchId, accountNumber, salary, date, trustFund, freeDays, sickDays, roles, timeFrames3);
        try {
            employeeController.login("123456789");
        } catch (Exception e) {
            e.printStackTrace();
        }

        Map<String, String> emps = employeeController.viewAvailableEmployees(0, true, Role.Cashier);
        assertEquals(2, emps.size());
        assertTrue(emps.get(ID).contains("Sandy Cheeks"));
        assertTrue(emps.get(ID).contains("WANTS"));
        assertFalse(emps.get(ID).contains("Sponge Bob"));
        assertTrue(emps.get("1").contains("Sponge Bob"));
        assertTrue(emps.get("1").contains("CAN"));
        assertFalse(emps.get("1").contains("Sandy Cheeks"));
        assertFalse(emps.containsKey("2"));
    }
}
