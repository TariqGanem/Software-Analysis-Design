package BusinessLayer.EmployeePackage;

import Resources.Preference;
import Resources.Role;

import java.util.*;

public class EmployeeController {
    private Map<String, Employee> employees;
    private Employee activeEmployee;

    public EmployeeController() {
        employees = new HashMap<>();
        activeEmployee = null;
    }

    public boolean login(String ID) throws Exception {
        for (Employee e : employees.values()) {
            if (e.getID().equals(ID)) {
                activeEmployee = e;
                return true;
            }
        }
        throw new Exception("No employee with the ID: " + ID + " was found in the system.");
    }

    public boolean addEmployee(String name, String ID, int bankId, int branchId, int accountNumber,
                               float salary, Date startDate, String trustFund, int freeDays, int sickDays, List<Role> skills) throws Exception {
        for (String s : employees.keySet()) {
            if (s.equals(ID)) {
                throw new Exception("There is already an employee with the ID: " + ID + " in the system.");
            }
        }

        employees.put(ID, new Employee(name, ID, bankId, branchId, accountNumber, salary, startDate, trustFund, freeDays, sickDays, skills));
        return true;
    }

    public boolean changeShiftPreference(int day, boolean isMorning, int preference) throws Exception {
        return activeEmployee.changeShiftPreference(day, isMorning, preference);
    }

    public String getSkills(String ID) throws Exception {
        if (activeEmployee.getID().equals(ID))
            return activeEmployee.describeRoles();
        else if (!activeEmployee.getIsManager())
            throw new Exception("The employee currently using the system doesn't have permission to view this content.");
        else
            return employees.get(ID).describeRoles();
    }

    public String viewProfile(String ID) throws Exception {
        if (activeEmployee.getID().equals(ID))
            return activeEmployee.viewProfile();
        else if (!activeEmployee.getIsManager())
            throw new Exception("The employee currently using the system doesn't have permission to view this content.");
        else
            return employees.get(ID).viewProfile();
    }

    public boolean isValidID(String ID) {
        return employees.keySet().contains(ID);
    }

    public boolean isManager() { return activeEmployee.getIsManager(); }

    public List<String> viewAvailableEmployees(int day, boolean isMorning, Role skill) {
        List<String> toReturn = new ArrayList<>();
        for (Employee e : employees.values()) {
            if (e.hasSkill(skill)) {
                Preference p = e.getPreference(day, isMorning);
                if (p.equals(Preference.WANT))
                    toReturn.add(e.getName() + " (" + e.getID() + ") WANTS to work at the specified date.");
                else if(p.equals(Preference.CAN))
                    toReturn.add(e.getName() + " (" + e.getID() + ") CAN work at the specified date.");
            }
        }
        return toReturn;
    }
}
