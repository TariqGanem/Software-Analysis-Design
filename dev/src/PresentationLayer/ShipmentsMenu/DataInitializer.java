package PresentationLayer.ShipmentsMenu;

import BusinessLayer.Response;
import BusinessLayer.ShipmentsModule.Facade;
import DTOPackage.ShippedItemDTO;

import java.text.SimpleDateFormat;
import java.util.*;

public class DataInitializer {

    Facade facade;

    public DataInitializer(Facade f) {
        facade = f;
    }


    public void initialize() {
        initTrucks();
        initLocations();
        initShipments();
    }

    private void initTrucks() {
        System.out.println("Loading Trucks:");
        Response res1 = facade.addTruck("HRY1234", "FH16", 6000, 3000);
        System.out.println(res1.getErrorOccurred() ? res1.getErrorMessage() : "Truck 1 is added successfully!");

        Response res2 = facade.addTruck("HYTE779", "GH67", 5000, 2500);
        System.out.println(res2.getErrorOccurred() ? res2.getErrorMessage() : "Truck 2 is added successfully!");

        Response res3 = facade.addTruck("1111111", "EIF", 8000, 1560);
        System.out.println(res3.getErrorOccurred() ? res3.getErrorMessage() : "Truck 3 is added successfully!");

        Response res4 = facade.addTruck("7777777", "EIF", 4500, 1560);
        System.out.println(res4.getErrorOccurred() ? res4.getErrorMessage() : "Truck 4 is added successfully!");
        System.out.println();
    }


    private void initLocations() {
        System.out.println("Loading Locations:");
        Response res1 = facade.addLocation("Beer Sheva, Alexander Yenai 17", "0503313300", "Yazan");
        System.out.println(res1.getErrorOccurred() ? res1.getErrorMessage() : "Location 1 is added successfully!");

        Response res2 = facade.addLocation("Tel Aviv, Merkaz 53", "0774353553", "Kareem");
        System.out.println(res2.getErrorOccurred() ? res2.getErrorMessage() : "Location 2 is added successfully!");

        Response res3 = facade.addLocation("Haifa hof hakarmel", "055853535", "Tariq");
        System.out.println(res3.getErrorOccurred() ? res3.getErrorMessage() : "Location 3 is added successfully!");

        Response res4 = facade.addLocation("Dead Sea, 4", "067888888", "John");
        System.out.println(res4.getErrorOccurred() ? res4.getErrorMessage() : "Location 4 is added successfully!");

        Response res5 = facade.addLocation("Natanya", "055535365", "sam");
        System.out.println(res5.getErrorOccurred() ? res5.getErrorMessage() : "Location 5 is added successfully!");

        System.out.println();
    }

    private void initShipments() {
//        System.out.println("Loading Shipments:");
//        initShipment1();
//        initShipment2();
//        System.out.println();
    }

    private void initShipment1() {
        Date date = null;
        try {
            date = new SimpleDateFormat("dd/MM/yyyy").parse("04/07/2021");
        } catch (Exception e) {
        }

        Map<Integer, List<ShippedItemDTO>> itemsPerLocation = new HashMap<>();
        List<ShippedItemDTO> items1 = new LinkedList<>();
        items1.add(new ShippedItemDTO("creama", 3, 9));
        items1.add(new ShippedItemDTO("weed", 6, 7));
        itemsPerLocation.put(1, items1);

        List<ShippedItemDTO> items2 = new LinkedList<>();
        items2.add(new ShippedItemDTO("apple", 1, 4));
        items2.add(new ShippedItemDTO("bana", 6, 9));
        itemsPerLocation.put(2, items2);

        Response res = facade.arrangeDelivery(date, 4, itemsPerLocation);
        System.out.println(res.getErrorOccurred() ? res.getErrorMessage() : "Shipment 1 is added successfully!");
    }

    public void initShipment2() {
        Date date = null;
        try {
            date = new SimpleDateFormat("dd/MM/yyyy").parse("05/07/2021");
        } catch (Exception e) {
        }

        Map<Integer, List<ShippedItemDTO>> itemsPerLocation = new HashMap<>();
        List<ShippedItemDTO> items1 = new LinkedList<>();
        items1.add(new ShippedItemDTO("Soda", 23, 4.8));
        items1.add(new ShippedItemDTO("weed", 6, 85));
        itemsPerLocation.put(2, items1);

        List<ShippedItemDTO> items2 = new LinkedList<>();
        items2.add(new ShippedItemDTO("sprite", 6, 3));
        items2.add(new ShippedItemDTO("cigars", 25, 3));
        itemsPerLocation.put(1, items2);

        Response res = facade.arrangeDelivery(date, 3, itemsPerLocation);
        System.out.println(res.getErrorOccurred() ? res.getErrorMessage() : "Shipment 2 is added successfully!");
    }
}
