package PresentationLayer;

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

    public static void main(String[] args) {
        BackendController backendController = new BackendController();
        MenuController menu = new MenuController();
        IOController io = IOController.getInstance();
        String ID = null;
        int option = -1;
        boolean errorOccurred = false, isManager = false;
        boolean goBack = false;
        io.println("THE FOLLOWING QUESTION IS FOR THE TESTER");
        io.println("If you want to load data into the system please type 1, to continue normally type any other number.");
        int init = io.getInt();
        if (init == 1) {
            InitializeData initclass = new InitializeData();
            initclass.initializeData(backendController);
        }
        io.println("");

        io.println("Hello And Welcome to Super-Lee!\n");
        while (true) {
            if (ID == null) {
                boolean successfulLogin = false;
                do {
                    ID = menu.loginMenu();
                    if (ID.equals("q"))
                        break;
                    successfulLogin = backendController.login(ID);
                } while (!successfulLogin);
            }

            if (ID.equals("q"))
                break;

            isManager = backendController.getIsManager();

            while (!errorOccurred && option != 0) {
                menu.showMainMenu(isManager);

                do {
                    io.print("Choose an option: ");
                    option = io.getInt();
                } while (option < 0 || (!isManager && option >= 4) || (isManager && option >= 9));

                switch (option) {
                    //Logout
                    case 0:
                        errorOccurred = backendController.logout();
                        if (!errorOccurred)
                            ID = null;
                        break;

                    //View my profile
                    case 1:
                        backendController.viewProfile("");
                        io.println("");
                        break;

                    //View my shifts
                    case 2:
                        String continueToViewShift = "";
                        boolean isAssigned = backendController.viewMyShifts();
                        if (!isAssigned) {
                            io.println("");
                            continue;
                        }
                        boolean displayShiftDetails = menu.displaySpecificEmployees();
                        if (displayShiftDetails) {
                            io.println("");
                            continue;
                        }
                        do {
                            int year, month, day;
                            do {
                                year = menu.showEnterYearMenu();
                                month = menu.showEnterMonthMenu();
                                day = menu.showEnterDayMenu();
                            } while (!backendController.checkLegalDate(year, month, day));
                            LocalDate date = LocalDate.of(year, month, day);
                            boolean isMorning = menu.showEnterMorningEvening();
                            backendController.viewSpecificShift(date, isMorning);
                            io.print("To continue type \"c\", to view another shift type anything else: ");
                            continueToViewShift = io.getString();
                        } while (!continueToViewShift.equals("c"));
                        io.println("");
                        break;

                    //Change shift preferences
                    case 3:
                        String continueChanging = "", morning_evening = "";
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

                            backendController.changePreference(day - 1, morning_evening.equals("m"), Preference.values()[prefIndex]);

                            io.print("To continue type \"c\", to change another preference type anything else: ");
                            continueChanging = io.getString();
                        } while (!continueChanging.equals("c"));
                        io.println("");
                        break;

                        //Add new shift
                    case 4:

                        //View shift
                    case 5:
                        do {
                            int year, month, day;
                            LocalDate date = null;
                            boolean isMorning = false;
                            boolean gotShift = true;
                            if(option == 4 || menu.showSpecificDateMenu()){
                                do {
                                    year = menu.showEnterYearMenu();
                                    month = menu.showEnterMonthMenu();
                                    day = menu.showEnterDayMenu();
                                } while (!backendController.checkLegalDate(year, month, day));
                                date = LocalDate.of(year, month, day);
                                isMorning = menu.showEnterMorningEvening();
                                if(option == 5)
                                    gotShift = backendController.viewSpecificShift(date, isMorning);
                            }else {
                                io.print("enter number of days: ");
                                List<ShiftDTO> shiftDTOs = backendController.viewShiftsAsAdmin(io.getInt());
                                if(shiftDTOs != null) {
                                    List<String> desc = new ArrayList<>();
                                    for (ShiftDTO shift : shiftDTOs)
                                        desc.add(shift.describeShift());
                                    int index = menu.showFutureShiftsMenu(desc);
                                    date = shiftDTOs.get(index).date;
                                    isMorning = shiftDTOs.get(index).isMorning;
                                }else{
                                    gotShift = false;
                                }
                            }
                            if(option == 4 && date.isBefore(LocalDate.now())){
                                gotShift = menu.showConfirmationMenu("this shift is in the past.");
                            }
                            if (gotShift && (option == 5 || backendController.addShift(date, isMorning))) {
                                int shiftMenu = menu.showUpdateShiftMenu();
                                switch (shiftMenu) {
                                    //go back
                                    case 0:
                                        goBack = true;
                                        break;
                                    case 1:
                                        //Assign employee to shift
                                        int day1 = (date.getDayOfWeek().getValue() + 1) % 7 == 0 ? 7 : (date.getDayOfWeek().getValue() + 1) % 7;
                                        Map<Role, Integer> personnel = backendController.getPersonnelForShift(day1, isMorning);
                                        Role role = menu.showShiftPersonnelMenu(personnel);
                                        Map<String, String> availableEmployees = backendController.viewAvailableEmployees(date, isMorning, role);
                                        String id = menu.showAvailableEmployeesMenu(availableEmployees);
                                        backendController.assignToShift(id, role);
                                        io.println("");
                                        break;

                                    case 2:
                                        //Remove employee from shift
                                        ShiftDTO myShift = backendController.getShift(date, isMorning);
                                        if (myShift != null) {
                                            Map<Role, List<String>> map = myShift.getPositions();
                                            String empId = menu.showShiftPositionsMenu(map);
                                            if (backendController.removeFromShift(empId))
                                                io.println("success!");
                                        }
                                        io.println("");
                                        break;

                                    case 3:
                                        //Delete shift
                                        boolean stop = false;
                                        if(date.isBefore(LocalDate.now())){
                                            stop = menu.showConfirmationMenu("this shift has past.");
                                        }
                                        if (!stop & backendController.removeShift(date, isMorning))
                                            io.println("success!");
                                        io.println("");
                                        break;

//                                    case 4:
//                                        //Display assigned employees
//                                        ShiftDTO shift = backendController.getShift(date, isMorning);
//                                        if (shift != null) {
//                                            Map<Role, List<String>> positions = shift.getPositions();
//                                            menu.showAssignedEmployeesMenu(positions);
//                                        }
//                                        io.println("");
//                                        break;

                                    default:

                                }
                            }
                            io.print("To continue type \"c\", to view another shift type anything else: ");
                            continueToViewShift = io.getString();
                        } while (!goBack && !continueToViewShift.equals("c"));
                        goBack = false;
                        io.println("");
                        break;

                    //View employee + update employee
                    case 6:
                        String viewID, updateEmployee;
                        io.print("Please enter the ID of the employee: ");
                        viewID = io.getString();
                        backendController.viewProfile(viewID);

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
                        String continueUpdate = null;
                        ResponseT<EmployeeDTO> newEmployee;
                        do {
                            updateIndex = menu.showUpdateEmployeeMenu();
                            newEmployee = backendController.getEmployeeDTO(viewID);
                            if (newEmployee.getErrorOccurred()) {
                                io.println("");
                                continue;
                            }
                            EmployeeDTO emp = backendController.getEmployeeDTO(viewID).getValue();
                            switch (updateIndex) {
                                case 0:
                                    //go back
                                    goBack = true;
                                    break;
                                case 1:
                                    emp.name = menu.showEnterStringMenu("name");
                                    break;

                                case 2:
                                    emp.bankId = menu.showEnterIntMenu("bank id");
                                    break;

                                case 3:
                                    emp.branchId = menu.showEnterIntMenu("branch id");
                                    break;

                                case 4:
                                    emp.accountNumber = menu.showEnterIntMenu("account number");
                                    break;

                                case 5:
                                    emp.salary = menu.showEnterFloatMenu("salary");
                                    break;

                                case 6:
                                    int year, month, day;
                                    do {
                                        year = menu.showEnterYearMenu();
                                        month = menu.showEnterMonthMenu();
                                        day = menu.showEnterDayMenu();
                                    } while (!backendController.checkLegalDate(year, month, day));
                                    LocalDate date = LocalDate.of(year, month, day);
                                    emp.startDate = date;
                                    break;

                                case 7:
                                    emp.trustFund = menu.showEnterStringMenu("trust fund");
                                    break;

                                case 8:
                                    emp.freeDays = menu.showEnterIntMenu("free days amount");
                                    break;

                                case 9:
                                    emp.sickDays = menu.showEnterIntMenu("sick days amount");
                                    break;

                                case 10:
                                    emp.skills = menu.showEnterRoleList();
                                    break;
                            }
                            backendController.setEmployeeDTO(emp);
                            io.print("To continue type \"c\", to change another field type anything else: ");
                            continueUpdate = io.getString();
                        } while (!goBack && !continueUpdate.equals("c"));
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
                        io.print("Please enter a bank id: ");
                        bankId = io.getInt();
                        io.print("Please enter a branch id: ");
                        branchId = io.getInt();
                        io.print("Please enter an account number: ");
                        accountNumber = io.getInt();
                        io.print("Please enter the salary: ");
                        salary = io.getFloat();
                        io.println("Please enter the start working date:");
                        do {
                            year = menu.showEnterYearMenu();
                            month = menu.showEnterMonthMenu();
                            day = menu.showEnterDayMenu();
                        } while (!backendController.checkLegalDate(year, month, day));
                        LocalDate date = LocalDate.of(year, month, day);
                        io.print("Please enter a trust fund: ");
                        trustFund = io.getString();
                        io.print("Please enter the amount of free days: ");
                        freeDays = io.getInt();
                        io.print("Please enter the amount of sick days: ");
                        sickDays = io.getInt();
                        skills = menu.showEnterRoleList();
                        timeFrames = menu.showEnterPreferenceArray();

                        backendController.addEmployee(name, ID, bankId, branchId, accountNumber, salary, date, trustFund, freeDays, sickDays, skills, timeFrames);
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
                            Map<Role, Integer> personnel = backendController.getPersonnelForShift(dayShift, morning_evening.equals("m"));
                            Role role = menu.showShiftPersonnelMenu(personnel);

                            io.print("how many " + role.toString() + " are needed? ");
                            int qtty = io.getInt();
                            backendController.defineShiftPersonnel(dayShift, morning_evening.equals("m"), role, qtty);
                            io.print("To continue type \"c\", to update more information type anything else: ");
                            continueChanging = io.getString();
                        } while (!continueChanging.equals("c"));
                        io.println("");
                        break;
                }
            }
            option = -1;
            errorOccurred = false;
        }
    }
}
