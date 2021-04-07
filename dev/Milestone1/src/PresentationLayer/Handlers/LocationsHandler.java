package PresentationLayer.Handlers;


import BusinessLayer.Facade;
import DTOs.TruckDTO;
import PresentationLayer.Printer;

import java.util.Scanner;

public class LocationsHandler {

    private Printer printer;
    private Scanner scanner;
    private Facade facade;

    public LocationsHandler(Facade facade) {
        printer = Printer.getInstance();
        scanner = new Scanner(System.in);
        this.facade = facade;
    }

    public void doSomething() {
        System.out.printf("invoking function from location controller via facade...");
        facade.addTruck(new TruckDTO("1", "2", 3, 4));
    }
}
