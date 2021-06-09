package PresentationLayer.ShipmentsMenu.Handlers;

import BusinessLayer.ShipmentsModule.Builder;
import BusinessLayer.ShipmentsModule.Facade;
import BusinessLayer.Response;
import BusinessLayer.ResponseT;
import BusinessLayer.ShipmentsModule.Objects.Item;
import DTOPackage.DriverDTO;
import DTOPackage.ItemDTO;
import DTOPackage.ShipmentDTO;
import DTOPackage.TruckDTO;

import java.util.*;

public class ShipmentsHandler extends Handler {

    private Facade facade;
    private LocationsHandler locationsHandler;
    private TrucksHandler trucksHandler;
    private DriversHandler driversHandler;
    private List<ShipmentDTO> shipments;

    public ShipmentsHandler(Facade facade) {
        this.facade = facade;
        locationsHandler = new LocationsHandler(facade);
        trucksHandler = new TrucksHandler(facade);
        driversHandler = new DriversHandler(facade);
        shipments = new LinkedList<>();
    }

    public void arrangeShipment() {
        System.out.printf("\nEnter order due date [dd/MM/yyyy]: ");
        Date date = getDate();
        System.out.printf("Enter shipment source: \n");
        locationsHandler.viewAllLocations();
        int source = locationsHandler.chooseLocation().getId();
        System.out.printf("Add a list of destinations in order: \n");
        Map<Integer, List<ItemDTO>> itemsPerDestination = getItemsPerDestination();
        double shipmentWeight = calculateShipmentWeight(itemsPerDestination);
        System.out.println("\n Your shipment weight is currently [ " + shipmentWeight + " ] Kg\n");
        Response res = facade.arrangeDelivery(date, source, itemsPerDestination);
        if (res.getErrorOccurred())
            printer.error(res.getErrorMessage());
        else {
            printer.success("Shipment has been arranged!");
        }
    }

    public void viewAllShipments() {
        ResponseT<List<ShipmentDTO>> res = facade.getAllShipments();
        shipments = res.getValue();
        if (res.getErrorOccurred())
            printer.error(res.getErrorMessage());
        else {
            printer.viewAllShipments(shipments);
        }
    }

    public void trackShipment() {
        System.out.printf("\nEnter tracking number: ");
        int trackingNumber = getInt();
        ResponseT<ShipmentDTO> res = facade.trackShipment(trackingNumber);
        if (res.getErrorOccurred())
            printer.error(res.getErrorMessage());
        else {
            printer.viewShipment(res.getValue(), trackingNumber);
        }
    }

    public void removeShipment() {
        System.out.printf("\nEnter shipment date [dd/MM/yyyy]: ");
        Date date = getDate();
        System.out.printf("Enter departure hour [00:00]: ");
        String hour = scanner.nextLine();
        System.out.printf("Enter driver's id: ");
        String id = scanner.nextLine();
        Response res = facade.removeShipment(date, hour, id);
        if (res.getErrorOccurred()){
            printer.error(res.getErrorMessage());
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
                itemName = scanner.nextLine();
                System.out.printf("\tItem's Weight: ");
                weight = getDouble();
                System.out.printf("\tItem's Amount: ");
                amount = getInt();
                System.out.println();
                items.add(new ItemDTO(itemName, amount, weight));
                printer.success("Item has been added!");
                System.out.printf("\nAdd another item? [y/n]: ");
                String another = scanner.nextLine();
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
                String another = scanner.nextLine();
                System.out.println();
                if (!another.equals("y"))
                    break;
            } catch (Exception e) {
                printer.error("Enter valid destination details!");
            }
        }
        return itemsPerDestination;
    }

    private double calculateShipmentWeight(Map<Integer, List<ItemDTO>> items_per_location) {
        double shipmentWeight = 0;
        for (Integer loc : items_per_location.keySet()) {
            for (ItemDTO item : items_per_location.get(loc)) {
                shipmentWeight += item.getWeight() * item.getAmount();
            }
        }
        return shipmentWeight;
    }
}
