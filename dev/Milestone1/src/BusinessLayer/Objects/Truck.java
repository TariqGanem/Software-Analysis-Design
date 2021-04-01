package BusinessLayer.Objects;

import java.util.Date;

public class Truck {
    private String truckPlateNumber;
    private String model;
    private double natoWeight;
    private double maxWeight;
    private Shipment shipment;
    private boolean available;

    public Truck(String truckPlateNumber, String model, double natoWeight, double maxWeight) {
        this.truckPlateNumber = truckPlateNumber;
        this.model = model;
        this.natoWeight = natoWeight;
        this.maxWeight = maxWeight;
        shipment = null;
        available = true;
    }

    public void transport(Shipment shipment) {
        //TODO
        available = false;
    }

    public void depositShipment() {
        this.shipment = null;
        available = true;
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

    public Shipment getShipment() {
        return shipment;
    }


    public boolean isAvailable() {
        return available;
    }
}