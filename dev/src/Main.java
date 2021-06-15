import DataAccessLayer.dbMaker;
import PresentationLayer.MainMenu;

import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        System.out.println("THE FOLLOWING QUESTION IS FOR THE TESTER");
        System.out.println("If you want to load data into the system please type 1, to continue normally type any other number.");
        Scanner scanner = new Scanner(System.in);
        int input = 0;
        try {
            input = Integer.parseInt(scanner.nextLine());
        } catch (Exception ignored) {
        }
        if (input == 1) {
            new dbMaker().initialize();
            new initializer().initialize();
        }
        System.out.println();
        //Running the application [Shipments/Employees/Suppliers/Stock Modules]
        MainMenu app = MainMenu.getInstance();
        app.run();

    }
}
