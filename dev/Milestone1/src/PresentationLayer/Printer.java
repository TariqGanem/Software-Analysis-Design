package PresentationLayer;

import DTOs.*;

import java.util.List;
import java.util.Scanner;

public class Printer {
    private static Printer instance = null;
    private static Scanner scanner;

    private Printer() {
        scanner = new Scanner(System.in);
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
                    + "\t Available: " + (trucks.get(i).isAvailable() ? "Yes" : "No")
            );
        }
    }

    public void viewAllDrivers(List<DriverDTO> drivers) {
        for (int i = 0; i < drivers.size(); i++) {
            System.out.println("\t" + (i + 1)
                    + ". Id Number: " + drivers.get(i).getId()
                    + "\t Name: " + drivers.get(i).getName()
                    + "\t Allowed Weight: " + drivers.get(i).getAllowedWeight()
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
        for (int i = 0; i < shipments.size(); i++) {
            System.out.println("\t" + (i + 1)
                    + ". Date: " + shipments.get(i).getDate()
                    + "\t Departure Hour: " + shipments.get(i).getDepartureHour()
                    + "\t Truck Plate Number: " + shipments.get(i).getTruckPlateNumber()
                    + "\t Driver Id: " + shipments.get(i).getDriverId()
                    + "\t Shipment Weight: " + shipments.get(i).getShipmentWeight()
                    + "\t Source: " + shipments.get(i).getSource()
                    + "\t Arrives At: "
            );
            viewAllLocations(shipments.get(i).getLocations());
            System.out.println("----------------------------------------------------");
        }
    }

    public void viewShipment(ShipmentDTO shipment, DocumentDTO document) {
//            System.out.println(
//                    "\t Date: " + shipment.getDate()
//                    + "\t Departure Hour: " + shipment.getDepartureHour()
//                    + "\t Truck Plate Number: " + shipment.getTruckPlateNumber()
//                    + "\t Driver Id: " + shipment.getDriverId()
//                    + "\t Shipment Weight: " + document.
//                    + "\t Source: " + shipments.get(i).getSource()
//                    + "\t Arrives At: "
//            );
//            viewAllLocations(shipments.get(i).getLocations());
    }
}
