package PresentationLayer;

import BusinessLayer.Facade;
import BusinessLayer.Response;
import BusinessLayer.ResponseT;
import DTOPackage.EmployeeDTO;
import DTOPackage.ShiftDTO;
import Resources.Preference;
import Resources.Role;

import java.time.LocalDate;
import java.util.Map;

public class    BackendController {
    private Facade facade;
    private IOController io;
    private String activeEmployee;

    public BackendController() {
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
        return response.getErrorOccurred();
    }

    public boolean getIsManager() {
        return facade.getIsManager();
    }

    public void viewMyProfile() {
        ResponseT<EmployeeDTO> response = facade.getEmployee(activeEmployee);
        if (response.getErrorOccurred())
            io.println(response.getErrorMessage());
        else
            io.println(response.getValue().viewProfile());
    }

    public void viewMyShifts() {
        ResponseT<Map<ShiftDTO, Role>> response = facade.getEmpShifts(activeEmployee);
        if (response.getErrorOccurred())
            io.println(response.getErrorMessage());
        else if(response.getValue().keySet().size() == 0)
            io.println("You are not assigned to any shifts.");
        else {
            for (ShiftDTO shiftDTO: response.getValue().keySet()) {
                io.println("At " + shiftDTO.describeShift() + " you are assigned as " + response.getValue().get(shiftDTO).name());
            }
        }
    }

    public void viewSpecificShift(LocalDate localDate, boolean isMorning) {
        ResponseT<Map<ShiftDTO, Role>> response = facade.getEmpShifts(activeEmployee);
        if (response.getErrorOccurred())
            io.println(response.getErrorMessage());
        else if(response.getValue().keySet().size() == 0)
            io.println("You are not assigned to any shifts.");
        else {
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
}