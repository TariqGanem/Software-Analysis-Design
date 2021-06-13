package PresentationLayer.EmployeesMenu;

import APIs.ShipmentsEmployeesAPI;
import Resources.Preference;
import Resources.Role;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class InitializeData {

    public void initializeData(PresentationController presentationController) {
        initializeEmployees(presentationController);
        initializeShiftPersonnel(presentationController);
        initializeShifts(presentationController);
        initDrivers();
    }

    private void initializeEmployees(PresentationController presentationController) {
        List roles = new ArrayList<Role>();
        roles.add(Role.StoreManager);
        roles.add(Role.ShiftManager);
        roles.add(Role.ShipmentsManager);
        String name = "Mr. Krabs";
        String ID1 = "123456789";
        int bankId = 10;
        int branchId = 100;
        int accountNumber = 1000;
        float salary = 5000;
        LocalDate date = LocalDate.of(1999, 1, 1);
        String trustFund = "Trust Fund";
        int freeDays = 10;
        int sickDays = 15;
        Preference[][] timeFrames = new Preference[7][2];
        for (int i = 0; i < 7; i++)
            for (int j = 0; j < 2; j++)
                timeFrames[i][j] = (i + j) % 2 == 0 ? Preference.WANT : Preference.CAN;
        presentationController.addEmployee(name, ID1, bankId, branchId, accountNumber, salary, date, trustFund, freeDays, sickDays, roles, timeFrames);

        roles = new ArrayList<Role>();
        roles.add(Role.ShiftManager);
        roles.add(Role.Cashier);
        roles.add(Role.Stocker);
        roles.add(Role.StoreManagerAssistant);
        name = "Squidward Tentacles";
        ID1 = "987654321";
        bankId = 12;
        branchId = 10;
        accountNumber = 999;
        salary = 10;
        date = LocalDate.of(1999, 1, 1);
        trustFund = "456987";
        freeDays = 0;
        sickDays = 5;
        timeFrames = new Preference[7][2];
        for (int i = 0; i < 7; i++)
            for (int j = 0; j < 2; j++)
                timeFrames[i][j] = (i + j) == 2 ? Preference.CANT : Preference.CAN;
        presentationController.addEmployee(name, ID1, bankId, branchId, accountNumber, salary, date, trustFund, freeDays, sickDays, roles, timeFrames);

        roles = new ArrayList<Role>();
        roles.add(Role.Cashier);
        roles.add(Role.Stocker);
        roles.add(Role.StoreKeeper);
        roles.add(Role.Driver);
        name = "SpongeBob SquarePants";
        ID1 = "111111111";
        bankId = 12;
        branchId = 55;
        accountNumber = 998;
        salary = 10000;
        date = LocalDate.of(1999, 5, 1); //Episode "Help Wanted"
        trustFund = "222";
        freeDays = 1000;
        sickDays = 1000;
        timeFrames = new Preference[7][2];
        for (int i = 0; i < 7; i++)
            for (int j = 0; j < 2; j++)
                timeFrames[i][j] = Preference.WANT;
        presentationController.addEmployee(name, ID1, bankId, branchId, accountNumber, salary, date, trustFund, freeDays, sickDays, roles, timeFrames);

        roles = new ArrayList<Role>();
        roles.add(Role.HRManager);
        roles.add(Role.Cashier);
        roles.add(Role.Driver);
        roles.add(Role.Stocker);
        name = "Patrick Star";
        ID1 = "222222222";
        bankId = 12;
        branchId = 66;
        accountNumber = 997;
        salary = 0.2f;
        date = LocalDate.of(2001, 2, 3); //Episode "Big Pink Loser"
        trustFund = "111";
        freeDays = 0;
        sickDays = 0;
        timeFrames = new Preference[7][2];
        for (int i = 0; i < 7; i++)
            for (int j = 0; j < 2; j++)
                timeFrames[i][j] = Preference.CANT;
        presentationController.addEmployee(name, ID1, bankId, branchId, accountNumber, salary, date, trustFund, freeDays, sickDays, roles, timeFrames);

        roles = new ArrayList<Role>();
        roles.add(Role.StoreKeeper);
        name = "Sandy Cheeks";
        ID1 = "333333333";
        bankId = 12;
        branchId = 100;
        accountNumber = 994;
        salary = 100.5f;
        date = LocalDate.of(2021, 1, 1);
        trustFund = "121";
        freeDays = 0;
        sickDays = 0;
        timeFrames = new Preference[7][2];
        for (int i = 0; i < 7; i++)
            for (int j = 0; j < 2; j++)
                timeFrames[i][j] = Preference.WANT;
        presentationController.addEmployee(name, ID1, bankId, branchId, accountNumber, salary, date, trustFund, freeDays, sickDays, roles, timeFrames);
    }

    private void initializeShiftPersonnel(PresentationController presentationController) {
        presentationController.login("123456789");
        for (int i = 1; i < 7; i++) {
            for (Role role : Role.values()) {
                presentationController.defineShiftPersonnel(i, true, role, i % 3 + 1);
                if (i == 6)
                    continue;
                presentationController.defineShiftPersonnel(i, false, role, i % 2 + 1);
            }
        }
        presentationController.logout();
    }

    private void initializeShifts(PresentationController presentationController) {
        presentationController.login("123456789");
        LocalDate date1 = LocalDate.of(2021, 7, 4);
        int day1 = (date1.getDayOfWeek().getValue() + 1) % 7;
        presentationController.addShift(date1, true);
        presentationController.assignToShift("123456789", Role.ShiftManager);
        presentationController.assignToShift("111111111", Role.Cashier);
        presentationController.assignToShift("222222222", Role.Driver);
        presentationController.assignToShift("333333333", Role.StoreKeeper);
        presentationController.assignToShift("987654321", Role.Stocker);

        LocalDate date2 = LocalDate.of(2021, 7, 5);
        int day2 = (date2.getDayOfWeek().getValue() + 1) % 7;
        day2 = day2 == 0 ? 7 : day2;
        presentationController.addShift(date2, true);
        presentationController.assignToShift("123456789", Role.ShiftManager);
        presentationController.assignToShift("987654321", Role.Cashier);
        presentationController.assignToShift("111111111", Role.Driver);
        presentationController.assignToShift("222222222", Role.HRManager);
        presentationController.assignToShift("333333333", Role.StoreKeeper);

        LocalDate date3 = LocalDate.of(2021, 7, 6);
        int day3 = (date3.getDayOfWeek().getValue() + 1) % 7;
        day3 = day3 == 0 ? 7 : day3;
        presentationController.addShift(date3, false);
        presentationController.assignToShift("123456789", Role.ShiftManager);
        presentationController.assignToShift("111111111", Role.Cashier);
        presentationController.assignToShift("222222222", Role.HRManager);
        presentationController.assignToShift("333333333", Role.StoreKeeper);
        presentationController.logout();
    }

    private void initDrivers() {
        new ShipmentsEmployeesAPI().initDriver("222222222", 7000);

        new ShipmentsEmployeesAPI().initDriver("111111111", 15000);
        System.out.println();
    }
}
