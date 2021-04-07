package PresentationLayer;

import BusinessLayer.Facade;
import BusinessLayer.Response;
import BusinessLayer.ResponseT;
import DTOPackage.EmployeeDTO;
import DTOPackage.ShiftDTO;
import Resources.Preference;
import Resources.Role;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;

public class PresentationController {
    private Facade facade;
    private IOController io;
    private String activeEmployee;

    public PresentationController() {
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

    public boolean viewMyShifts() {
        ResponseT<Map<ShiftDTO, Role>> response = facade.getEmpShifts(activeEmployee);
        if (response.getErrorOccurred())
            io.println(response.getErrorMessage());
        else if(response.getValue().keySet().size() == 0) {
            io.println("You are not assigned to any shifts.");
            return false;
        }
        else {
            for (ShiftDTO shiftDTO: response.getValue().keySet()) {
                io.println("At " + shiftDTO.describeShift() + " you are assigned as " + response.getValue().get(shiftDTO).name());
            }
        }
        return true;
    }

    public boolean viewSpecificShift(LocalDate localDate, boolean isMorning) {
        ResponseT<Map<ShiftDTO, Role>> response = facade.getEmpShifts(activeEmployee);
        if (response.getErrorOccurred()) {
            io.println(response.getErrorMessage());
            return false;
        } else if (response.getValue().keySet().size() == 0){
            io.println("You are not assigned to any shifts.");
            return false;
        }else {
            for (ShiftDTO shiftDTO: response.getValue().keySet()) {
                if(!shiftDTO.date.equals(localDate))
                    continue;
                for (Role role: shiftDTO.positions.keySet()) {
                    io.println(role.name() + ":");
                    for (String ID: shiftDTO.positions.get(role)) {
                        io.println("\t" + ID + " " + facade.getEmployee(ID).getValue().name);
                    }
                }
            }
            return true;
        }
    }

    public List<ShiftDTO> viewShiftsAsAdmin(int dayFromToday) {
        ResponseT<List<ShiftDTO>> res = facade.getShifts(dayFromToday);
        if(res.getErrorOccurred()) {
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
        }else {
            ShiftDTO shiftDTO = response.getValue();
            if(shiftDTO.positions.isEmpty())
                io.println("No employee is assigned to this shift yet.");
            for (Role role: shiftDTO.positions.keySet()) {
                io.println(role.name() + ":");
                for (String ID: shiftDTO.positions.get(role)) {
                    io.println("\t" + ID + " " + facade.getEmployee(ID).getValue().name);
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
        Response responseOfSet = facade.setEmployee(responseOfGet.getValue());
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
        if (res.getErrorOccurred()){
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

    public void setEmployeeDTO(EmployeeDTO employee) {
        Response response = facade.setEmployee(employee);
        if (response.getErrorOccurred())
            io.println(response.getErrorMessage());
    }

    public void addEmployee(String name, String ID, int bankId, int branchId, int accountNumber, float salary,
                            LocalDate startDate, String trustFund, int freeDays, int sickDays, List<Role> skills, Preference[][] timeFrames) {
        Response response = facade.addEmployee(name, ID, bankId, branchId, accountNumber, salary, startDate, trustFund, freeDays, sickDays, skills, timeFrames);
        if (response.getErrorOccurred())
            io.println(response.getErrorMessage());
        else
            io.println("Employee added successfully.");
    }

    public void defineShiftPersonnel(int day, boolean isMorning, Role role, int qtty) {
        Response res = facade.definePersonnelForShift(day, isMorning, role, qtty);
        if(res.getErrorOccurred())
            io.println(res.getErrorMessage());
    }

    public Map<Role, Integer> getPersonnelForShift(int day, boolean isMorning){
        return facade.getPersonnelForShift(day, isMorning);
    }

    public void assignToShift(String id, Role role) {
        Response res = facade.AssignToShift(id, role);
        if(res.getErrorOccurred())
            io.println(res.getErrorMessage());
    }

    public Map<String,String> viewAvailableEmployees(LocalDate date, boolean isMorning, Role role, boolean unavailable) {
        ResponseT<Map<String,String>> res = facade.viewAvailableEmployees(date, isMorning, role, unavailable);
        if(!res.getErrorOccurred())
            return res.getValue();
        io.println(res.getErrorMessage());
        return null;
    }

    public boolean removeFromShift(String empId) {
        Response res = facade.removeFromShift(empId);
        if(res.getErrorOccurred()) {
            io.println(res.getErrorMessage());
            return false;
        }
        return true;
    }

    public boolean removeShift(LocalDate date, boolean isMorning) {
        Response res = facade.removeShift(date, isMorning);
        if(res.getErrorOccurred()) {
            io.println(res.getErrorMessage());
            return false;
        }
        return true;
    }
}
