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

    public void logout() {
        activeEmployee = null;
    }

    public Employee getEmployee(String ID) throws Exception {
        if (!activeEmployee.getIsManager() && !activeEmployee.getID().equals(ID))
            throw new NoPermissionException("The employee currently using the system doesn't have permission to view this content.");
        if (!isValidID(ID))
            throw new IllegalArgumentException("No employee with the ID: " + ID + " was found in the system.");
        return employees.get(ID);
    }

    public String getName(String ID) throws IllegalArgumentException {
        if (!isValidID(ID))
            throw new IllegalArgumentException("No employee with the ID: " + ID + " was found in the system.");
        return employees.get(ID).getName();
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
        toUpdate.setName(name);
        toUpdate.setID(ID);
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

    public boolean isValidID(String ID) {
        return employees.containsKey(ID);
    }

    public boolean isManager() {
        return activeEmployee != null && activeEmployee.getIsManager();
    }

    public Map<String, String> viewAvailableEmployees(int day, boolean isMorning, Role skill) {
        Map<String, String> ret = new HashMap<>();
        for (Employee e : employees.values()) {
            if (e.hasSkill(skill)) {
                Preference p = e.getPreference(day, isMorning);
                if (p.equals(Preference.WANT))
                    ret.put(e.getID(), e.getName() + " WANTS to work at the specified date.");
                else if (p.equals(Preference.CAN))
                    ret.put(e.getID(), e.getName() + " CAN to work at the specified date.");
            }
        }
        return ret;
    }

    public Map<String, String> viewUnavailableEmployees(int day, boolean isMorning, Role skill) {
        Map<String, String> ret = new HashMap<>();
        for (Employee e : employees.values()) {
            if (e.hasSkill(skill)) {
                Preference p = e.getPreference(day, isMorning);
                if (p.equals(Preference.CANT))
                    ret.put(e.getID(), e.getName() + " CANT work at the specified date.");
            }
        }
        return ret;
    }
}
