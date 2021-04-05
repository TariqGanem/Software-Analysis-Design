package BusinessLayer.DTOs;

import BusinessLayer.Objects.Document;
import BusinessLayer.Objects.Location;
import BusinessLayer.Objects.Shipment;

import java.util.Date;
import java.util.LinkedList;
import java.util.List;

public class ShipmentDTO {
    private Date date;
    private String departureHour;
    private String truckPlateNumber;
    private String driverId;
    private double shipmentWeight;
    private List<String> log;
    private List<DocumentDTO> documents;
    private Location source;
    private List<LocationDTO> destinations;

    public ShipmentDTO(Shipment s){
        date = s.getDate();
        departureHour = s.getDepartureHour();
        truckPlateNumber = s.getTruckPlateNumber();
        driverId = s.getDriverId();
        shipmentWeight = s.getShipmentWeight();
        log = s.getLog();
        documents = new LinkedList<>();
        for (Document d: s.getDocuments()) {
            documents.add(new DocumentDTO(d));
        }
        source = s.getSource();
        destinations = new LinkedList<>();
        for (Location location: s.getDestinations()) {
            destinations.add(new LocationDTO(location));
        }
    }

    public Date getDate() {
        return date;
    }

    public String getDepartureHour() {
        return departureHour;
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

    public Location getSource() {
        return source;
    }

    public List<DocumentDTO> getDocuments(){
        return documents;
    }

    public List<LocationDTO> getLocations(){
        return destinations;
    }
}