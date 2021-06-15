package APIs;

import PresentationLayer.EmployeesMenu.PresentationController;
import Resources.Role;

public class EmployeesSuppliersAPI {
    private PresentationController pController = PresentationController.getInstance();

    public boolean hasRole(String ID, Role role) {
        return pController.hasRole(ID, role);
    }
}
