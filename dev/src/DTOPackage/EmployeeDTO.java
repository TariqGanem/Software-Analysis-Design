package DTOPackage;

import Resources.Preference;
import Resources.Role;

import java.util.Date;
import java.util.List;

public class EmployeeDTO {
    public String name;
    public String ID;
    public int bankId;
    public int branchId;
    public int accountNumber;
    public float salary;
    public Date startDate;
    public String trustFund;
    public int freeDays;
    public int sickDays;
    public List<Role> skills;
    public Preference[][] timeFrames = new Preference[6][2];
}
