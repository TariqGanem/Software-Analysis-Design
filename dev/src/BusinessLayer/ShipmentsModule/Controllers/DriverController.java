package BusinessLayer.ShipmentsModule.Controllers;

import BusinessLayer.ShipmentsModule.Builder;
import BusinessLayer.ShipmentsModule.Objects.Driver;
import DataAccessLayer.ShipmentsModule.Mappers.DriverMapper;

import java.util.List;

public class DriverController {
    private DriverMapper mapper;

    public DriverController() {
        mapper = DriverMapper.getInstance();
    }

    /***
     * Searching for the needed driver
     * @param driverID - Driver unique ID
     * @return driver which has the given ID
     * @throws Exception in case driver not found in the system
     */
    public Driver getDriver(String driverID) throws Exception {
        return Builder.build(mapper.getDriver(driverID));
    }

    /***
     * Adding a new driver to the system
     * @param id - Driver unique ID
     * @param name - Driver's name
     * @param allowedWeight - The maximum weight the driver can transport
     * @throws Exception in case of invalid parameters
     */
    public void addDriver(String id, String name, double allowedWeight) throws Exception {
        if (id == null || id.trim().isEmpty() || name == null || name.trim().isEmpty() || allowedWeight <= 0)
            throw new Exception("Couldn't add new driver - Invalid parameters");
        mapper.addDriver(id, allowedWeight, true);
    }

    /**
     * @return all drivers in the system
     */
    public List<Driver> getAllDrivers() throws Exception {
        return Builder.buildDriversList(mapper.getAllDrivers());
    }

    /**
     * @param weight - The weight of the delivery
     * @return an available driver if exists
     * @throws Exception in case of there is no available driver
     */
    public Driver getAvailableDriver(double weight) throws Exception {
        return null; // TODO
    }

    /**
     * Makes the driver unavailable because he is currently in work
     *
     * @param id - The id of the requested driver
     */
    public void freeDriver(String id) throws Exception {
        mapper.updateDriver(id, true);
    }

    public void makeUnavailableDriver(String id) throws Exception {
        mapper.updateDriver(id, false);
    }
}