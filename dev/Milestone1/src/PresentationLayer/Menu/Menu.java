package PresentationLayer.Menu;

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

    private Menu(){
        printer = Printer.getInstance();
        items = new LinkedList<>();
        scanner = new Scanner(System.in);
    }

    public static Menu getInstance(){
        if(instance == null)
            instance = new Menu();
        return instance;
    }

    /**
     * Activating the menu for the user
     */
    public void run(){
        printer.printBanner();
        addItems();
        viewMenuItems();
    }

    private void addItems() {
        //TODO
    }

    private void viewMenuItems() {
        printer.viewOptions(items);
    }

}
