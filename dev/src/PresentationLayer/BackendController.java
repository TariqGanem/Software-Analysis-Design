package PresentationLayer;

import BusinessLayer.Facade;
import BusinessLayer.Response;
import BusinessLayer.ResponseT;
import DTOPackage.EmployeeDTO;

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

    }
}