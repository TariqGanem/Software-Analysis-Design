package PresentationLayer.Handlers;

import BusinessLayer.Facade;
import BusinessLayer.Response;
import BusinessLayer.ResponseT;
import DTOs.DocumentDTO;
import DTOs.ShipmentDTO;

import java.util.*;

public class ShipmentsHandler extends Handler {

    private Facade facade;

    public ShipmentsHandler(Facade facade) {
        this.facade = facade;
    }

    public void arrangeShipment() {
        System.out.printf("\nEnter shipment date [dd/MM/yyyy]: ");
        Date date = getDate();
        System.out.printf("Enter departure hour [00:00]: ");
        String hour = scanner.next();
        System.out.printf("Enter shipment source: ");
        String source = scanner.next();
        System.out.printf("Add a list of items for this shipment: \n");
        Map<String, List<Double>> items = getItems();
        System.out.printf("Add a list of destinations in order: \n");
        List<String> destinations = getDestinations();
        System.out.println();
        Response res = facade.arrangeDelivery(date, hour, source, items, destinations);
        if (res.errorOccured())
            printer.error(res.getMsg());
        else {
            printer.success("Shipment has been arranged!");
        }
    }

    public void viewAllShipments() {
        ResponseT<List<ShipmentDTO>> res = facade.getAllShipments();
        if (res.errorOccured())
            printer.error(res.getMsg());
        else {
            printer.viewAllShipments(res.getValue());
        }
    }

    public void trackShipment() {
        System.out.printf("\nEnter tracking number: ");
        int trackingNumber = getInt();
        ResponseT<ShipmentDTO> res = facade.trackShipment(trackingNumber);
        if (res.errorOccured())
            printer.error(res.getMsg());
        else {
            List<DocumentDTO> documents = res.getValue().getDocuments();
            for (DocumentDTO doc : documents) {
                if (doc.getTrackingNumber() == trackingNumber) {
                    printer.viewShipment(res.getValue(), doc);
                    break;
                }
            }
        }
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

    private List<String> getDestinations() {
        List<String> destinations = new LinkedList<>();
        String address;
        String phone;
        String contactName;
        while (true) {
            try {
                System.out.printf("\n\tEnter details for destination number [" + (destinations.size() + 1) + "]:\n");
                System.out.printf("\tAddress: ");
                address = scanner.next();
                System.out.printf("\tPhone Number: ");
                phone = scanner.next();
                System.out.printf("\tContact Name: ");
                contactName = scanner.next();
                System.out.println();
                destinations.add(address);
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
        return destinations;
    }
}
