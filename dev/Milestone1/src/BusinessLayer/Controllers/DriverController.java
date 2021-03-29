package BusinessLayer.Controllers;

import BusinessLayer.Objects.Driver;

import java.util.LinkedList;
import java.util.List;

public class DriverController {
    private List<Driver> data;

    public DriverController() {
        data = new LinkedList<>();
    }

    /***
     * Searching for the needed driver
     * @param driverID - Driver unique ID
     * @return driver which has the given ID
     * @throws Exception in case driver not found in the system
     */
    public Driver getDriver(String driverID) throws Exception {
        for (Driver d: data) {
            if(d.getId().equals(driverID))
                return d;
        }
        throw new Exception("No such driver id");
    }

    /***
     * Adding a new driver to the system
     * @param id - Driver unique ID
     * @param name - Driver's name
     * @param allowedWeight - The maximum weight the driver can transport
     * @throws Exception in case of invalid parameters
     */
    public void addDriver(String id, String name, double allowedWeight) throws Exception {
        for (Driver d: data) {
            if(d.getId().equals(id))
                throw new Exception("Couldn't add new driver - driverId already exists");
        }
        if(allowedWeight <= 0)
            throw new Exception("Couldn't add new driver - Invalid parameters");
        data.add(new Driver(id, name, allowedWeight));
    }
}