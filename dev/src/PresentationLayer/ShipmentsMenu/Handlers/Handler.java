package PresentationLayer.ShipmentsMenu.Handlers;

import PresentationLayer.ShipmentsMenu.Printer;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Scanner;

public class Handler {
    protected Printer printer;
    protected Scanner scanner;


    public Handler() {
        printer = Printer.getInstance();
        scanner = new Scanner(System.in);
    }

    protected double getDouble() {
        while (true) {
            try {
                double output = Double.parseDouble(scanner.nextLine());
                if (output > 0)
                    return output;
                else
                    printer.error("Weight must be positive");
            } catch (Exception e) {
                printer.error("Enter only positive numbers!");
            }
        }
    }

    protected Date getDate() {
        while (true) {
            try {
                return new SimpleDateFormat("dd/MM/yyyy").parse(scanner.nextLine());
            } catch (Exception e) {
                printer.error("Enter valid date format!");
            }
        }
    }


    protected int getInt() {
        while (true) {
            try {
                return Integer.parseInt(scanner.nextLine());
            } catch (Exception e) {
                printer.error("Enter only natural numbers");
            }
        }
    }

}
