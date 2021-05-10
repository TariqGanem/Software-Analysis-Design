package BusinessLayer.ShipmentsModule.Controllers;

import APIs.EmployeeModuleAPI.EmployeeModuleAPI;
import BusinessLayer.ShipmentsModule.Builder;
import BusinessLayer.ShipmentsModule.Objects.Driver;
import DTOPackage.DriverDTO;
import DataAccessLayer.ShipmentsModule.Mappers.DriverMapper;

import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Date;
import java.util.LinkedList;
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
     * @param allowedWeight - The maximum weight the driver can transport
     * @throws Exception in case of invalid parameters
     */
    public void addDriver(String id, double allowedWeight) throws Exception {
        if (id == null || id.trim().isEmpty() || allowedWeight <= 0)
            throw new Exception("Couldn't add new driver - Invalid parameters");
        mapper.addDriver(id, allowedWeight);
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
    public List<Driver> getAvailableDriver(double weight, Date date, String hour) throws Exception {
        boolean isMorning = handleHour(hour);
        List<String> ids = new EmployeeModuleAPI().getAvailableDrivers(convertToLocalDateViaInstant(date), isMorning);
        List<Driver> availableDrivers = new LinkedList<>();
        for (String id : ids) {
            DriverDTO driver = mapper.getAvailableDriver(id, weight);
            if (driver != null) {
                availableDrivers.add(Builder.build(driver));
            }
        }
        if (availableDrivers.isEmpty())
            throw new Exception("No available drivers");
        return availableDrivers;
    }

    private boolean handleHour(String hour) {
        int left = Integer.parseInt(hour.substring(0, 2));
        return left >= 6 && left <= 14;
    }

    public LocalDate convertToLocalDateViaInstant(Date dateToConvert) {
        return dateToConvert.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
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