package PresentationLayer.ShipmentsMenu.Handlers;

import APIs.StoreShipmentAPI;
import BusinessLayer.Response;
import BusinessLayer.ResponseT;
import BusinessLayer.ShipmentsModule.Facade;
import DTOPackage.ShipmentDTO;
import DTOPackage.ShippedItemDTO;

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
        Map<Integer, List<ShippedItemDTO>> itemsPerDestination = getItemsPerDestination();
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
        if (res.getErrorOccurred()) {
            printer.error(res.getErrorMessage());
        } else {
            printer.success("Shipment has been removed!");
        }
    }

    private List<ShippedItemDTO> getItems() {
        List<ShippedItemDTO> items = new LinkedList<>();
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
                items.add(new ShippedItemDTO(itemName, amount, weight));
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

    private Map<Integer, List<ShippedItemDTO>> getItemsPerDestination() {
        Map<Integer, List<ShippedItemDTO>> itemsPerDestination = new HashMap<>();
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

    private double calculateShipmentWeight(Map<Integer, List<ShippedItemDTO>> items_per_location) {
        double shipmentWeight = 0;
        for (Integer loc : items_per_location.keySet()) {
            for (ShippedItemDTO item : items_per_location.get(loc)) {
                shipmentWeight += item.getWeight() * item.getAmount();
            }
        }
        return shipmentWeight;
    }

    public void approveShipments() {
        System.out.println("Shipments waiting for approval:");
        ResponseT res = facade.getNotApprovedShipments();
        if (res.getErrorOccurred())
            printer.error(res.getErrorMessage());
        else {
            List<ShipmentDTO> shipmentsList = facade.getNotApprovedShipments().getValue();
            printer.viewAllShipments(shipmentsList);
            if (!shipmentsList.isEmpty()) {
                System.out.println("\nSelect shipment index to approve it, to approve all type 'all':\n");
                int index = getShipmentInt(shipmentsList.size());
                Response resEach = null;
                if (index == -1) {
                    for (ShipmentDTO shipment : shipmentsList) {
                        resEach = facade.approveShipment(shipment.getDate(),
                                shipment.getDepartureHour(),
                                shipment.getDriverId());
                        if (resEach.getErrorOccurred()) {
                            printer.error(resEach.getErrorMessage());
                        }

                        new StoreShipmentAPI().updateAmounts(facade.getReceivedItems(shipment.getShipmentId()).getValue());
                    }
                    printer.success("All the shipment are now approved!");
                } else {
                    resEach = facade.approveShipment(shipmentsList.get(index - 1).getDate(),
                            shipmentsList.get(index - 1).getDepartureHour(),
                            shipmentsList.get(index - 1).getDriverId());
                    new StoreShipmentAPI().updateAmounts(facade.getReceivedItems(shipmentsList.get(index - 1).getShipmentId()).getValue());
                    if (resEach.getErrorOccurred()) {
                        printer.error(resEach.getErrorMessage());
                    } else {
                        printer.success("Shipment you have selected is now approved!");
                    }
                }
            }
        }
    }

    private int getShipmentInt(int max_size) {
        while (true) {
            try {
                String line = scanner.next();
                if (line.equals("all")) {
                    return -1;
                }
                int index = Integer.parseInt(line);
                if (index - 1 < 0 || index > max_size) {
                    printer.error("Choose index between 1 and " + max_size);
                } else {
                    return index;
                }
            } catch (Exception e) {
                printer.error("Enter only natural numbers");
            }
        }
    }


}
