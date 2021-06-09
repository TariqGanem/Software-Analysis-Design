package BusinessLayer.ShipmentsModule.Objects;

public class Driver {
    private String id;
    private String name;
    private double allowedWeight;

    public Driver(String id, String name, double allowedWeight) {
        this.id = id;
        this.name = name;
        this.allowedWeight = allowedWeight;
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

}