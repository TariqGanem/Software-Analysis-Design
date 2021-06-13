package PresentationLayer;

import BusinessLayer.SuppliersModule.Application.Facade;
import Enums.ContactMethod;

import java.time.LocalDate;
import java.util.Scanner;

public class MainMenu implements Menu {
    private Menu suppliers = new SuppliersMenu();
    private Menu contracts = new ContractsMenu();
    private Menu orders = new OrdersMenu();
    private StoreMenu store = new StoreMenu();

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
                        store.showSpecificMenu();
                        break;
                    case 5:
                        init();
                        break;
                    case 6:
                        terminate = true;
                        break;
                    default:
                        System.out.println("Enter a number between 1 to 6.");
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
                "4.Manage Store" + "\n" +
                "5.Setup inputs" +"\n"+
                "6.Exit" + "\n");
    }


    private void init(){
        Facade facade = Facade.getInstance();
        //entering two suppliers
        facade.AddSupplier("aaa","aa",12, 050342423, 123,"none","fixed",true);
        facade.AddSupplier("abb","ab",13, 2342343, 1235,"none","single",false);
        //entering a contact persons
        facade.AddContactPerson(12,"abc", ContactMethod.Phone,"05043423234");
        facade.AddMethod(12,"asd", ContactMethod.Email,"mas@gmail.com");
        //entering a quantity report
        //entering items
        facade.AddItem(12,1,"kitkat",3.5,15.6);
        facade.AddItem(12,2,"doritos",5,11);
        //entering discounts
        facade.AddDiscount(12,1,5,5);
        facade.OpenFixedOrder(12, LocalDate.parse("2021-08-08"));
        facade.OpenFixedOrder(13, LocalDate.parse("2021-11-08"));
        facade.AddItemToOrder(1,1, 2);
        facade.AddItemToOrder(1,2, 5);
        facade.submitOrder(1);

    }

}