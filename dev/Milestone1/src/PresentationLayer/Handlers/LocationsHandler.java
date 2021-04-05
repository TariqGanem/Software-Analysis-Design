package PresentationLayer.Handlers;

import BusinessLayer.Facade;

public class LocationsHandler {
    private Facade facade;

    public LocationsHandler() {
    }

    public void doSomething() {
        System.out.printf("invoking function from location controller via facade...");
    }
}
