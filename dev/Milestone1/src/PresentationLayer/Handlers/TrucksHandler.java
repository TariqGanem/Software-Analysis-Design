package PresentationLayer.Handlers;

import BusinessLayer.DTOs.TruckDTO;
import BusinessLayer.Facade;
import BusinessLayer.ResponseT;
import PresentationLayer.Printer;

import java.util.List;

public class TrucksHandler {
    private Facade facade;
    private Printer printer;

    public TrucksHandler() {
        facade = new Facade();
        printer = Printer.getInstance();
    }

    public void doSomething() {
        System.out.println("invoking function from truck controller via facade...");
    }

    public void viewAllTrucks(){
         ResponseT<List<TruckDTO>> res = facade.getAlltrucks();
         if(res.errorOccured())
             printer.error(res.getMsg());
         else
             printer.viewAllTrucks(res.getValue());
    }

}
