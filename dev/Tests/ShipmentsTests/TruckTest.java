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
import static org.junit.Assert.assertNotEquals;

public class TruckTest {

    private Facade data;

    @Before
    public void setUp() {
        data = new Facade();
        try (Connection con = DriverManager.getConnection(dbMaker.path)) {
            String sqlStatement = "delete from Trucks";
            PreparedStatement p = con.prepareStatement(sqlStatement);
            p.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void testAddTruck() {
        int numOfTrucks = data.getAllTrucks().getValue().size();
        data.addTruck("ID_1", "BMW", 6.5, 12);
        assertEquals(numOfTrucks + 1, data.getAllTrucks().getValue().size());
        assertEquals("ID_1", data.getAllTrucks().getValue().get(0).getTruckPlateNumber());
        assertEquals("BMW", data.getAllTrucks().getValue().get(0).getModel());
        assertEquals(6.5, data.getAllTrucks().getValue().get(0).getNatoWeight(), 0.0);
        assertEquals(12, data.getAllTrucks().getValue().get(0).getMaxWeight(), 0.0);
    }

    @Test
    public void testAddTruckWithSameId() {
        Response res = data.addTruck("ID_1", "Volvo", 4, 8);
        assertEquals("Truck already exists!", res.getErrorMessage());
        assertNotEquals(2, data.getAllTrucks().getValue().size());
    }

    @Test
    public void testTruckWeight() {
        Response res1 = data.addTruck("ID_2", "BMW2", 6.5, 0);
        Response res2 = data.addTruck("ID_3", "BMW3", -1, -3);
        assertEquals("Couldn't add new truck - Illegal truck weight", res1.getErrorMessage());
        assertEquals("Couldn't add new truck - Illegal truck weight", res2.getErrorMessage());
        assertEquals(0, data.getAllTrucks().getValue().size());
    }

    @Test
    public void testEmptyOrNullFields() {
        Response res1 = data.addTruck("", "BMW", 6, 10);
        Response res2 = data.addTruck("123456", null, 5, 12);
        assertEquals("Couldn't add new truck - Invalid parameters", res1.getErrorMessage());
        assertEquals("Couldn't add new truck - Invalid parameters", res2.getErrorMessage());
        assertEquals(0, data.getAllTrucks().getValue().size());
    }
}