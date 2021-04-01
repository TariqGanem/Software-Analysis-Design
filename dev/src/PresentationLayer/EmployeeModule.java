package PresentationLayer;

public class EmployeeModule {

    public static void main(String[] args) {
        BackendController backendController = new BackendController();
        IOController io = IOController.getInstance();
        String ID = null;
        int option = -1;
        boolean errorOccurred = false, isManager = false;

        io.println("Hello And Welcome to Super-Lee!\n");
        while (true) {
            if(ID == null)
                ID = loginMenu(backendController);

            if (ID.equals("Q"))
                break;

            isManager = backendController.getIsManager();

            io.println("Main Menu:");
            io.println("----------------------");
            showEmployeeMenu();
            if (isManager)
                showManagerMenu();
            io.println("Type 0 to quit");
            io.println("----------------------");

            do {
                io.print("Choose an option: ");
                option = io.getInt();
            } while (option < 0 || (!isManager && option >= 4) || (isManager && option >= 9));

            switch (option) {
                case 0: //TODO: CREATE LOGOUT
                    ID = null;
                    break;

                case 1:
                    backendController.viewMyProfile();
                    break;

                case 2:
                    //backendController.viewMyShifts();
                    break;

                case 3:
                    //backendController.changePreferences();
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
    }

    public static String loginMenu(BackendController backendController){
        IOController io = IOController.getInstance();
        String ID = null;
        do {
            io.println("Type \"Q\" to quit.");
            io.print("Please enter your employee ID: ");
            ID = io.getString();
            if(ID.equals("Q"))
                break;
        } while (backendController.login(ID));
        return ID;
    }

    public static void showEmployeeMenu() {
        IOController io = IOController.getInstance();
        io.println("1. View my profile");
        io.println("2. View my shifts");
        io.println("3. Change my preferences");
    }

    public static void showManagerMenu() {
        IOController io = IOController.getInstance();
        io.println("4. Add new shift");
        io.println("5. Add new employee");
        io.println("6. View employee profile");
        io.println("7. View shift information");
        io.println("8. Define shift personnel");
    }

}
