package BusinessLayer.Objects;

import java.util.Date;

public class Truck {
    private String truckPlateNumber;
    private String model;
    private double natoWeight;
    private double maxWeight;
    private Shipment shipment;

    public Truck(String truckPlateNumber, String model, double natoWeight, double maxWeight) {
        this.truckPlateNumber = truckPlateNumber;
        this.model = model;
        this.natoWeight = natoWeight;
        this.maxWeight = maxWeight;
        shipment = null;
    }

    public void transport(Shipment shipment) {

    }

    public void depositShipment() {
        this.shipment = null;
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
}