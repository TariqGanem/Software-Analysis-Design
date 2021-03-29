package BusinessLayer.EmployeePackage;

public class Bank {
    private int bankId;
    private int branchId;
    private int accountNumber;

    public Bank(int bankId, int branchId, int accountNumber) {
        this.bankId = bankId;
        this.branchId = branchId;
        this.accountNumber = accountNumber;
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
}
