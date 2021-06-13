package PresentationLayer;

import DataAccessLayer.dbMaker;
import PresentationLayer.EmployeesMenu.EmployeeModule;
import PresentationLayer.ShipmentsMenu.Menu;
import PresentationLayer.StoreMenu.StoreMenu;
import PresentationLayer.SuppliersMenu.SupplierMenu;

import java.util.Scanner;

public class MainMenu {

    private static MainMenu instance = null;
    Scanner scanner;

    StoreMenu storemenu;
    SupplierMenu suppliermenu;
    Menu shipmentsMenu;
    EmployeeModule employeeMenu;

    private MainMenu() {
        scanner = new Scanner(System.in);
        new dbMaker().initialize();
        storemenu = new StoreMenu();
        suppliermenu = new SupplierMenu();
        shipmentsMenu = Menu.getInstance();
        employeeMenu = new EmployeeModule();
    }

    public static MainMenu getInstance() {
        if (instance == null)
            instance = new MainMenu();
        return instance;
    }

    public void run() {
        int input = -1;
        while (input != 5) {
            showOptions();
            input = getInput();
            switch (input) {
                case 1:
                    employeeMenu.run();
                    break;
                case 2:
                    shipmentsMenu.run();
                    break;
                case 3:
                    suppliermenu.Print_Menu();
                    break;
                case 4:
                    storemenu.showSpecificMenu();
                    break;
            }
        }
    }

    private void showOptions() {
        System.out.println("$$$ << Welcome to Super Lee >> $$$");
        System.out.println("\nPlease choose the relevant option below:");
        System.out.println("1. Employee related issues.\n" +
                "2. Shipments related issues.\n" +
                "3. Suppliers related issues.\n" +
                "4. Store related issues.\n" +
                "5. Exit.\n");
    }

    private int getInput() {
        while (true) {
            try {
                int input = Integer.parseInt(scanner.nextLine());
                return input >= 1 && input <= 5 ? input : null;
            } catch (Exception e) {
                System.out.println("Enter only numbers between [1-5]!");
            }
        }
    }
}