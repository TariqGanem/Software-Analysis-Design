package PresentationLayer.ShipmentsMenu.Handlers;

import BusinessLayer.ShipmentsModule.Facade;
import BusinessLayer.Response;
import BusinessLayer.ResponseT;
import DTOPackage.DriverDTO;

import java.util.Date;
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

    public DriverDTO chooseAvailableDriver() {
        while (true) {
            try {
                return drivers.get(getInt() - 1);
            } catch (Exception e) {
                printer.error("Invalid input!");
            }
        }
    }

    public DriverDTO handleAvailableDriver(double totalWeight, Date date, String hour) {
        ResponseT<List<DriverDTO>> res = facade.getAllAvailableDrivers(totalWeight, date, hour);
        drivers = res.getValue();
        if (res.getErrorOccurred()) {
            printer.error(res.getErrorMessage());
        } else {
            System.out.println("\n Now all drivers below can drive this truck, Choose one:");
            printer.viewAllDrivers(drivers);
            return chooseAvailableDriver();
        }
        return null;
    }
}