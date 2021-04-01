package BusinessLayer.DTOs;

import BusinessLayer.Objects.Document;
import BusinessLayer.Objects.Location;

import java.util.Date;
import java.util.List;

public class ShipmentDTO {
    private Date date;
    private String departureHour;
    private String truckPlateNumber;
    private String driverId;
    private double shipmentWeight;
    private List<String> log;
    private List<Document> documents;
    private Location source;
    private List<Location> destinations;
}