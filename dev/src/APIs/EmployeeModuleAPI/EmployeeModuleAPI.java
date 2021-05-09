package APIs.EmployeeModuleAPI;

import PresentationLayer.EmployeeMenu.PresentationController;
import Resources.Role;

import java.time.LocalDate;
import java.util.List;

public class EmployeeModuleAPI implements iEmployeeModuleAPI {
    private PresentationController pController;

    public EmployeeModuleAPI() {
        pController = PresentationController.getInstance();
    }

    @Override
    public boolean isRoleAssignedToShift(LocalDate date, boolean isMorning, Role role) {
        return pController.API_isRoleAssignedToShift(date, isMorning, role);
    }

    @Override
    public boolean isDriverAssignedToShift(LocalDate date, boolean isMorning, String ID) {
        return pController.API_isDriverAssignedToShift(date, isMorning, ID);
    }

    @Override
    public List<String> getAvailableDrivers(LocalDate date, boolean isMorning) {
        return pController.API_getAvailableDrivers(date, isMorning);
    }

    @Override
    public boolean isShipmentManager(String ID) {
        return pController.API_isShipmentManager(ID);
    }
}
