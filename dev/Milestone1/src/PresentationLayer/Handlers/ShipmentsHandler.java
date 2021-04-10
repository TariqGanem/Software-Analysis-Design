package PresentationLayer.Handlers;

import BusinessLayer.Facade;
import BusinessLayer.Response;
import BusinessLayer.ResponseT;
import DTOs.DocumentDTO;
import DTOs.ShipmentDTO;

import java.util.*;

public class ShipmentsHandler extends Handler {

    private Facade facade;
    private LocationsHandler locationsHandler;
    private List<ShipmentDTO> shipments;
    public ShipmentsHandler(Facade facade) {
        this.facade = facade;
        locationsHandler = new LocationsHandler(facade);
        shipments = new LinkedList<>();
    }

    public void arrangeShipment() {
        System.out.printf("\nEnter shipment date [dd/MM/yyyy]: ");
        Date date = getDate();
        System.out.printf("Enter departure hour [00:00]: ");
        String hour = scanner.next();
        System.out.printf("Enter shipment source: \n");
        locationsHandler.viewAllLocations();
        String source = locationsHandler.chooseLocation().getAddress();
        System.out.printf("Add a list of destinations in order: \n");
        Map<String, Map<String, List<Double>>> itemsPerDestination = getItemsPerDestination();
        System.out.println();
        Response res = facade.arrangeDelivery(date, hour, source, itemsPerDestination);
        if (res.errorOccured())
            printer.error(res.getMsg());
        else {
            printer.success("Shipment has been arranged!");
        }
    }

    public void viewAllShipments() {
        ResponseT<List<ShipmentDTO>> res = facade.getAllShipments();
        shipments = res.getValue();
        if (res.errorOccured())
            printer.error(res.getMsg());
        else {
            printer.viewAllShipments(shipments);
        }
    }

    public void trackShipment() {
        System.out.printf("\nEnter tracking number: ");
        int trackingNumber = getInt();
        ResponseT<ShipmentDTO> res = facade.trackShipment(trackingNumber);
        if (res.errorOccured())
            printer.error(res.getMsg());
        else {
            DocumentDTO doc = res.getValue().getDocuments().get(trackingNumber);
            printer.viewShipment(res.getValue(), doc);
        }
    }

    public void removeShipment(){
        System.out.printf("\nEnter shipment date [dd/MM/yyyy]: ");
        Date date = getDate();
        System.out.printf("Enter departure hour [00:00]: ");
        String hour = scanner.next();
        System.out.printf("Enter driver's id: ");
        String id = scanner.next();
//        Response res = facade.removeShipment(date, hour, id);
//        if (res.errorOccured()) {
//            printer.error(res.getMsg());
//        } else {
//            printer.success("Shipment has been removed!");
//        }
    }

    private Map<String, List<Double>> getItems() {
        Map<String, List<Double>> items = new HashMap<>();
        String itemName;
        double weight;
        Integer amount;
        List<Double> amountAndWight;
        while (true) {
            try {
                System.out.printf("\n\tEnter details for item number [" + (items.size() + 1) + "]:\n");
                System.out.printf("\tItem's Name: ");
                itemName = scanner.next();
                System.out.printf("\tItem's Weight: ");
                weight = getDouble();
                System.out.printf("\tItem's Amount: ");
                amount = getInt();
                System.out.println();
                amountAndWight = new LinkedList<>();
                amountAndWight.add(weight);
                amountAndWight.add(Double.parseDouble(amount.toString()));
                items.put(itemName, amountAndWight);
                printer.success("Item has been added!");
                System.out.printf("\nAdd another item? [y/n]: ");
                String another = scanner.next();
                System.out.println();
                if (!another.equals("y"))
                    break;
            } catch (Exception e) {
                printer.error("Enter valid item details!");
            }
        }
        return items;
    }

    private Map<String, Map<String, List<Double>>> getItemsPerDestination() {
        Map<String, Map<String, List<Double>>> itemsPerDestination = new HashMap<>();
        String address;
        while (true) {
            try {
                System.out.printf("\n\tChoose destination number [" + (itemsPerDestination.size() + 1) + "]:\n");
                locationsHandler.viewAllLocations();
                address = locationsHandler.chooseLocation().getAddress();
                System.out.println();
                itemsPerDestination.put(address, getItems());
                printer.success("Destination has been added!");
                System.out.printf("\nAdd another destination? [y/n]: ");
                String another = scanner.next();
                System.out.println();
                if (!another.equals("y"))
                    break;
            } catch (Exception e) {
                printer.error("Enter valid destination details!");
            }
        }
        return itemsPerDestination;
    }
}
