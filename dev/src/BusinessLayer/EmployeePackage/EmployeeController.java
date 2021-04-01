package BusinessLayer.EmployeePackage;

import Resources.Preference;
import Resources.Role;

import javax.naming.NoPermissionException;
import java.time.LocalDate;
import java.util.*;

public class EmployeeController {
    private Map<String, Employee> employees;
    private Employee activeEmployee;

    public EmployeeController() {
        employees = new HashMap<>();
        activeEmployee = null;
    }

    public Employee getEmployee(String ID) throws NoPermissionException {
        if (!activeEmployee.getIsManager())
            throw new NoPermissionException("The employee currently using the system doesn't have permission to view this content.");
        if (!isValidID(ID))
            throw new IllegalArgumentException("No employee with the ID: " + ID + " was found in the system.");
        return employees.get(ID);
    }

    public void login(String ID) {
        for (Employee e : employees.values()) {
            if (e.getID().equals(ID)) {
                activeEmployee = e;
                return;
            }
        }
        throw new IllegalArgumentException("No employee with the ID: " + ID + " was found in the system.");
    }

    public void updateEmployee(String name, String ID, int bankId, int branchId, int accountNumber, float salary, LocalDate startDate,
                               String trustFund, int freeDays, int sickDays, List<Role> skills, Preference[][] timeFrames) throws Exception {
        Employee toUpdate = getEmployee(ID);
        if(!toUpdate.getID().equals(ID))
            throw new IllegalArgumentException("You are not allowed to change the ID of an employee.");
        toUpdate.setName(name);
        toUpdate.setBankId(bankId);
        toUpdate.setBranchId(branchId);
        toUpdate.setAccountNumber(accountNumber);
        toUpdate.setSalary(salary);
        toUpdate.setStartDate(startDate);
        toUpdate.setTrustFund(trustFund);
        toUpdate.setFreeDays(freeDays);
        toUpdate.setSickDays(sickDays);
        toUpdate.setSkills(skills);
        toUpdate.setTimeFrames(timeFrames);
    }

    public void addEmployee(String name, String ID, int bankId, int branchId, int accountNumber,
                            float salary, LocalDate startDate, String trustFund, int freeDays, int sickDays, List<Role> skills, Preference[][] timeFrames) {
        for (String s : employees.keySet()) {
            if (s.equals(ID)) {
                throw new IllegalArgumentException("There is already an employee with the ID: " + ID + " in the system.");
            }
        }

        employees.put(ID, new Employee(name, ID, bankId, branchId, accountNumber, salary, startDate, trustFund, freeDays, sickDays, skills, timeFrames));
    }

    /*public boolean changeShiftPreference(int day, boolean isMorning, int preference) {
        return activeEmployee.changeShiftPreference(day, isMorning, preference);
    }*/

    /*public String getSkills(String ID) throws NoPermissionException {
        if (activeEmployee.getID().equals(ID))
            return activeEmployee.describeRoles();
        else if (!activeEmployee.getIsManager())
            throw new NoPermissionException("The employee currently using the system doesn't have permission to view this content.");
        else
            return employees.get(ID).describeRoles();
    }*/

    /*public String viewProfile(String ID) throws NoPermissionException {
        if (activeEmployee.getID().equals(ID))
            return activeEmployee.viewProfile();
        else if (!activeEmployee.getIsManager())
            throw new NoPermissionException("The employee currently using the system doesn't have permission to view this content.");
        else
            return employees.get(ID).viewProfile();
    }*/

    public boolean isValidID(String ID) {
        return employees.keySet().contains(ID);
    }

    public boolean isManager() {
        return activeEmployee.getIsManager();
    }

    public List<String> viewAvailableEmployees(int day, boolean isMorning, Role skill) {
        List<String> toReturn = new ArrayList<>();
        for (Employee e : employees.values()) {
            if (e.hasSkill(skill)) {
                Preference p = e.getPreference(day, isMorning);
                if (p.equals(Preference.WANT))
                    toReturn.add(e.getName() + " (" + e.getID() + ") WANTS to work at the specified date.");
                else if (p.equals(Preference.CAN))
                    toReturn.add(e.getName() + " (" + e.getID() + ") CAN work at the specified date.");
            }
        }
        return toReturn;
    }
}
