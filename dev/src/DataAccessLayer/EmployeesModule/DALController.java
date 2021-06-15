package DataAccessLayer.EmployeesModule;

import BusinessLayer.EmployeesModule.EmployeePackage.Employee;
import BusinessLayer.EmployeesModule.ShiftPackage.Shift;
import BusinessLayer.Response;
import BusinessLayer.ResponseT;
import DataAccessLayer.dbMaker;
import Resources.Role;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;

public class DALController {
    private EmployeeMapper employeeMapper;
    private ShiftMapper shiftMapper;
    private ShiftPersonnelMapper shiftPersonnelMapper;
    private HRAlertsHandler hrAlertsHandler;

    public DALController() {
        employeeMapper = EmployeeMapper.getInstance(dbMaker.path);
        shiftMapper = ShiftMapper.getInstance(dbMaker.path);
        shiftPersonnelMapper = ShiftPersonnelMapper.getInstance(dbMaker.path);
        hrAlertsHandler = HRAlertsHandler.getInstance(dbMaker.path);
    }

    public ResponseT<Employee> getEmployee(String ID) {
        return employeeMapper.getEmployee(ID);
    }

    public Response setEmployee(Employee emp) {
        return employeeMapper.setEmployee(emp);
    }

    public Response updateEmployee(Employee emp, String oldID) {
        return employeeMapper.updateEmployee(emp, oldID);
    }

    public ResponseT<Map<Role, Integer>[]> getShiftPersonnel() {
        return shiftPersonnelMapper.getShiftPersonnel();
    }

    public Response updateShiftPersonnel(int dayIndex, Role role, int qtty) {
        return shiftPersonnelMapper.updateShiftPersonnel(dayIndex, role, qtty);
    }

    public Response setShiftPersonnel(int dayIndex, Role role, int qtty) {
        return shiftPersonnelMapper.setShiftPersonnel(dayIndex, role, qtty);
    }

    public ResponseT<Shift> getShift(LocalDate date, boolean isMorning) {
        return shiftMapper.getShift(date, isMorning);
    }

    public ResponseT<List<Shift>> getShifts(int daysFromToday) {
        return shiftMapper.getShifts(daysFromToday);
    }

    public Response setShift(Shift shift) {
        return shiftMapper.setShift(shift);
    }

    public Response insertToShift(Shift shift, Role role, String ID) {
        return shiftMapper.insertToShift(shift, role, ID);
    }

    public Response removeFromShift(Shift shift, String ID) {
        return shiftMapper.removeFromShift(shift, ID);
    }

    public Response deleteShift(LocalDate date, boolean isMorning) {
        return shiftMapper.deleteShift(date, isMorning);
    }

    public ResponseT<Map<Shift, Role>> getEmpShifts(String id) {
        return shiftMapper.getEmpShifts(id);
    }

    public ResponseT<List<String>> getEmployeeIDs() {
        return employeeMapper.getEmployeeIDs();
    }

    public ResponseT<List<Employee>> getAvailableEmployees(int day, boolean isMorning, Role skill, boolean getAvailable) {
        return employeeMapper.getAvailableEmployees(day, isMorning, skill, getAvailable);
    }

    public ResponseT<List<String>> getAvailableDrivers(LocalDate date, boolean isMorning) {
        return shiftMapper.getAvailableDrivers(date, isMorning);
    }

    public ResponseT<List<String>> checkForAlerts() {
        return hrAlertsHandler.getAlerts();
    }

    public Response alertHRManager(String msg) {
        return hrAlertsHandler.addAlert(msg);
    }
}
