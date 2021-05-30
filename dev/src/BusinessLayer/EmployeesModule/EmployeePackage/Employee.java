package BusinessLayer.EmployeesModule.EmployeePackage;

import Resources.Preference;
import Resources.Role;

import java.time.LocalDate;
import java.util.List;

public class Employee {
    private String name;
    private String ID;
    private int bankId;
    private int branchId;
    private int accountNumber;
    private float salary;
    private LocalDate startDate;
    private String trustFund;
    private int freeDays;
    private int sickDays;
    private List<Role> skills;
    private Preference[][] timeFrames = new Preference[7][2];

    public Employee(String name, String ID, int bankId, int branchId, int accountNumber, float salary, LocalDate startDate,
                    String trustFund, int freeDays, int sickDays, List<Role> skills, Preference[][] timeFrames) {
        setName(name);
        setID(ID);
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

    public void setID(String ID) {
        if (!ID.matches("[0-9]+") && !ID.equals("admin"))
            throw new IllegalArgumentException("Only use numbers for your ID.");
        this.ID = ID;
    }

    public void setBankId(int bankId) {
        if (bankId < 0) throw new IllegalArgumentException("The bankId can't be lower than 0.");
        this.bankId = bankId;
    }

    public void setBranchId(int branchId) {
        if (branchId < 0) throw new IllegalArgumentException("The branchId can't be lower than 0.");
        this.branchId = branchId;
    }

    public void setAccountNumber(int accountNumber) {
        if (accountNumber < 0) throw new IllegalArgumentException("The accountNumber can't be lower than 0.");
        this.accountNumber = accountNumber;
    }

    public void setSalary(float salary) {
        if (salary < 0) throw new IllegalArgumentException("The salary can't be lower than 0.");
        this.salary = salary;
    }

    public void setStartDate(LocalDate startDate) {
        if (startDate.isAfter(LocalDate.now()))
            throw new IllegalArgumentException("The startDate can't be after today.");
        this.startDate = startDate;
    }

    public void setTrustFund(String trustFund) {
        this.trustFund = trustFund;
    }

    public void setFreeDays(int freeDays) {
        if (freeDays < 0) throw new IllegalArgumentException("The freeDays can't be lower than 0.");
        this.freeDays = freeDays;
    }

    public void setSickDays(int sickDays) {
        if (sickDays < 0) throw new IllegalArgumentException("The sickDays can't be lower than 0.");
        this.sickDays = sickDays;
    }

    public void setSkills(List<Role> skills) {
        if (skills.size() == 0)
            throw new IllegalArgumentException("The role list needs to contain at least one role!");
        int i = 0, j = 0;
        for (Role r1 : skills) {
            j = 0;
            for (Role r2 : skills) {
                if (i != j && r1.name().equals(r2.name()))
                    throw new IllegalArgumentException("Can't add two of the same role to an employee.");
                j++;
            }
            i++;
        }
        this.skills = skills;
    }

    public void setTimeFrames(Preference[][] timeFrames) {
        if (timeFrames.length != 7 | timeFrames[0].length != 2)
            throw new IllegalArgumentException("Time preferences must correlate to the shifts in a week.");
        this.timeFrames = timeFrames;

        if (timeFrames[5][1] == null)
            timeFrames[5][1] = Preference.CANT;
        if (timeFrames[6][0] == null)
            timeFrames[6][0] = Preference.CANT;
        if (timeFrames[6][1] == null)
            timeFrames[6][1] = Preference.CANT;
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

    public int getAccountNumber() {
        return accountNumber;
    }

    public float getSalary() {
        return salary;
    }

    public LocalDate getStartDate() {
        return startDate;
    }

    public String getTrustFund() {
        return trustFund;
    }

    public int getFreeDays() {
        return freeDays;
    }

    public int getSickDays() {
        return sickDays;
    }

    public List<Role> getSkills() {
        return skills;
    }

    public Preference[][] getTimeFrames() {
        return timeFrames;
    }
}
