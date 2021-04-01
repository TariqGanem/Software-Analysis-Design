package BusinessLayer.EmployeePackage;

import Resources.Preference;
import Resources.Role;

import java.util.Calendar;
import java.util.Date;
import java.util.List;

public class Employee {
    private String name;
    private String ID;
    private int bankId;
    private int branchId;
    private int accountNumber;
    private float salary;
    private Date startDate;
    private String trustFund;
    private int freeDays;
    private int sickDays;
    private List<Role> skills;
    private Preference[][] timeFrames = new Preference[6][2];

    public Employee(String name, String ID, int bankId, int branchId, int accountNumber, float salary, Date startDate,
                    String trustFund, int freeDays, int sickDays, List<Role> skills, Preference[][] timeFrames) {
        setName(name);
        this.ID = ID;
        setBankId(bankId);
        setBranchId(branchId);
        setAccountNumber(accountNumber);
        setSalary(salary);
        setStartDate(startDate);
        setTrustFund(trustFund);
        setFreeDays(freeDays);
        setSickDays(sickDays);
        setSkills(skills);
        setTimeFrames(timeFrames);
    }

    /*public boolean changeShiftPreference(int day, boolean isMorning, int preference) {
        if (day > 5 || day < 0)
            throw new IndexOutOfBoundsException("The day entered was out of bounds.");
        else if (day == 5 && !isMorning)
            throw new IndexOutOfBoundsException("There is no shift at friday evening.");
        else if (preference < 0 || preference > 2)
            throw new IndexOutOfBoundsException("The preference entered was out of bounds.");

        if (isMorning)
            timeFrames[day][0] = Preference.values()[preference];
        else
            timeFrames[day][1] = Preference.values()[preference];

        return true;
    }*/

    public Preference getPreference(int day, boolean isMorning) {
        if (isMorning)
            return timeFrames[day][0];
        else
            return timeFrames[day][1];
    }

    public boolean hasSkill(Role skill) {
        return skills.contains(skill);
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setBankId(int bankId) {
        if(bankId < 0) throw new IllegalArgumentException("The bankId can't be lower than 0.");
        this.bankId = bankId;
    }

    public void setBranchId(int branchId) {
        if(branchId < 0) throw new IllegalArgumentException("The branchId can't be lower than 0.");
        this.branchId = branchId;
    }

    public void setAccountNumber(int accountNumber) {
        if(accountNumber < 0) throw new IllegalArgumentException("The accountNumber can't be lower than 0.");
        this.accountNumber = accountNumber;
    }

    public void setSalary(float salary) {
        if(salary < 0) throw new IllegalArgumentException("The salary can't be lower than 0.");
        this.salary = salary;
    }

    public void setStartDate(Date startDate) {
        if(startDate.after(Calendar.getInstance().getTime())) throw new IllegalArgumentException("The startDate can't be after today.");
        this.startDate = startDate;
    }

    public void setTrustFund(String trustFund) {
        this.trustFund = trustFund;
    }

    public void setFreeDays(int freeDays) {
        if(freeDays < 0) throw new IllegalArgumentException("The freeDays can't be lower than 0.");
        this.freeDays = freeDays;
    }

    public void setSickDays(int sickDays) {
        if(sickDays < 0) throw new IllegalArgumentException("The sickDays can't be lower than 0.");
        this.sickDays = sickDays;
    }

    public void setSkills(List<Role> skills) {
        this.skills = skills;
    }

    public void setTimeFrames(Preference[][] timeFrames) {
        if(timeFrames.length != 6 | timeFrames[0].length != 2) throw new IllegalArgumentException("Time preferences must correlate to the shifts in a week.");
        this.timeFrames = timeFrames;
    }

    public String getID() {
        return ID;
    }

    public String getName() {
        return name;
    }

    public boolean getIsManager() {
        return skills.contains(Role.StoreManager) || skills.contains(Role.HRManager);
    }

    public int getBankId() {
        return bankId;
    }

    public int getBranchId() {
        return branchId;
    }

    public int getAccountNumber() { return accountNumber; }

    public float getSalary() { return salary; }

    public Date getStartDate() { return startDate; }

    public String getTrustFund() { return trustFund; }

    public int getFreeDays() { return freeDays; }

    public int getSickDays() { return sickDays; }

    public List<Role> getSkills() { return skills; }

    public Preference[][] getTimeFrames() { return timeFrames; }
}
