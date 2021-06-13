package APIs;

import PresentationLayer.ShipmentsMenu.Menu;

import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Date;

public class ShipmentsEmployeesAPI {

    public ShipmentsEmployeesAPI() {
    }

    public void addDriver(String driverId) {
        Menu.getInstance().getDriversHandler().addDriver(driverId);
    }

    public void initDriver(String id, double allowedWeight) {
        Menu.getInstance().getDriversHandler().initDriver(id, allowedWeight);
    }

    public void deleteShipmentWithDriver(String id, LocalDate date, boolean isMorning) {
        Menu.getInstance().getFacade().removeAllShipmentsDriver(Date.from(date.atStartOfDay(ZoneId.systemDefault()).toInstant()), isMorning, id);
    }

    public void deleteShipmentWithStoreKeeper(LocalDate date, boolean isMorning) {
        Menu.getInstance().getFacade().removeShipments(Date.from(date.atStartOfDay(ZoneId.systemDefault()).toInstant()), isMorning);
    }

}
