package BusinessLayer.EmployeesModule.EmployeePackage;

import BusinessLayer.ResponseT;
import DataAccessLayer.EmployeesModule.DALController;
import Resources.Preference;
import Resources.Role;

import javax.naming.NoPermissionException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class EmployeeController {
    private Map<String, Employee> employees;
    private Employee activeEmployee;
    private DALController dalController;
    private List<String> IDs;

    public EmployeeController(DALController dalController) {
        employees = new HashMap<>();
        activeEmployee = null;
        IDs = new ArrayList<>();
        this.dalController = dalController;

        ResponseT<Employee> res = dalController.getEmployee("admin");
        if (!res.getErrorOccurred())
            employees.put("admin", res.getValue());
        else {
            List roles = new ArrayList<Role>();
            roles.add(Role.StoreManager);
            roles.add(Role.ShipmentsManager);
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
    }

    public void logout() {
        activeEmployee = null;
    }

    public Employee getEmployee(String ID) throws Exception {
        if (activeEmployee != null && !activeEmployee.getIsManager() && !activeEmployee.getID().equals(ID))
            throw new NoPermissionException("The employee currently using the system doesn't have permission to view this content.");
        if (!doesIDExist(ID)) {
            ResponseT<Employee> empResponse = dalController.getEmployee(ID);
            if (empResponse.getErrorOccurred())
                throw new IllegalArgumentException("No employee with the ID: " + ID + " was found in the system.");
            employees.put(ID, empResponse.getValue());
        }

        return employees.get(ID);
    }

    public String getName(String ID) {
        try {
            return getEmployee(ID).getName();
        } catch (Exception ignored) {
            return null;
        }
    }

    public void login(String ID) throws Exception {
        for (Employee e : employees.values()) {
            if (e.getID().equals(ID)) {
                activeEmployee = e;
                return;
            }
        }
        activeEmployee = getEmployee(ID);
    }

    public void updateEmployee(String oldID, String name, String newID, int bankId, int branchId, int accountNumber, float salary, LocalDate startDate,
                               String trustFund, int freeDays, int sickDays, List<Role> skills, Preference[][] timeFrames) throws Exception {
        Employee toUpdate = getEmployee(oldID);
        toUpdate.setName(name);
        if (!oldID.equals(newID))
            for (String empID : employees.keySet())
                if (empID.equals(newID))
                    throw new IllegalArgumentException("There is already a user with the ID " + newID + " in the system.");
        toUpdate.setID(newID);
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
        if (!oldID.equals(newID)) {
            employees.remove(oldID);
            employees.put(newID, toUpdate);
        }

        dalController.updateEmployee(employees.get(newID), oldID);
    }

    public void addEmployee(String name, String ID, int bankId, int branchId, int accountNumber,
                            float salary, LocalDate startDate, String trustFund, int freeDays, int sickDays, List<Role> skills, Preference[][] timeFrames) {
        for (String s : employees.keySet()) {
            if (s.equals(ID)) {
                throw new IllegalArgumentException("There is already an employee with the ID: " + ID + " in the system.");
            }
        }

        employees.put(ID, new Employee(name, ID, bankId, branchId, accountNumber, salary, startDate, trustFund, freeDays, sickDays, skills, timeFrames));

        dalController.setEmployee(employees.get(ID));
    }

    public boolean doesIDExist(String ID) {
        return employees.containsKey(ID);
    }

    public boolean isValidID(String ID) {
        if (IDs.isEmpty()) {
            ResponseT<List<String>> res = dalController.getEmployeeIDs();
            if (res.getErrorOccurred())
                return false;
            IDs = res.getValue();
        }
        return IDs.contains(ID);
    }

    public boolean isManager() {
        return activeEmployee != null && activeEmployee.getIsManager();
    }

    public Map<String, String> viewAvailableEmployees(int day, boolean isMorning, Role skill) {
        Map<String, String> ret = new HashMap<>();
        ResponseT<List<Employee>> employeesFromDB = dalController.getAvailableEmployees(day, isMorning, skill, true);
        if (!employeesFromDB.getErrorOccurred()) {
            for (Employee e : employeesFromDB.getValue()) {
                if (!employees.containsKey(e.getID()))
                    employees.put(e.getID(), e);
            }
        }

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
        ResponseT<List<Employee>> employeesFromDB = dalController.getAvailableEmployees(day, isMorning, skill, false);
        if (!employeesFromDB.getErrorOccurred()) {
            for (Employee e : employeesFromDB.getValue()) {
                if (!employees.containsKey(e.getID()))
                    employees.put(e.getID(), e);
            }
        }

        for (Employee e : employees.values()) {
            if (e.hasSkill(skill)) {
                Preference p = e.getPreference(day, isMorning);
                if (p.equals(Preference.CANT))
                    ret.put(e.getID(), e.getName() + " CANT work at the specified date.");
            }
        }
        return ret;
    }

    public boolean API_isShipmentManager(String ID) {
        try {
            Employee emp = getEmployee(ID);
            return emp.getSkills().contains(Role.ShipmentsManager);
        } catch (Exception e) {
            return false;
        }
    }

    public void API_alertHRManager(String msg) {
        dalController.alertHRManager(msg);
    }

    public ResponseT<List<String>> checkForAlerts() {
        if (!activeEmployee.hasSkill(Role.HRManager))
            return new ResponseT<>(new ArrayList<>());
        return dalController.checkForAlerts();
    }

    public boolean hasRole(String ID, Role role) {
        try {
            return getEmployee(ID).hasSkill(role);
        } catch (Exception ignored) {
            return false;
        }
    }
}
