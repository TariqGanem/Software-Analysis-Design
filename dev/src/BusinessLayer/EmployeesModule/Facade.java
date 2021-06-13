package BusinessLayer.EmployeesModule;

import BusinessLayer.EmployeesModule.EmployeePackage.Employee;
import BusinessLayer.EmployeesModule.EmployeePackage.EmployeeController;
import BusinessLayer.EmployeesModule.ShiftPackage.Shift;
import BusinessLayer.EmployeesModule.ShiftPackage.ShiftController;
import BusinessLayer.Response;
import BusinessLayer.ResponseT;
import DTOPackage.EmployeeDTO;
import DTOPackage.ShiftDTO;
import DataAccessLayer.EmployeesModule.DALController;
import Resources.Preference;
import Resources.Role;

import javax.naming.NoPermissionException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Facade {
    private EmployeeController employeeController;
    private ShiftController shiftController;

    public Facade() {
        DALController dalController = new DALController();
        employeeController = new EmployeeController(dalController);
        shiftController = new ShiftController(dalController);
    }

    public boolean getIsManager() {
        return employeeController.isManager();
    }

    public ResponseT<EmployeeDTO> getEmployee(String ID) {
        ResponseT<EmployeeDTO> response;
        try {
            response = new ResponseT<EmployeeDTO>(toEmployeeDTO(employeeController.getEmployee(ID)));
        } catch (Exception e) {
            response = new ResponseT<EmployeeDTO>(e.getMessage(), new EmployeeDTO());
        }
        return response;
    }

    public String getName(String ID) {
        return employeeController.getName(ID);
    }

    public Response setEmployee(String oldID, EmployeeDTO employee) {
        Response response;
        Employee rollback = null;
        List<Role> rollbackSkills = null;
        LocalDate rollbackDate = null;
        Preference[][] rollbackTimeFrames = new Preference[7][2];
        try {
            rollbackSkills = new ArrayList<>(employeeController.getEmployee(oldID).getSkills());
            rollbackDate = employeeController.getEmployee(oldID).getStartDate();
            for (int i = 0; i < 7; i++)
                for (int j = 0; j < 2; j++)
                    rollbackTimeFrames[i][j] = employeeController.getEmployee(oldID).getTimeFrames()[i][j];
            rollback = employeeController.getEmployee(oldID);

            employeeController.updateEmployee(rollback.getID(), employee.name, employee.ID, employee.bankId, employee.branchId, employee.accountNumber, employee.salary,
                    employee.startDate, employee.trustFund, employee.freeDays, employee.sickDays, employee.skills, employee.timeFrames);
            response = new Response();
        } catch (Exception e) {
            response = new Response(e.getMessage());
            try {
                employeeController.updateEmployee(rollback.getID(), rollback.getName(), rollback.getID(), rollback.getBankId(), rollback.getBranchId(), rollback.getAccountNumber(),
                        rollback.getSalary(), rollbackDate, rollback.getTrustFund(), rollback.getFreeDays(), rollback.getSickDays(), rollbackSkills, rollbackTimeFrames);
            } catch (Exception ignored) {
            }
        }
        return response;
    }

    private EmployeeDTO toEmployeeDTO(Employee employee) {
        EmployeeDTO toReturn = new EmployeeDTO();
        toReturn.name = employee.getName();
        toReturn.ID = employee.getID();
        toReturn.bankId = employee.getBankId();
        toReturn.branchId = employee.getBranchId();
        toReturn.accountNumber = employee.getAccountNumber();
        toReturn.salary = employee.getSalary();
        toReturn.startDate = employee.getStartDate();
        toReturn.trustFund = employee.getTrustFund();
        toReturn.freeDays = employee.getFreeDays();
        toReturn.sickDays = employee.getSickDays();
        List<Role> newSkills = new ArrayList<>(employee.getSkills());
        toReturn.skills = newSkills;
        Preference[][] newTimeFrames = new Preference[7][2];
        for (int i = 0; i < 7; i++)
            for (int j = 0; j < 2; j++)
                newTimeFrames[i][j] = employee.getTimeFrames()[i][j];
        toReturn.timeFrames = newTimeFrames;
        return toReturn;
    }

    public ResponseT<ShiftDTO> getShift(LocalDate date, boolean isMorning) {
        try {
            Shift shift = shiftController.getShift(date, isMorning);
            if (shift == null)
                return new ResponseT<ShiftDTO>("Couldn't find specified shift");
            return new ResponseT<ShiftDTO>(toShiftDTO(shift));
        } catch (Exception ex) {
            return new ResponseT<ShiftDTO>(ex.getMessage());
        }
    }

    public ResponseT<List<ShiftDTO>> getShifts(int daysFromToday) {
        List<ShiftDTO> shiftDTOs = new ArrayList<>();
        for (Shift shift : shiftController.getShifts(daysFromToday)) {
            shiftDTOs.add(toShiftDTO(shift));
        }
        ResponseT<List<ShiftDTO>> res;
        if (shiftDTOs.isEmpty())
            return new ResponseT<>("no shift in the next " + daysFromToday + " days.");
        return new ResponseT<>(shiftDTOs);
    }

    private ShiftDTO toShiftDTO(Shift shift) {
        return new ShiftDTO(shift.getDate(), shift.isMorning(), shift.getPositions());
    }

    public Response login(String ID) {
        Response response;
        try {
            employeeController.login(ID);
            response = new Response();
        } catch (Exception e) {
            response = new Response(e.getMessage());
        }
        return response;
    }

    public Response logout() {
        Response response;
        try {
            employeeController.logout();
            response = new Response();
        } catch (Exception e) {
            response = new Response(e.getMessage());
        }
        return response;
    }

    public Response AssignToShift(String id, Role skill) {
        try {
            if (!employeeController.isValidID(id))
                throw new IllegalArgumentException("invalid id.");
            if (!employeeController.isManager())
                throw new NoPermissionException("this act can be performed by managers only.");
            List<Role> roles = employeeController.getEmployee(id).getSkills();
            if (roles.stream().noneMatch(x -> x.name().equals(skill.name())))
                throw new NoPermissionException("this employee doesn't have the correct roles.");
            shiftController.AssignToShift(id, skill);
        } catch (Exception e) {
            return new Response(e.getMessage());
        }
        return new Response();
    }

    public Response removeFromShift(String id) {
        try {
            if (!employeeController.isValidID(id))
                throw new IllegalArgumentException("invalid id.");
            if (!employeeController.isManager())
                throw new NoPermissionException("this act can be performed by managers only.");
            shiftController.removeFromShift(id);
        } catch (Exception e) {
            return new Response(e.getMessage());
        }
        return new Response();
    }

    public Response definePersonnelForShift(int day, boolean isMorning, Role skill, int qtty) {
        try {
            if (!employeeController.isManager())
                throw new NoPermissionException("this act can be performed by managers only.");
            shiftController.definePersonnelForShift(day, isMorning, skill, qtty);
        } catch (Exception e) {
            return new Response(e.getMessage());
        }
        return new Response();
    }

    public Map<Role, Integer> getPersonnelForShift(int day, boolean isMorning) {
        return shiftController.getPersonnelForShift(day, isMorning);
    }

    public Response addShift(LocalDate date, boolean isMorning) {
        try {
            if (!employeeController.isManager())
                throw new NoPermissionException("this act can be performed by managers only.");
            shiftController.addShift(date, isMorning);
        } catch (Exception e) {
            return new Response(e.getMessage());
        }
        return new Response();
    }

    public Response removeShift(LocalDate date, boolean isMorning) {
        try {
            if (!employeeController.isManager())
                throw new NoPermissionException("this act can be performed by managers only.");
            if (!shiftController.removeShift(date, isMorning))
                throw new IllegalArgumentException("this shift does not exists.");
        } catch (Exception e) {
            return new Response(e.getMessage());
        }
        return new Response();
    }

    public Response addEmployee(String name, String ID, int bankId, int branchId, int accountNumber, float salary,
                                LocalDate startDate, String trustFund, int freeDays, int sickDays, List<Role> skills, Preference[][] timeFrames) {
        Response response;
        try {
            employeeController.addEmployee(name, ID, bankId, branchId, accountNumber, salary, startDate, trustFund, freeDays, sickDays, skills, timeFrames);
            response = new Response();
        } catch (Exception e) {
            response = new Response(e.getMessage());
        }
        return response;
    }

    public ResponseT<Map<String, String>> viewAvailableEmployees(LocalDate date, boolean isMorning, Role skill, boolean unavailable) {
        ResponseT<Map<String, String>> response;
        try {
            int day = (date.getDayOfWeek().getValue() + 1) % 7;
            if (day == 0)
                day = 7;
            Map<String, String> employees;
            if (!unavailable)
                employees = employeeController.viewAvailableEmployees(day, isMorning, skill);
            else
                employees = employeeController.viewUnavailableEmployees(day, isMorning, skill);
            response = new ResponseT<Map<String, String>>(employees);
        } catch (Exception e) {
            response = new ResponseT<Map<String, String>>(e.getMessage());
        }
        return response;
    }

    public ResponseT<Map<ShiftDTO, Role>> getEmpShifts(String id) {
        Map<Shift, Role> shifts = shiftController.getEmpShifts(id);
        Map<ShiftDTO, Role> ret = new HashMap<>();
        for (Shift shift : shifts.keySet()) {
            ShiftDTO sDTO = toShiftDTO(shift);
            ret.put(sDTO, shifts.get(shift));
        }
        return new ResponseT<>(ret);
    }

    public boolean API_isRoleAssignedToShift(LocalDate date, boolean isMorning, Role role) {
        return shiftController.API_isRoleAssignedToShift(date, isMorning, role);
    }

    public boolean API_isDriverAssignedToShift(LocalDate date, boolean isMorning, String ID) {
        return shiftController.API_isDriverAssignedToShift(date, isMorning, ID);
    }


    public List<String> API_getAvailableDrivers(LocalDate date, boolean isMorning) {
        return shiftController.API_getAvailableDrivers(date, isMorning);
    }

    public boolean API_isShipmentManager(String ID) {
        return employeeController.API_isShipmentManager(ID);
    }

    public void API_alertHRManager(String msg) {
        employeeController.API_alertHRManager(msg);
    }

    public ResponseT<List<String>> checkForAlerts() {
        return employeeController.checkForAlerts();
    }

    public boolean hasRole(String ID, Role role) {
        return employeeController.hasRole(ID, role);
    }
}
