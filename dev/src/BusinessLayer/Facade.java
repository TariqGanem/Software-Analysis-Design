package BusinessLayer;

import BusinessLayer.EmployeePackage.*;
import BusinessLayer.ShiftPackage.*;

public class Facade {
    private EmployeeController employeeController;
    private ShiftController shiftController;
    private boolean isManager;

    public Facade() {
        employeeController = new EmployeeController();
        shiftController = new ShiftController();
        isManager = false;
    }

    public Response login(String ID) {
        Response response;
        try {
            employeeController.login(ID);
            response = new Response();
        } catch (Exception e) {
            response = new Response(e);
        }
        return response;
    }

    public ResponseT<String> viewProfile(String ID) {
        ResponseT<String> response;
        try{
            String profile = employeeController.viewProfile(ID);
            response = new ResponseT<String>(profile);
        } catch (Exception e) {
            response = new ResponseT<String>(e.getMessage());
        }
        return response;
    }
}
