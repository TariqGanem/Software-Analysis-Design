package BusinessLayer.Controllers;

import BusinessLayer.Objects.Location;

import java.util.LinkedList;
import java.util.List;

public class LocationController {
    private List<Location> data;

    public LocationController() {
        data = new LinkedList<>();
    }

    /***
     * Returns the needed location
     * @param address - unique id for the location
     * @return wanted location
     * @throws Exception in case of invalid parameters
     */
    public Location getLocation(String address) throws Exception {
        for (Location location : data) {
            if (location.getAddress().equals(address))
                return location;
        }
        throw new Exception("No such location");
    }

    /***
     * Adds a new location to the system
     * @param address - unique id for the location
     * @param phoneNumber - Phone number for the driver who transports delivery for this location
     * @param contactName - The name of the driver who transports delivery for this location
     * @throws Exception in case of invalid parameters
     */
    public void addLocation(String address, String phoneNumber, String contactName) throws Exception {
        for (Location location : data) {
            if (location.getAddress().equals(address))
                throw new Exception("Couldn't add new location - address already exists");
        }
        if (address == null || address.trim().isEmpty() || phoneNumber == null || phoneNumber.trim().isEmpty() || contactName == null || contactName.trim().isEmpty())
            throw new Exception("Couldn't add new location - Invalid parameters");
        Location loc = new Location(address, phoneNumber, contactName);
        data.add(loc);
    }

    /**
     * @return all locations in the system
     */
    public List<Location> getAllLocations() {
        return data;
    }
}