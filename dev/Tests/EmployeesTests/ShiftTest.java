package EmployeesTests;

import BusinessLayer.EmployeesModule.ShiftPackage.Shift;
import DataAccessLayer.dbMaker;
import Resources.Role;
import org.junit.Before;
import org.junit.Test;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.time.LocalDate;

import static org.junit.Assert.*;

public class ShiftTest {

    private Shift shift;

    @Before
    public void setup() {
        shift = new Shift(LocalDate.now(), true);
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
            sqlStatement = "delete from ShiftPersonnel;";
            p = con.prepareStatement(sqlStatement);
            p.executeUpdate();
            sqlStatement = "delete from Shift;";
            p = con.prepareStatement(sqlStatement);
            p.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void testAssignEmployee() {

        try {
            shift.assignEmployee(Role.Stocker, "1234");
            fail("should assign shift manager first.");
        } catch (Exception ignored) {
        }
        shift.assignEmployee(Role.ShiftManager, "1234");
        assertEquals(shift.isAssignedToShift("1234"), Role.ShiftManager);
    }

    @Test
    public void testRemoveFromShift() {
        shift.assignEmployee(Role.ShiftManager, "1234");
        assertEquals(shift.isAssignedToShift("1234"), Role.ShiftManager);
        shift.assignEmployee(Role.Cashier, "2345");
        assertEquals(shift.isAssignedToShift("2345"), Role.Cashier);
        shift.removeFromShift("2345");
        assertNull(shift.isAssignedToShift("2345"));
    }
}