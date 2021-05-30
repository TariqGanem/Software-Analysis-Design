package EmployeesTests;

import BusinessLayer.EmployeesModule.EmployeePackage.Employee;
import DataAccessLayer.dbMaker;
import Resources.Preference;
import Resources.Role;
import org.junit.Before;
import org.junit.Test;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.*;

public class EmployeeTest {
    private Employee employee;

    @Before
    public void setUp() {
        List<Role> roles = new ArrayList<>();
        roles.add(Role.Cashier);
        employee = new Employee("Sponge Bob", "123456789", 10, 100, 1000, 5000, LocalDate.now(),
                "Trust Fund", 10, 15, roles, new Preference[7][2]);
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
    public void testSetBankId() {
        try {
            employee.setBankId(-1);
            fail("Allowed bankId to be set to -1.");
        } catch (Exception e) {
            String expectedMessage = "The bankId can't be lower than 0.";
            String actualMessage = e.getMessage();

            assertTrue(e instanceof IllegalArgumentException);
            assertEquals(actualMessage, expectedMessage);
        }
    }

    @Test
    public void testSetBranchId() {
        try {
            employee.setBranchId(-1);
            fail("Allowed branchId to be set to -1.");
        } catch (Exception e) {
            String expectedMessage = "The branchId can't be lower than 0.";
            String actualMessage = e.getMessage();

            assertTrue(e instanceof IllegalArgumentException);
            assertEquals(actualMessage, expectedMessage);
        }
    }

    @Test
    public void testSetAccountNumber() {
        try {
            employee.setAccountNumber(-1);
            fail("Allowed accountNumber to be set to -1.");
        } catch (Exception e) {
            String expectedMessage = "The accountNumber can't be lower than 0.";
            String actualMessage = e.getMessage();

            assertTrue(e instanceof IllegalArgumentException);
            assertEquals(actualMessage, expectedMessage);
        }
    }

    @Test
    public void testSetSalary() {
        try {
            employee.setSalary(-1);
            fail("Allowed salary to be set to -1.");
        } catch (Exception e) {
            String expectedMessage = "The salary can't be lower than 0.";
            String actualMessage = e.getMessage();

            assertTrue(e instanceof IllegalArgumentException);
            assertEquals(actualMessage, expectedMessage);
        }
    }

    @Test
    public void testSetStartDay() {
        try {
            employee.setStartDate(LocalDate.of(2100, 5, 5));
            fail("Allowed startDate to be set to the future.");
        } catch (Exception e) {
            String expectedMessage = "The startDate can't be after today.";
            String actualMessage = e.getMessage();

            assertTrue(e instanceof IllegalArgumentException);
            assertEquals(actualMessage, expectedMessage);
        }
    }

    @Test
    public void testSetSickDays() {
        try {
            employee.setSickDays(-1);
            fail("Allowed sickDays to be set to -1.");
        } catch (Exception e) {
            String expectedMessage = "The sickDays can't be lower than 0.";
            String actualMessage = e.getMessage();

            assertTrue(e instanceof IllegalArgumentException);
            assertEquals(actualMessage, expectedMessage);
        }
    }

    @Test
    public void testSetFreeDays() {
        try {
            employee.setFreeDays(-1);
            fail("Allowed freeDays to be set to -1.");
        } catch (Exception e) {
            String expectedMessage = "The freeDays can't be lower than 0.";
            String actualMessage = e.getMessage();

            assertTrue(e instanceof IllegalArgumentException);
            assertEquals(actualMessage, expectedMessage);
        }
    }

    @Test
    public void testSetTimeFrames() {
        try {
            employee.setTimeFrames(new Preference[11][3]);
            fail("Allowed timeFrames to be set to an array of lengths other than 7 and 2.");
        } catch (Exception e) {
            String expectedMessage = "Time preferences must correlate to the shifts in a week.";
            String actualMessage = e.getMessage();

            assertTrue(e instanceof IllegalArgumentException);
            assertEquals(actualMessage, expectedMessage);
        }
    }

    @Test
    public void testHasSkill() {
        assertTrue(employee.hasSkill(Role.Cashier));
    }

    @Test
    public void testHasSkillAfterChange() {
        List<Role> roles = new ArrayList<>();
        roles.add(Role.StoreManager);
        employee.setSkills(roles);

        assertFalse(employee.hasSkill(Role.Cashier));
        assertTrue(employee.hasSkill(Role.StoreManager));
    }
}