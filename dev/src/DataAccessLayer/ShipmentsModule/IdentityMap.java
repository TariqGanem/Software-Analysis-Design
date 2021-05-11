package DataAccessLayer.ShipmentsModule;

import DTOPackage.*;

import java.util.LinkedList;
import java.util.List;

public class IdentityMap {

    private static IdentityMap instance = null;
    private List<TruckDTO> trucks;
    private List<LocationDTO> locations;
    private List<DriverDTO> drivers;
    private List<DocumentDTO> documents;
    private List<ShipmentDTO> shipments;

    private IdentityMap() {
        trucks = new LinkedList<>();
        locations = new LinkedList<>();
        drivers = new LinkedList<>();
        documents = new LinkedList<>();
        shipments = new LinkedList<>();
    }

    public static IdentityMap getInstance() {
        if (instance == null)
            instance = new IdentityMap();
        return instance;
    }

    public List<TruckDTO> getTrucks() {
        return trucks;
    }

    public List<LocationDTO> getLocations() {
        return locations;
    }

    public List<DriverDTO> getDrivers() {
        return drivers;
    }

    public List<DocumentDTO> getDocuments() {
        return documents;
    }

    public List<ShipmentDTO> getShipments() {
        return shipments;
    }

    public void setTrucks(List<TruckDTO> trucks) {
        this.trucks = trucks;
    }

    public void setDrivers(List<DriverDTO> drivers) {
        this.drivers = drivers;
    }

    public void setLocations(List<LocationDTO> locations) {
        this.locations = locations;
    }

    public void setShipments(List<ShipmentDTO> shipments) {
        this.shipments = shipments;
    }
}
