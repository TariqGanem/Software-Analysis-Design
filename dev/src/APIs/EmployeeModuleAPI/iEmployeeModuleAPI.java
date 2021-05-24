package APIs.EmployeeModuleAPI;

import Resources.Role;

import java.time.LocalDate;
import java.util.List;

public interface iEmployeeModuleAPI {
    boolean isRoleAssignedToShift(LocalDate date, boolean isMorning, Role role);

    boolean isDriverAssignedToShift(LocalDate date, boolean isMorning, String ID);


    List<String> getAvailableDrivers(LocalDate date, boolean isMorning);

    boolean isShipmentManager(String ID);

    void alertHRManager(LocalDate date);

}
