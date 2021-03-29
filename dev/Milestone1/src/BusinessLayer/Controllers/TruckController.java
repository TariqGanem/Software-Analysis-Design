package BusinessLayer.Controllers;

import BusinessLayer.Objects.Shipment;
import BusinessLayer.Objects.Truck;

import java.util.Date;
import java.util.LinkedList;
import java.util.List;

public class TruckController {
    private List<Truck> data;

    public TruckController() {
        data = new LinkedList<>();
    }


    /***
     * Weighing truck before transportation
     * @return truck's weight.
     */
    public double weighTruck(String truckID) throws Exception {
        Truck t = getTruck(truckID);
        return t.getNatoWeight() + t.getShipment().getShipmentWeight();
    }

    public Truck getTruck(String truckID) throws Exception {
        for (Truck t : data) {
            if (t.getTruckPlateNumber().equals(truckID))
                return t;
        }
        throw new Exception("No such truck id.");
    }
}