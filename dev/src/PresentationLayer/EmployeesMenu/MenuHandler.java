package PresentationLayer.EmployeesMenu;

import Resources.Preference;
import Resources.Role;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class MenuHandler {

    IOController io = IOController.getInstance();

    public void showMainMenu(boolean isManager) {
        io.println("Main Menu:");
        io.println("----------------------");
        showEmployeeMenu();
        if (isManager)
            showManagerMenu();
        io.println("Type 0 to logout");
        io.println("----------------------");
    }

    public String loginMenu() {
        String ID = null;
        io.println("Type \"q\" to quit.");
        io.print("Please enter your employee ID: ");
        ID = io.getString();
        return ID;
    }

    public void showEmployeeMenu() {
        io.println("1. View my profile");
        io.println("2. View my shifts");
        io.println("3. Change my preferences");
    }

    public void showManagerMenu() {
        io.println("4. Add new shift");
        io.println("5. View shift information");
        io.println("6. View employee profile");
        io.println("7. Add new employee");
        io.println("8. Define shift personnel");
    }

    public boolean displaySpecificEmployees() {
        String YES_NO = null;
        io.println("Would you like to view the employees in a specific shift?");
        do {
            io.print("Type \"y\" or \"n\": ");
            YES_NO = io.getString();
        } while (!YES_NO.equals("y") && !YES_NO.equals("n"));
        return YES_NO.equals("y");
    }

    public LocalDate showEnterDateMenu() {
        int year, month, day;
        boolean success = false;
        LocalDate date = null;
        do {
            io.print("Please enter a year: ");
            year = io.getInt();
            io.print("Please enter a month: ");
            month = io.getInt();
            io.print("Please enter a day: ");
            day = io.getInt();
            try {
                date = LocalDate.of(year, month, day);
                success = true;
            } catch (Exception ignored) {
            }
        } while (!success);
        return date;
    }

    public boolean showEnterMorningEvening() {
        String answer;

        io.println("Is the shift in the morning or in the evening?");
        do {
            io.print("Type \"m\" or \"e\": ");
            answer = io.getString();
        } while (!answer.equals("m") && !answer.equals("e"));

        return answer.equals("m");
    }

    public int showPreferenceMenu() {
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

        io.println("What action would you like to perform?");
        io.println("----------------------");
        io.println("1) Assign employee to shift");
        io.println("2) Remove employee from shift");
        io.println("3) Delete shift");
        io.println("Type 0 to quit");
        io.println("----------------------");

        int answer;
        do {
            io.print("Enter a number between 0 and 3: ");
            answer = io.getInt();
        } while (answer < 0 || answer > 3);

        return answer;
    }

    public int showUpdateEmployeeMenu() {

        io.println("What information do you want to change?");
        io.println("----------------------");
        io.println("1) Name");
        io.println("2) ID");
        io.println("3) Bank id");
        io.println("4) Branch id");
        io.println("5) Account Number");
        io.println("6) Salary");
        io.println("7) Start Date");
        io.println("8) Trust Fund");
        io.println("9) Free Days");
        io.println("10) Sick Days");
        io.println("11) Skills");
        io.println("Type 0 to quit");
        io.println("----------------------");

        int answer;
        do {
            io.print("Enter a number between 0 and 11: ");
            answer = io.getInt();
        } while (answer < 0 || answer > 11);

        return answer;
    }

    public String showEnterStringMenu(String whatToEnter) {
        io.print("Please enter a new " + whatToEnter + ": ");
        return io.getString();
    }

    public int showEnterIntMenu(String whatToEnter) {
        io.print("Please enter a new " + whatToEnter + ": ");
        return io.getInt();
    }

    public float showEnterFloatMenu(String whatToEnter) {
        io.print("Please enter a new " + whatToEnter + ": ");
        return io.getFloat();
    }

    public List<Role> showEnterRoleList() {
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
            if (answer >= 1 && answer <= Role.values().length) {
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
                io.print("What is your preference for " + whatDayIsIt(i).toUpperCase());
                if (j == 0) {
                    io.println(" MORNING? ");
                } else {
                    io.println(" EVENING?");
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

    public void showAvailableEmployeesMenu(Map<String, String> availableEmployees, int startIndex) {
        String[] ids = new String[availableEmployees.size()];
        int next = startIndex;
        for (String id : availableEmployees.keySet()) {
            ids[next - startIndex] = id;
            next++;
        }
        for (int i = 0; i < ids.length; i++) {
            io.println(startIndex + ") (" + ids[i] + ") " + availableEmployees.get(ids[i]));
            startIndex++;
        }
    }

    public String showShiftPositionsMenu(Map<Role, List<String>> map) {
        List<String> ret = new ArrayList<>();
        for (List<String> list : map.values())
            ret.addAll(list);
        int i = 1;
        for (String id : ret) {
            io.println(i + ") " + id);
            i++;
        }
        int answer;
        if (ret.size() == 0)
            return null;
        do {
            io.print("enter a number between 1 and " + ret.size() + ": ");
            answer = io.getInt();
        } while (answer < 1 || answer > ret.size());
        return ret.get(answer - 1);
    }

    public Role showShiftPersonnelMenu(Map<Role, Integer> map, String msg) {
        int i = 1;
        Role[] roles = map.keySet().toArray(new Role[0]);
        for (Role role : roles) {
            io.println(i + ") " + role.toString() + " - " + map.get(role) + " Are needed.");
            i++;
        }
        int index;
        do {
            io.print(msg + "\nEnter a number between 1 and " + Role.values().length + ": ");
            index = io.getInt();
        } while (index < 1 || index > Role.values().length);
        return roles[index - 1];
    }

    public boolean showSpecificDateMenu() {
        io.println("1) Specific date");
        io.println("2) View shifts x days from now");
        int num;
        do {
            io.print("enter a number between 1 and 2: ");
            num = io.getInt();
        } while (num != 1 & num != 2);
        io.println("");
        return num == 1;
    }

    public int showFutureShiftsMenu(List<String> desc) {
        int i = 1;
        for (String str : desc) {
            io.println(i + ") " + str);
            i++;
        }
        int num;
        do {
            io.print("enter a number between 1 and " + desc.size() + ": ");
            num = io.getInt();
        } while (num < 1 | num > desc.size());
        return num - 1;
    }

    public boolean showConfirmationMenu(String msg) {
        boolean confirmation = true;
        io.println("----------------------");
        io.println(msg);
        io.println("ARE YOU SURE? \n1) yes\n2) no");
        io.println("----------------------");
        int ans;
        do {
            io.println("Enter 1 or 2 (yes/no): ");
            ans = io.getInt();
        } while (ans != 1 & ans != 2);
        if (ans == 2) {
            confirmation = false;
            io.println("Cancelled!");
        }
        return confirmation;
    }

    public boolean askToProceed(String msg) {
        io.print("For the \"Main Menu\" type \"c\". To " + msg + " type anything else: ");
        String continueToAct = io.getString();
        return !continueToAct.equals("c");
    }
}
