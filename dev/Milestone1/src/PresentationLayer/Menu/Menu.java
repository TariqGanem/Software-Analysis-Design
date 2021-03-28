package PresentationLayer.Menu;

import PresentationLayer.Menu.MenuItems.Exit;
import PresentationLayer.Menu.MenuItems.MenuItem;
import PresentationLayer.Objects.Printer;

import java.util.LinkedList;
import java.util.List;
import java.util.Scanner;

/**
 * User Menu interface - Singleton
 */
public class Menu {
    private static Menu instance = null;
    private static Printer printer;
    private static List<MenuItem> items;
    private static Scanner scanner;

    private Menu() {
        printer = Printer.getInstance();
        items = new LinkedList<>();
        scanner = new Scanner(System.in);
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
        MenuItem exit = new Exit("Exit");
        addMenuItem(exit);
    }


    public void selectItem() {
        String inputL = null;
        do {
            inputL = scanner.nextLine();
            if (validInt(inputL)) {
                int input = Integer.parseInt(inputL);
                items.get(input - 1).activate();
                if (printer.confirm())
                    viewMenuItems();
                else
                    items.get(items.size() - 1).activate();
            }
        } while (true);
    }

    public boolean validInt(String input) {
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

    public static void addMenuItem(MenuItem item) {
        items.add(item);
    }

}
