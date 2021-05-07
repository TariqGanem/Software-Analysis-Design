package BusinessLayer.ShipmentsModule.Objects;

public class Truck {
    private String truckPlateNumber;
    private String model;
    private double natoWeight;
    private double maxWeight;
    private boolean available;

    public Truck(String truckPlateNumber, String model, double natoWeight, double maxWeight, boolean available) {
        this.truckPlateNumber = truckPlateNumber;
        this.model = model;
        this.natoWeight = natoWeight;
        this.maxWeight = maxWeight;
        this.available = available;
    }

    public String getTruckPlateNumber() {
        return truckPlateNumber;
    }

    public String getModel() {
        return model;
    }

    public double getNatoWeight() {
        return natoWeight;
    }

    public double getMaxWeight() {
        return maxWeight;
    }

    public boolean isAvailable() {
        return available;
    }

    public void makeAvailable() {
        available = true;
    }
}