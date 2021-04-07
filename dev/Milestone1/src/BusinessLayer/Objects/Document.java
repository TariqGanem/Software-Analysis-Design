package BusinessLayer.Objects;

import java.util.Map;

public class Document {
    private int trackingNumber;
    private Map<String, Integer> products;
    private Location destination;

    public Document(int trackingNumber, Map<String, Integer> products, Location destination) {
        this.trackingNumber = trackingNumber;
        this.products = products;
        this.destination = destination;
    }

    public int getTrackingNumber() {
        return trackingNumber;
    }

    public Map<String, Integer> getProducts() {
        return products;
    }

    public Location getDestination() {
        return destination;
    }
}