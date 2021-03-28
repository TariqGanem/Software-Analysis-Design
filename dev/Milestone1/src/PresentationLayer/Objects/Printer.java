package PresentationLayer.Objects;

import PresentationLayer.Menu.MenuItems.MenuItem;
import java.util.List;
import java.util.Scanner;

public class Printer {
    private static Printer instance = null;
    private static Scanner scanner;

    private Printer() {
        scanner = new Scanner(System.in);
    }

    public static Printer getInstance() {
        if (instance == null)
            instance = new Printer();
        return instance;
    }

    /**
     * Welcome message upon starting
     */
    public void printBanner() {
        System.out.println("\n" +
                " _    _      _                            _____       _____                             _               \n" +
                "| |  | |    | |                          |_   _|     /  ___|                           | |              \n" +
                "| |  | | ___| | ___ ___  _ __ ___   ___    | | ___   \\ `--. _   _ _ __  _ __   ___ _ __| |     ___  ___ \n" +
                "| |/\\| |/ _ \\ |/ __/ _ \\| '_ ` _ \\ / _ \\   | |/ _ \\   `--. \\ | | | '_ \\| '_ \\ / _ \\ '__| |    / _ \\/ _ \\\n" +
                "\\  /\\  /  __/ | (_| (_) | | | | | |  __/   | | (_) | /\\__/ / |_| | |_) | |_) |  __/ |  | |___|  __/  __/\n" +
                " \\/  \\/ \\___|_|\\___\\___/|_| |_| |_|\\___|   \\_/\\___/  \\____/ \\__,_| .__/| .__/ \\___|_|  \\_____/\\___|\\___|\n" +
                "                                                                 | |   | |                              \n" +
                "                                                                 |_|   |_|                              \n");
    }

    public void viewOptions(List<MenuItem> items) {
        System.out.println("\nPlease select an option from the Menu:\n");
        for (int i = 0; i < items.size(); i++) {
            //TODO
        }
        System.out.println();
    }
}
