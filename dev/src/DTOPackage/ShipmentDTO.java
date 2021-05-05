package DTOPackage;

import BusinessLayer.ShipmentsModule.Objects.Location;
import BusinessLayer.ShipmentsModule.Objects.Shipment;

import java.util.*;

public class ShipmentDTO {
    private Date date;
    private String departureHour;
    private String truckPlateNumber;
    private String driverId;
    private double shipmentWeight;
    private Map<Integer, DocumentDTO> documents;
    private LocationDTO source;
    private List<LocationDTO> destinations;

    public ShipmentDTO(Shipment s) {
        this.date = s.getDate();
        this.departureHour = s.getDepartureHour();
        this.truckPlateNumber = s.getTruckPlateNumber();
        this.driverId = s.getDriverId();
        this.source = new LocationDTO(s.getSource());
        this.documents = new HashMap<>();
        this.shipmentWeight = 0;
        this.destinations = new LinkedList<>();
        for (int i =0; i < s.getDestinations().size(); i++){
            destinations.add(new LocationDTO(s.getDestinations().get(i)));
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

    public LocationDTO getSource() {
        return source;
    }

    public Map<Integer, DocumentDTO> getDocuments() {
        return documents;
    }

    public List<LocationDTO> getLocations() {
        return destinations;
    }
}