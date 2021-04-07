package PresentationLayer.Handlers;

import BusinessLayer.Response;
import DTOs.TruckDTO;
import BusinessLayer.Facade;
import BusinessLayer.ResponseT;
import PresentationLayer.Printer;

import java.util.List;
import java.util.Scanner;

public class TrucksHandler extends Handler {

    public TrucksHandler() {
        super();
    }

    public void addTruck() {
        System.out.printf("\nEnter truck plate number:");
        String plateNumber = scanner.next();
        System.out.printf("\nEnter truck model:");
        String model = scanner.next();
        System.out.printf("\nEnter truck's nato weight:");
        double natoWeight = getDouble();
        System.out.printf("\nEnter truck's max weight:");
        double maxWeight = getDouble();
        System.out.println();
        Response res = facade.addTruck(new TruckDTO(plateNumber, model, natoWeight, maxWeight));
        if (res.errorOccured())
            printer.error(res.getMsg());
        else {
            printer.success("Truck has been added!");
            goBack();
        }
    }

    public void viewAllTrucks() {
        ResponseT<List<TruckDTO>> res = facade.getAlltrucks();
        if (res.errorOccured())
            printer.error(res.getMsg());
        else {
            printer.viewAllTrucks(res.getValue());
            goBack();
        }
    }

}
