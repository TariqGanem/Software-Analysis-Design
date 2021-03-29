package BusinessLayer.EmployeePackage;

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
                               float salary, Date startDate, String trustFund, int freeDays, int sickDays, String skills) throws Exception {
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

    public Employee viewProfile(String ID) throws Exception {
        if (activeEmployee.getID().equals(ID))
            return activeEmployee;
        else if (!activeEmployee.getIsManager())
            throw new Exception("The employee currently using the system doesn't have permission to view this content.");
        else
            return employees.get(ID);
    }

    public boolean isValidID(String ID) {
        return employees.keySet().contains(ID);
    }

    public List<Employee> viewAvailableEmployees(int day, boolean isMorning, String skill) {
        List<Employee> toReturn = new ArrayList<>();
        for (Employee e : employees.values()) {
            if (e.hasSkill(skill)) {
                preferences p = e.getPreference(day, isMorning);
                if (p.equals(preferences.WANT) || p.equals(preferences.CAN))
                    toReturn.add(e);
            }
        }
        return toReturn;
    }
}
