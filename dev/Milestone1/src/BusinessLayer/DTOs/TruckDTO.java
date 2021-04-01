package BusinessLayer.DTOs;

import BusinessLayer.Objects.Shipment;
import BusinessLayer.Objects.Truck;

public class TruckDTO {
    private String truckPlateNumber;
    private String model;
    private double natoWeight;
    private double maxWeight;
    private ShipmentDTO shipment;
    private boolean available;

    public TruckDTO(Truck t){
        truckPlateNumber = t.getTruckPlateNumber();
        model = t.getModel();
        natoWeight = t.getNatoWeight();
        maxWeight = t.getMaxWeight();
        shipment = new ShipmentDTO(t.getShipment());
        available = t.isAvailable();
    }
}