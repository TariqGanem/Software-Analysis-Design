package PresentationLayer.EmployeesMenu;

import BusinessLayer.EmployeesModule.Facade;
import BusinessLayer.Response;
import BusinessLayer.ResponseT;
import DTOPackage.EmployeeDTO;
import DTOPackage.ShiftDTO;
import Resources.Preference;
import Resources.Role;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class PresentationController {
    private static PresentationController instance = null;
    private Facade facade;
    private IOController io;
    private String activeEmployee;

    public static PresentationController getInstance() {
        if (instance == null)
            instance = new PresentationController();
        return instance;
    }

    private PresentationController() {
        facade = new Facade();
        io = IOController.getInstance();
        activeEmployee = null;
    }

    public boolean login(String ID) {
        Response response = facade.login(ID);
        if (response.getErrorOccurred())
            io.println(response.getErrorMessage());
        else
            activeEmployee = ID;
        return !response.getErrorOccurred();
    }

    public boolean getIsManager() {
        return facade.getIsManager();
    }

    public boolean viewProfile(String ID) {
        ResponseT<EmployeeDTO> response = ID.equals("") ? facade.getEmployee(activeEmployee) : facade.getEmployee(ID);
        if (response.getErrorOccurred())
            io.println(response.getErrorMessage());
        else
            io.println(response.getValue().viewProfile());
        return response.getErrorOccurred();
    }

    public Map<ShiftDTO, Role> viewMyShifts() {
        ResponseT<Map<ShiftDTO, Role>> response = facade.getEmpShifts(activeEmployee);
        if (response.getErrorOccurred()) {
            io.println(response.getErrorMessage());
            return null;
        } else if (response.getValue().keySet().size() == 0) {
            io.println("You are not assigned to any shifts.");
            return null;
        }
        int i = 1;
        for (ShiftDTO shiftDTO : response.getValue().keySet()) {
            io.println(i + ") At " + shiftDTO.describeShift() + " you are assigned as " + response.getValue().get(shiftDTO).name());
            i++;
        }
        return response.getValue();
    }

    public List<ShiftDTO> viewShiftsAsAdmin(int dayFromToday) {
        ResponseT<List<ShiftDTO>> res = facade.getShifts(dayFromToday);
        if (res.getErrorOccurred()) {
            io.println(res.getErrorMessage());
            return null;
        }
        return res.getValue();
    }

    public boolean viewAShiftAsAdmin(LocalDate localDate, boolean isMorning) {
        ResponseT<ShiftDTO> response = facade.getShift(localDate, isMorning);
        if (response.getErrorOccurred()) {
            io.println(response.getErrorMessage());
            return false;
        } else {
            ShiftDTO shiftDTO = response.getValue();
            if (shiftDTO.positions.isEmpty())
                io.println("No employee is assigned to this shift yet.");
            for (Role role : shiftDTO.positions.keySet()) {
                io.println(role.name() + ":");
                for (String ID : shiftDTO.positions.get(role)) {
                    String nameResponse = facade.getName(ID);
                    if (response.getErrorOccurred()) {
                        io.println(response.getErrorMessage());
                        return false;
                    }
                    io.println("\t" + ID + " " + nameResponse);
                }
            }
            return true;
        }
    }

    public boolean logout() {
        Response response = facade.logout();
        if (response.getErrorOccurred())
            io.println(response.getErrorMessage());
        else
            activeEmployee = null;
        return response.getErrorOccurred();
    }

    public void changePreference(int day, boolean isMorning, Preference preference) {
        ResponseT<EmployeeDTO> responseOfGet = facade.getEmployee(activeEmployee);
        if (isMorning)
            responseOfGet.getValue().timeFrames[day][0] = preference;
        else
            responseOfGet.getValue().timeFrames[day][1] = preference;
        Response responseOfSet = facade.setEmployee(responseOfGet.getValue().ID, responseOfGet.getValue());
        if (responseOfSet.getErrorOccurred())
            io.println(responseOfSet.getErrorMessage());
    }

    public boolean addShift(LocalDate date, boolean isMorning) {
        Response res = facade.addShift(date, isMorning);
        if (res.getErrorOccurred()) {
            io.println(res.getErrorMessage());
            return false;
        }
        return true;
    }

    public ShiftDTO getShift(LocalDate date, boolean isMorning) {
        ResponseT<ShiftDTO> res = facade.getShift(date, isMorning);
        if (res.getErrorOccurred()) {
            io.println(res.getErrorMessage());
            return null;
        }
        return res.getValue();
    }

    public ResponseT<EmployeeDTO> getEmployeeDTO(String ID) {
        ResponseT<EmployeeDTO> response = facade.getEmployee(ID);
        if (response.getErrorOccurred())
            io.println(response.getErrorMessage());
        return response;
    }

    public boolean setEmployeeDTO(String oldID, EmployeeDTO employee) {
        Response response = facade.setEmployee(oldID, employee);
        if (response.getErrorOccurred())
            io.println(response.getErrorMessage());
        else
            activeEmployee = employee.ID;
        return response.getErrorOccurred();
    }

    public boolean addEmployee(String name, String ID, int bankId, int branchId, int accountNumber, float salary,
                               LocalDate startDate, String trustFund, int freeDays, int sickDays, List<Role> skills, Preference[][] timeFrames) {
        Response response = facade.addEmployee(name, ID, bankId, branchId, accountNumber, salary, startDate, trustFund, freeDays, sickDays, skills, timeFrames);
        if (response.getErrorOccurred())
            io.println(response.getErrorMessage());
        else
            io.println("Employee added successfully.");
        return response.getErrorOccurred();
    }

    public void defineShiftPersonnel(int day, boolean isMorning, Role role, int qtty) {
        Response res = facade.definePersonnelForShift(day, isMorning, role, qtty);
        if (res.getErrorOccurred())
            io.println(res.getErrorMessage());
    }

    public Map<Role, Integer> getPersonnelForShift(int day, boolean isMorning) {
        return facade.getPersonnelForShift(day, isMorning);
    }

    public void assignToShift(String id, Role role) {
        Response res = facade.AssignToShift(id, role);
        if (res.getErrorOccurred())
            io.println(res.getErrorMessage());
    }

    public Map<String, String> viewAvailableEmployees(LocalDate date, boolean isMorning, Role role, boolean unavailable) {
        ResponseT<Map<String, String>> res = facade.viewAvailableEmployees(date, isMorning, role, unavailable);
        if (!res.getErrorOccurred())
            return res.getValue();
        io.println(res.getErrorMessage());
        return null;
    }

    public boolean removeFromShift(String empId) {
        Response res = facade.removeFromShift(empId);
        if (res.getErrorOccurred()) {
            io.println(res.getErrorMessage());
            return false;
        }
        return true;
    }

    public boolean removeShift(LocalDate date, boolean isMorning) {
        Response res = facade.removeShift(date, isMorning);
        if (res.getErrorOccurred()) {
            io.println(res.getErrorMessage());
            return false;
        }
        return true;
    }

    public boolean isIDAlreadyRegistered(String ID) {
        ResponseT<EmployeeDTO> res = facade.getEmployee(ID);
        return !res.getErrorOccurred();
    }

    public boolean API_isRoleAssignedToShift(LocalDate date, boolean isMorning, Role role) {
        return facade.API_isRoleAssignedToShift(date, isMorning, role);
    }

    public boolean API_isDriverAssignedToShift(LocalDate date, boolean isMorning, String ID) {
        return facade.API_isDriverAssignedToShift(date, isMorning, ID);
    }


    public List<String> API_getAvailableDrivers(LocalDate date, boolean isMorning) {
        return facade.API_getAvailableDrivers(date, isMorning);
    }

    public boolean API_isShipmentManager(String ID) {
        return facade.API_isShipmentManager(ID);
    }

    public void API_alertHRManager(String msg) {
        facade.API_alertHRManager(msg);
    }

    public List<String> checkForAlerts() {
        ResponseT<List<String>> res = facade.checkForAlerts();
        if (res.getErrorOccurred())
            return new ArrayList<>();
        else return res.getValue();
    }

    public boolean hasRole(String ID, Role role) {
        return facade.hasRole(ID, role);
    }
}
