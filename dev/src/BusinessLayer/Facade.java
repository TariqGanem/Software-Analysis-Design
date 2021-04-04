package BusinessLayer;

import java.time.LocalDate;
import java.util.*;

import BusinessLayer.EmployeePackage.*;
import BusinessLayer.ShiftPackage.*;
import DTOPackage.*;
import Resources.Preference;
import Resources.Role;

import java.util.List;

import javax.naming.NoPermissionException;

public class Facade {
    private EmployeeController employeeController;
    private ShiftController shiftController;

    public Facade() {
        employeeController = new EmployeeController();
        shiftController = new ShiftController();


        //TODO: REMOVE THIS
        //TODO: REMOVE THIS
        //TODO: REMOVE THIS
        //TODO: REMOVE THIS
        //TODO: REMOVE THIS
        List roles = new ArrayList<Role>();
        roles.add(Role.StoreManager);
        String name = "Sponge Bob";
        String ID1 = "123456789";
        int bankId = 10;
        int branchId = 100;
        int accountNumber = 1000;
        float salary = 5000;
        LocalDate date = LocalDate.now();
        String trustFund = "Trust Fund";
        int freeDays = 10;
        int sickDays = 15;
        Preference[][] timeFrames = new Preference[7][2];
        for (int i = 0; i < 7; i++)
            for (int j = 0; j < 2; j++)
                timeFrames[i][j] = Preference.WANT;
        List roles1 = new ArrayList<Role>();
        roles1.add(Role.Cashier);
        employeeController.addEmployee(name, ID1, bankId, branchId, accountNumber, salary, date, trustFund, freeDays, sickDays, roles, timeFrames);
        employeeController.addEmployee(name, "987654321", bankId, branchId, accountNumber, salary, date, trustFund, freeDays, sickDays, roles1, timeFrames);

    }

    public boolean getIsManager() {
        return employeeController.isManager();
    }

    public ResponseT<EmployeeDTO> getEmployee(String ID) {
        ResponseT<EmployeeDTO> response;
        try {
            response = new ResponseT<EmployeeDTO>(toEmployeeDTO(employeeController.getEmployee(ID)));
        } catch (Exception e) {
            response = new ResponseT<EmployeeDTO>(e, new EmployeeDTO());
        }
        return response;
    }

    public Response setEmployee(EmployeeDTO employee) {
        Response response;
        Employee rollback = null;
        List<Role> rollbackSkills = null;
        LocalDate rollbackDate = null;
        Preference[][] rollbackTimeFrames = new Preference[7][2];
        try {
            rollbackSkills = new ArrayList<>(employeeController.getEmployee(employee.ID).getSkills());
            rollbackDate = employeeController.getEmployee(employee.ID).getStartDate();
            for (int i = 0; i < 7; i++)
                for (int j = 0; j < 2; j++)
                    rollbackTimeFrames[i][j] = employeeController.getEmployee(employee.ID).getTimeFrames()[i][j];
            rollback = employeeController.getEmployee(employee.ID);

            employeeController.updateEmployee(employee.name, employee.ID, employee.bankId, employee.branchId, employee.accountNumber, employee.salary,
                    employee.startDate, employee.trustFund, employee.freeDays, employee.sickDays, employee.skills, employee.timeFrames);
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
        toReturn.startDate = employee.getStartDate();
        toReturn.trustFund = employee.getTrustFund();
        toReturn.freeDays = employee.getFreeDays();
        toReturn.sickDays = employee.getSickDays();
        List<Role> newSkills = new ArrayList<>(employee.getSkills());
        toReturn.skills = newSkills;
        Preference[][] newTimeFrames = new Preference[7][2];
        for (int i = 0; i < 7; i++)
            for (int j = 0; j < 2; j++)
                newTimeFrames[i][j] = employee.getTimeFrames()[i][j];
        toReturn.timeFrames = newTimeFrames;
        return toReturn;
    }

    public ResponseT<ShiftDTO> getShift(LocalDate date, boolean isMorning) {
        return new ResponseT<ShiftDTO>(toShiftDTO(shiftController.getShift(date, isMorning)));
    }

    private ShiftDTO toShiftDTO(Shift shift) {
        return new ShiftDTO(shift.getDate(), shift.isMorning(), shift.getPositions());
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

    public Response logout() {
        Response response;
        try {
            employeeController.logout();
            response = new Response();
        } catch (Exception e) {
            response = new Response(e);
        }
        return response;
    }

    public Response AssignToShift(String id, Role skill) {
        try {
            if (!employeeController.isValidID(id))
                throw new IllegalArgumentException("invalid id.");
            if (!employeeController.isManager())
                throw new NoPermissionException("this act can be performed by managers only.");
            shiftController.AssignToShift(id, skill);
        } catch (Exception e) {
            return new Response(e);
        }
        return new Response();
    }

    public Response removeFromShift(String id) {
        try {
            if (!employeeController.isValidID(id))
                throw new IllegalArgumentException("invalid id.");
            if (!employeeController.isManager())
                throw new NoPermissionException("this act can be performed by managers only.");
            shiftController.removeFromShift(id);
        } catch (Exception e) {
            return new Response(e);
        }
        return new Response();
    }

    public Response definePersonnelForShift(int day, boolean isMorning, Role skill, int qtty) {
        try {
            if (!employeeController.isManager())
                throw new NoPermissionException("this act can be performed by managers only.");
            shiftController.definePersonnelForShift(day, isMorning, skill, qtty);
        } catch (Exception e) {
            return new Response(e);
        }
        return new Response();
    }

    public Response addShift(LocalDate date, boolean isMorning) {
        try {
            if (!employeeController.isManager())
                throw new NoPermissionException("this act can be performed by managers only.");
            shiftController.addShift(date, isMorning);
        } catch (Exception e) {
            return new Response(e);
        }
        return new Response();
    }

    public Response removeShift(LocalDate date, boolean isMorning) {
        try {
            if (!employeeController.isManager())
                throw new NoPermissionException("this act can be performed by managers only.");
            shiftController.removeShift(date, isMorning);
        } catch (Exception e) {
            return new Response(e);
        }
        return new Response();
    }

    public Response addEmployee(String name, String ID, int bankId, int branchId, int accountNumber, float salary,
                                LocalDate startDate, String trustFund, int freeDays, int sickDays, List<Role> skills, Preference[][] timeFrames) {
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

    public ResponseT<Map<ShiftDTO, Role>> getEmpShifts(String id) {
        Map<Shift, Role> shifts = shiftController.getEmpShifts(id);
        Map<ShiftDTO, Role> ret = new HashMap<>();
        for (Shift shift : shifts.keySet()) {
            ShiftDTO sDTO = toShiftDTO(shift);
            ret.put(sDTO, shifts.get(shift));
        }
        return new ResponseT<>(ret);
    }

}
