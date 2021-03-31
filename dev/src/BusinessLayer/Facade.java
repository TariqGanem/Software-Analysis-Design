package BusinessLayer;

import BusinessLayer.EmployeePackage.*;
import BusinessLayer.ShiftPackage.*;
import java.util.Date;
import java.util.List;

import javax.naming.NoPermissionException;

public class Facade {
    private EmployeeController employeeController;
    private ShiftController shiftController;
    //private boolean isManager;

    public Facade() {
        employeeController = new EmployeeController();
        shiftController = new ShiftController();
        //isManager = false;
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
    		if(!employeeController.isManager())
    			throw new NoPermissionException("this act can be performed by managers only.");
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
			if(!employeeController.isManager())
				throw new NoPermissionException("this act can be performed by managers only.");
			shiftController.removeFromShift(id);
		} catch (Exception e) {
			return new Response(e);
		}
		return new Response();
	}
	
	public Response definePersonnelForShift(int day, boolean isMorning, Role skill, int qtty) {
		try {
			if(!employeeController.isManager())
				throw new NoPermissionException("this act can be performed by managers only.");
			shiftController.definePersonnelForShift(day, isMorning, skill, qtty);
		} catch (Exception e) {
			return new Response(e);
		}
		return new Response();
	}
	
	public Response addShift(Date date, boolean isMorning) {
		try {
			if(!employeeController.isManager())
				throw new IllegalArgumentException("this act can be performed by managers only.");
			shiftController.addShift(date, isMorning);
		} catch (Exception e) {
			return new Response(e);
		}
		return new Response();
	}
    

    public ResponseT<String> viewProfile(String ID) {
        ResponseT<String> response;
        try {
            String profile = employeeController.viewProfile(ID);
            response = new ResponseT<String>(profile);
        } catch (Exception e) {
            response = new ResponseT<String>(e);
        }
        return response;
    }

    public Response changeShiftPreference(int day, boolean isMorning, int preference) {
        Response response;
        try {
            employeeController.changeShiftPreference(day, isMorning, preference);
            response = new Response();
        } catch (Exception e) {
            response = new Response(e);
        }
        return response;
    }

    public Response addEmployee(String name, String ID, int bankId, int branchId, int accountNumber, float salary,
                                Date startDate, String trustFund, int freeDays, int sickDays, List<Role> skills) {
        Response response;
        try {
            employeeController.addEmployee(name, ID, bankId, branchId, accountNumber, salary, startDate, trustFund, freeDays, sickDays, skills);
            response = new Response();
        } catch (Exception e) {
            response = new Response(e);
        }
        return response;
    }

    public ResponseT<String> getSkills(String ID) {
        ResponseT<String> response;
        try {
            String roles = employeeController.getSkills(ID);
            response = new ResponseT<String>(roles);
        } catch (Exception e) {
            response = new ResponseT<String>(e);
        }
        return response;
    }

    public ResponseT<List<String>> viewAvailableEmployees(int day, boolean isMorning, Role skill) {
        ResponseT<List<String>> response;
        try {
            List<String> employees = employeeController.viewAvailableEmployees(day, isMorning, skill);
            response = new ResponseT<List<String>>(employees);
        } catch (Exception e) {
            response = new ResponseT<List<String>>(e);
        }
        return response;
    }

}
