package BusinessLayer.ShipmentsModule.Objects;

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
    private Map<Location, List<Item>> items_per_location;

    public Shipment(Date date, String departureHour, String truckPlateNumber, String driverId, Map<Location, List<Item>> items, Location source) {
        this.date = date;
        this.departureHour = departureHour;
        this.truckPlateNumber = truckPlateNumber;
        this.driverId = driverId;
        this.source = source;
        this.documents = new HashMap<>();
        this.items_per_location = items;
        this.shipmentWeight = 0;
        for (Location l: items_per_location.keySet()) {
            for ( Item i: items_per_location.get(l)) {
                shipmentWeight += i.getWeight()*i.getAmount();
            }
        }
        this.destinations = new LinkedList<>();
        destinations.addAll(items_per_location.keySet());
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
    public void addDocument(List<Item> products, Location dest, double weight, int trackingNumber) {
        Document d = new Document(trackingNumber, products, dest);
        d.updateWeight(weight);
        documents.put(trackingNumber, d);
    }

    public List<Item> getItems() {
        List<Item> l = new LinkedList<>();
        for (Location loc: items_per_location.keySet()) {
            l.addAll(items_per_location.get(loc));
        }
        return l;
    }

//    public Map<Location, Map<String, List<Double>>> getItemsPerLocation() {
//        return items_per_location;
//    }
}