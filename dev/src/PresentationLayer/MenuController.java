package PresentationLayer;

import Resources.Preference;
import Resources.Role;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class MenuController {

    public void showMainMenu(boolean isManager) {
        IOController io = IOController.getInstance();
        io.println("Main Menu:");
        io.println("----------------------");
        showEmployeeMenu();
        if (isManager)
            showManagerMenu();
        io.println("Type 0 to quit");
        io.println("----------------------");
    }

    public String loginMenu() {
        IOController io = IOController.getInstance();
        String ID = null;
        io.println("Type \"Q\" to quit.");
        io.print("Please enter your employee ID: ");
        ID = io.getString();
        return ID;
    }

    public void showEmployeeMenu() {
        IOController io = IOController.getInstance();
        io.println("1. View my profile");
        io.println("2. View my shifts");
        io.println("3. Change my preferences");
    }

    public void showManagerMenu() {
        IOController io = IOController.getInstance();
        io.println("4. Add new shift");
        io.println("5. Add new employee");
        io.println("6. View employee profile");
        io.println("7. View shift information");
        io.println("8. Define shift personnel");
    }

    public boolean displaySpecificEmployees() {
        IOController io = IOController.getInstance();
        String YES_NO = null;
        io.println("Would you like to view the employees in a specific shift?");
        do {
            io.print("Type \"y\" or \"n\": ");
            YES_NO = io.getString();
        } while (!YES_NO.equals("y") && !YES_NO.equals("n"));
        return YES_NO.equals("y");
    }

    public LocalDate showEnterDateMenu() {
        IOController io = IOController.getInstance();
        int year, month, day;
        LocalDate date = null;

        io.print("Please enter a year: ");
        year = io.getInt();
        io.print("Please enter a month: ");
        month = io.getInt();
        io.print("Please enter a day: ");
        day = io.getInt();

        try {
            date = LocalDate.of(year, month, day);
        } catch (Exception ignored) {
            io.println("You entered illegal values for a date, you may try again.");
        }

        return date;
    }

    public boolean showEnterMorningEvening() {
        IOController io = IOController.getInstance();
        String answer;

        io.println("Is the shift in the morning or in the evening?");
        do {
            io.print("Type \"m\" or \"e\": ");
            answer = io.getString();
        } while (!answer.equals("m") && !answer.equals("e"));

        return answer.equals("m");
    }

    public int showPreferenceMenu() {
        IOController io = IOController.getInstance();
        io.println("Please pick one of the following preferences:");
        int i = 1;
        for (Preference preference : Preference.values()) {
            io.println(i + ") " + preference.name());
            i++;
        }
        int prefIndex;
        do {
            io.print("Please pick a number between 1 and " + (i - 1) + ": ");
            prefIndex = io.getInt();
        } while (prefIndex <= 0 || prefIndex >= i);
        return prefIndex - 1;
    }

    public int showUpdateShiftMenu() {
        IOController io = IOController.getInstance();

        io.println("What action would you like to perform?");
        io.println("1) Assign an employee");
        io.println("2) Remove an employee");
        io.println("3) Delete shift");
        io.println("4) Display assigned employees");

        int answer;
        do {
            io.print("Enter a number between 1 and 4: ");
            answer = io.getInt();
        } while (answer < 1 || answer > 4);

        return answer;
    }

    public int showUpdateEmployeeMenu() {
        IOController io = IOController.getInstance();

        io.println("What information do you want to change?");
        io.println("1) Name");
        io.println("2) Bank id");
        io.println("3) Branch id");
        io.println("4) Account Number");
        io.println("5) Salary");
        io.println("6) Start Date");
        io.println("7) Trust Fund");
        io.println("8) Free Days");
        io.println("9) Sick Days");
        io.println("10) Skills");

        int answer;
        do {
            io.print("Enter a number between 1 and 10: ");
            answer = io.getInt();
        } while (answer < 1 || answer > 11);

        return answer;
    }

    public String showEnterStringMenu(String whatToEnter) {
        IOController io = IOController.getInstance();

        io.print("Please enter a new " + whatToEnter + ": ");

        return io.getString();
    }

    public int showEnterIntMenu(String whatToEnter) {
        IOController io = IOController.getInstance();

        io.print("Please enter a new " + whatToEnter + ": ");

        return io.getInt();
    }

    public float showEnterFloatMenu(String whatToEnter) {
        IOController io = IOController.getInstance();

        io.print("Please enter a new " + whatToEnter + ": ");

        return io.getFloat();
    }

    public List<Role> showEnterRoleList() {
        IOController io = IOController.getInstance();
        List<Role> skills = new ArrayList<>();
        int answer = 0;

        int i = 1;
        for (Role role : Role.values()) {
            io.println(i + ") " + role.name());
            i++;
        }
        io.println("Please choose the roles of the employee:");

        do {
            io.print("Pick a number between 1 and " + Role.values().length + ": ");
            answer = io.getInt();
            if (answer >= 1 && answer < Role.values().length) {
                skills.add(Role.values()[answer - 1]);
            }
            io.println("Type \"0\" to stop adding roles.");
        } while (answer != 0);

        return skills;
    }

    public Preference[][] showEnterPreferenceArray() {
        Preference[][] timeFrames = new Preference[7][2];
        timeFrames[5][1] = null;
        timeFrames[6][0] = null;
        timeFrames[6][1] = null;
        IOController io = IOController.getInstance();

        io.println("Please pick one of the following preferences for each shift presented to you:");
        int p = 1;
        for (Preference preference : Preference.values()) {
            io.println(p + ") " + preference.name());
            p++;
        }

        for (int i = 0; i < 7; i++) {
            for (int j = 0; j < 2; j++) {
                if ((i == 5 && j == 1) || i == 6)
                    continue;
                io.print("What is your preference for " + whatDayIsIt(i));
                if (j == 0) {
                    io.println(" morning? ");
                } else {
                    io.println(" evening?");
                }
                int index = 0;
                do {
                    io.print("Please enter a number between 1 and " + Preference.values().length + ": ");
                    index = io.getInt();
                } while (index <= 0 || index > Preference.values().length);
                timeFrames[i][j] = Preference.values()[index - 1];
            }
        }

        return timeFrames;
    }

    private String whatDayIsIt(int day) {
        switch (day) {
            case 0:
                return "sunday";
            case 1:
                return "monday";
            case 2:
                return "tuesday";
            case 3:
                return "wednesday";
            case 4:
                return "thursday";
            case 5:
                return "friday";
            case 6:
                return "saturday";
            default:
                return "not a day of the week :(";
        }
    }

    public Role showRoleMenu() {
        IOController io = IOController.getInstance();
        int i = 1;
        for (Role role : Role.values()) {
            io.println(i + ") " + role.toString());
            i++;
        }
        int index;
        do {
            io.print("Enter a number between 1 to " + Role.values().length + ": ");
            index = io.getInt();
        }while(index < 1 || index > Role.values().length);
        return Role.values()[index - 1];
    }

    public String showAvailableEmployeesMenu(Map<String,String> availableEmployees) {
        IOController io = IOController.getInstance();
        String[] ids = (String[])availableEmployees.keySet().toArray();
        for (int i = 0; i < ids.length; i++)
            io.println((i+1) + ") " + ids[i] + " " + availableEmployees.get(ids[i]));
        int index;
        do {
            io.print("Enter a number between 1 to " + ids.length);
            index = io.getInt();
        }while(index < 1 || index > ids.length);
        return ids[index - 1];
    }
}
