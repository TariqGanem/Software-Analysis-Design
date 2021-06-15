package PresentationLayer.SuppliersMenu;

import BusinessLayer.SuppliersModule.Application.Facade;
import Resources.ContactMethod;

import java.util.Scanner;

public class SupplierMenu implements Menu {
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
//                        init();
//                        break;
//                    case 5:
                        terminate = true;
                        break;
                    default:
                        System.out.println("Enter a number between 1 to 4.");
                }
            }
        } catch (Exception exception) {
            System.out.println("Wrong input, try again: ");
            Print_Menu();
        }

    }

    private void menu_options() {
        System.out.println("======Main Menu======" + "\n" +
                "1.Manage Suppliers" + "\n" +
                "2.Manage Contracts" + "\n" +
                "3.Manage Orders" + "\n" +
//                "4.Setup inputs" +"\n"+
                "4.Exit" + "\n");
    }


    public void init() {
        Facade facade = Facade.getInstance();
        //entering two suppliers
        facade.AddSupplier("aaa", "aa", 12, "050342423", 123, "none", "fixed", true);
        facade.AddSupplier("abb", "ab", 13, "2342343", 1235, "none", "single", false);
        //entering a contact persons
        facade.AddContactPerson(12, "abc", ContactMethod.Phone, "05043423234");
        facade.AddMethod(12, "asd", ContactMethod.Email, "mas@gmail.com");
        //entering a quantity report
        //entering items
        facade.AddItem(12, 1, "kitkat", 3.5, 15.6);
        facade.AddItem(12, 2, "doritos", 5, 11);
        //entering discounts
        facade.AddDiscount(12, 1, 5, 5);
    }

}