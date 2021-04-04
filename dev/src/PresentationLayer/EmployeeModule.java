package PresentationLayer;

import BusinessLayer.ResponseT;
import DTOPackage.EmployeeDTO;
import Resources.Preference;
import Resources.Role;

import java.time.LocalDate;
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

        io.println("Hello And Welcome to Super-Lee!\n");
        while (true) {
            if (ID == null) {
                boolean successfulLogin = false;
                do {
                    ID = menu.loginMenu();
                    if (ID.equals("Q"))
                        break;
                    successfulLogin = backendController.login(ID);
                } while (!successfulLogin);
            }

            if (ID.equals("Q"))
                break;

            isManager = backendController.getIsManager();

            while (!errorOccurred && option != 0) {
                menu.showMainMenu(isManager);

                do {
                    io.print("Choose an option: ");
                    option = io.getInt();
                } while (option < 0 || (!isManager && option >= 4) || (isManager && option >= 9));

                switch (option) {
                    case 0:
                        errorOccurred = backendController.logout();
                        if (!errorOccurred)
                            ID = null;
                        break;

                    case 1:
                        backendController.viewProfile("");
                        break;

                    case 2:
                        String continueToViewShift = "";
                        backendController.viewMyShifts();
                        boolean displayShiftDetails = menu.displaySpecificEmployees();
                        if (displayShiftDetails) {
                            do {
                                LocalDate date = null;
                                do {
                                    date = menu.showEnterDateMenu();
                                } while (date == null);
                                boolean isMorning = menu.showEnterMorningEvening();
                                backendController.viewSpecificShift(date, isMorning);
                                io.print("To find another shift type anything other than \"continue\": ");
                                continueToViewShift = io.getString();
                            } while (!continueToViewShift.equals("continue"));
                        }
                        break;

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

                            io.print("To change another preference type anything other than \"continue\": ");
                            continueChanging = io.getString();
                        } while (!continueChanging.equals("continue"));
                        break;

                    case 4:
                        //backendController.addShift();


                    case 5:
                        //backendController.viewShift();
                        do {
                            LocalDate date;
                            do {
                                date = menu.showEnterDateMenu();
                            } while (date == null);
                            boolean isMorning = menu.showEnterMorningEvening();
                            if (option == 5)
                                backendController.viewSpecificShift(date, isMorning);
                            if (option == 5 || backendController.addShift(date, isMorning)) {
                                int shiftMenu = menu.showUpdateShiftMenu();
                                switch (shiftMenu) {
                                    case 1:
                                        Role role = menu.showRoleMenu();
                                        Map<String, String> availableEmployees = backendController.viewAvailableEmployees(date, isMorning, role);
                                        String id = menu.showAvailableEmployeesMenu(availableEmployees);
                                        backendController.assignToShift(id, role);
                                        break;

                                    case 2:

                                        break;

                                    case 3:

                                        break;

                                    case 4:

                                        break;

                                    default:

                                }
                            }
                            io.print("To find another shift type anything other than \"continue\": ");
                            continueToViewShift = io.getString();
                        } while (!continueToViewShift.equals("continue"));
                        break;

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

                        if (updateEmployee.equals("y")) {
                            int updateIndex;
                            String continueUpdate = null;
                            ResponseT<EmployeeDTO> newEmployee;
                            do {
                                updateIndex = menu.showUpdateEmployeeMenu();
                                newEmployee = backendController.getEmployeeDTO(viewID);
                                switch (updateIndex) {
                                    case 1:
                                        String updateName = menu.showEnterStringMenu("name");
                                        if (!newEmployee.getErrorOccurred()) {
                                            EmployeeDTO emp = backendController.getEmployeeDTO(viewID).getValue();
                                            emp.name = updateName;
                                            backendController.setEmployeeDTO(emp);
                                        }
                                        break;

                                    case 2:
                                        int updateBankId = menu.showEnterIntMenu("bank id");
                                        if (!newEmployee.getErrorOccurred()) {
                                            EmployeeDTO emp = backendController.getEmployeeDTO(viewID).getValue();
                                            emp.bankId = updateBankId;
                                            backendController.setEmployeeDTO(emp);
                                        }
                                        break;

                                    case 3:
                                        int updateBranchId = menu.showEnterIntMenu("branch id");
                                        if (!newEmployee.getErrorOccurred()) {
                                            EmployeeDTO emp = backendController.getEmployeeDTO(viewID).getValue();
                                            emp.branchId = updateBranchId;
                                            backendController.setEmployeeDTO(emp);
                                        }
                                        break;

                                    case 4:
                                        int updateAccountNumber = menu.showEnterIntMenu("account number");
                                        if (!newEmployee.getErrorOccurred()) {
                                            EmployeeDTO emp = backendController.getEmployeeDTO(viewID).getValue();
                                            emp.accountNumber = updateAccountNumber;
                                            backendController.setEmployeeDTO(emp);
                                        }
                                        break;

                                    case 5:
                                        float updateSalary = menu.showEnterFloatMenu("salary");
                                        if (!newEmployee.getErrorOccurred()) {
                                            EmployeeDTO emp = backendController.getEmployeeDTO(viewID).getValue();
                                            emp.salary = updateSalary;
                                            backendController.setEmployeeDTO(emp);
                                        }
                                        break;

                                    case 6:
                                        LocalDate updateStartDate = menu.showEnterDateMenu();
                                        if (!newEmployee.getErrorOccurred()) {
                                            EmployeeDTO emp = backendController.getEmployeeDTO(viewID).getValue();
                                            emp.startDate = updateStartDate;
                                            backendController.setEmployeeDTO(emp);
                                        }
                                        break;

                                    case 7:
                                        String updateTrustFund = menu.showEnterStringMenu("trust fund");
                                        if (!newEmployee.getErrorOccurred()) {
                                            EmployeeDTO emp = backendController.getEmployeeDTO(viewID).getValue();
                                            emp.trustFund = updateTrustFund;
                                            backendController.setEmployeeDTO(emp);
                                        }
                                        break;

                                    case 8:
                                        int updateFreeDays = menu.showEnterIntMenu("free days amount");
                                        if (!newEmployee.getErrorOccurred()) {
                                            EmployeeDTO emp = backendController.getEmployeeDTO(viewID).getValue();
                                            emp.freeDays = updateFreeDays;
                                            backendController.setEmployeeDTO(emp);
                                        }
                                        break;

                                    case 9:
                                        int updateSickDays = menu.showEnterIntMenu("sick days amount");
                                        if (!newEmployee.getErrorOccurred()) {
                                            EmployeeDTO emp = backendController.getEmployeeDTO(viewID).getValue();
                                            emp.sickDays = updateSickDays;
                                            backendController.setEmployeeDTO(emp);
                                        }
                                        break;

                                    case 10:
                                        List<Role> updateSkills = menu.showEnterRoleList();
                                        if (!newEmployee.getErrorOccurred()) {
                                            EmployeeDTO emp = backendController.getEmployeeDTO(viewID).getValue();
                                            emp.skills = updateSkills;
                                            backendController.setEmployeeDTO(emp);
                                        }
                                        break;
                                }
                                io.print("To change another field type anything other than \"continue\": ");
                                continueUpdate = io.getString();
                            } while (!continueUpdate.equals("continue"));
                        }
                        break;

                    case 7:
                        String name, newID, trustFund;
                        int bankId, branchId, accountNumber, freeDays, sickDays;
                        float salary;
                        List<Role> skills;
                        LocalDate startDate;
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
                        startDate = menu.showEnterDateMenu();
                        io.print("Please enter a trust fund: ");
                        trustFund = io.getString();
                        io.print("Please enter the amount of free days: ");
                        freeDays = io.getInt();
                        io.print("Please enter the amount of sick days: ");
                        sickDays = io.getInt();
                        skills = menu.showEnterRoleList();
                        timeFrames = menu.showEnterPreferenceArray();

                        backendController.addEmployee(name, ID, bankId, branchId, accountNumber, salary, startDate, trustFund, freeDays, sickDays, skills, timeFrames);
                        break;

                    case 8:
                        //backendController.defineShiftPersonnel();
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
                            Role role = menu.showRoleMenu();
                            io.print("how many " + role.toString() + " are needed? ");
                            int qtty = io.getInt();
                            backendController.defineShiftPersonnel(day, morning_evening.equals("m"), role, qtty);
                            io.print("To change another preference type anything other than \"continue\": ");
                            continueChanging = io.getString();
                        } while (!continueChanging.equals("continue"));
                        break;

                    default:
                        break;
                }
            }
            errorOccurred = false;
        }
    }
}
