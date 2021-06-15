package PresentationLayer.ShipmentsMenu;

import DTOPackage.*;

import java.text.SimpleDateFormat;
import java.util.Collection;
import java.util.List;

public class Printer {
    private static Printer instance = null;

    private Printer() {

    }

    public static Printer getInstance() {
        if (instance == null)
            instance = new Printer();
        return instance;
    }

    /**
     * Welcome message upon starting
     */
    public void printBanner() {
        System.out.println(
                " _    _      _                            _____       _____                             _               \n" +
                        "| |  | |    | |                          |_   _|     /  ___|                           | |              \n" +
                        "| |  | | ___| | ___ ___  _ __ ___   ___    | | ___   \\ `--. _   _ _ __  _ __   ___ _ __| |     ___  ___ \n" +
                        "| |/\\| |/ _ \\ |/ __/ _ \\| '_ ` _ \\ / _ \\   | |/ _ \\   `--. \\ | | | '_ \\| '_ \\ / _ \\ '__| |    / _ \\/ _ \\\n" +
                        "\\  /\\  /  __/ | (_| (_) | | | | | |  __/   | | (_) | /\\__/ / |_| | |_) | |_) |  __/ |  | |___|  __/  __/\n" +
                        " \\/  \\/ \\___|_|\\___\\___/|_| |_| |_|\\___|   \\_/\\___/  \\____/ \\__,_| .__/| .__/ \\___|_|  \\_____/\\___|\\___|\n" +
                        "                                                                 | |   | |                              \n" +
                        "                                                                 |_|   |_|                              ");
    }

    /**
     * Printing all menu options
     */
    public void viewOptions(List<String> items) {
        System.out.println("\nPlease select an option from the Menu:\n");
        for (int i = 0; i < items.size(); i++) {
            System.out.println(i + 1 + ". " + items.get(i));
        }
        System.out.println();
    }

    /**
     * Printing formal error message
     */
    public void error(String msg) {
        System.out.println("---ERROR---");
        System.out.println(msg);
    }

    public void success(String msg) {
        System.out.println("---SUCCESS---");
        System.out.println(msg);
    }

    public void viewAllTrucks(List<TruckDTO> trucks) {
        for (int i = 0; i < trucks.size(); i++) {
            System.out.println("\t" + (i + 1)
                    + ". Truck Plate Number: " + trucks.get(i).getTruckPlateNumber()
                    + "\t Model: " + trucks.get(i).getModel()
                    + "\t Nato Weight: " + trucks.get(i).getNatoWeight()
                    + "\t Max Weight: " + trucks.get(i).getMaxWeight()
            );
        }
    }

    public void viewAllDrivers(List<DriverDTO> drivers) {
        for (int i = 0; i < drivers.size(); i++) {
            System.out.println("\t" + (i + 1)
                            + ". Id Number: " + drivers.get(i).getId()
                            + "\t Name: " + drivers.get(i).getName()
                            + "\t Allowed Weight: " + drivers.get(i).getAllowedWeight()
//                    + "\t Available: " + (drivers.get(i).isAvailable() ? "Yes" : "No")
            );
        }
    }


    public void viewAllLocations(List<LocationDTO> locations) {
        for (int i = 0; i < locations.size(); i++) {
            System.out.println("\t" + (i + 1)
                    + ". Address: " + locations.get(i).getAddress()
                    + "\t Phone: " + locations.get(i).getPhoneNumber()
                    + "\t Contact Name: " + locations.get(i).getContactName()
            );
        }
    }

    public void viewAllShipments(List<ShipmentDTO> shipments) {
        if (shipments.isEmpty()) {
            System.out.println("There are no such shipments ...");
            return;
        }

        for (int i = 0; i < shipments.size(); i++) {
            System.out.println("\n");
            System.out.println("\t" + (i + 1)
                    + ". Date: " + new SimpleDateFormat("dd/MM/yyyy").format(shipments.get(i).getDate())
                    + "\t Departure Hour: " + shipments.get(i).getDepartureHour()
                    + "\t Truck Plate Number: " + shipments.get(i).getTruckPlateNumber()
                    + "\t Driver Id: " + shipments.get(i).getDriverId()
                    + "\t Shipment Weight: " + shipments.get(i).getShipmentWeight()
                    + "\n\t Approved: " + (shipments.get(i).isApproved() ? "YES" : "NO")
                    + "\t Delivered: " + (shipments.get(i).isDelivered() ? "YES" : "NO")
                    + "\t Source --> "
                    + "\t Address: " + shipments.get(i).getSource().getAddress()
                    + "\t Phone: " + shipments.get(i).getSource().getPhoneNumber()
                    + " (" + shipments.get(i).getSource().getContactName() + ")"
            );
            List<LocationDTO> destination = shipments.get(i).getDestinations();
            System.out.println("\t Arrived At:");
            for (int j = 0; j < destination.size(); j++) {
                int tracking = -1;
                Collection<DocumentDTO> docs = shipments.get(i).getDocuments().values();
                for (DocumentDTO doc : docs) {
                    if (doc.getDestination().getAddress().equals(destination.get(j).getAddress())) {
                        tracking = doc.getTrackingNumber();
                        break;
                    }
                }
                System.out.println("\t\t" + (j + 1) + ". "
                        + "Address: " + destination.get(j).getAddress()
                        + "\t Phone: " + destination.get(j).getPhoneNumber()
                        + " (" + destination.get(j).getContactName() + ")"
                        + "\t Tracking Number:" + tracking
                );

            }
            System.out.println("\n");
        }
    }

    public void viewShipment(ShipmentDTO shipment, int tracking) {
        System.out.println(
                "\t Date: " + new SimpleDateFormat("dd/MM/yyyy").format(shipment.getDate())
                        + "\t Departure Hour: " + shipment.getDepartureHour()
                        + "\t Truck Plate Number: " + shipment.getTruckPlateNumber()
                        + "\t Driver Id: " + shipment.getDriverId()
                        + "\t Shipment Weight: " + shipment.getDocuments().get(tracking).getTrackingNumber()
                        + "\n\t Source: " + shipment.getSource().getAddress()
                        + "\t\t Arrives At: " + shipment.getDocuments().get(tracking).getDestination().getAddress()
                        + "\t Phone: " + shipment.getDocuments().get(tracking).getDestination().getPhoneNumber()
                        + " (" + shipment.getDocuments().get(tracking).getDestination().getContactName() + ")"
        );
        List<ShippedItemDTO> products = shipment.getDocuments().get(tracking).getProducts();
        System.out.println(products.size() != 0 ? "\t This shipment contains:" : "This shipment has no items :(");
        int i = 0;
        for (ShippedItemDTO item : products) {
            System.out.println(
                    "\t\t" + (i + 1) + ". " + item.getName() + "\t"
                            + "\tUnit Weight: " + item.getWeight()
                            + "\tAmount: " + item.getAmount()
            );
            i++;
        }
    }
}
