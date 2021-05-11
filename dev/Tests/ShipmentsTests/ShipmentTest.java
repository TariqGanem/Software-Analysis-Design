package ShipmentsTests;

import BusinessLayer.ShipmentsModule.Facade;
import DTOPackage.ItemDTO;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.text.SimpleDateFormat;
import java.util.*;


public class ShipmentTest {
    private Facade data;

    @Before
    public void setUp() {
        data = new Facade();
    }

    @Test
    public void testArrangeDelivery() {
        Date date = null;
        try {
            date = new SimpleDateFormat("dd/MM/yyyy").parse("04/07/2021");
        } catch (Exception e) {
        }
        String departureHour = "11:07";
        String source = "Beersheva, Alexander Yennai 17";
        String dest = "Haifa, 12";
        Map<Integer, List<ItemDTO>> items_per_locs = new HashMap<>();
        List<ItemDTO> list =  new LinkedList<>();
        list.add(new ItemDTO("bamba", 2, 1));
        list.add(new ItemDTO("choco", 1, 1));
        items_per_locs.put(data.getAllLocations().getValue().get(1).getId(), list);
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
    }

    @After
    public void tearDown() {
        data = new Facade();
    }

}