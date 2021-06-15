package PresentationLayer.EmployeesMenu;

import APIs.ShipmentsEmployeesAPI;
import BusinessLayer.ResponseT;
import DTOPackage.EmployeeDTO;
import DTOPackage.ShiftDTO;
import Resources.Preference;
import Resources.Role;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class EmployeeModule {

    public void run() {
        PresentationController presentationController = PresentationController.getInstance();
        ShipmentsEmployeesAPI shipmentModuleAPI = new ShipmentsEmployeesAPI();
        MenuHandler menu = new MenuHandler();
        IOController io = IOController.getInstance();
        String ID = null;
        int option = -1;
        boolean errorOccurred = false, isManager = false;
        boolean goBack = false;
        boolean proceed = true;
//        io.println("THE FOLLOWING QUESTION IS FOR THE TESTER");
//        io.println("If you want to load data into the system please type 1, to continue normally type any other number.");
//        int init = io.getInt();
//        if (init == 1) {
//            InitializeData initclass = new InitializeData();
//            initclass.initializeData(presentationController);
//        }
//        io.println("");

        io.println("Hello And Welcome to Super-Lee!\n");
        while (true) {
            if (ID == null) {
                boolean successfulLogin = false;
                do {
                    ID = menu.loginMenu();
                    if (ID.equals("q"))
                        break;
                    successfulLogin = presentationController.login(ID);
                    if (!successfulLogin)
                        continue;
                    List<String> alerts = presentationController.checkForAlerts();
                    if (!alerts.isEmpty()) {
                        io.println("Dear HRManager!\n");
                        for (String message : alerts) {
                            io.println(message);
                        }
                        io.println("");
                    }
                } while (!successfulLogin);
            }

            if (ID.equals("q"))
                break;


            isManager = presentationController.getIsManager();
            io.println("");

            while (!errorOccurred && option != 0) {
                menu.showMainMenu(isManager);

                do {
                    io.print("Choose an option: ");
                    option = io.getInt();
                } while (option < 0 || (!isManager && option >= 4) || (isManager && option >= 9));
                io.println("");
                switch (option) {

                    //Logout
                    case 0:
                        errorOccurred = presentationController.logout();
                        if (!errorOccurred)
                            ID = null;
                        io.println("");
                        break;

                    //View my profile
                    case 1:
                        presentationController.viewProfile("");
                        io.println("");
                        break;

                    //View my shifts
                    case 2:
                        Map<ShiftDTO, Role> myShifts = presentationController.viewMyShifts();
                        if (myShifts == null) {
                            io.println("");
                            continue;
                        }
                        boolean displayShiftDetails = menu.displaySpecificEmployees();
                        if (!displayShiftDetails) {
                            io.println("");
                            continue;
                        }
                        do {
                            io.print("Please enter a number between 1 and " + myShifts.keySet().size() + ": ");
                            int shiftToDisplay = io.getInt();
                            LocalDate date = myShifts.keySet().toArray(new ShiftDTO[0])[shiftToDisplay - 1].date;
                            boolean isMorning = myShifts.keySet().toArray(new ShiftDTO[0])[shiftToDisplay - 1].isMorning;
                            presentationController.viewAShiftAsAdmin(date, isMorning);
                            proceed = menu.askToProceed("view another shift");
                        } while (proceed);
                        io.println("");
                        break;

                    //Change shift preferences
                    case 3:
                        String morning_evening = "";
                        do {
                            io.print("Please enter the day of the week in numbers: ");
                            int day = 0;
                            do {
                                io.print("Pick a number between 1 and 7: ");
                                day = io.getInt();
                            } while (day <= 0 || day > 7);
                            io.println("Is the shift in the morning or in the evening?");
                            do {
                                io.print("Type \"m\" or \"e\": ");
                                morning_evening = io.getString();
                            } while (!morning_evening.equals("m") && !morning_evening.equals("e"));
                            int prefIndex = menu.showPreferenceMenu();

                            presentationController.changePreference(day - 1, morning_evening.equals("m"), Preference.values()[prefIndex]);

                            proceed = menu.askToProceed("change another preference");
                        } while (proceed);
                        io.println("");
                        break;

                    //Add new shift
                    case 4:

                        //View shift
                    case 5:
                        do {
                            LocalDate date = null;
                            boolean isMorning = false;
                            boolean gotShift = true;
                            if (option == 4 || menu.showSpecificDateMenu()) {
                                date = menu.showEnterDateMenu();
                                isMorning = menu.showEnterMorningEvening();
//                                if (option == 5)
//                                    gotShift = presentationController.viewAShiftAsAdmin(date, isMorning);
                            } else {

                                int daysNum;
                                do {
                                    io.print("Enter number of days: ");
                                    daysNum = io.getInt();
                                } while (daysNum <= 0);
                                io.println("");
                                List<ShiftDTO> shiftDTOs = presentationController.viewShiftsAsAdmin(daysNum);
                                if (shiftDTOs != null) {
                                    List<String> desc = new ArrayList<>();
                                    for (ShiftDTO shift : shiftDTOs)
                                        desc.add(shift.describeShift());
                                    int index = menu.showFutureShiftsMenu(desc);
                                    date = shiftDTOs.get(index).date;
                                    isMorning = shiftDTOs.get(index).isMorning;
                                } else {
                                    gotShift = false;
                                }
                            }
                            io.println("");
                            if (option == 4) {
                                if (date.isBefore(LocalDate.now())) {
                                    gotShift = menu.showConfirmationMenu("this date already past.");
                                }
                                gotShift = gotShift && presentationController.addShift(date, isMorning);
                            }
                            gotShift = gotShift && presentationController.viewAShiftAsAdmin(date, isMorning);
                            io.println("");

                            if (gotShift) {

                                int shiftMenu = menu.showUpdateShiftMenu();
                                switch (shiftMenu) {
                                    //go back
                                    case 0:
                                        goBack = true;
                                        break;
                                    case 1:
                                        //Assign employee to shift
                                        int day1 = (date.getDayOfWeek().getValue() + 1) % 7 == 0 ? 7 : (date.getDayOfWeek().getValue() + 1) % 7;
                                        int listSize = 0;
                                        Map<Role, Integer> personnel = presentationController.getPersonnelForShift(day1, isMorning);
                                        Role role = menu.showShiftPersonnelMenu(personnel, "Which employee do you want to add?");
                                        io.println("");
                                        Map<String, String> availableEmployees = presentationController.viewAvailableEmployees(date, isMorning, role, false);
                                        listSize += availableEmployees.size();
                                        menu.showAvailableEmployeesMenu(availableEmployees, 1);
                                        io.println("Do you want to also view the employees who marked 'CANT' on this shift?");
                                        String viewCant = "";
                                        do {
                                            io.print("Enter \"y\" or \"n\": ");
                                            viewCant = io.getString();
                                        } while (!viewCant.equals("y") && !viewCant.equals("n"));
                                        Map<String, String> unavailableEmployees = null;
                                        if (viewCant.equals("y")) {
                                            unavailableEmployees = presentationController.viewAvailableEmployees(date, isMorning, role, true);
                                            listSize += unavailableEmployees.size();
                                            menu.showAvailableEmployeesMenu(unavailableEmployees, listSize);
                                        }
                                        io.println("");
                                        io.println("Please choose from the list which employee would you like to add.");
                                        int id = 0;
                                        do {
                                            io.print("Enter a number between 1 and " + listSize + ": ");
                                            id = io.getInt();
                                        } while (id <= 0 || id > listSize);
                                        if (id <= availableEmployees.size())
                                            presentationController.assignToShift(availableEmployees.keySet().toArray(new String[0])[id - 1], role);
                                        else
                                            presentationController.assignToShift(unavailableEmployees.keySet().toArray(new String[0])[id - availableEmployees.size() - 1], role);
                                        io.println("");
                                        break;

                                    case 2:
                                        //Remove employee from shift
                                        ShiftDTO myShift = presentationController.getShift(date, isMorning);
                                        if (myShift != null) {
                                            Map<Role, List<String>> map = myShift.getPositions();
                                            String empId = menu.showShiftPositionsMenu(map);
                                            if (empId == null)
                                                io.println("There are no employees in the shift.");
                                            else if (presentationController.removeFromShift(empId))
                                                io.println("success!");
                                        }
                                        io.println("");
                                        break;

                                    case 3:
                                        //Delete shift
                                        boolean stop = false;
                                        if (date.isBefore(LocalDate.now())) {
                                            stop = menu.showConfirmationMenu("this shift has past.");
                                        }
                                        if (!stop & presentationController.removeShift(date, isMorning))
                                            io.println("success!");
                                        io.println("");
                                        break;

                                    default:
                                        break;

                                }
                            }
                            String msg = option == 5 ? "view" : "add";
                            proceed = !goBack && menu.askToProceed(msg + " another shift");
                        } while (proceed);
                        goBack = false;
                        io.println("");
                        break;

                    //View employee + update employee
                    case 6:
                        String viewID, updateEmployee;
                        do {
                            io.print("Please enter the ID of the employee: ");
                            viewID = io.getString();
                            io.println("");
                            errorOccurred = presentationController.viewProfile(viewID);
                        } while (errorOccurred);

                        io.println("Do you want to change this employee's information?:");
                        do {
                            io.print("Type \"y\" or \"n\": ");
                            updateEmployee = io.getString();
                        } while (!updateEmployee.equals("y") && !updateEmployee.equals("n"));

                        if (updateEmployee.endsWith("n")) {
                            io.println("");
                            continue;
                        }

                        int updateIndex;
                        ResponseT<EmployeeDTO> newEmployee;
                        do {
                            updateIndex = menu.showUpdateEmployeeMenu();
                            newEmployee = presentationController.getEmployeeDTO(viewID);
                            if (newEmployee.getErrorOccurred()) {
                                io.println("");
                                continue;
                            }
                            EmployeeDTO emp = newEmployee.getValue();
                            String oldID = emp.ID;
                            switch (updateIndex) {
                                case 0:
                                    //go back
                                    goBack = true;
                                    break;
                                case 1:
                                    emp.name = menu.showEnterStringMenu("name");
                                    break;

                                case 2:
                                    emp.ID = menu.showEnterStringMenu("id");
                                    if (!emp.ID.matches("[0-9]+")) {
                                        io.println("ID must contain only numbers.");
                                        continue;
                                    }
                                    break;

                                case 3:
                                    emp.bankId = menu.showEnterIntMenu("bank id");
                                    break;

                                case 4:
                                    emp.branchId = menu.showEnterIntMenu("branch id");
                                    break;

                                case 5:
                                    emp.accountNumber = menu.showEnterIntMenu("account number");
                                    break;

                                case 6:
                                    emp.salary = menu.showEnterFloatMenu("salary");
                                    break;

                                case 7:
                                    emp.startDate = menu.showEnterDateMenu();
                                    break;

                                case 8:
                                    emp.trustFund = menu.showEnterStringMenu("trust fund");
                                    break;

                                case 9:
                                    emp.freeDays = menu.showEnterIntMenu("free days amount");
                                    break;

                                case 10:
                                    emp.sickDays = menu.showEnterIntMenu("sick days amount");
                                    break;

                                case 11:
                                    emp.skills = menu.showEnterRoleList();
                                    break;
                            }
                            boolean errAddingEmployee = presentationController.setEmployeeDTO(oldID, emp);
                            if (!errAddingEmployee && emp.skills.contains(Role.Driver)) {
                                shipmentModuleAPI.addDriver(emp.ID);
                            }
                            proceed = !goBack && menu.askToProceed("change another field");
                        } while (proceed);
                        goBack = false;
                        io.println("");
                        break;

                    //Add new employee
                    case 7:
                        String name, newID, trustFund;
                        int bankId, branchId, accountNumber, freeDays, sickDays, year, month, day;
                        float salary;
                        List<Role> skills;
                        Preference[][] timeFrames;

                        io.print("Please enter a name: ");
                        name = io.getString();
                        io.print("Please enter an ID: ");
                        ID = io.getString();
                        if (!ID.matches("[0-9]+")) {
                            io.println("ID must contain only numbers.");
                            continue;
                        }
                        if (presentationController.isIDAlreadyRegistered(ID)) {
                            io.println("ID already registered in the system.");
                            continue;
                        }
                        io.print("Please enter a bank id: ");
                        bankId = io.getInt();
                        io.print("Please enter a branch id: ");
                        branchId = io.getInt();
                        io.print("Please enter an account number: ");
                        accountNumber = io.getInt();
                        io.print("Please enter the salary: ");
                        salary = io.getFloat();
                        io.println("Please enter the start working date:");
                        LocalDate date = menu.showEnterDateMenu();
                        io.print("Please enter a trust fund: ");
                        trustFund = io.getString();
                        io.print("Please enter the amount of free days: ");
                        freeDays = io.getInt();
                        io.print("Please enter the amount of sick days: ");
                        sickDays = io.getInt();
                        skills = menu.showEnterRoleList();
                        timeFrames = menu.showEnterPreferenceArray();

                        boolean addEmployeeError = presentationController.addEmployee(name, ID, bankId, branchId, accountNumber, salary, date, trustFund, freeDays, sickDays, skills, timeFrames);
                        if (!addEmployeeError && skills.contains(Role.Driver)) {
                            shipmentModuleAPI.addDriver(ID);
                        }

                        io.println("");
                        break;

                    //Update shift personnel
                    case 8:
                        do {
                            io.print("Please enter the day of the week in numbers: ");
                            int dayShift = 0;
                            do {
                                io.print("Pick a number between 1 and 7: ");
                                dayShift = io.getInt();
                            } while (dayShift <= 0 || dayShift > 7);
                            io.println("Is the shift in the morning or in the evening?");
                            do {
                                io.print("Type \"m\" or \"e\": ");
                                morning_evening = io.getString();
                            } while (!morning_evening.equals("m") && !morning_evening.equals("e"));
                            Map<Role, Integer> personnel = presentationController.getPersonnelForShift(dayShift, morning_evening.equals("m"));
                            Role role = menu.showShiftPersonnelMenu(personnel, "What job do you want to change the amount of?");

                            io.print("how many " + role.toString() + " are needed? ");
                            int qtty = io.getInt();
                            presentationController.defineShiftPersonnel(dayShift, morning_evening.equals("m"), role, qtty);
                            proceed = menu.askToProceed("update more information");
                        } while (proceed);
                        io.println("");
                        break;

                }
            }
            option = -1;
            errorOccurred = false;
        }
    }
}