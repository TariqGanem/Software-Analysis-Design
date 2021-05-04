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

    public void addDriver() {
        System.out.printf("\nEnter driver's Id: ");
        String Id = scanner.next();
        System.out.printf("\nEnter driver's name: ");
        String name = scanner.next();
        System.out.printf("\nEnter driver license max weight: ");
        double allowedWeight = getDouble();
        System.out.println();
        Response res = facade.addDriver(Id, name, allowedWeight);
        if (res.errorOccured())
            printer.error(res.getMsg());
        else {
            printer.success("Driver has been added!");
        }
    }

    public void viewAllDrivers() {
        ResponseT<List<DriverDTO>> res = facade.getAlldrivers();
        drivers = res.getValue();
        if (res.errorOccured())
            printer.error(res.getMsg());
        else {
            printer.viewAllDrivers(drivers);
        }
    }
}