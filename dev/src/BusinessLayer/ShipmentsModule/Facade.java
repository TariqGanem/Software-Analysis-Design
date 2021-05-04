package ShipmentsModule;

import ShipmentsModule.Controllers.DriverController;
import ShipmentsModule.Controllers.LocationController;
import ShipmentsModule.Controllers.ShipmentController;
import ShipmentsModule.Controllers.TruckController;
import ShipmentsModule.Objects.Driver;
import ShipmentsModule.Objects.Location;
import ShipmentsModule.Objects.Shipment;
import ShipmentsModule.Objects.Truck;
import DTOs.DriverDTO;
import DTOs.LocationDTO;
import DTOs.ShipmentDTO;
import DTOs.TruckDTO;

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
     * @param truckId - the requested truck unique id
     * @return response of type TruckDTO by the given id
     */
    public ResponseT<TruckDTO> getTruckDTO(String truckId) {
        try {
            return new ResponseT<>(new TruckDTO(truckController.getTruck(truckId)));
        } catch (Exception e) {
            return new ResponseT<>(e.getMessage());
        }
    }

    /**
     * @param id - the requested driver unique id
     * @return response of type DriverDTO by the given id
     */
    public ResponseT<DriverDTO> getDriverDTO(String id) {
        try {
            return new ResponseT<>(new DriverDTO(driverController.getDriver(id)));
        } catch (Exception e) {
            return new ResponseT<>(e.getMessage());
        }
    }

    /**
     * @param address - the requested location unique address
     * @return response of type LocationDTO by the given id
     */
    public ResponseT<LocationDTO> getLocationDTO(String address) {
        try {
            return new ResponseT<>(new LocationDTO(locationController.getLocation(address)));
        } catch (Exception e) {
            return new ResponseT<>(e.getMessage());
        }
    }

    /**
     * Adding a new location to the system
     *
     * @param address     - location unique id
     * @param phoneNumber - phone number of the person to contact
     * @param contactName - the name of the person to contact
     * @return response contains msg in case of any error
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
     * @param truckPlateNumber - truck's plate number unique id
     * @param model            - Trucks model
     * @param natoWeight       - Truck's nato weight without any shipments
     * @param maxWeight        - The maximum weight which the truck can transport
     * @return response contains msg in case of any error
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
     * Adds a driver to the system
     *
     * @param id            - The unique id of the requested driver
     * @param name          - The driver's name
     * @param allowedWeight - The maximum weight he can transport in a delivery
     * @return response contains msg in case of any error
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
     * @return Response of type List<DTO> containing all trucks in the system
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
     * @return all drivers in the system
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
     * Arranges a delivery (adding a new delivery) to the system
     *
     * @param date               - Date of the shipment to be transported
     * @param departureHour      - The exact hour for the transportation of the shipment
     * @param source             - The source's address unique id
     * @param items_per_location - Map[ItemName, List[ItemWeight, Quantity]] foreach location
     * @return response of type msg in case of any error
     */
    public Response arrangeDelivery(Date date, String departureHour, String source, Map<String, Map<String, List<Double>>> items_per_location) {
        try {
            double weight = 0;
            for (String loc : items_per_location.keySet()) {
                for (String item : items_per_location.get(loc).keySet()) {
                    weight += (items_per_location.get(loc).get(item).get(0) * items_per_location.get(loc).get(item).get(1));
                }
            }
            TruckDTO truck = new TruckDTO(truckController.getAvailableTruck(weight));
            DriverDTO driver = new DriverDTO(driverController.getAvailableDriver(weight + truck.getNatoWeight()));
            Location s = locationController.getLocation(source);
            Map<Location, Map<String, List<Double>>> items = new HashMap<>();
            for (String loc : items_per_location.keySet()) {
                if (loc.equals(source)) {
                    return new Response("Cannot deliver to destination as the same source, shipment removed.");
                }
                items.put(locationController.getLocation(loc), items_per_location.get(loc));
            }
            shipmentController.addShipment(date, departureHour, truck.getTruckPlateNumber(), driver.getId(), items, s);
            double realWeight = weighTruck(truck.getTruckPlateNumber(), date, departureHour, driver.getId());
            for (String dest : items_per_location.keySet()) {
                shipmentController.addDocument(date, departureHour, driver.getId(), locationController.getLocation(dest), items_per_location.get(dest), realWeight);
            }
            truckController.depositeTruck(truck.getTruckPlateNumber());
            driverController.freeDriver(driver.getId());
            return new Response();
        } catch (Exception e) {
            return new Response(e.getMessage());
        }
    }

    /**
     * Weighs the requested truck as requested before transportation
     *
     * @param truckId       - The truck's unique plate number
     * @param date          - Date of the shipment to be transported
     * @param departureHour - The exact hour for the transportation of the shipment
     * @param driverId      - The driver's unique id
     * @return the truck's total weight
     * @throws Exception in case of any error occurs
     * @throws Exception in case of any error occurs
     */
    private double weighTruck(String truckId, Date date, String departureHour, String driverId) throws Exception {
        double realWeight = truckController.getTruck(truckId).getNatoWeight();
        realWeight += shipmentController.getShipment(date, departureHour, driverId).getShipmentWeight();
        if (realWeight > truckController.getTruck(truckId).getMaxWeight() + truckController.getTruck(truckId).getNatoWeight()) {
            shipmentController.deleteShipment(date, departureHour, driverId);
            throw new Exception("Truck's weight exceeded the limit, shipment has been removed.");
        }
        return realWeight;
    }

    /**
     * @return all locations in the system
     */
    public ResponseT<List<LocationDTO>> getAllLocations() {
        try {
            List<Location> locations = locationController.getAllLocations();
            List<LocationDTO> locationsDTO = new LinkedList<>();
            for (Location d : locations) {
                locationsDTO.add(getLocationDTO(d.getAddress()).getValue());
            }
            return new ResponseT<>(locationsDTO);
        } catch (Exception e) {
            return new ResponseT<>(e.getMessage());
        }
    }

    /**
     * @return all the shipments in the system
     */
    public ResponseT<List<ShipmentDTO>> getAllShipments() {
        try {
            List<Shipment> shipments = shipmentController.getAllShipments();
            List<ShipmentDTO> shipmentsDTO = new LinkedList<>();
            for (Shipment d : shipments) {
                shipmentsDTO.add(getShipmentDTO(d.getDate(), d.getDepartureHour(), d.getDriverId()).getValue());
            }
            return new ResponseT<>(shipmentsDTO);
        } catch (Exception e) {
            return new ResponseT<>(e.getMessage());
        }
    }

    /**
     * Getting a shipment by its unique Ids
     *
     * @param date          - Date of the shipment to be transported
     * @param departureHour - The exact hour for the transportation of the shipment
     * @param driverId      - The driver's unique id
     * @return a response of type ShipmentDTO (the requested shipment)
     */
    public ResponseT<ShipmentDTO> getShipmentDTO(Date date, String departureHour, String driverId) {
        try {
            return new ResponseT<>(new ShipmentDTO(shipmentController.getShipment(date, departureHour, driverId)));
        } catch (Exception e) {
            return new ResponseT<>(e.getMessage());
        }
    }

    /**
     * A shipment follow to view it to the user
     *
     * @param trackingId - the tracking unique number foreach document(sub-shipment)
     * @return response of type ShipmentDTO in order to use in PL
     */
    public ResponseT<ShipmentDTO> trackShipment(int trackingId) {
        try {
            Shipment shipment = shipmentController.trackShipment(trackingId);
            ShipmentDTO s = getShipmentDTO(shipment.getDate(), shipment.getDepartureHour(), shipment.getDriverId()).getValue();
            return new ResponseT<>(s);
        } catch (Exception e) {
            return new ResponseT<>(e.getMessage());
        }
    }

    /**
     * Allows removing shipment from the system
     *
     * @param date          - Date of the shipment to be transported
     * @param departureHour - The exact hour for the transportation of the shipment
     * @param driverId      - The driver's unique id
     * @return response of type msg in case of any error
     */
    public Response removeShipment(Date date, String departureHour, String driverId) {
        try {
            truckController.depositeTruck(shipmentController.getShipment(date, departureHour, driverId).getTruckPlateNumber());
            driverController.freeDriver(driverId);
            shipmentController.deleteShipment(date, departureHour, driverId);

            return new Response();
        } catch (Exception e) {
            return new Response(e.getMessage());
        }
    }
}