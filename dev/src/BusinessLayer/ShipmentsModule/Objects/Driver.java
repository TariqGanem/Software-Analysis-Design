package BusinessLayer.ShipmentsModule.Objects;

public class Driver {
    private String id;
    private String name;
    private double allowedWeight;
    private boolean available;

    public Driver(String id, String name, double allowedWeight, boolean available) {
        this.id = id;
        this.name = name;
        this.allowedWeight = allowedWeight;
        this.available = available;
    }

    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public double getAllowedWeight() {
        return allowedWeight;
    }

    public boolean isAvailable() {
        return available;
    }

    public void setAvailable(boolean available) {
        this.available = available;
    }
}