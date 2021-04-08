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
import javafx.util.Pair;

import java.util.*;

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
     * @return
     */
    public Response addLocation(String address, String phoneNumber, String contactName) {
        try {
            locationController.addLocation(address, phoneNumber, contactName);
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
     *
     * @param date
     * @param departureHour
     * @param source
     * @param items
     * @param dests
     * @return
     */
    public Response arrangeDelivery(Date date, String departureHour, String source, Map<String, Pair<Double, Integer>> items, List<String> dests) {
        try {
            double weight = 0;
            for (String item: items.keySet()) {
                weight += (items.get(item).getKey() * items.get(item).getValue());
            }
            DriverDTO driver = new DriverDTO(driverController.getAvailableDriver(weight));
            TruckDTO truck = new TruckDTO(truckController.getAvailableTruck(weight));
            Location s = locationController.getLocation(source);
            List<Location> d = new LinkedList<>();
            for (String loc : dests) {
                d.add(locationController.getLocation(loc));
            }
            shipmentController.addShipment(date, departureHour, truck.getTruckPlateNumber(), driver.getId(), items, s, d);
            double realWeight = weighTruck(truck.getTruckPlateNumber(), date, departureHour, driver.getId());
            for (String dest : dests) {
                shipmentController.addDocument(date, departureHour, driver.getId(), locationController.getLocation(dest), items, realWeight);
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
    private double weighTruck(String truckId, Date date, String departureHour, String driverId) throws Exception {
        double realWeight = truckController.getTruck(truckId).getNatoWeight();
        realWeight += shipmentController.getShipment(date, departureHour, driverId).getShipmentWeight();
        if (realWeight > truckController.getTruck(truckId).getMaxWeight()) {
            shipmentController.deleteShipment(date, departureHour, driverId);
            throw new Exception("Truck's weight exceeded the limit, shipment has been removed.");
        }
        return realWeight;
    }
}