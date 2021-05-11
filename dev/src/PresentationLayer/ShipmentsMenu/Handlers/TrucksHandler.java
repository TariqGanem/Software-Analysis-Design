package PresentationLayer.ShipmentsMenu.Handlers;

import BusinessLayer.ShipmentsModule.Facade;
import BusinessLayer.ShipmentsModule.Response;
import BusinessLayer.ShipmentsModule.ResponseT;
import DTOPackage.TruckDTO;

import java.text.SimpleDateFormat;
import java.util.Date;
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
        if (res.errorOccured())
            printer.error(res.getMsg());
        else {
            printer.success("Truck has been added!");
        }
    }

    public void viewAllTrucks() {
        ResponseT<List<TruckDTO>> res = facade.getAllTrucks();
        trucks = res.getValue();
        if (res.errorOccured())
            printer.error(res.getMsg());
        else {
            printer.viewAllTrucks(trucks);
        }
    }

    public TruckDTO chooseAvailableTruck() {
        while (true) {
            try {
                return trucks.get(getInt() - 1);
            } catch (Exception e) {
                printer.error("Invalid input!");
            }
        }
    }

    public TruckDTO handleAvailableTrucks(double weight, Date date, String hour) {
        ResponseT<List<TruckDTO>> res = facade.getAvailableTrucks(weight, date, hour);
        trucks = res.getValue();
        if (res.errorOccured())
            printer.error(res.getMsg());
        else {
            try {
                System.out.printf("All trucks below are available on ["
                        + new SimpleDateFormat("dd/MM/yyyy").format(date) + " - "
                        + hour + "]\nand capable to transport this shipment.\nPlease choose one:\n");
            } catch (Exception e) {
                printer.error(e.getMessage());
            }
            printer.viewAllTrucks(trucks);
            return chooseAvailableTruck();
        }
        return null;
    }

}
