package ShipmentsModule.Controllers;

import ShipmentsModule.Objects.Truck;

import java.util.LinkedList;
import java.util.List;

public class TruckController {
    private List<Truck> trucks;

    public TruckController() {
        trucks = new LinkedList<>();
    }


    /***
     * Searching for the needed truck
     * @param truckID - Truck unique ID
     * @return Truck which has the given ID
     * @throws Exception in case truck not found in the system
     */
    public Truck getTruck(String truckID) throws Exception {
        for (Truck t : trucks) {
            if (t.getTruckPlateNumber().equals(truckID))
                return t;
        }
        throw new Exception("No such truck id");
    }

    /***
     * Adding a new truck of the system
     * @param truckPlateNumber - Truck unique ID
     * @param model - Truck's model
     * @param natoWeight - Truck's weight without the shipment
     * @param maxWeight - The maximum possible weight of the shipment that truck can transport
     * @throws Exception in case of invalid parameters
     */
    public void addTruck(String truckPlateNumber, String model, double natoWeight, double maxWeight) throws Exception {
        for (Truck t : trucks) {
            if (t.getTruckPlateNumber().equals(truckPlateNumber))
                throw new Exception("Couldn't add new truck - truckPlateNumber already exists");
        }
        if (maxWeight <= 0 || natoWeight <= 0)
            throw new Exception("Couldn't add new truck - Illegal truck weight");
        if (truckPlateNumber == null || truckPlateNumber.isEmpty() || model == null || model.isEmpty())
            throw new Exception("Couldn't add new truck - Invalid parameters");
        trucks.add(new Truck(truckPlateNumber, model, natoWeight, maxWeight));
    }

    /**
     * @return all trucks in the system
     */
    public List<Truck> getAlltrucks() {
        return trucks;
    }

    /**
     * @param weight - The shipment weight
     * @return an available truck which can transport a delivery of weight @param-weight
     * @throws Exception
     */
    public Truck getAvailableTruck(double weight) throws Exception {
        for (Truck t : trucks) {
            if (t.isAvailable() && t.getMaxWeight() >= weight)
                return t;
        }
        throw new Exception("No truck available");
    }

    /**
     * Makes the truck unavailable because it is currently in use
     *
     * @param truckPlateNumber - Unique id for truck
     */
    public void depositeTruck(String truckPlateNumber) {
        for (Truck t : trucks) {
            if (t.getTruckPlateNumber().equals(truckPlateNumber))
                t.makeAvailable();
        }
    }
}