package PresentationLayer;

import org.omg.Messaging.SYNC_WITH_TRANSPORT;

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
        String value = "";
        Integer ret = null;
        do {
            try {
                value = scanner.next();
                ret = Integer.parseInt(value);
            } catch (Exception ignored) {
                System.out.println("Please enter an integer.");
            }
        } while (ret == null);
        return ret;
    }

    public void print(String str) {
        System.out.print(str);
    }

    public void println(String str) {
        System.out.println(str);
    }
}
