package BusinessLayer.Controllers;

import BusinessLayer.Objects.Location;
import BusinessLayer.Objects.Shipment;

import java.util.Date;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

public class ShipmentController {
    private List<Shipment> data;
    private int trackingNumber;

    public ShipmentController() {
        data = new LinkedList<>();
        trackingNumber = 0;
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
     * Adding a shipment to the system
     * @param date - Date of the shipment to be transported
     * @param departureHour - The exact hour for the transportation of the shipment
     * @param truckPlateNumber - The truck's id which transports the delivery
     * @param driverId - The driver's id which transports the delivery
     * @param items_per_location - foreach Location there is a map -> foreach item there is list[2] -> [weight, amount]
     * @param source - The location which the delivery will start on
     * @throws Exception in case of wrong parameters values
     */
    public void addShipment(Date date, String departureHour, String truckPlateNumber, String driverId, Map<Location, Map<String, List<Double>>> items_per_location, Location source) throws Exception {
        if (departureHour == null || departureHour.trim().isEmpty())
            throw new Exception("Couldn't add new shipment - Invalid parameters");
        for (Shipment s : data) {
            if (s.getDate().compareTo(date) == 0 && s.getDepartureHour().equals(departureHour) && s.getDriverId().equals(driverId))
                throw new Exception("Couldn't add new shipment - Shipment already exists");
        }
        data.add(new Shipment(date, departureHour, truckPlateNumber, driverId, items_per_location, source));
    }

    /**
     * Adding a document to the shipment (foreach location as requested)
     * @param date - Date of the shipment to be transported
     * @param departureHour - The exact hour for the transportation of the shipment
     * @param driverId - The driver's unique id
     * @param dest - The destination of the shipment
     * @param products - The items that will be transported
     * @param weight - The shipment's weight + the truck's weight
     */
    public void addDocument(Date date, String departureHour, String driverId, Location dest, Map<String, List<Double>> products, double weight) {
        for (Shipment s : data) {
            if (s.getDate().compareTo(date) == 0 && s.getDepartureHour().equals(departureHour) && s.getDriverId().equals(driverId)) {
                s.addDocument(products, dest, weight, trackingNumber);
                trackingNumber++;
                break;
            }
        }
    }

    /** Deleting a shipment from the system
     * @param date - Date of the shipment to be transported
     * @param departureHour - The exact hour for the transportation of the shipment
     * @param driverId - The driver's unique id
     */
    public void deleteShipment(Date date, String departureHour, String driverId) throws Exception {
        for (Shipment s : data) {
            if (s.getDate().compareTo(date) == 0 && s.getDepartureHour().equals(departureHour) && s.getDriverId().equals(driverId)) {
                data.remove(s);
                break;
            }
        }
        throw new Exception("Shipment not found, invalid parameters.");
    }

    /**
     * @param trackingId - Document trackingNumber
     * @return The shipment per tracking id of its document
     */
    public Shipment trackShipment(int trackingId) throws Exception {
        for (Shipment s: data) {
            for (int id: s.getDocuments().keySet()) {
                if(id == trackingId){
                    return s;
                }
            }
        }
        throw new Exception("Invalid document id.");
    }

    /**
     * @return all shipments in the system
     */
    public List<Shipment> getAllShipments() {
        return data;
    }
}