package BusinessLayer.Controllers;

import BusinessLayer.Objects.Document;
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
     *
     * @param date
     * @param departureHour
     * @param truckPlateNumber
     * @param driverId
     * @param items_per_location
     * @param source
     * @throws Exception
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
     * @param date
     * @param departureHour
     * @param driverId
     * @param dest
     * @param products
     * @throws Exception
     */
    public void addDocument(Date date, String departureHour, String driverId, Location dest, Map<String, List<Double>> products, double weight) throws Exception {
        for (Shipment s : data) {
            if (s.getDate().compareTo(date) == 0 && s.getDepartureHour().equals(departureHour) && s.getDriverId().equals(driverId)) {
                s.addDocument(products, dest, weight, trackingNumber);
                trackingNumber++;
                break;
            }
        }
    }

    /**
     * @param date
     * @param departureHour
     * @param driverId
     */
    public void deleteShipment(Date date, String departureHour, String driverId) {
        for (Shipment s : data) {
            if (s.getDate().compareTo(date) == 0 && s.getDepartureHour().equals(departureHour) && s.getDriverId().equals(driverId)) {
                data.remove(s);
                break;
            }
        }
    }

    public List<Shipment> getAllShipments() {
        return data;
    }

    /**
     *
     * @param trackingId
     * @return
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
}