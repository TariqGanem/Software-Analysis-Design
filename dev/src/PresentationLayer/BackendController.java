package PresentationLayer;

import BusinessLayer.Facade;
import BusinessLayer.Response;
import BusinessLayer.ResponseT;
import DTOPackage.EmployeeDTO;
import DTOPackage.ShiftDTO;
import Resources.Role;

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
}