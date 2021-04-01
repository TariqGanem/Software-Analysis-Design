package PresentationLayer;

import java.sql.SQLOutput;
import java.util.Scanner;

public class IOController {
    private static IOController controller = null;
    private Scanner scanner;

    private IOController() {
        scanner = new Scanner(System.in);
    }

    public static IOController getInstance() {
        if (controller == null)
            controller = new IOController();
        return controller;
    }

    public String getString() {
        return scanner.next();
    }

    public int getInt() {
        return scanner.nextInt();
    }

    public void print(String str) {
        System.out.print(str);
    }

    public void println(String str) {
        System.out.println(str);
    }
}
