package ShipmentsTests;

import BusinessLayer.ShipmentsModule.Facade;
import BusinessLayer.ShipmentsModule.Response;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;

public class TruckTest {

    private Facade data;

    @Before
    public void setUp() {
        data = new Facade();
    }

    @Test
    public void testAddTruck() {
        data.addTruck("12345", "BMW", 6.5, 12);
        assertEquals(1, data.getAllTrucks().getValue().size());
        assertEquals(data.getAllTrucks().getValue().get(0).getTruckPlateNumber(), "12345");
        assertEquals(data.getAllTrucks().getValue().get(0).getModel(), "BMW");
        assertEquals(6.5, data.getAllTrucks().getValue().get(0).getNatoWeight(), 0.0);
        assertEquals(12, data.getAllTrucks().getValue().get(0).getMaxWeight(), 0.0);
    }

    @Test
    public void testAddTruckWithSameId() {
        data.addTruck("12345", "BMW", 6.5, 12);
        Response res = data.addTruck("12345", "Volvo", 4, 8);
        assertEquals("Couldn't add new truck - truckPlateNumber already exists", res.getMsg());
        assertNotEquals(2, data.getAllTrucks().getValue().size());
    }

    @Test
    public void testTruckWeight() {
        Response res1 = data.addTruck("12345", "BMW", 6.5, 0);
        Response res2 = data.addTruck("123456", "BMWW", -1, -3);
        assertEquals("Couldn't add new truck - Illegal truck weight", res1.getMsg());
        assertEquals("Couldn't add new truck - Illegal truck weight", res2.getMsg());
        assertEquals(0, data.getAllTrucks().getValue().size());
    }

    @Test
    public void testEmptyOrNullFields() {
        Response res1 = data.addTruck("", "BMW", 6, 10);
        Response res2 = data.addTruck("123456", null, 5, 12);
        assertEquals("Couldn't add new truck - Invalid parameters", res1.getMsg());
        assertEquals("Couldn't add new truck - Invalid parameters", res2.getMsg());
        assertEquals(0, data.getAllTrucks().getValue().size());
    }

    @After
    public void tearDown() {
        data = new Facade();
    }
}