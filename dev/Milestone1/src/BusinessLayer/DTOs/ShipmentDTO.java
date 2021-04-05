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

    public ShipmentDTO(Date date, String departureHour, String truckPlateNumber, String driverId, double shipmentWeight,
                       List<String> log, List<DocumentDTO> documents, Location source, List<LocationDTO> destinations) {
        this.date = date;
        this.departureHour = departureHour;
        this.truckPlateNumber = truckPlateNumber;
        this.driverId = driverId;
        this.shipmentWeight = shipmentWeight;
        this.log = log;
        this.documents = documents;
        this.source = source;
        this.destinations = destinations;
    }
}