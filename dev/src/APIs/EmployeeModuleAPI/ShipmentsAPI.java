package APIs.EmployeeModuleAPI;

import PresentationLayer.ShipmentsMenu.Menu;

public class ShipmentsAPI {

    public ShipmentsAPI() {
    }

    public void addDriver(String driverId, String name, double allowedWeight) {
        Menu.getInstance().getDriversHandler().addDriver(driverId, name, allowedWeight);
    }

}
