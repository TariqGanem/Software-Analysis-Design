package DataAccessLayer;

import BusinessLayer.EmployeePackage.Employee;
import BusinessLayer.Response;
import BusinessLayer.ResponseT;
import BusinessLayer.ShiftPackage.Shift;

import java.sql.Connection;
import java.sql.DriverManager;
import java.time.LocalDate;

public class DALController {
    private DALController instance = null;
    private EmployeeMapper employeeMapper;
    private ShiftMapper shiftMapper;
    private ShiftPersonnelMapper shiftPersonnelMapper;

    private DALController() {
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
}
