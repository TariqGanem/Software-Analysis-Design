package PresentationLayer.SuppliersMenu;

import BusinessLayer.SuppliersModule.Application.Facade;
import BusinessLayer.SuppliersModule.Response.Response;
import DTOPackage.ContractDTO;

import java.util.Scanner;

public class ContractsMenu implements Menu {
    private Facade facade = Facade.getInstance();

    @Override
    public void Print_Menu() {
        boolean terminate = false;
        Scanner sc = new Scanner(System.in);
        int company_id;
        int opt;
        while (!terminate) {
            menu_options();
            opt = Integer.parseInt(sc.nextLine());
            switch (opt) {
                case 1:
                    QuantityPrint_Menu();
                    break;
                case 2:
                    ItemPrint_Menu();
                    break;
                case 3:
                    try {
                        System.out.print("Please enter a Supplier's company id: ");
                        company_id = Integer.parseInt(sc.nextLine());
                        Response<ContractDTO> contract = facade.PrintContract(company_id);
                        if (contract.isError())
                            System.out.println(contract.getErrorMessage());
                        else
                            System.out.println(contract.getValue().toString());
                    } catch (Exception e) {
                        System.out.println("Wrong inputs!!!");
                    }
                    break;
                case 4:
                    terminate = true;
                    break;
                default:
                    System.out.println("Enter a number between 1 to 4.");
            }
            System.out.println("");
        }
    }

    private void menu_options() {
        System.out.println("======Contract Menu======" + "\n" +
                "1.Add or Edit Quantity contract" + "\n" +
                "2.Modify item" + "\n" +
                "3.Print contract" + "\n" +
                "4.Return to src.Main menu" + "\n");
    }

    public void QuantityPrint_Menu() {
        boolean terminate = false;
        Scanner sc = new Scanner(System.in);
        int company_id;
        int opt;
        while (!terminate) {
            QuantityMenu_options();
            opt = Integer.parseInt(sc.nextLine());
            switch (opt) {
                case 1:
                    try {
                        System.out.print("Please enter a Supplier's company id: ");
                        company_id = Integer.parseInt(sc.nextLine());
                        System.out.print("Please enter Item's id to discounts: ");
                        int item_id = Integer.parseInt(sc.nextLine());
                        System.out.print("Please enter Item's quantity to discounts: ");
                        int quantity = Integer.parseInt(sc.nextLine());
                        System.out.print("Please enter Item's price to discounts: ");
                        double price = Double.parseDouble(sc.nextLine());
                        Response contract = facade.AddDiscount(company_id, item_id, quantity, price);
                        if (contract.isError())
                            System.out.println(contract.getErrorMessage());
                        else
                            System.out.println("The item discount is added successfully!");
                    } catch (Exception e) {
                        System.out.println("Wrong inputs!!!");
                    }
                    break;
                case 2:
                    try {
                        System.out.print("Please enter a Supplier's company id: ");
                        company_id = Integer.parseInt(sc.nextLine());
                        System.out.print("Please enter Item's id: ");
                        int item_id = Integer.parseInt(sc.nextLine());
                        System.out.print("Please enter Discount's quantity: ");
                        int quantity = Integer.parseInt(sc.nextLine());
                        Response contract = facade.RemoveItemQuantity(company_id, item_id, quantity);
                        if (contract.isError())
                            System.out.println(contract.getErrorMessage());
                        else
                            System.out.println("The item successfully has been removed from the quantity report!");
                    } catch (Exception e) {
                        System.out.println("Wrong inputs!!!");
                    }
                    break;
                case 3:
                    terminate = true;
                    break;
                default:
                    System.out.println("Enter a number between 1 to 3.");
            }
            System.out.println("");
        }
    }

    private void QuantityMenu_options() {
        System.out.println("Please choose a function:" + "\n" +
                "1.Add item discount to report" + "\n" +
                "2.Remove item from report" + "\n" +
                "3.Return back" + "\n");
    }

    public void ItemPrint_Menu() {
        boolean terminate = false;
        Scanner sc = new Scanner(System.in);
        int company_id;
        int opt;
        while (!terminate) {
            ItemMenu_options();
            opt = Integer.parseInt(sc.nextLine());
            switch (opt) {
                case 1:
                    try {
                        System.out.print("Please enter a Supplier's company id: ");
                        company_id = Integer.parseInt(sc.nextLine());
                        System.out.print("Please enter new Item's id: ");
                        int item_id = Integer.parseInt(sc.nextLine());
                        System.out.print("Please enter Item's name: ");
                        String name = sc.nextLine();
                        System.out.print("Please enter Item's price: ");
                        double price = Double.parseDouble(sc.nextLine());
                        System.out.print("Please enter Item's weight(grams): ");
                        double weight = Double.parseDouble(sc.nextLine());
                        Response contract = facade.AddItem(company_id, item_id, name, price, weight);
                        if (contract.isError())
                            System.out.println(contract.getErrorMessage());
                        else
                            System.out.println("The item is successfully added to the contract!");
                    } catch (Exception e) {
                        System.out.println("Wrong inputs!!!");
                    }
                    break;
                case 2:
                    try {
                        System.out.print("Please enter a Supplier's company id: ");
                        company_id = Integer.parseInt(sc.nextLine());
                        System.out.print("Please enter Item's id: ");
                        int item_id = Integer.parseInt(sc.nextLine());
                        Response contract = facade.RemoveItem(company_id, item_id);
                        if (contract.isError())
                            System.out.println(contract.getErrorMessage());
                        else
                            System.out.println("The item has been successfully removed from the contract!");
                    } catch (Exception e) {
                        System.out.println("Wrong inputs!!!");
                    }
                    break;
                case 3:
                    try {
                        System.out.print("Please enter a Supplier's company id: ");
                        company_id = Integer.parseInt(sc.nextLine());
                        System.out.print("Please enter Item's id: ");
                        int item_id = Integer.parseInt(sc.nextLine());
                        System.out.print("Please enter a price: ");
                        double price = Double.parseDouble(sc.nextLine());
                        Response contract = facade.ChangePrice(company_id, item_id, price);
                        if (contract.isError())
                            System.out.println(contract.getErrorMessage());
                        else
                            System.out.println("The item's price has been successfully changed!");
                    } catch (Exception e) {
                        System.out.println("Wrong inputs!!!");
                    }
                    break;
                case 4:
                    terminate = true;
                    break;
                default:
                    System.out.println("Enter a number between 1 to 4.");
            }
            System.out.println("");
        }
    }

    private void ItemMenu_options() {
        System.out.println("Please choose a function: " + "\n" +
                "1.Add Item to contract" + "\n" +
                "2.Remove item from contract" + "\n" +
                "3.Change an item's price" + "\n" +
                "4.Return back" + "\n");
    }
}
