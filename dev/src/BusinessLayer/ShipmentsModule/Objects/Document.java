package BusinessLayer.ShipmentsModule.Objects;

import java.util.List;

public class Document {
    private int trackingNumber;
    private List<Item> products;
    private Location destination;
    private double weight;

    public int getTrackingNumber() {
        return trackingNumber;
    }

    public Document(int trackingNumber, List<Item> products, Location destination) {
        this.trackingNumber = trackingNumber;
        this.products = products;
        this.destination = destination;
        weight = 0;
    }

    public List<Item> getProducts() {
        return products;
    }

    public Location getDestination() {
        return destination;
    }

    public void updateWeight(double weight) {
        this.weight = weight;
    }

    public double getWeight() {
        return weight;
    }
}