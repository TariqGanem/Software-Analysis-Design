package PresentationLayer.Handlers;


import BusinessLayer.Facade;
import DTOs.TruckDTO;
import PresentationLayer.Printer;

import java.util.Scanner;

public class LocationsHandler extends Handler {

    private Facade facade;

    public LocationsHandler(Facade facade) {
        this.facade = facade;
    }

    public void doSomething() {
        System.out.printf("invoking function from location controller via facade...");
        facade.addTruck(new TruckDTO("1", "2", 3, 4));
    }
}
