package BusinessLayer.DTOs;

import BusinessLayer.Objects.Shipment;

public class TruckDTO {
    private String truckPlateNumber;
    private String model;
    private double natoWeight;
    private double maxWeight;
    private Shipment shipment;
    private boolean available;
}