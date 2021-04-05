package PresentationLayer;

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
    private TrucksHandler trucksHandler;
    private LocationsHandler locationsHandler;

    private Menu() {
        printer = Printer.getInstance();
        items = new LinkedList<>();
        scanner = new Scanner(System.in);
        trucksHandler = new TrucksHandler();
        locationsHandler = new LocationsHandler();
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
        addMenuItem("one");
        addMenuItem("two");
        addMenuItem("Exit");//Keep last
    }


    private void selectItem() {
        String inputL = null;
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
                // JUST AN EXAMPLE...
                trucksHandler.doSomething();
                break;
            case 2:
                locationsHandler.doSomething();
                break;
            case 3:
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
