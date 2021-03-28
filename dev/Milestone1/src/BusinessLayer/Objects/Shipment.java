package BusinessLayer.Objects;

import java.util.Date;
import java.util.LinkedList;
import java.util.List;

public class Shipment {
    private Date date;
    private String departueHour;
    private String truckPlateNumber;
    private String driverId;
    private double shipmentWeight;
    private List<String> log;

    public Shipment(Date date, String departueHour, String truckPlateNumber, String driverId, double shipmentWeight) {
        this.date = date;
        this.departueHour = departueHour;
        this.truckPlateNumber = truckPlateNumber;
        this.driverId = driverId;
        this.shipmentWeight = shipmentWeight;
        this.log = new LinkedList<>();
    }

    public Date getDate() {
        return date;
    }

    public String getDepartueHour() {
        return departueHour;
    }

    public String getTruckPlateNumber() {
        return truckPlateNumber;
    }

    public String getDriverId() {
        return driverId;
    }

    public double getShipmentWeight() {
        return shipmentWeight;
    }

    public List<String> getLog() {
        return log;
    }
}