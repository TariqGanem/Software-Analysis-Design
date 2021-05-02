package DataAccessLayer.EmployeeModule;

import BusinessLayer.EmployeeModule.EmployeePackage.Employee;
import BusinessLayer.EmployeeModule.Response;
import BusinessLayer.EmployeeModule.ResponseT;
import BusinessLayer.EmployeeModule.ShiftPackage.Shift;
import Resources.Role;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.List;
import java.util.Map;

public class DALController {
    private DALController instance = null;
    private EmployeeMapper employeeMapper;
    private ShiftMapper shiftMapper;
    private ShiftPersonnelMapper shiftPersonnelMapper;

    public DALController() {
        String dbFile = "SuperLi.db";
        String url = "jdbc:sqlite:" + dbFile;
        Connection con = null;
        try {
            con = DriverManager.getConnection(url);
        } catch (Exception ignored) {
        }

        employeeMapper = EmployeeMapper.getInstance(con);
        shiftMapper = ShiftMapper.getInstance(con);
        shiftPersonnelMapper = ShiftPersonnelMapper.getInstance(con);
    }

    public ResponseT<Employee> getEmployee(String ID) {
        return employeeMapper.getEmployee(ID);
    }

    public Response setEmployee(Employee emp) {
        return employeeMapper.setEmployee(emp);
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


    public Response insertToShift(Shift shift, Role role, String ID){
        return shiftMapper.insertToShift(shift,role,ID);
    }

    public Response removeFromShift(Shift shift, String ID){
        return shiftMapper.removeFromShift(shift, ID);
    }

    public void deleteShift(LocalDate date, boolean isMorning) {
        shiftMapper.deleteShift(date, isMorning);
    }

    public ResponseT<Map<Shift, Role>> getEmpShifts(String id) {
        return shiftMapper.getEmpShifts(id);
    }
}
