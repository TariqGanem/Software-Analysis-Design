package PresentationLayer.ShipmentsMenu.Handlers;

import BusinessLayer.ShipmentsModule.Facade;
import BusinessLayer.ShipmentsModule.Response;
import BusinessLayer.ShipmentsModule.ResponseT;
import DTOPackage.TruckDTO;

import java.util.LinkedList;
import java.util.List;

public class TrucksHandler extends Handler {
    private Facade facade;
    private List<TruckDTO> trucks;

    public TrucksHandler(Facade facade) {
        this.facade = facade;
        trucks = new LinkedList<>();
    }

    public void addTruck() {
        System.out.printf("\nEnter truck plate number: ");
        String plateNumber = scanner.next();
        System.out.printf("\nEnter truck model:");
        String model = scanner.next();
        System.out.printf("\nEnter truck's nato weight: ");
        double natoWeight = getDouble();
        System.out.printf("\nEnter truck's max weight: ");
        double maxWeight = getDouble();
        System.out.println();
        Response res = facade.addTruck(plateNumber, model, natoWeight, maxWeight);
        if (res.errorOccured())
            printer.error(res.getMsg());
        else {
            printer.success("Truck has been added!");
        }
    }

    public void viewAllTrucks() {
        ResponseT<List<TruckDTO>> res = facade.getAlltrucks();
        trucks = res.getValue();
        if (res.errorOccured())
            printer.error(res.getMsg());
        else {
            printer.viewAllTrucks(trucks);
        }
    }

}
