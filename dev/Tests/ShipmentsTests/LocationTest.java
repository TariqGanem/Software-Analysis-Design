package ShipmentsTests;

import BusinessLayer.Response;
import BusinessLayer.ShipmentsModule.Facade;
import DataAccessLayer.dbMaker;
import org.junit.Before;
import org.junit.Test;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import static org.junit.Assert.assertEquals;

public class LocationTest {
    private Facade data;

    @Before
    public void setUp() {
        data = new Facade();
        try (Connection con = DriverManager.getConnection(dbMaker.path)) {
            String sqlStatement = "delete from Locations";
            PreparedStatement p = con.prepareStatement(sqlStatement);
            p.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void testAddLocation() {
        int numOfLocations = data.getAllLocations().getValue().size();
        data.addLocation("Haifa, 12", "0509691235", "Yazan");
        data.addLocation("Beersheva, Alexander Yennai 17", "0509991523", "Tariq");
        assertEquals(numOfLocations + 2, data.getAllLocations().getValue().size());
        assertEquals("Beersheva, Alexander Yennai 17", data.getAllLocations().getValue().get(1).getAddress());
        assertEquals("0509991523", data.getAllLocations().getValue().get(1).getPhoneNumber());
        assertEquals("Tariq", data.getAllLocations().getValue().get(1).getContactName());
    }

    @Test
    public void testNullFields() {
        Response res1 = data.addLocation("lol_1", null, "Tariq");
        Response res2 = data.addLocation(null, "0509489122", "Tariq");
        Response res3 = data.addLocation("lol_2", "0508115512", null);
        assertEquals("Couldn't add new location - Invalid parameters", res1.getErrorMessage());
        assertEquals("Couldn't add new location - Invalid parameters", res2.getErrorMessage());
        assertEquals("Couldn't add new location - Invalid parameters", res3.getErrorMessage());
        assertEquals(0, data.getAllLocations().getValue().size());
    }

    @Test
    public void testInvalidArgs() {
        Response res1 = data.addLocation("  ", "0509991523", "asd");
        Response res2 = data.addLocation("lol_3", " ", "dsa");
        Response res3 = data.addLocation("lol_4", "Yazan", " ");
        assertEquals("Couldn't add new location - Invalid parameters", res1.getErrorMessage());
        assertEquals("Couldn't add new location - Invalid parameters", res2.getErrorMessage());
        assertEquals("Couldn't add new location - Invalid parameters", res3.getErrorMessage());
        assertEquals(0, data.getAllLocations().getValue().size());
    }
}