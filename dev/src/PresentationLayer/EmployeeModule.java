package PresentationLayer;

import Resources.Preference;

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
            if (ID == null)
                ID = menu.loginMenu(backendController);

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
                        backendController.viewMyProfile();
                        break;

                    case 2:
                        String continueToViewShift = "";
                        backendController.viewMyShifts();
                        boolean displayShiftDetails = menu.displaySpecificEmployees();
                        if (displayShiftDetails) {
                            do {
                                menu.showSpecificShift(backendController);
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
                        break;

                    case 5:
                        //backendController.addEmployee();
                        break;

                    case 6:
                        //backendController.viewEmployee();
                        break;

                    case 7:
                        //backendController.viewShift();
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
