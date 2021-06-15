package APIs;

import PresentationLayer.EmployeesMenu.PresentationController;
import Resources.Role;

import java.time.LocalDate;
import java.util.List;

public class EmployeesShipmentsAPI {
    private PresentationController pController;

    public EmployeesShipmentsAPI() {
        pController = PresentationController.getInstance();
    }

    public boolean isRoleAssignedToShift(LocalDate date, boolean isMorning, Role role) {
        return pController.API_isRoleAssignedToShift(date, isMorning, role);
    }

    public boolean isDriverAssignedToShift(LocalDate date, boolean isMorning, String ID) {
        return pController.API_isDriverAssignedToShift(date, isMorning, ID);
    }

    public List<String> getScheduledDrivers(LocalDate date, boolean isMorning) {
        return pController.API_getAvailableDrivers(date, isMorning);
    }

    public void alertHRManager(String msg) {
        pController.API_alertHRManager(msg);
    }

    public boolean hasRole(String ID, Role role) {
        return pController.hasRole(ID, role);
    }
}
