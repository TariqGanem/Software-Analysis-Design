package ShipmentsMenu;

import BusinessLayer.Facade;
import BusinessLayer.Response;

import java.text.SimpleDateFormat;
import java.util.*;

public class DataInitializer {

    Facade facade;

    public DataInitializer(Facade f) {
        facade = f;
    }


    public void initialize() {
        initTrucks();
        initDrivers();
        initLocations();
        initShipments();
    }

    public void initTrucks() {
        facade.addTruck("HRY1234", "FH16", 6000, 3000);
        facade.addTruck("HYTE779", "GH67", 5000, 2500);
        facade.addTruck("7783624", "EIF", 8000, 1560);
    }

    public void initDrivers() {
        facade.addDriver("20816222", "Yazan", 7000);
        facade.addDriver("20877734", "Tariq", 9000);
        facade.addDriver("31556888", "Kareem", 15000);
    }

    public void initLocations() {
        facade.addLocation("Beer Sheva, Alexander Yenai 17", "0503313300", "Yazan");
        facade.addLocation("Tel Aviv, Merkaz 53", "0774353553", "Kareem");
        facade.addLocation("Haifa hof hakarmel", "055853535", "Tariq");
    }

    public void initShipments() {
        Date date = null;
        try {
            date = new SimpleDateFormat("dd/MM/yyyy").parse("22/07/2021");
        } catch (Exception e) {
        }

        Map<String, Map<String, List<Double>>> iPerl = new HashMap<>();

        Map<String, List<Double>> itemInfo1 = new HashMap<>();
        List<Double> items1 = new LinkedList<>();
        items1.add(4.6);
        items1.add(2.0);
        itemInfo1.put("Milk", items1);
        iPerl.put("Tel Aviv, Merkaz 53", itemInfo1);


        Map<String, List<Double>> itemInfo2 = new HashMap<>();
        List<Double> items2 = new LinkedList<>();
        items2.add(6.7);
        items2.add(9.0);
        itemInfo2.put("Bamba", items2);
        iPerl.put("Haifa hof hakarmel", itemInfo2);


        Response res = facade.arrangeDelivery(date, "14:00", "Beer Sheva, Alexander Yenai 17", iPerl);
        if (res.errorOccured())
            System.out.println(res.getMsg());
    }
}
