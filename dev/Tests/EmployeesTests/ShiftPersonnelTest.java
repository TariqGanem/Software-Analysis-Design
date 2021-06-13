package EmployeesTests;

import BusinessLayer.EmployeesModule.ShiftPackage.ShiftPersonnel;
import DataAccessLayer.EmployeesModule.DALController;
import DataAccessLayer.dbMaker;
import Resources.Role;
import org.junit.Before;
import org.junit.Test;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;


public class ShiftPersonnelTest {
    private ShiftPersonnel sp;

    @Before
    public void setup() {
        sp = new ShiftPersonnel(new DALController());
        try (Connection con = DriverManager.getConnection(dbMaker.path)) {
            String sqlStatement = sqlStatement = "delete from ShiftPersonnel;";
            PreparedStatement p = con.prepareStatement(sqlStatement);
            p.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void testSetQtty() {
        try {
            sp.setQtty(6, false, Role.Cashier, 2);
            fail("no shift on friday night. should've fail.");
        } catch (Exception ignored) {
        }
        try {
            sp.setQtty(8, true, Role.HRManager, 2);
            fail("day num 1-6.");
        } catch (Exception ignored) {
        }
        try {
            sp.setQtty(6, false, Role.StoreManagerAssistant, -1);
            fail("negative quantity.");
        } catch (Exception ignored) {
        }
        sp.setQtty(6, true, Role.Stocker, 2);
        assertTrue(sp.getQtty(6, true).get(Role.Stocker) == 2);
        sp.setQtty(6, true, Role.Stocker, 1);
        assertTrue(sp.getQtty(6, true).get(Role.Stocker) == 1);
    }

    @Test
    public void testGetQtty() {
        try {
            sp.getQtty(6, false).get(Role.Stocker);
            fail("no shift on friday night. should've fail.");
        } catch (Exception ignored) {
        }
        try {
            sp.getQtty(8, true).get(Role.Stocker);
            fail("day num 1-6.");
        } catch (Exception ignored) {
        }
        try {
            sp.getQtty(6, false).get(Role.Stocker);
            fail("negative quantity.");
        } catch (Exception ignored) {
        }
        assertTrue(sp.getQtty(6, true).get(Role.Stocker) == 1);//check default values
        sp.setQtty(6, true, Role.Stocker, 2);
        assertTrue(sp.getQtty(6, true).get(Role.Stocker) == 2);
    }
}