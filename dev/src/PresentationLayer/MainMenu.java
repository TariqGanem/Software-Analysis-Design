package PresentationLayer;

import DataAccessLayer.dbMaker;
import PresentationLayer.EmployeeMenu.EmployeeModule;
import PresentationLayer.ShipmentsMenu.Menu;

import java.util.Scanner;

public class MainMenu {

    private static MainMenu instance = null;
    Scanner scanner;

    Menu shipmentsMenu;
    EmployeeModule employeeMenu;

    private MainMenu() {
        scanner = new Scanner(System.in);
        new dbMaker().initialize();
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
        while (input != 3) {
            showOptions();
            input = getInput();
            switch (input) {
                case 1:
                    employeeMenu.run();
                    break;
                case 2:
                    shipmentsMenu.run();
                    break;
            }
        }
    }

    private void showOptions() {
        System.out.println("$$$ << Welcome to Super Lee >> $$$");
        System.out.println("\nPlease choose the relevant option below:");
        System.out.println("1. Employee related issues.\n" +
                "2. Shipments related issues.\n" +
                "3. Exit.\n");
    }

    private int getInput() {
        while (true) {
            try {
                int input = Integer.parseInt(scanner.nextLine());
                return input >= 1 && input <= 3 ? input : null;
            } catch (Exception e) {
                System.out.println("Enter only numbers between [1-3]!");
            }
        }
    }
}