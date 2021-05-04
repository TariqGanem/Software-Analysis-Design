package DTOs;

import BusinessLayer.Objects.Driver;

public class DriverDTO {
    private String id;
    private String name;
    private double allowedWeight;
    private boolean available;

    public DriverDTO(Driver d) {
        id = d.getId();
        name = d.getName();
        allowedWeight = d.getAllowedWeight();
        available = d.isAvailable();
    }

    public DriverDTO(String id, String name, double allowedWeight, boolean available) {
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
}