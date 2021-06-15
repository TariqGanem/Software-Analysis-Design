package BusinessLayer.ShipmentsModule;

import APIs.EmployeesShipmentsAPI;
import BusinessLayer.Response;
import BusinessLayer.ResponseT;
import BusinessLayer.ShipmentsModule.Controllers.DriverController;
import BusinessLayer.ShipmentsModule.Controllers.LocationController;
import BusinessLayer.ShipmentsModule.Controllers.ShipmentController;
import BusinessLayer.ShipmentsModule.Controllers.TruckController;
import BusinessLayer.ShipmentsModule.Objects.*;
import BusinessLayer.StoreModule.Objects.ItemSpecs;
import DTOPackage.*;
import Resources.Role;

import java.text.SimpleDateFormat;
import java.time.ZoneId;
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
     * Adding a new location to the system
     *
     * @param address     - location unique id
     * @param phoneNumber - phone number of the person to contact
     * @param contactName - the name of the person to contact
     * @return response contains msg in case of any error
     */
    public ResponseT<Integer> addLocation(String address, String phoneNumber, String contactName) {
        try {
            int id = locationController.addLocation(address, phoneNumber, contactName);
            return new ResponseT(id);
        } catch (Exception e) {
            return new ResponseT(e.getMessage());
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
     * @param allowedWeight - The maximum weight he can transport in a delivery
     * @return response contains msg in case of any error
     */
    public Response addDriver(String id, double allowedWeight) {
        try {
            driverController.addDriver(id, allowedWeight);
            return new Response();
        } catch (Exception e) {
            return new Response(e.getMessage());
        }
    }

    /**
     * @return Response of type List<DTO> containing all trucks in the system
     */
    public ResponseT<List<TruckDTO>> getAllTrucks() {
        try {
            List<Truck> trucks = truckController.getAllTrucks();
            return new ResponseT<>(Builder.buildTrucksListDTO(trucks));
        } catch (Exception e) {
            return new ResponseT<>(e.getMessage());
        }
    }

    /**
     * @return all drivers in the system
     */
    public ResponseT<List<DriverDTO>> getAllDrivers() {
        try {
            List<Driver> drivers = driverController.getAllDrivers();
            return new ResponseT<>(Builder.buildDriversListDTO(drivers));
        } catch (Exception e) {
            return new ResponseT<>(e.getMessage());
        }
    }

    /**
     * Arranges a delivery (adding a new delivery) to the system
     *
     * @param orderDueDate          - Date of the shipment to be transported
     * @param sourceId              - The source's address unique id
     * @param items_per_destination - List of items per each destination (Integer => destinationId)
     * @return response of type msg in case of any error
     */
    public Response arrangeDelivery(Date orderDueDate, int sourceId, Map<Integer, List<ShippedItemDTO>> items_per_destination) {
        try {
            Map<Integer, List<Item>> items = Builder.buildItemsPerDestinations(items_per_destination);
            double shipmentWeight = calculateShipmentWeight(items);

            boolean matchWithinWeek;
            Truck truck = null;
            Driver driver;

            for (Date date : getDatesInNextWeek(orderDueDate)) {
                for (Boolean isMorning : new boolean[]{true, false}) {
                    truck = truckController.getAvailableTruck(shipmentWeight, date, isMorning);
                    driver = driverController.getAvailableDriver(truck.getNatoWeight() + truck.getMaxWeight(), date, isMorning);
                    matchWithinWeek = truck != null && driver != null && findStoreKeeper(date, isMorning);
                    if (matchWithinWeek) {
                        String hour = generateHour(isMorning);
                        addShipmentToBeApproved(date, hour, truck.getTruckPlateNumber(), driver.getId(), sourceId, items);
                        truckController.scheduleTruck(truck.getTruckPlateNumber(), date, isMorning, driver.getId());
                        return new Response();
                    }
                }
            }
            if (truck == null)
                return new Response("There are no available trucks in the system within a week from order date.");

            notifyHRManager(orderDueDate);

            return new Response("Unexpected Error...");
        } catch (Exception e) {
            return new Response(e.getMessage());
        }
    }

    private void notifyHRManager(Date date) throws Exception {
        List<Date> dates = getDatesInNextWeek(date);

        String msg = "The Shipment department System discovered mismatching issues in shifts:\n"
                + "-Try to assign driver / storekeeper in the shifts from " + new SimpleDateFormat("dd/MM/yyyy").format(dates.get(0))
                + " to " + new SimpleDateFormat("dd/MM/yyyy").format(dates.get(dates.size() - 1)) + " !";

        new EmployeesShipmentsAPI().alertHRManager(msg);

        throw new Exception("The system didn't succeed to schedule a shipment for this order within 7 days!\n" +
                "The System notified the HR Manager, try again when solved by HR Manager.");
    }


    private void addShipmentToBeApproved(Date date, String departureHour, String truckPlateNumber, String driverId, int sourceId, Map<Integer, List<Item>> items) throws Exception {
        int shipmentId = shipmentController.addShipment(date, departureHour, truckPlateNumber, driverId, sourceId);
        if (shipmentId > 0) {
            for (Integer loc : items.keySet()) {
                if (loc == sourceId) {
                    throw new Exception("Cannot deliver to destination as the same source!");
                }
                shipmentController.addDocument(shipmentId, loc, items.get(loc));
            }
        }
    }

    private boolean findStoreKeeper(Date date, boolean isMorning) {
        return new EmployeesShipmentsAPI().isRoleAssignedToShift(date.toInstant().atZone(ZoneId.systemDefault()).toLocalDate(),
                isMorning, Role.StoreKeeper);
    }

    /***
     * Allows us to find out if its Morning or Evening
     * @param hour - String representation of a real hour
     * @return true if the string represents morning hour --> 6:00 - 14:00
     */
    private boolean handleHour(String hour) {
        int left = Integer.parseInt(hour.substring(0, 2));
        return left >= 6 && left <= 14;
    }

    private String generateHour(boolean isMorning) {
        String hours, minutes;
        Random rand = new Random();
        minutes = ((int) (Math.random() * 60)) + "";
        if (isMorning)
            hours = (rand.nextInt((13 - 6) + 1) + 6) + "";
        else
            hours = (rand.nextInt((21 - 14) + 1) + 14) + "";
        if (hours.length() == 1)
            hours = "0" + hours;
        if (minutes.length() == 1)
            minutes = "0" + minutes;
        return hours + ":" + minutes;
    }

    private List<Date> getDatesInNextWeek(Date dueDate) {
        List<Date> dates = new LinkedList<>();
        long ONE_DAY_MILLI_SECONDS = 24 * 60 * 60 * 1000;
        Date nextDay;
        for (int i = 0; i < 7; i++) {
            nextDay = new Date(dueDate.getTime() + (i) * ONE_DAY_MILLI_SECONDS);
            dates.add(nextDay);
        }
        return dates;
    }

    /**
     * Weighs the requested truck as requested before transportation
     *
     * @param truckId - The truck's unique plate number
     * @return the truck's total weight
     * @throws Exception in case of any error occurs
     * @throws Exception in case of any error occurs
     */
    private double weighTruck(String truckId, double shipmentWeight) throws Exception {
        double realWeight = truckController.getTruck(truckId).getNatoWeight();
        realWeight += shipmentWeight;
        if (realWeight > truckController.getTruck(truckId).getMaxWeight() + truckController.getTruck(truckId).getNatoWeight()) {
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
            return new ResponseT<>(Builder.buildLocationsListDTO(locations));
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
            return new ResponseT<>(Builder.buildShipmentsListDTO(shipments));
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
            return new ResponseT<>(Builder.buildDTO(shipment));
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
            Shipment shipment = shipmentController.getShipment(date, departureHour, driverId);
            //driverController.freeDriver(driverId);
            shipmentController.deleteShipment(shipment.getShipmentId());

            return new Response();
        } catch (Exception e) {
            return new Response(e.getMessage());
        }
    }

    private double calculateShipmentWeight(Map<Integer, List<Item>> items_per_location) {
        double shipmentWeight = 0;
        for (Integer loc : items_per_location.keySet()) {
            for (Item item : items_per_location.get(loc)) {
                shipmentWeight += item.getWeight() * item.getAmount();
            }
        }
        return shipmentWeight;
    }

    public ResponseT<List<ShipmentDTO>> getNotApprovedShipments() {
        try {
            List<Shipment> shipments = shipmentController.getNotApprovedShipments();
            return new ResponseT<>(Builder.buildShipmentsListDTO(shipments));
        } catch (Exception e) {
            return new ResponseT<>(e.getMessage());
        }
    }

    public Response approveShipment(Date date, String depHour, String driverId) {
        try {
            shipmentController.approveShipment(date, depHour, driverId);
            return new Response();
        } catch (Exception e) {
            return new Response(e.getMessage());
        }
    }

    public Response removeAllShipmentsDriver(Date date, boolean isMorning, String driverId) {
        try {
            shipmentController.removeAllShipmentsDriver(date, isMorning, driverId);
            return new Response();
        } catch (Exception e) {
            return new Response(e.getMessage());
        }
    }

    public Response removeShipments(Date date, boolean isMorning) {
        try {
            shipmentController.removeShipments(date, isMorning);
            return new Response();
        } catch (Exception e) {
            return new Response(e.getMessage());
        }
    }

    public ResponseT<List<ItemSpecs>> getReceivedItems(int shipmentId) {
        try {
            List<ItemSpecs> receivedItems = shipmentController.getReceivedItems(shipmentId);
            return new ResponseT<>(receivedItems);
        } catch (Exception e) {
            return new ResponseT<>(e.getMessage());
        }
    }

}