package BusinessLayer;

import java.util.Date;

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


    public Response AssignToShift(String id, Role skill) {
    	try {
    		if(!employeeController.isValidID(id))
				throw new IllegalArgumentException("invalid id.");
    		shiftController.AssignToShift(id, skill);
		} catch (Exception e) {
			return new Response(e);
		}
		return new Response();
	}
	
	public Response removeFromShift(String id) {
		try {
			if(!employeeController.isValidID(id))
				throw new IllegalArgumentException("invalid id.");
			shiftController.removeFromShift(id);
		} catch (Exception e) {
			return new Response(e);
		}
		return new Response();
	}
	
	public Response definePersonnelForShift(int day, boolean isMorning, Role skill, int qtty) {
		try {
			shiftController.definePersonnelForShift(day, isMorning, skill, qtty);
		} catch (Exception e) {
			return new Response(e);
		}
		return new Response();
	}
	
	public Response addShift(Date date, boolean isMorning) {
		try {
			shiftController.addShift(date, isMorning);
		} catch (Exception e) {
			return new Response(e);
		}
		return new Response();
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
