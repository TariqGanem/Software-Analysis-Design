package PresentationLayer.Handlers;

import BusinessLayer.Facade;
import BusinessLayer.Objects.Location;
import PresentationLayer.DataInitializer;
import PresentationLayer.Printer;

import java.util.Scanner;

public class Handler {
    protected Printer printer;
    protected Scanner scanner;


    public Handler() {
        printer = Printer.getInstance();
        scanner = new Scanner(System.in);
        scanner.useDelimiter("\n");
    }

    protected double getDouble() {
        while (true) {
            try {
                return Double.parseDouble(scanner.next());
            } catch (Exception e) {
                printer.error("Enter only numbers!");
            }
        }
    }

    public void goBack() {
        System.out.println("\nPress any button to view menu...");
        scanner.next();
    }


//    protected double getInt() {
//        while (true) {
//            try {
//                return Integer.parseInt(scanner.next());
//            } catch (Exception e) {
//                printer.error("Enter only numbers");
//            }
//        }
//    }

}