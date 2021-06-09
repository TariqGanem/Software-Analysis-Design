package EmployeesTests;

import BusinessLayer.EmployeesModule.EmployeePackage.Employee;
import BusinessLayer.EmployeesModule.ShiftPackage.Shift;
import BusinessLayer.EmployeesModule.ShiftPackage.ShiftController;
import DataAccessLayer.EmployeesModule.DALController;
import DataAccessLayer.dbMaker;
import Resources.Role;
import org.junit.Before;
import org.junit.Test;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.Calendar;

import static org.junit.Assert.*;

public class ShiftControllerTest {
    private ShiftController sc;
    private Employee emp;

    @Before
    public void setup() {
        sc = new ShiftController(new DALController());
        try (Connection con = DriverManager.getConnection(dbMaker.path)) {
            String sqlStatement = "delete from Shift;";
            PreparedStatement p = con.prepareStatement(sqlStatement);
            p.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void testGetShift() {
        Shift shift;
        sc.addShift(LocalDate.now(), false);
        try {
            Shift s = sc.getShift(LocalDate.now(), true);
            assertNull(s);
        } catch (Exception ignored) {
        }
        shift = sc.getShift(LocalDate.now(), false);
        assertNotNull(shift);
    }

    @Test
    public void testAssignToShift() {
        sc.addShift(LocalDate.now(), false);
        Shift s = sc.getShift(LocalDate.now(), false);
        boolean ans = sc.AssignToShift("1234", Role.ShiftManager);
        assertTrue(ans);
        assertEquals(s.isAssignedToShift("1234"), Role.ShiftManager);
        try {
            sc.AssignToShift("1234", Role.ShiftManager);
            fail("should warn the manager that the amount of shift manager is over full");
        } catch (Exception ignored) {
        }
        assertEquals(s.isAssignedToShift("1234"), Role.ShiftManager);
    }

    @Test
    public void testRemoveFromShift() {
        sc.addShift(LocalDate.now(), false);
        try {
            sc.removeFromShift("1234");
            fail("should'nt be able to remove from empty shift.");
        } catch (Exception ignored) {
        }
        try {
            sc.AssignToShift("1234", Role.ShiftManager);
            sc.removeShift(LocalDate.now(), false);
            sc.removeFromShift("1234");
            fail("should'nt be able to remove with no active shift.");
        } catch (Exception ignored) {
        }
        sc.addShift(LocalDate.now(), false);
        sc.AssignToShift("1234", Role.ShiftManager);
        try {
            assertTrue(sc.removeFromShift("1234"));
            fail("removed the last shift manager in the shift");
        } catch (Exception ignored) {
        }
    }

    @Test
    public void testAddShift() {
        int day = (LocalDate.now().getDayOfWeek().getValue() + 1) % 7;
        LocalDate date = LocalDate.now().plusDays(7 - day);//saturday
        try {
            sc.addShift(date, false);
            fail("day num 1-6.");
        } catch (Exception ignored) {
        }
        date = LocalDate.now().plusDays(6 - day);
        System.out.println(date);
        try {
            sc.addShift(date, false);
            fail("no shift on friday night. should've fail.");
        } catch (Exception ignored) {
        }
        assertTrue(sc.addShift(date, true));
    }

    @Test
    public void testRemoveShift() {
        Calendar cal = Calendar.getInstance();
        cal.setTime(cal.getTime());
        int day = cal.get(Calendar.DAY_OF_WEEK) + 7;
        LocalDate date = LocalDate.now().plusDays(5 - day);
        sc.addShift(date, true);
        sc.AssignToShift("admin", Role.ShiftManager);
        assertTrue(sc.removeShift(date, true));
        assertFalse(sc.removeShift(date, true));
    }
}