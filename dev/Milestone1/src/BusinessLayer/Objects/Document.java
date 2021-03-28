package BusinessLayer.Objects;

import java.util.Map;

public class Document {
    private int trackingNumber;
    private Map<Integer, String> products;
    private Location destination;

    public Document(int trackingNumber, Map<Integer, String> products, Location destination) {
        this.trackingNumber = trackingNumber;
        this.products = products;
        this.destination = destination;
    }

    public int getTrackingNumber() {
        return trackingNumber;
    }

    public Map<Integer, String> getProducts() {
        return products;
    }

    public Location getDestination() {
        return destination;
    }
}