import ShipmentsModule.Facade;
import ShipmentsModule.Response;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;

public class LocationTest {
    private Facade data;

    @Before
    public void setUp() {
        data = new Facade();
    }

    @Test
    public void testAddDriver() {
        data.addLocation("Beersheva, Alexander Yennai 17", "0509991523", "Tariq");
        assertEquals(1, data.getAllLocations().getValue().size());
        assertEquals("Beersheva, Alexander Yennai 17", data.getAllLocations().getValue().get(0).getAddress());
        assertEquals("0509991523", data.getAllLocations().getValue().get(0).getPhoneNumber());
        assertEquals("Tariq", data.getAllLocations().getValue().get(0).getContactName());
    }

    @Test
    public void testAddSameAddress() {
        data.addLocation("Beersheva, Alexander Yennai 17", "0509991523", "Tariq");
        Response res = data.addLocation("Beersheva, Alexander Yennai 17", "0509991523", "Tariq");
        assertEquals("Couldn't add new location - address already exists", res.getMsg());
        assertNotEquals(2, data.getAlldrivers().getValue().size());
    }

    @Test
    public void testNotValidFields() {
        Response res1 = data.addLocation("  ", "0509991523", "Tariq");
        Response res2 = data.addLocation("Beersheva, Alexander Yennai 17", null, "Tariq");
        Response res3 = data.addLocation("Beersheva, Alexander Yennai 17", "Yazan", null);
        assertEquals("Couldn't add new location - Invalid parameters", res1.getMsg());
        assertEquals("Couldn't add new location - Invalid parameters", res2.getMsg());
        assertEquals("Couldn't add new location - Invalid parameters", res3.getMsg());
        assertEquals(0, data.getAllLocations().getValue().size());
    }

    @After
    public void tearDown() {
        data = new Facade();
    }
}