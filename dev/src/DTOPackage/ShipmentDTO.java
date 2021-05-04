package DTOPackage;

import BusinessLayer.ShipmentsModule.Objects.Location;
import BusinessLayer.ShipmentsModule.Objects.Shipment;

import java.util.*;

public class ShipmentDTO {
    private Date date;
    private String departureHour;
    private String truckPlateNumber;
    private String driverId;
    private double shipmentWeight; //TODO - we can implement a function that calculates all items weight.
    private Map<Integer, DocumentDTO> documents;
    private LocationDTO source;
    private List<LocationDTO> destinations;
    private Map<String, List<Double>> items; //TODO we don't actually need this.
    private Map<LocationDTO, Map<String, List<Double>>> items_per_location;//TODO Map<locationId, List<Item>>

    public ShipmentDTO(Shipment s) {
        date = s.getDate();
        departureHour = s.getDepartureHour();
        truckPlateNumber = s.getTruckPlateNumber();
        driverId = s.getDriverId();
        shipmentWeight = s.getShipmentWeight();
        documents = new HashMap<>();
        for (int id : s.getDocuments().keySet()) {
            documents.put(id, new DocumentDTO(s.getDocuments().get(id)));
        }
        source = new LocationDTO(s.getSource());
        destinations = new LinkedList<>();
        for (Location location : s.getDestinations()) {
            destinations.add(new LocationDTO(location));
        }
        items = s.getItems();
        items_per_location = new HashMap<>();
        for (Location loc : s.getItemsPerLocation().keySet()) {
            items_per_location.put(new LocationDTO(loc), s.getItemsPerLocation().get(loc));
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