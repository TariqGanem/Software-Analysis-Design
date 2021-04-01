package BusinessLayer;

import java.util.*;

import BusinessLayer.EmployeePackage.*;
import BusinessLayer.ShiftPackage.*;
import DTOPackage.EmployeeDTO;
import DTOPackage.ShiftDTO;
import Resources.Preference;
import Resources.Role;

import java.util.Date;

import javax.naming.NoPermissionException;

public class Facade {
    private EmployeeController employeeController;
    private ShiftController shiftController;
    private boolean isManager;

    public Facade() {
        employeeController = new EmployeeController();
        shiftController = new ShiftController();
        isManager = false;
    }

    public ResponseT<EmployeeDTO> getEmployee(String ID) {
        ResponseT<EmployeeDTO> response;
        try {
            response = new ResponseT<EmployeeDTO>(toEmployeeDTO(employeeController.getEmployee(ID)));
        } catch (Exception e) {
            response = new ResponseT<EmployeeDTO>(e);
        }
        return response;
    }

    public Response setEmployee(EmployeeDTO employee) {
        Response response;
        Employee rollback = null;
        List<Role> rollbackSkills = null;
        Date rollbackDate = null;
        Preference[][] rollbackTimeFrames = new Preference[6][2];
        try {
            rollbackSkills = new ArrayList<>(employeeController.getEmployee(employee.ID).getSkills());
            rollbackDate = new Date(employeeController.getEmployee(employee.ID).getStartDate().getTime());
            for (int i = 0; i < 6; i++)
                for (int j = 0; j < 2; j++)
                    rollbackTimeFrames[i][j] = employeeController.getEmployee(employee.ID).getTimeFrames()[i][j];
            rollback = employeeController.getEmployee(employee.ID);

            employeeController.updateEmployee(employee.name, employee.ID, employee.bankId, employee.branchId, employee.accountNumber, employee.salary,
                    employee.startDate, employee.trustFund, employee.freeDays, employee.sickDays, employee.skills, rollbackTimeFrames);
            response = new Response();
        } catch (Exception e) {
            response = new Response(e);
            try {
                employeeController.updateEmployee(rollback.getName(), rollback.getID(), rollback.getBankId(), rollback.getBranchId(), rollback.getAccountNumber(),
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
        toReturn.startDate = new Date(employee.getStartDate().getTime());
        toReturn.trustFund = employee.getTrustFund();
        toReturn.freeDays = employee.getFreeDays();
        toReturn.sickDays = employee.getSickDays();
        List<Role> newSkills = new ArrayList<>(employee.getSkills());
        toReturn.skills = newSkills;
        Preference[][] newTimeFrames = new Preference[6][2];
        for (int i = 0; i < 6; i++)
            for (int j = 0; j < 2; j++)
                newTimeFrames[i][j] = employee.getTimeFrames()[i][j];
        toReturn.timeFrames = newTimeFrames;
        return toReturn;
    }
/*
    public ResponseT<ShiftDTO> getShift(Date date, boolean isMorning) {
    	return new ResponseT<ShiftDTO>(toShiftDTO(shiftController.getShift(date, isMorning)));
    }
*/
    private ShiftDTO toShiftDTO(Shift shift) {
    	return new ShiftDTO(shift.getDate(), shift.isMorning(), shift.getPositions());
    }

    public Response login(String ID) {
        Response response;
        try {
            employeeController.login(ID);
            isManager = employeeController.isManager();
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
    			throw new NoPermissionException("this act can be performed by managers only.");
            shiftController.addShift(date, isMorning);
        } catch (Exception e) {
            return new Response(e);
        }
        return new Response();
    }
    
    public Response removeShift(Date date, boolean isMorning) {
        try {
        	if(!employeeController.isManager())
    			throw new NoPermissionException("this act can be performed by managers only.");
            shiftController.removeShift(date, isMorning);
        } catch (Exception e) {
            return new Response(e);
        }
        return new Response();
    }

    public Response addEmployee(String name, String ID, int bankId, int branchId, int accountNumber, float salary,
                                Date startDate, String trustFund, int freeDays, int sickDays, List<Role> skills, Preference[][] timeFrames) {
        Response response;
        try {
            employeeController.addEmployee(name, ID, bankId, branchId, accountNumber, salary, startDate, trustFund, freeDays, sickDays, skills, timeFrames);
            response = new Response();
        } catch (Exception e) {
            response = new Response(e);
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

    public ResponseT<Map<ShiftDTO, Role>> getEmpShifts(String id){
        Map<Shift, Role> shifts = shiftController.getEmpShifts(id);
        Map<ShiftDTO, Role> ret = new HashMap<>();
        for(Shift shift : shifts.keySet()){
            ShiftDTO sDTO = toShiftDTO(shift);
            ret.put(sDTO, shifts.get(shift));
        }
        return new ResponseT<>(ret);
    }

}
