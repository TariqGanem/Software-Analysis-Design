package DTOPackage;

import BusinessLayer.EmployeePackage.Bank;

import Resources.Preference;
import Resources.Role;

import java.util.Date;
import java.util.List;

public class EmployeeDTO {
    public String name;
    public String ID;
    public Bank bank;
    public float salary;
    public Date startDate;
    public String trustFund;
    public int freeDays;
    public int sickDays;
    public List<Role> skills;
    public Preference[][] timeFrames = new Preference[6][2];
}
