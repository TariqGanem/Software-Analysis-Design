package PresentationLayer;

import DTOs.TruckDTO;
import PresentationLayer.Handlers.Handler;
import PresentationLayer.Handlers.LocationsHandler;
import PresentationLayer.Handlers.TrucksHandler;

import java.util.LinkedList;
import java.util.List;
import java.util.Scanner;

/**
 * User Menu interface - Singleton
 */
public class Menu {
    private static Menu instance = null;
    private static Printer printer;
    private static List<String> items;
    private Scanner scanner;
    private static Handler handler;

    private Menu() {
        printer = Printer.getInstance();
        items = new LinkedList<>();
        scanner = new Scanner(System.in);
        handler = Handler.getInstance();
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
        printer.printBanner();
        addItems();
        viewMenuItems();
        selectItem();
    }

    private void addItems() {
        addMenuItem("Initialize System Data");
        addMenuItem("Add Truck");
        addMenuItem("Add Driver");
        addMenuItem("Add Location");
        addMenuItem("View All Trucks");
        addMenuItem("View All Drivers");
        addMenuItem("View All Locations");
        addMenuItem("View All Shipment Transportations");
        addMenuItem("Track Shipment");
        addMenuItem("Exit");//Keep last
    }


    private void selectItem() {
        String inputL;
        do {
            inputL = scanner.nextLine();
            if (validInput(inputL)) {
                int input = Integer.parseInt(inputL);
                handleSelection(input);
                viewMenuItems();
            }
        } while (true);
    }

    private void handleSelection(int input) {
        switch (input) {
            case 1:
                (new DataInitializer(handler.getFacade())).initialize();
                break;
            case 2:
                handler.getTruckHandler().addTruck();
                break;
            case 3:

                break;
            case 4:
                handler.getLocationHandler().doSomething();
                break;
            case 5:
                handler.getTruckHandler().viewAllTrucks();
                break;
            case 6:
                break;
            case 7:
                break;
            case 8:
                break;
            case 9:
                exit();
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

    private void exit() {
        System.out.println("The application exited successfully!");
        System.exit(0);
    }

    private void viewMenuItems() {
        printer.viewOptions(items);
    }

    private static void addMenuItem(String item) {
        items.add(item);
    }

}
