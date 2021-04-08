package PresentationLayer.Handlers;


import BusinessLayer.Facade;
import BusinessLayer.Response;
import BusinessLayer.ResponseT;
import DTOs.LocationDTO;
import DTOs.TruckDTO;
import PresentationLayer.Printer;

import java.util.List;
import java.util.Scanner;

public class LocationsHandler extends Handler {

    private Facade facade;

    public LocationsHandler(Facade facade) {
        this.facade = facade;
    }


    public void addLocation() {
        System.out.printf("\nEnter address: ");
        String address = scanner.next();
        System.out.printf("\nEnter phone number:");
        String phoneNumber = scanner.next();
        System.out.printf("\nEnter contact name: ");
        String contactName = scanner.next();
        System.out.println();
        Response res = facade.addLocation(address, phoneNumber, contactName);
        if (res.errorOccured())
            printer.error(res.getMsg());
        else {
            printer.success("Location has been added!");
        }
    }

    public void viewAllLocations() {
        ResponseT<List<LocationDTO>> res = facade.getAllLocations();
        if (res.errorOccured())
            printer.error(res.getMsg());
        else {
            printer.viewAllLocations(res.getValue());
        }
    }
}
