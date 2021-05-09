package BusinessLayer.ShipmentsModule.Objects;

import java.util.*;

public class Shipment {
    private int shipmentId;
    private Date date;
    private String departureHour;
    private String truckPlateNumber;
    private String driverId;
    private Map<Integer, Document> documents;
    private Location source;

    public Shipment(int shipmentId, Date date, String departureHour, String truckPlateNumber, String driverId, Location source) {
        this.shipmentId = shipmentId;
        this.date = date;
        this.departureHour = departureHour;
        this.truckPlateNumber = truckPlateNumber;
        this.driverId = driverId;
        this.source = source;
        this.documents = new HashMap<>();
    }

    public int getShipmentId() {
        return shipmentId;
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
        int shipmentWeight = 0;
        for (Document doc : documents.values()) {
            shipmentWeight += doc.getWeight();
        }
        return shipmentWeight;
    }

    public Location getSource() {
        return source;
    }

    public List<Location> getDestinations() {
        List<Location> destinations = new LinkedList<>();
        documents.values().forEach(d -> destinations.add(d.getDestination()));
        return destinations;
    }

    public Map<Integer, Document> getDocuments() {
        return documents;
    }

    /**
     * Adding document per location to the delivery
     *
     * @param products       - The products which is transported to the specific destination
     * @param dest           - The requested destination
     * @param trackingNumber - Tracking number for the delivery as requested
     */
    public void addDocument(List<Item> products, Location dest, int trackingNumber) {
        Document d = new Document(trackingNumber, products, dest);
        documents.put(trackingNumber, d);
    }

}