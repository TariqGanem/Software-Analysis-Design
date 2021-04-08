package BusinessLayer.Objects;

import javafx.util.Pair;

import java.util.Map;

public class Document {
    private int trackingNumber;
    private Map<String, Pair<Double, Integer>> products;
    private Location destination;
    private double weight;

    public Document(int trackingNumber, Map<String, Pair<Double, Integer>> products, Location destination) {
        this.trackingNumber = trackingNumber;
        this.products = products;
        this.destination = destination;
        weight = 0;
    }

    public int getTrackingNumber() {
        return trackingNumber;
    }

    public Map<String, Pair<Double, Integer>> getProducts() {
        return products;
    }

    public Location getDestination() {
        return destination;
    }

    public void updateWeight(double weight){
        this.weight = weight;
    }
}