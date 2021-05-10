package APIs.EmployeeModuleAPI;

import PresentationLayer.ShipmentsMenu.Menu;

public class ShipmentsAPI {

    public ShipmentsAPI() {
    }

    public void addDriver(String driverId) {
        Menu.getInstance().getDriversHandler().addDriver(driverId);
    }

    public void initDriver(String id, double allowedWeight) {
        Menu.getInstance().getDriversHandler().initDriver(id, allowedWeight);
    }

}
