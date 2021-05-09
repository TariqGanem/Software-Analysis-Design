package PresentationLayer.ShipmentsMenu.Handlers;

import BusinessLayer.ShipmentsModule.Facade;
import BusinessLayer.ShipmentsModule.Response;
import BusinessLayer.ShipmentsModule.ResponseT;
import DTOPackage.ItemDTO;
import DTOPackage.ShipmentDTO;

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
        int source = locationsHandler.chooseLocation().getId();
        System.out.printf("Add a list of destinations in order: \n");
        Map<Integer, List<ItemDTO>> itemsPerDestination = getItemsPerDestination();
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
            printer.viewShipment(res.getValue(), trackingNumber);
        }
    }

    public void removeShipment() {
        System.out.printf("\nEnter shipment date [dd/MM/yyyy]: ");
        Date date = getDate();
        System.out.printf("Enter departure hour [00:00]: ");
        String hour = scanner.next();
        System.out.printf("Enter driver's id: ");
        String id = scanner.next();
        Response res = facade.removeShipment(date, hour, id);
        if (res.errorOccured()) {
            printer.error(res.getMsg());
        } else {
            printer.success("Shipment has been removed!");
        }
    }

    private List<ItemDTO> getItems() {
        List<ItemDTO> items = new LinkedList<>();
        String itemName;
        double weight;
        double amount;
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
                items.add(new ItemDTO(itemName, amount, weight));
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

    private Map<Integer, List<ItemDTO>> getItemsPerDestination() {
        Map<Integer, List<ItemDTO>> itemsPerDestination = new HashMap<>();
        int locId;
        while (true) {
            try {
                System.out.printf("\n\tChoose destination number [" + (itemsPerDestination.size() + 1) + "]:\n");
                locationsHandler.viewAllLocations();
                locId = locationsHandler.chooseLocation().getId();
                System.out.println();
                itemsPerDestination.put(locId, getItems());
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
