package BusinessLayer;

import BusinessLayer.Controllers.DriverController;
import BusinessLayer.Controllers.LocationController;
import BusinessLayer.Controllers.ShipmentController;
import BusinessLayer.Controllers.TruckController;
import BusinessLayer.Objects.Location;
import DTOs.DriverDTO;
import DTOs.LocationDTO;
import DTOs.TruckDTO;
import BusinessLayer.Objects.Driver;
import BusinessLayer.Objects.Truck;

import java.util.Date;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

public class Facade {
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

    /**
     * @param truckId
     * @return
     */
    public ResponseT<TruckDTO> getTruckDTO(String truckId) {
        try {
            return new ResponseT<>(new TruckDTO(truckController.getTruck(truckId)));
        } catch (Exception e) {
            return new ResponseT<>(e.getMessage());
        }
    }

    /**
     * @param id
     * @return
     */
    public ResponseT<DriverDTO> getDriverDTO(String id) {
        try {
            return new ResponseT<>(new DriverDTO(driverController.getDriver(id)));
        } catch (Exception e) {
            return new ResponseT<>(e.getMessage());
        }
    }

    /**
     * @param address
     * @return
     */
    public ResponseT<LocationDTO> getLocationDTO(String address) {
        try {
            return new ResponseT<>(new LocationDTO(locationController.getLocation(address)));
        } catch (Exception e) {
            return new ResponseT<>(e.getMessage());
        }
    }

    /**
     * @param address
     * @param phoneNumber
     * @param contactName
     * @param products
     * @return
     */
    public Response addLocation(String address, String phoneNumber, String contactName, Map<String, Integer> products) {
        try {
            locationController.addLocation(address, phoneNumber, contactName, products);
            return new Response();
        } catch (Exception e) {
            return new Response(e.getMessage());
        }
    }

    /**
     * @param truckPlateNumber
     * @param model
     * @param natoWeight
     * @param maxWeight
     * @return
     */
    public Response addTruck(String truckPlateNumber, String model, double natoWeight, double maxWeight) {
        try {
            truckController.addTruck(truckPlateNumber, model, natoWeight, maxWeight);
            return new Response();
        } catch (Exception e) {
            return new Response(e.getMessage());
        }
    }

    /**
     * @param id
     * @param name
     * @param allowedWeight
     * @return
     */
    public Response addDriver(String id, String name, double allowedWeight) {
        try {
            driverController.addDriver(id, name, allowedWeight);
            return new Response();
        } catch (Exception e) {
            return new Response(e.getMessage());
        }
    }

    /**
     * @return
     */
    public ResponseT<List<TruckDTO>> getAlltrucks() {
        try {
            List<Truck> trucks = truckController.getAlltrucks();
            List<TruckDTO> trucksDTO = new LinkedList<>();
            for (Truck t : trucks) {
                trucksDTO.add(getTruckDTO(t.getTruckPlateNumber()).getValue());
            }
            return new ResponseT<>(trucksDTO);
        } catch (Exception e) {
            return new ResponseT<>(e.getMessage());
        }
    }

    /**
     * @return
     */
    public ResponseT<List<DriverDTO>> getAlldrivers() {
        try {
            List<Driver> drivers = driverController.getAlldrivers();
            List<DriverDTO> driversDTO = new LinkedList<>();
            for (Driver d : drivers) {
                driversDTO.add(getDriverDTO(d.getId()).getValue());
            }
            return new ResponseT<>(driversDTO);
        } catch (Exception e) {
            return new ResponseT<>(e.getMessage());
        }
    }

    /**
     * @param date
     * @param departureHour
     * @param weight
     * @param source
     * @param binds
     * @return
     */
    public Response arrangeDelivery(Date date, String departureHour, double weight, String source, Map<String, Map<String, Integer>> binds) {
        try {
            DriverDTO driver = new DriverDTO(driverController.getAvailableDriver(weight));
            TruckDTO truck = new TruckDTO(truckController.getAvailableTruck(weight));
            weighTruck(truck.getTruckPlateNumber(), date, departureHour, driver.getId());
            Location s = locationController.getLocation(source);
            shipmentController.addShipment(date, departureHour, truck.getTruckPlateNumber(), driver.getId(), weight, s);
            for (String d : binds.keySet()) {
                shipmentController.addDocument(date, departureHour, driver.getId(), locationController.getLocation(d), binds.get(d));
            }
            return new Response();
        } catch (Exception e) {
            return new Response(e.getMessage());
        }
    }

    /**
     * @param truckId
     * @param date
     * @param departureHour
     * @param driverId
     * @throws Exception
     */
    private void weighTruck(String truckId, Date date, String departureHour, String driverId) throws Exception {
        double realWeight = truckController.getTruck(truckId).getNatoWeight();
        realWeight += shipmentController.getShipment(date, departureHour, driverId).getShipmentWeight();
        if (realWeight > truckController.getTruck(truckId).getMaxWeight())
            throw new Exception("Truck's weight exceeded the limit.");
    }
}