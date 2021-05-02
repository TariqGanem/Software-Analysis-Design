package EmployeeModuleAPI;

import Resources.Role;

import java.time.LocalDate;

public interface iEmployeeModuleAPI {
    boolean isRoleAssignedToShift(LocalDate date, boolean isMorning, Role role);

    boolean isDriverAssignedToShift(LocalDate date, boolean isMorning, String ID);
}
