package PresentationLayer;

import Resources.Preference;

import java.time.LocalDate;

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
                    if(ID.equals("Q"))
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
                            switch (updateIndex){
                                case 1:
                                    String name = menu.showEnterStringMenu("name");
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
