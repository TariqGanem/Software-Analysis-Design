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
    private List<Document> documents;
    private int nextTrackingNumber;
    private Location source;
    private List<Location> destinations;
    private Map<String, List<Double>> items;

    public Shipment(Date date, String departureHour, String truckPlateNumber, String driverId, Map<String, List<Double>> items, Location source, List<Location> dests) {
        this.date = date;
        this.departureHour = departureHour;
        this.truckPlateNumber = truckPlateNumber;
        this.driverId = driverId;
        this.source = source;
        this.destinations = dests;
        this.documents = new LinkedList<>();
        this.nextTrackingNumber = 0;
        this.shipmentWeight = 0;
        this.items = items;
        for (String item : items.keySet()) {
            shipmentWeight += (items.get(item).get(0) * items.get(item).get(1));
        }
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

    public Location getSource() {
        return source;
    }

    public List<Location> getDestinations() {
        return destinations;
    }

    public List<Document> getDocuments() {
        return documents;
    }

    /**
     * @param products
     * @param dest
     */
    public void addDocument(Map<String, List<Double>> products, Location dest, double weight) {
        Document d = new Document(nextTrackingNumber, products, dest);
        d.updateWeight(weight);
        documents.add(d);
        nextTrackingNumber++;
    }

    public Map<String, List<Double>> getItems() {
        return items;
    }
}