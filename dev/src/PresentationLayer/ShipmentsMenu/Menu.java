package PresentationLayer.ShipmentsMenu;

import BusinessLayer.ShipmentsModule.Facade;
import PresentationLayer.ShipmentsMenu.Handlers.DriversHandler;
import PresentationLayer.ShipmentsMenu.Handlers.LocationsHandler;
import PresentationLayer.ShipmentsMenu.Handlers.ShipmentsHandler;
import PresentationLayer.ShipmentsMenu.Handlers.TrucksHandler;

import java.util.LinkedList;
import java.util.List;
import java.util.Scanner;

/**
 * User Menu interface - Singleton
 */
public class Menu {
    private static Menu instance = null;
    private Printer printer;
    private List<String> items;
    private Scanner scanner;
    private TrucksHandler trucksHandler;
    private LocationsHandler locationsHandler;
    private DriversHandler driversHandler;
    private ShipmentsHandler shipmentsHandler;
    private Facade facade;

    private Menu() {
        printer = Printer.getInstance();
        items = new LinkedList<>();
        scanner = new Scanner(System.in);
        facade = new Facade();
        trucksHandler = new TrucksHandler(facade);
        locationsHandler = new LocationsHandler(facade);
        driversHandler = new DriversHandler(facade);
        shipmentsHandler = new ShipmentsHandler(facade);
        addItems();
    }

    public static Menu getInstance() {
        if (instance == null)
            instance = new Menu();
        return instance;
    }

    /**
     * Activating the menu for the user
     */
    public void run() {
        viewMenuItems();
        selectItem();
    }

    private void addItems() {
        addMenuItem("Initialize System Data");
        addMenuItem("Add Truck");
        addMenuItem("Add Location");
        addMenuItem("View All Trucks");
        addMenuItem("View All Drivers");
        addMenuItem("View All Locations");
        addMenuItem("View All Shipment Transportations");
        addMenuItem("Track Shipment");
        addMenuItem("Arrange Shipment");
        addMenuItem("Remove Shipment");
        addMenuItem("Go back to main menu");//Keep last
    }


    private void selectItem() {
        String inputL;
        do {
            inputL = scanner.nextLine();
            if (validInput(inputL)) {
                int input = Integer.parseInt(inputL);
                handleSelection(input);
                if (input == 11)
                    return;
                viewMenuItems();
            }
        } while (true);
    }

    private void handleSelection(int input) {
        switch (input) {
            case 1:
                (new DataInitializer(facade)).initialize();
                break;
            case 2:
                trucksHandler.addTruck();
                break;
            case 3:
                locationsHandler.addLocation();
                break;
            case 4:
                trucksHandler.viewAllTrucks();
                break;
            case 5:
                driversHandler.viewAllDrivers();
                break;
            case 6:
                locationsHandler.viewAllLocations();
                break;
            case 7:
                shipmentsHandler.viewAllShipments();
                break;
            case 8:
                shipmentsHandler.trackShipment();
                break;
            case 9:
                shipmentsHandler.arrangeShipment();
                break;
            case 10:
                shipmentsHandler.removeShipment();
                break;
        }
    }

    private boolean validInput(String input) {
        for (int i = 0; i < input.length(); i++) {
            if ((int) input.charAt(i) < 48 || (int) input.charAt(i) > 57) {
                printer.error("Enter only numbers!");
                return false;
            }
        }
        int number = Integer.parseInt(input);
        if (number < 1 || number > items.size()) {
            printer.error("Select valid number from the menu above!");
            return false;
        } else
            return true;
    }


    private void viewMenuItems() {
        printer.viewOptions(items);
    }

    private void addMenuItem(String item) {
        items.add(item);
    }

    public DriversHandler getDriversHandler() {
        return driversHandler;
    }
}
