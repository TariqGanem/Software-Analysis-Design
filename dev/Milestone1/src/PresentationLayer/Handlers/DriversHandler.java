package PresentationLayer.Handlers;

import BusinessLayer.Facade;
import BusinessLayer.Response;
import BusinessLayer.ResponseT;
import DTOs.DriverDTO;
import DTOs.TruckDTO;

import java.util.List;

public class DriversHandler extends Handler {

    private Facade facade;

    public DriversHandler(Facade facade) {
        this.facade = facade;
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
        if (res.errorOccured())
            printer.error(res.getMsg());
        else {
            printer.viewAllDrivers(res.getValue());
        }
    }
}