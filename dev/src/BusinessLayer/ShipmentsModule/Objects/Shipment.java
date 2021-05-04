package ShipmentsModule.Objects;

import java.util.*;

public class Shipment {
    private Date date;
    private String departureHour;
    private String truckPlateNumber;
    private String driverId;
    private double shipmentWeight;
    private Map<Integer, Document> documents;
    private Location source;
    private List<Location> destinations;
    private Map<String, List<Double>> items;
    private Map<Location, Map<String, List<Double>>> items_per_location;

    public Shipment(Date date, String departureHour, String truckPlateNumber, String driverId, Map<Location, Map<String, List<Double>>> items, Location source) {
        this.date = date;
        this.departureHour = departureHour;
        this.truckPlateNumber = truckPlateNumber;
        this.driverId = driverId;
        this.source = source;
        this.documents = new HashMap<>();
        this.items_per_location = items;
        this.shipmentWeight = 0;
        for (Location location : items_per_location.keySet()) {
            for (String item : items_per_location.get(location).keySet()) {
                shipmentWeight += (items_per_location.get(location).get(item).get(0) * items_per_location.get(location).get(item).get(1));
            }
        }
        this.destinations = new LinkedList<>();
        destinations.addAll(items_per_location.keySet());
        this.items = new HashMap<>();
        for (Location location : items_per_location.keySet()) {
            for (String item : items_per_location.get(location).keySet()) {
                this.items.put(item, items_per_location.get(location).get(item));
            }
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

    public Map<Integer, Document> getDocuments() {
        return documents;
    }

    /**
     * Adding document per location to the delivery
     *
     * @param products       - The products which is transported to the specific destination
     * @param dest           - The requested destination
     * @param weight         - The weight of the shipment for the specific location
     * @param trackingNumber - Tracking number for the delivery as requested
     */
    public void addDocument(Map<String, List<Double>> products, Location dest, double weight, int trackingNumber) {
        Document d = new Document(trackingNumber, products, dest);
        d.updateWeight(weight);
        documents.put(trackingNumber, d);
    }

    public Map<String, List<Double>> getItems() {
        return items;
    }

    public Map<Location, Map<String, List<Double>>> getItemsPerLocation() {
        return items_per_location;
    }
}