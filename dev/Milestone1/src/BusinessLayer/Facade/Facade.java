package BusinessLayer.Facade;

import BusinessLayer.Controllers.DriverController;
import BusinessLayer.Controllers.LocationController;
import BusinessLayer.Controllers.ShipmentController;
import BusinessLayer.Controllers.TruckController;

public class Facade {
    private static Facade instance = null;
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

    public static Facade getInstance() {
        if (instance == null)
            instance = new Facade();
        return instance;
    }

}