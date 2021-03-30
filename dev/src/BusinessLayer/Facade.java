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
            response = new Response(e.getMessage());
        }
        return response;
    }

    public Response AssignToShift(String id, Role skill) {
    	try {
    		shiftController.AssignToShift(id, skill);
		} catch (Exception e) {
			return new Response(e);
		}
		return new Response();
	}
	
	public Response removeFromShift(String id) {
		return shiftController.removeFromShift(id);
	}
	
	public Response definePersonnelForShift(int day, boolean isMorning, Role skill, int qtty) {
		return shiftController.definePersonnelForShift(day, isMorning, skill, qtty);
	}
	
	public boolean addShift(Date date, boolean isMorning) {
		
	}
    
}
