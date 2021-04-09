package Presentation_Layer;

import java.util.Scanner;

public class MainMenu implements Menu {
    private Menu suppliers = new SuppliersMenu();
    private Menu contracts = new ContractsMenu();
    private Menu orders = new OrdersMenu();
    @Override
    public void Print_Menu() {
        try {
            boolean terminate = false;
            Scanner sc = new Scanner(System.in);
            int opt;
            while (!terminate) {
                menu_options();
                opt = Integer.parseInt(sc.nextLine());
                switch (opt) {
                    case 1:
                        suppliers.Print_Menu();
                        break;
                    case 2:
                        contracts.Print_Menu();
                        break;
                    case 3:
                        orders.Print_Menu();
                        break;
                    case 4:

                        break;

                        case 5:
                        terminate = true;
                        break;
                    default:
                        System.out.println("Enter a number between 1 to 4.");
                }
            }
        }
        catch (Exception exception){
            System.out.println("Wrong input, try again: ");
            Print_Menu();
        }

    }

    private void menu_options() {
        System.out.println("======Main Menu======" + "\n" +
                "1.Manage Suppliers" + "\n" +
                "2.Manage Contracts" + "\n" +
                "3.Manage Orders" + "\n" +
                "4.Exit" + "\n");
    }

}