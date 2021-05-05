import BusinessLayer.ShipmentsModule.Facade;
import BusinessLayer.ShipmentsModule.Response;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.text.SimpleDateFormat;
import java.util.*;

import static org.junit.Assert.assertEquals;

public class ShipmentTest {
    private Facade data;

    @Before
    public void setUp() {
        data = new Facade();
        data.addLocation("Beersheva, Alexander Yennai 17", "000", "Tariq");
        data.addLocation("Tel Aviv, Merkaz 53", "1111", "Yazan");
        data.addDriver("315526640", "Tariq", 10000);
        data.addTruck("00000", "BMW", 1000, 2000);
    }

    @Test
    public void testArrangeDelivery() {
        Date date = null;
        try {
            date = new SimpleDateFormat("dd/MM/yyyy").parse("4/10/2021");
        } catch (Exception e) {
        }
        String departureHour = "20:30";
        String source = "Beersheva, Alexander Yennai 17";

        Map<String, Map<String, List<Double>>> iPerl = new HashMap<>();
        Map<String, List<Double>> itemInfo = new HashMap<>();
        List<Double> items1 = new LinkedList<>();
        items1.add(3.5);
        items1.add(2.0);
        itemInfo.put("Milk", items1);
        iPerl.put("Tel Aviv, Merkaz 53", itemInfo);
        data.arrangeDelivery(date, departureHour, source, iPerl);
        assertEquals(1, data.getAllShipments().getValue().size());
        assertEquals("Beersheva, Alexander Yennai 17", data.getAllShipments().getValue().get(0).getSource().getAddress());
        assertEquals("Tel Aviv, Merkaz 53", data.getAllShipments().getValue().get(0).getLocations().get(0).getAddress());
        assertEquals(7.0, data.getAllShipments().getValue().get(0).getShipmentWeight(), 0.0);
    }

    @Test
    public void testNotValidFields() {
        Date date = null;
        try {
            date = new SimpleDateFormat("dd/MM/yyyy").parse("4/10/2021");
        } catch (Exception e) {
        }
        String departureHour = "20:30";
        String source = "Non available location";

        Map<String, Map<String, List<Double>>> iPerl = new HashMap<>();
        Map<String, List<Double>> itemInfo = new HashMap<>();
        List<Double> items1 = new LinkedList<>();
        items1.add(3.5);
        items1.add(2.0);
        itemInfo.put("Milk", items1);
        iPerl.put("Tel Aviv, Merkaz 53", itemInfo);
        Response res = data.arrangeDelivery(date, departureHour, source, iPerl);
        assertEquals("No such location", res.getMsg());
    }

    @After
    public void tearDown() {
        data = new Facade();
    }

}