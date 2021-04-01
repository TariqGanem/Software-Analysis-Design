package BusinessLayer;

import BusinessLayer.Controllers.DriverController;
import BusinessLayer.Controllers.LocationController;
import BusinessLayer.Controllers.ShipmentController;
import BusinessLayer.Controllers.TruckController;
import BusinessLayer.DTOs.DriverDTO;
import BusinessLayer.DTOs.LocationDTO;
import BusinessLayer.DTOs.TruckDTO;
import BusinessLayer.Objects.Driver;
import BusinessLayer.Objects.Truck;

public class Facade {
    private Facade instance = null;
    private DriverController driverController;
    private TruckController truckController;
    private LocationController locationController;
    private ShipmentController shipmentController;

    public Facade() {
        this.driverController = new DriverController();
        this.truckController = new TruckController();
        this.locationController = new LocationController();
        this.shipmentController = new ShipmentController();
    }

    public Facade getInstance() {
        if (instance == null)
            instance = new Facade();
        return instance;
    }

    public ResponseT<TruckDTO> getTruckDTO(String truckId) {
        try {
            return new ResponseT<>(new TruckDTO(truckController.getTruck(truckId)));
        } catch (Exception e) {
            return new ResponseT<>(e.getMessage());
        }
    }

    public ResponseT<DriverDTO> getDriverDTO(String id) {
        try {
            return new ResponseT<>(new DriverDTO(driverController.getDriver(id)));
        } catch (Exception e) {
            return new ResponseT<>(e.getMessage());
        }
    }

    public ResponseT<LocationDTO> getLocationDTO(String address){
        try {
            return new ResponseT<>(new LocationDTO(locationController.getLocation(address)));
        } catch (Exception e) {
            return new ResponseT<>(e.getMessage());
        }
    }
}