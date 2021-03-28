package BusinessLayer.Objects;

import java.util.Date;
import java.util.LinkedList;
import java.util.List;

public class Shipment {
    private Date date;
    private String departureHour;
    private String truckPlateNumber;
    private String driverId;
    private double shipmentWeight;
    private List<String> log;
    private List<Document> documents;
    private Location source;
    private List<Location> destinations;

    public Shipment(Date date, String departureHour, String truckPlateNumber, String driverId, double shipmentWeight, Location source) {
        this.date = date;
        this.departureHour = departureHour;
        this.truckPlateNumber = truckPlateNumber;
        this.driverId = driverId;
        this.shipmentWeight = shipmentWeight;
        this.log = new LinkedList<>();
        this.source = source;
        this.destinations = new LinkedList<>();
    }


    public Date getDate() {
        return date;
    }

    public String getDepartureHour() {
        return departureHour;
    }

    public String getTruckPlateNumber() {
        return truckPlateNumber;
    }

    public String getDriverId() {
        return driverId;
    }

    public double getShipmentWeight() {
        return shipmentWeight;
    }

    public List<String> getLog() {
        return log;
    }

    public Location getSource() {
        return source;
    }

    public List<Location> getDestinations() {
        return destinations;
    }
}