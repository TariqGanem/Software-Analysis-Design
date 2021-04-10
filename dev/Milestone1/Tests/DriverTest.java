import BusinessLayer.Facade;
import BusinessLayer.Response;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;


public class DriverTest {

    private Facade data;

    @Before
    public void setUp() {
        data = new Facade();
    }

    @Test
    public void testAddDriver() {
        data.addDriver("315526640", "Tariq", 12);
        assertEquals(1, data.getAlldrivers().getValue().size());
        assertEquals("315526640", data.getAlldrivers().getValue().get(0).getId());
        assertEquals("Tariq", data.getAlldrivers().getValue().get(0).getName());
        assertEquals(12, data.getAlldrivers().getValue().get(0).getAllowedWeight(), 0.0);
    }

    @Test
    public void testAddDriverWithSameId() {
        data.addDriver("315526640", "Tariq", 12);
        Response res = data.addDriver("315526640", "Yazan", 8);
        assertEquals("Couldn't add new driver - driverId already exists", res.getMsg());
        assertNotEquals(2, data.getAlldrivers().getValue().size());
    }

    @Test
    public void testNotValidFields() {
        Response res1 = data.addDriver("315526640", "", 12);
        Response res2 = data.addDriver(null, "Tariq", 12);
        Response res3 = data.addDriver("208167684", "Yazan", -1);
        assertEquals("Couldn't add new driver - Invalid parameters", res1.getMsg());
        assertEquals("Couldn't add new driver - Invalid parameters", res2.getMsg());
        assertEquals("Couldn't add new driver - Invalid parameters", res3.getMsg());
        assertEquals(0, data.getAlldrivers().getValue().size());
    }

    @After
    public void tearDown() {
        data = new Facade();
    }
}