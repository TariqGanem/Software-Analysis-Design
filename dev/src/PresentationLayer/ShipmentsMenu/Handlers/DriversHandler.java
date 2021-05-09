package PresentationLayer.ShipmentsMenu.Handlers;

import BusinessLayer.ShipmentsModule.Facade;
import BusinessLayer.ShipmentsModule.Response;
import BusinessLayer.ShipmentsModule.ResponseT;
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
        Response res =  facade.addDriver(Id, allowedWeight);
        if(res.errorOccured())
            System.out.println(res.getMsg());
    }

    public void viewAllDrivers() {
        ResponseT<List<DriverDTO>> res = facade.getAllDrivers();
        drivers = res.getValue();
        if (res.errorOccured())
            printer.error(res.getMsg());
        else {
            printer.viewAllDrivers(drivers);
        }
    }
}