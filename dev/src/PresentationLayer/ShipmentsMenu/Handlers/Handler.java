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
        scanner.useDelimiter("\n");
    }

    protected double getDouble() {
        while (true) {
            try {
                Double output = Double.parseDouble(scanner.next());
                if (output > 0)
                    return Double.parseDouble(scanner.next());
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
                return new SimpleDateFormat("dd/MM/yyyy").parse(scanner.next());
            } catch (Exception e) {
                printer.error("Enter valid date format!");
            }
        }
    }


    protected int getInt() {
        while (true) {
            try {
                return Integer.parseInt(scanner.next());
            } catch (Exception e) {
                printer.error("Enter only natural numbers");
            }
        }
    }

}
