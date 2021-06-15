package PresentationLayer.ShipmentsMenu.Handlers;

import BusinessLayer.Response;
import BusinessLayer.ResponseT;
import BusinessLayer.ShipmentsModule.Facade;
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
        String plateNumber = scanner.nextLine();
        System.out.printf("\nEnter truck model:");
        String model = scanner.nextLine();
        System.out.printf("\nEnter truck's nato weight: ");
        double natoWeight = getDouble();
        System.out.printf("\nEnter truck's max weight: ");
        double maxWeight = getDouble();
        System.out.println();
        Response res = facade.addTruck(plateNumber, model, natoWeight, maxWeight);
        if (res.getErrorOccurred())
            printer.error(res.getErrorMessage());
        else {
            printer.success("Truck has been added!");
        }
    }

    public void viewAllTrucks() {
        ResponseT<List<TruckDTO>> res = facade.getAllTrucks();
        trucks = res.getValue();
        if (res.getErrorOccurred())
            printer.error(res.getErrorMessage());
        else {
            printer.viewAllTrucks(trucks);
        }
    }


}
