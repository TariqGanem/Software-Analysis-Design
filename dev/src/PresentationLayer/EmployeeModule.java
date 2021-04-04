package PresentationLayer;

import BusinessLayer.ResponseT;
import DTOPackage.EmployeeDTO;
import Resources.Preference;
import Resources.Role;

import java.time.LocalDate;
import java.util.List;

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
                        do {
                            LocalDate date = null;
                            do {
                                date = menu.showEnterDateMenu();
                            } while (date == null);
                            boolean isMorning = menu.showEnterMorningEvening();
                            if(backendController.addShift(date, isMorning))
                                menu.showUpdateEmployeeMenu();
                            io.print("To find another shift type anything other than \"continue\": ");
                            continueToViewShift = io.getString();
                        } while (!continueToViewShift.equals("continue"));
                        break;

                    case 5:
                        //backendController.addEmployee();
                        break;

                    case 6:
                        String viewID, updateEmployee;
                        io.print("Please enter the ID of the employee: ");
                        viewID = io.getString();
                        backendController.viewProfile(viewID);

                        do {
                            io.print("Type \"y\" or \"n\": ");
                            updateEmployee = io.getString();
                        } while (!updateEmployee.equals("y") && !updateEmployee.equals("n"));

                        if (updateEmployee.equals("y")) {
                            int updateIndex = menu.showUpdateEmployeeMenu();
                            ResponseT<EmployeeDTO> newEmployee;
                            switch (updateIndex) {
                                case 1:
                                    String name = menu.showEnterStringMenu("name");
                                    newEmployee = backendController.getEmployeeDTO(viewID);
                                    if (!newEmployee.getErrorOccurred()) {
                                        backendController.getEmployeeDTO(viewID).getValue().name = name;
                                        backendController.setEmployeeDTO(newEmployee.getValue());
                                    }

                                case 2:
                                    int bankId = menu.showEnterIntMenu("bank id");
                                    newEmployee = backendController.getEmployeeDTO(viewID);
                                    if (!newEmployee.getErrorOccurred()) {
                                        backendController.getEmployeeDTO(viewID).getValue().bankId = bankId;
                                        backendController.setEmployeeDTO(newEmployee.getValue());
                                    }

                                case 3:
                                    int branchId = menu.showEnterIntMenu("branch id");
                                    newEmployee = backendController.getEmployeeDTO(viewID);
                                    if (!newEmployee.getErrorOccurred()) {
                                        backendController.getEmployeeDTO(viewID).getValue().branchId = branchId;
                                        backendController.setEmployeeDTO(newEmployee.getValue());
                                    }

                                case 4:
                                    int accountNumber = menu.showEnterIntMenu("account number");
                                    newEmployee = backendController.getEmployeeDTO(viewID);
                                    if (!newEmployee.getErrorOccurred()) {
                                        backendController.getEmployeeDTO(viewID).getValue().accountNumber = accountNumber;
                                        backendController.setEmployeeDTO(newEmployee.getValue());
                                    }

                                case 5:
                                    float salary = menu.showEnterFloatMenu("salary");
                                    newEmployee = backendController.getEmployeeDTO(viewID);
                                    if (!newEmployee.getErrorOccurred()) {
                                        backendController.getEmployeeDTO(viewID).getValue().salary = salary;
                                        backendController.setEmployeeDTO(newEmployee.getValue());
                                    }

                                case 6:
                                    LocalDate startDate = menu.showEnterDateMenu();
                                    newEmployee = backendController.getEmployeeDTO(viewID);
                                    if (!newEmployee.getErrorOccurred()) {
                                        backendController.getEmployeeDTO(viewID).getValue().startDate = startDate;
                                        backendController.setEmployeeDTO(newEmployee.getValue());
                                    }

                                case 7:
                                    String trustFund = menu.showEnterStringMenu("trust fund");
                                    newEmployee = backendController.getEmployeeDTO(viewID);
                                    if (!newEmployee.getErrorOccurred()) {
                                        backendController.getEmployeeDTO(viewID).getValue().trustFund = trustFund;
                                        backendController.setEmployeeDTO(newEmployee.getValue());
                                    }

                                case 8:
                                    int freeDays = menu.showEnterIntMenu("free days amount");
                                    newEmployee = backendController.getEmployeeDTO(viewID);
                                    if (!newEmployee.getErrorOccurred()) {
                                        backendController.getEmployeeDTO(viewID).getValue().freeDays = freeDays;
                                        backendController.setEmployeeDTO(newEmployee.getValue());
                                    }

                                case 9:
                                    int sickDays = menu.showEnterIntMenu("sick days amount");
                                    newEmployee = backendController.getEmployeeDTO(viewID);
                                    if (!newEmployee.getErrorOccurred()) {
                                        backendController.getEmployeeDTO(viewID).getValue().sickDays = sickDays;
                                        backendController.setEmployeeDTO(newEmployee.getValue());
                                    }

                                case 10:
                                    List<Role> skills = menu.showEnterRoleList();
                                    newEmployee = backendController.getEmployeeDTO(viewID);
                                    if (!newEmployee.getErrorOccurred()) {
                                        backendController.getEmployeeDTO(viewID).getValue().skills = skills;
                                        backendController.setEmployeeDTO(newEmployee.getValue());
                                    }
                            }

                        }
                        break;

                    case 7:
                        //backendController.viewShift();
                        do {
                            menu.viewShiftMenu(backendController);
                            io.print("To view another shift type anything other than \"continue\": ");
                            continueChanging = io.getString();
                        } while (!continueChanging.equals("continue"));
                        break;

                    case 8:
                        //backendController.defineShiftPersonnel();
                        break;

                    default:
                        break;
                }
            }
            errorOccurred = false;
        }
    }
}
