package PresentationLayer.Handlers;

import BusinessLayer.Facade;

public class TrucksHandler {
    private Facade facade;

    public TrucksHandler() {
    }

    public void doSomething() {
        System.out.println("invoking function from truck controller via facade...");
    }

}
