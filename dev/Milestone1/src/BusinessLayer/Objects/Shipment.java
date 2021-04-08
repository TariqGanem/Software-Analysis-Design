package BusinessLayer.Objects;

import java.util.Date;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

public class Shipment {
    private Date date;
    private String departureHour;
    private String truckPlateNumber;
    private String driverId;
    private double shipmentWeight;
    private List<String> log;
    private List<Document> documents;
    private int nextTrackingNumber;
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
        this.documents = new LinkedList<>();
        this.nextTrackingNumber = 0;
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

    public List<Document> getDocuments() {
        return documents;
    }

    public int getNextTrackingNumber() {
        return nextTrackingNumber;
    }

    /**
     * @param products
     * @param dest
     */
    public void addDocument(Map<String, Integer> products, Location dest) {
        Document d = new Document(nextTrackingNumber, products, dest);
        documents.add(d);
        nextTrackingNumber++;
    }


}