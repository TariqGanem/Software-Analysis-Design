package ShipmentsModule.Objects;

public class Driver {
    private String id;
    private String name;
    private double allowedWeight;
    private boolean available;

    public Driver(String id, String name, double allowedWeight) {
        this.id = id;
        this.name = name;
        this.allowedWeight = allowedWeight;
        this.available = true;
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

    public void makeAvailable() {
        available = true;
    }
}