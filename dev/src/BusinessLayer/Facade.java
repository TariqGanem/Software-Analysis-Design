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

        //Add the admin employee
        List roles = new ArrayList<Role>();
        roles.add(Role.StoreManager);
        String name = "admin";
        String ID1 = "admin";
        int bankId = 0;
        int branchId = 0;
        int accountNumber = 0;
        float salary = 0;
        LocalDate date = LocalDate.of(2000, 1, 1);
        String trustFund = "admin";
        int freeDays = 0;
        int sickDays = 0;
        Preference[][] timeFrames = new Preference[7][2];
        for (int i = 0; i < 7; i++)
            for (int j = 0; j < 2; j++)
                timeFrames[i][j] = Preference.CANT;
        addEmployee(name, ID1, bankId, branchId, accountNumber, salary, date, trustFund, freeDays, sickDays, roles, timeFrames);
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

    public ResponseT<List<ShiftDTO>> getShifts(int daysFromToday){
        List<ShiftDTO> shiftDTOs = new ArrayList<>();
        for (Shift shift : shiftController.getShifts()) {
            System.out.println(shift.getDate().toString());
            if(shift.getDate().isAfter(LocalDate.now().plusDays(daysFromToday)) || shift.getDate().isBefore(LocalDate.now()))
                continue;
            shiftDTOs.add(toShiftDTO(shift));
        }
        ResponseT<List<ShiftDTO>> res;
        if(shiftDTOs.isEmpty())
            return new ResponseT<>(new NullPointerException("no shift in the next " + daysFromToday + " days."));
        return new ResponseT<>(shiftDTOs);
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
            // add exception when the employee does not have the skill
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

    public Map<Role, Integer> getPersonnelForShift(int day, boolean isMorning){
        return shiftController.getPersonnelForShift(day, isMorning);
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
            if(!shiftController.removeShift(date, isMorning))
                throw new IllegalArgumentException("this shift does not exists.");
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

    public ResponseT<Map<String, String>> viewAvailableEmployees(LocalDate date, boolean isMorning, Role skill) {
        ResponseT<Map<String, String>> response;
        try {
            Map<String, String> employees = employeeController.viewAvailableEmployees((date.getDayOfWeek().getValue() + 1) % 7, isMorning, skill);
            response = new ResponseT<Map<String, String>>(employees);
        } catch (Exception e) {
            response = new ResponseT<Map<String, String>>(e);
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

    public Response checkLegalDate(int year, int month, int day) {
        Response response;
        try {
            LocalDate date = LocalDate.of(year, month, day);
            response = new Response();
        } catch (Exception ignored) {
            response = new Response(new Exception("You entered an illegal date"));
        }
        return response;
    }
}
