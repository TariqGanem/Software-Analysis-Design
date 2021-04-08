package BusinessLayer.Controllers;

import BusinessLayer.Objects.Location;
import BusinessLayer.Objects.Shipment;
import javafx.util.Pair;

import java.util.Date;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

public class ShipmentController {
    private List<Shipment> data;

    public ShipmentController() {
        data = new LinkedList<>();
    }

    /***
     * Searching for a specific shipment in the system
     * @param date - Date of the shipment to be transported
     * @param departureHour - The exact hour for the transportation of the shipment
     * @param driverId - The driver's unique id
     * @return the wanted shipment
     * @throws Exception in case of invalid parameters
     */
    public Shipment getShipment(Date date, String departureHour, String driverId) throws Exception {
        for (Shipment s : data) {
            if (s.getDate().compareTo(date) == 0 && s.getDepartureHour().equals(departureHour) && s.getDriverId().equals(driverId))
                return s;
        }
        throw new Exception("No such shipment");
    }

    /**
     * Adding a new shipment to the system
     * @param date - Date of the shipment to be transported
     * @param departureHour - The exact hour for the transportation of the shipment
     * @param truckPlateNumber - The truck's unique id for the transportation
     * @param driverId - The driver's unique id
     * @param items - All items with quantities and weights to transport
     * @param source - Source location of the transportation
     * @param dests - All locations to transport the delivery
     * @throws Exception
     */
    public void addShipment(Date date, String departureHour, String truckPlateNumber, String driverId, Map<String, Pair<Double, Integer>> items, Location source, List<Location> dests) throws Exception {
        if (departureHour == null || departureHour.trim().isEmpty())
            throw new Exception("Couldn't add new shipment - Invalid parameters");
        for (Shipment s : data) {
            if (s.getDate().compareTo(date) == 0 && s.getDepartureHour().equals(departureHour) && s.getDriverId().equals(driverId))
                throw new Exception("Couldn't add new shipment - Shipment already exists");
        }
        data.add(new Shipment(date, departureHour, truckPlateNumber, driverId, items, source, dests));
    }

    public void addDocument(Date date, String departureHour, String driverId, Location dest, Map<String, Pair<Double, Integer>> products) throws Exception {
        for (Shipment s : data) {
            if (s.getDate().compareTo(date) == 0 && s.getDepartureHour().equals(departureHour) && s.getDriverId().equals(driverId)) {
                s.addDocument(products, dest);
                break;
            }
        }
    }
}