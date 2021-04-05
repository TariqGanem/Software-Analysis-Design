package BusinessLayer.Objects;

public class Truck {
    private String truckPlateNumber;
    private String model;
    private double natoWeight;
    private double maxWeight;
    private boolean available;

    public Truck(String truckPlateNumber, String model, double natoWeight, double maxWeight) {
        this.truckPlateNumber = truckPlateNumber;
        this.model = model;
        this.natoWeight = natoWeight;
        this.maxWeight = maxWeight;
        available = true;
    }

    public void transport() {
        //TODO
        available = false;
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

    public void depositeTruck() {
        available = true;
    }
}