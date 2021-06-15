package BusinessLayer.ShipmentsModule.Controllers;

import BusinessLayer.ShipmentsModule.Builder;
import BusinessLayer.ShipmentsModule.Objects.Location;
import DataAccessLayer.ShipmentsModule.Mappers.LocationMapper;

import java.util.List;

public class LocationController {
    private LocationMapper mapper;
    private int id;

    public LocationController() {
        mapper = LocationMapper.getInstance();
        id = mapper.getMaxID() + 1;
    }

    /***
     * Returns the needed location
     * @param addressId - unique id for the location
     * @return wanted location
     * @throws Exception in case of invalid parameters
     */
    public Location getLocation(int addressId) throws Exception {
        return Builder.build(mapper.getLocation(addressId));
    }

    /***
     * Adds a new location to the system
     * @param address - unique id for the location
     * @param phoneNumber - Phone number for the driver who transports delivery for this location
     * @param contactName - The name of the driver who transports delivery for this location
     * @throws Exception in case of invalid parameters
     */
    public int addLocation(String address, String phoneNumber, String contactName) throws Exception {
        if (address == null || address.trim().isEmpty() || phoneNumber == null || phoneNumber.trim().isEmpty() || contactName == null || contactName.trim().isEmpty())
            throw new Exception("Couldn't add new location - Invalid parameters");
        if (!mapper.locationExists(address, phoneNumber, contactName)) {
            mapper.addLocation(id, address, phoneNumber, contactName);
            id++;
            return id - 1;
        }
        return mapper._selectLocationE(address, phoneNumber, contactName).getId();
    }

    /**
     * @return all locations in the system
     */
    public List<Location> getAllLocations() throws Exception {
        return Builder.buildLocationsList(mapper.getAllLocations());
    }
}