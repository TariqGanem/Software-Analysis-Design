package BusinessLayer.EmployeePackage;

import BusinessLayer.Role;

import java.util.Date;
import java.util.List;

public class Employee {
    private String name;
    private String ID;
    private Bank bank;
    private float salary;
    private Date startDate;
    private String trustFund;
    private int freeDays;
    private int sickDays;
    private List<Role> skills;
    private boolean isManager;
    private Preference[][] timeFrames = new Preference[6][2];

    public Employee(String name, String ID, int bankId, int branchId, int accountNumber, float salary, Date startDate, String trustFund, int freeDays, int sickDays, List<Role> skills) {
        this.name = name;
        this.ID = ID;
        this.bank = new Bank(bankId, branchId, accountNumber);
        this.salary = salary;
        this.startDate = startDate;
        this.trustFund = trustFund;
        this.freeDays = freeDays;
        this.sickDays = sickDays;
        this.skills = skills;
        isManager = skills.contains(Role.StoreManager) || skills.contains(Role.HRManager);
    }

    public String getID() {
        return ID;
    }

    public String getName() {
        return name;
    }

    public boolean getIsManager() {
        return isManager;
    }

    public boolean changeShiftPreference(int day, boolean isMorning, int preference) throws Exception {
        if (day > 5 || day < 0)
            throw new Exception("The day entered was out of bounds.");
        else if (day == 5 && !isMorning)
            throw new Exception("There is no shift at friday evening.");
        else if (preference < 0 || preference > 2)
            throw new Exception("The preference entered was out of bounds.");

        if (isMorning)
            timeFrames[day][0] = Preference.values()[preference];
        else
            timeFrames[day][1] = Preference.values()[preference];

        return true;
    }

    public Preference getPreference(int day, boolean isMorning) {
        if (isMorning)
            return timeFrames[day][0];
        else
            return timeFrames[day][1];
    }

    public boolean hasSkill(Role skill) {
        return skills.contains(skill);
    }

    private String describeRoles() {
        String desc = "";
        for (Role r : skills) {
            desc = r.name() + ", ";
        }
        return desc.substring(0, desc.length() - 2);
    }

    private String describeTimePreferences() {
        String desc = "";
        desc = "Sunday: \n" + "Morning: " + timeFrames[0][0].name() + "| Evening: " + timeFrames[0][1].name() + "\n" +
                "Monday: \n" + "Morning: " + timeFrames[1][0].name() + "| Evening: " + timeFrames[1][1].name() + "\n" +
                "Tuesday: \n" + "Morning: " + timeFrames[2][0].name() + "| Evening: " + timeFrames[2][1].name() + "\n" +
                "Wednesday: \n" + "Morning: " + timeFrames[3][0].name() + "| Evening: " + timeFrames[3][1].name() + "\n" +
                "Thursday: \n" + "Morning: " + timeFrames[4][0].name() + "| Evening: " + timeFrames[4][1].name() + "\n" +
                "Friday: \n" + "Morning: " + timeFrames[5][0].name();
        return desc;
    }

    public String viewProfile() {
        String profile = "";
        profile = "Name: " + name + "\n" +
                "ID: " + ID + "\n" +
                "Bank information:\n" + bank.description() +
                "Salary: " + salary + "\n" +
                "Start date: " + startDate.toString() + "\n" +
                "Trust fund: " + trustFund + "\n" +
                "Free days: " + freeDays + "\n" +
                "Sick days: " + sickDays + "\n" +
                "Roles: " + describeRoles() + "\n" +
                "Time preferences: \n" + describeRoles() + "\n";
        return profile;
    }
}
