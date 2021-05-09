package PresentationLayer.ShipmentsMenu.Handlers;

import BusinessLayer.ShipmentsModule.Facade;
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

    public void addDriver(String Id, String name, double allowedWeight) {
        facade.addDriver(Id, name, allowedWeight);
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