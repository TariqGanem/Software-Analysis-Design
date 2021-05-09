package DTOPackage;

import Resources.Preference;
import Resources.Role;

import java.time.LocalDate;
import java.util.List;

public class EmployeeDTO {
    public String name;
    public String ID;
    public int bankId;
    public int branchId;
    public int accountNumber;
    public float salary;
    public LocalDate startDate;
    public String trustFund;
    public int freeDays;
    public int sickDays;
    public List<Role> skills;
    public Preference[][] timeFrames = new Preference[7][2];

    public String describeRoles() {
        StringBuilder desc = new StringBuilder();
        for (Role r : skills) {
            desc.append(r.name()).append(", ");
        }
        return desc.substring(0, desc.length() - 2);
    }

    private String describeTimePreferences() {
        String desc = "";
        desc = "Sunday: \n" + "\tMorning: " + timeFrames[0][0].name() + " | Evening: " + timeFrames[0][1].name() + "\n" +
                "Monday: \n" + "\tMorning: " + timeFrames[1][0].name() + " | Evening: " + timeFrames[1][1].name() + "\n" +
                "Tuesday: \n" + "\tMorning: " + timeFrames[2][0].name() + " | Evening: " + timeFrames[2][1].name() + "\n" +
                "Wednesday: \n" + "\tMorning: " + timeFrames[3][0].name() + " | Evening: " + timeFrames[3][1].name() + "\n" +
                "Thursday: \n" + "\tMorning: " + timeFrames[4][0].name() + " | Evening: " + timeFrames[4][1].name() + "\n" +
                "Friday: \n" + "\tMorning: " + timeFrames[5][0].name();
        return desc;
    }

    public String bankDescription() {
        return "\tBank id: " + bankId + "\n\tBranch id: " + branchId + "\n\tAccount number: " + accountNumber + "\n";
    }

    public String viewProfile() {
        String profile = "";
        profile = "Name: " + name + "\n" +
                "ID: " + ID + "\n" +
                "Bank information:\n" + bankDescription() +
                "Salary: " + salary + "\n" +
                "Start date: " + startDate.toString() + "\n" +
                "Trust fund: " + trustFund + "\n" +
                "Free days: " + freeDays + "\n" +
                "Sick days: " + sickDays + "\n" +
                "Roles: " + describeRoles() + "\n" +
                "Time preferences: \n" + describeTimePreferences() + "\n";
        return profile;
    }
}
