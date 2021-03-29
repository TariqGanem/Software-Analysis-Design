package BusinessLayer.EmployeePackage;

import java.util.Date;

enum preferences {
    WANT,
    CAN,
    CANT
}

public class Employee {
    private String name;
    private String ID;
    private Bank bank;
    private float salary;
    private Date startDate;
    private String trustFund;
    private int freeDays;
    private int sickDays;
    private String skills;
    private boolean isManager;
    private preferences[][] timeFrames = new preferences[6][2];

    public Employee(String name, String ID, int bankId, int branchId, int accountNumber, float salary, Date startDate, String trustFund, int freeDays, int sickDays, String skills) {
        this.name = name;
        this.ID = ID;
        this.bank = new Bank(bankId, branchId, accountNumber);
        this.salary = salary;
        this.startDate = startDate;
        this.trustFund = trustFund;
        this.freeDays = freeDays;
        this.sickDays = sickDays;
        this.skills = skills;
        isManager = skills.contains("HRManager") || skills.contains("StoreManager");
    }

    public String getID() {
        return ID;
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
            timeFrames[day][0] = preferences.values()[preference];
        else
            timeFrames[day][1] = preferences.values()[preference];

        return true;
    }

    public preferences getPreference(int day, boolean isMorning) {
        if (isMorning)
            return timeFrames[day][0];
        else
            return timeFrames[day][1];
    }

    public boolean hasSkill(String skill) {
        return skills.contains(skill);
    }
}
