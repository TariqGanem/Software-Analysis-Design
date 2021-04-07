package PresentationLayer.Handlers;

import BusinessLayer.Facade;
import BusinessLayer.Objects.Location;
import PresentationLayer.DataInitializer;
import PresentationLayer.Printer;

import java.util.Scanner;

public class Handler {
    private static Handler instance = null;
    protected Facade facade;
    protected Printer printer;
    protected Scanner scanner;
    private TrucksHandler trucksHandler;
    private LocationsHandler locationsHandler;


    private Handler() {
        facade = new Facade();
        trucksHandler = new TrucksHandler(facade);
        locationsHandler = new LocationsHandler(facade);
        printer = Printer.getInstance();
        scanner = new Scanner(System.in);
        scanner.useDelimiter("\n");
    }

    public static Handler getInstance() {
        if (instance == null)
            instance = new Handler();
        return instance;
    }

    public TrucksHandler getTruckHandler(){
        return trucksHandler;
    }

    public LocationsHandler getLocationHandler(){
        return locationsHandler;
    }

    public Facade getFacade(){
        return facade;
    }

    protected double getDouble() {
        while (true) {
            try {
                return Double.parseDouble(scanner.next());
            } catch (Exception e) {
                printer.error("Enter only numbers");
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
