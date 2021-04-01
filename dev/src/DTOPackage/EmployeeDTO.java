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

    public String describeRoles() {
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

    public String bankDescription(){
        return "Bank id: " + bankId + "\nBranch id: " + branchId + "\nAccount number: " + accountNumber + "\n";
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
