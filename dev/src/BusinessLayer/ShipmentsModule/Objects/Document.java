package BusinessLayer.ShipmentsModule.Objects;

import java.util.List;

public class Document {
    private int trackingNumber;
    private List<Item> products;
    private Location destination;

    public int getTrackingNumber() {
        return trackingNumber;
    }

    public Document(int trackingNumber, List<Item> products, Location destination) {
        this.trackingNumber = trackingNumber;
        this.products = products;
        this.destination = destination;
    }

    public List<Item> getProducts() {
        return products;
    }

    public Location getDestination() {
        return destination;
    }

    public double getWeight() {
        double weight = 0;
        for (Item item : products) {
            weight += item.getWeight() * item.getAmount();
        }
        return weight;
    }
}