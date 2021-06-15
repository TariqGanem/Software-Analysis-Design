package PresentationLayer.ShipmentsMenu.Handlers;

import BusinessLayer.Response;
import BusinessLayer.ResponseT;
import BusinessLayer.ShipmentsModule.Facade;
import DTOPackage.DriverDTO;

import java.util.LinkedList;
import java.util.List;

public class DriversHandler extends Handler {

    private Facade facade;
    List<DriverDTO> drivers;

    public DriversHandler(Facade facade) {
        this.facade = facade;
        drivers = new LinkedList<>();
    }

    public void addDriver(String Id) {
        System.out.println("Enter Allowed Weight for Driver:");
        double allowedWeight = getDouble();
        Response res = facade.addDriver(Id, allowedWeight);
        if (res.getErrorOccurred())
            System.out.println(res.getErrorMessage());
    }

    public void initDriver(String Id, double allowedWeight) {
        Response res = facade.addDriver(Id, allowedWeight);
        if (res.getErrorOccurred())
            System.out.println(res.getErrorMessage());
    }

    public void viewAllDrivers() {
        ResponseT<List<DriverDTO>> res = facade.getAllDrivers();
        drivers = res.getValue();
        if (res.getErrorOccurred())
            printer.error(res.getErrorMessage());
        else {
            printer.viewAllDrivers(drivers);
        }
    }


}