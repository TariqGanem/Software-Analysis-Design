package PresentationLayer.SuppliersMenu;

import APIs.EmployeesSuppliersAPI;
import BusinessLayer.SuppliersModule.Application.Facade;
import BusinessLayer.SuppliersModule.Response.Response;
import DTOPackage.SupplierDTO;
import Resources.ContactMethod;

import java.util.Scanner;

import static Resources.Role.StoreKeeper;
import static Resources.Role.StoreManager;

public class SuppliersMenu implements Menu {
    private final Facade facade = Facade.getInstance();

    @Override
    public void Print_Menu() {
        boolean terminate = false;
        Scanner sc = new Scanner(System.in);
        int opt;
        while (!terminate) {
            System.out.println("Type \"q\" to quit. ");
            System.out.print("Please enter your employee ID: ");
            String id = sc.nextLine();
            if (id == "q")
                break;
            menu_options(id);
            if (new EmployeesSuppliersAPI().hasRole(id, StoreKeeper) || new EmployeesSuppliersAPI().hasRole(id, StoreManager)) {
                opt = Integer.parseInt(sc.nextLine());
                switch (opt) {
                    case 1:
                        try {
                            System.out.print("Enter Supplier's name: ");
                            String supplier_name = sc.nextLine();
                            System.out.print("Enter Manufactures name: ");
                            String Manifactur_name = sc.nextLine();
                            System.out.print("Enter company id: ");
                            int company_id = Integer.parseInt(sc.nextLine());
                            System.out.print("Enter phone: ");
                            String phone = sc.nextLine();
                            System.out.print("Enter bank account: ");
                            int bank_account = Integer.parseInt(sc.nextLine());
                            System.out.print("Enter payment conditions: ");
                            String payment_conditions = sc.nextLine();
                            System.out.print("Enter contract type: ");
                            String contract_type = sc.nextLine();
                            System.out.print("Enter self pickup(true/false): ");
                            boolean selfpickup = Boolean.parseBoolean(sc.nextLine());
                            Response supplier = facade.AddSupplier(supplier_name, Manifactur_name, company_id, phone, bank_account,
                                    payment_conditions, contract_type, selfpickup);
                            if (supplier.isError())
                                System.out.println(supplier.getErrorMessage());
                            else
                                System.out.println("The supplier has been added successfully!");
                        } catch (Exception e) {
                            System.out.println("Wrong inputs!!!");
                        }
                        break;
                    case 2:
                        try {
                            System.out.print("Enter company id: ");
                            int company_id = Integer.parseInt(sc.nextLine());
                            Response supplier = facade.RemoveSupplier(company_id);
                            if (supplier.isError())
                                System.out.println(supplier.getErrorMessage());
                            else
                                System.out.println("The supplier has been removed successfully!");
                        } catch (Exception e) {
                            System.out.println("Wrong inputs!!!");
                        }
                        break;
                    case 3:
                        EditSupplierMenu();
                        break;
                    case 4:
                        try {
                            System.out.print("Enter company id: ");
                            int company_id = Integer.parseInt(sc.nextLine());
                            Response<SupplierDTO> supplier = facade.PrintSupplierCard(company_id);
                            if (supplier.isError())
                                System.out.println(supplier.getErrorMessage());
                            else
                                System.out.println(supplier.getValue().toString());
                        } catch (Exception e) {
                            System.out.println("Wrong inputs!!!");
                        }
                        break;
                    case 5:
                        terminate = true;
                        break;
                    default:
                        System.out.println("Enter a number between 1 to 5.");
                }
            } else {
                opt = Integer.parseInt(sc.nextLine());
                switch (opt) {
                    case 1:
                        try {
                            System.out.print("Enter company id: ");
                            int company_id = Integer.parseInt(sc.nextLine());
                            Response<SupplierDTO> supplier = facade.PrintSupplierCard(company_id);
                            if (supplier.isError())
                                System.out.println(supplier.getErrorMessage());
                            else
                                System.out.println(supplier.getValue().toString());
                        } catch (Exception e) {
                            System.out.println("Wrong inputs!!!");
                        }
                        break;
                    case 2:
                        terminate = true;
                        break;
                    default:
                        System.out.println("Enter a number between 1 to 2.");
                }
            }
            System.out.println("");
        }
    }

    private void menu_options(String id) {
        if (new EmployeesSuppliersAPI().hasRole(id, StoreKeeper) || new EmployeesSuppliersAPI().hasRole(id, StoreManager)) {
            System.out.println("======Supplier Menu======" + "\n" +
                    "1.Add new Supplier" + "\n" +
                    "2.Delete a current Supplier" + "\n" +
                    "3.Edit a current Supplier" + "\n" +
                    "4.Print Supplier card" + "\n" +
                    "5.Return to Suppliers menu" + "\n");
        } else {
            System.out.println("======Supplier Menu======" + "\n" +
                    "1.Print Supplier card" + "\n" +
                    "2.Return to Suppliers menu" + "\n");
        }
    }

    private void EditSupplierMenu() {
        boolean terminate = false;
        Scanner sc = new Scanner(System.in);
        int opt;
        while (!terminate) {
            SupplierMenu_options();
            opt = Integer.parseInt(sc.nextLine());
            switch (opt) {
                case 1:
                    ContactMenu();
                    break;
                case 2:
                    try {
                        System.out.print("Enter company id: ");
                        int company_id = Integer.parseInt(sc.nextLine());
                        System.out.print("Enter new payment conditions: ");
                        String payment_conditions = sc.nextLine();
                        Response supplier = facade.ChangePaymentConditions(company_id, payment_conditions);
                        if (supplier.isError())
                            System.out.println(supplier.getErrorMessage());
                        else
                            System.out.println("The supplier's payment conditions has been changed successfully!");
                    } catch (Exception e) {
                        System.out.println("Wrong inputs!!!");
                    }
                    break;
                case 3:
                    try {
                        System.out.print("Enter company id: ");
                        int company_id = Integer.parseInt(sc.nextLine());
                        System.out.print("Enter new Bank account: ");
                        int bank_account = Integer.parseInt(sc.nextLine());
                        Response supplier = facade.ChangeBankAccount(company_id, bank_account);
                        if (supplier.isError())
                            System.out.println(supplier.getErrorMessage());
                        else
                            System.out.println("The supplier's bank account has been changed successfully!");
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

    private void SupplierMenu_options() {
        System.out.println("Please choose a function:" + "\n" +
                "1.Add,Edit or Print a contact person" + "\n" +
                "2.Change payment conditions" + "\n" +
                "3.Change Supplier Bank account number" + "\n" +
                "4.Return back" + "\n");
    }

    private void ContactMenu() {
        boolean terminate = false;
        Scanner sc = new Scanner(System.in);
        int opt;
        int company_id;
        while (!terminate) {
            ContactMenu_options();
            opt = Integer.parseInt(sc.nextLine());
            switch (opt) {
                case 1:
                    try {
                        System.out.print("Enter company id: ");
                        company_id = Integer.parseInt(sc.nextLine());
                        System.out.print("Enter person's name: ");
                        String name = sc.nextLine();
                        ContactMethod method = null;
                        while (true) {
                            ContactMethods_options();
                            int choose = Integer.parseInt(sc.nextLine());
                            method = parse(choose);
                            if (method != null)
                                break;
                        }
                        System.out.print("Enter the method's data: ");
                        String method_data = sc.nextLine();
                        Response supplier = facade.AddContactPerson(company_id, name, method, method_data);
                        if (supplier.isError())
                            System.out.println(supplier.getErrorMessage());
                        else
                            System.out.println("The contact person has been added successfully!");
                        break;
                    } catch (Exception e) {
                        System.out.println("Wrong inputs!!!");
                    }
                case 2:
                    try {
                        System.out.print("Enter company id: ");
                        company_id = Integer.parseInt(sc.nextLine());
                        System.out.print("Enter person's name: ");
                        String ContactPerson_name = sc.nextLine();
                        Response supplier = facade.RemoveContact(company_id, ContactPerson_name);
                        if (supplier.isError())
                            System.out.println(supplier.getErrorMessage());
                        else
                            System.out.println("The contact person has been removed successfully!");
                    } catch (Exception e) {
                        System.out.println("Wrong inputs!!!");
                    }
                    break;
                case 3:
                    try {
                        System.out.print("Enter company id: ");
                        company_id = Integer.parseInt(sc.nextLine());
                        System.out.print("Enter the person's name: ");
                        String person_name = sc.nextLine();
                        ContactMethod method = null;
                        while (true) {
                            ContactMethods_options();
                            int choose = Integer.parseInt(sc.nextLine());
                            method = parse(choose);
                            if (method != null)
                                break;
                        }
                        System.out.print("Enter the method's data: ");
                        String method_data = sc.nextLine();
                        Response supplier = facade.AddMethod(company_id, person_name, method, method_data);
                        if (supplier.isError())
                            System.out.println(supplier.getErrorMessage());
                        else
                            System.out.println("The new method has been added successfully!");
                    } catch (Exception e) {
                        System.out.println("Wrong inputs!!!");
                    }
                    break;
                case 4:
                    try {
                        System.out.print("Enter company id: ");
                        company_id = Integer.parseInt(sc.nextLine());
                        System.out.print("Enter the person's name: ");
                        String person_name = sc.nextLine();
                        ContactMethod method = null;
                        while (true) {
                            ContactMethods_options();
                            int choose = Integer.parseInt(sc.nextLine());
                            method = parse(choose);
                            if (method != null)
                                break;
                        }
                        Response supplier = facade.RemoveMethod(company_id, person_name, method);
                        if (supplier.isError())
                            System.out.println(supplier.getErrorMessage());
                        else
                            System.out.println("The contact person's method has been removed successfully!");
                    } catch (Exception e) {
                        System.out.println("Wrong inputs!!!");
                    }
                    break;
                case 5:
                    try {
                        System.out.print("Enter company id: ");
                        company_id = Integer.parseInt(sc.nextLine());
                        System.out.print("Enter the person's name: ");
                        String person_name = sc.nextLine();
                        ContactMethod method = null;
                        while (true) {
                            ContactMethods_options();
                            int choose = Integer.parseInt(sc.nextLine());
                            method = parse(choose);
                            if (method != null)
                                break;
                        }
                        System.out.print("Enter the method's data: ");
                        String method_data = sc.nextLine();
                        Response supplier = facade.EditMethod(company_id, person_name, method, method_data);
                        if (supplier.isError())
                            System.out.println(supplier.getErrorMessage());
                        else
                            System.out.println("The contact person's method has been edited successfully!");
                    } catch (Exception e) {
                        System.out.println("Wrong inputs!!!");
                    }
                    break;
                case 6:
                    try {
                        System.out.print("Enter company id: ");
                        company_id = Integer.parseInt(sc.nextLine());
                        Response<SupplierDTO> supplier = facade.PrintAllContacts(company_id);
                        if (supplier.isError())
                            System.out.println(supplier.getErrorMessage());
                        else
                            System.out.println(supplier.getValue().AllContacts());
                    } catch (Exception e) {
                        System.out.println("Wrong inputs!!!");
                    }
                    break;
                case 7:
                    terminate = true;
                    break;
                default:
                    System.out.println("Enter a number between 1 to 6.");
            }
            System.out.println("");
        }
    }

    private void ContactMenu_options() {
        System.out.println("Please choose a function:" + "\n" +
                "1.Add new Contact Person" + "\n" +
                "2.Delete a Contact Person" + "\n" +
                "3.Add a new method" + "\n" +
                "4.Remove a method" + "\n" +
                "5.Edit a Contact Person" + "\n" +
                "6.Print all contacts" + "\n" +
                "7.Return back" + "\n");
    }

    private void ContactMethods_options() {
        System.out.println(
                "Please choose a contact method: " + "\n" +
                        "1.Email" + "\n" +
                        "2.Mobile" + "\n" +
                        "3.Phone" + "\n" +
                        "4.Fax" + "\n"
        );
    }

    private ContactMethod parse(int input) {
        switch (input) {
            case 1:
                return ContactMethod.Email;

            case 2:
                return ContactMethod.Mobile;

            case 3:
                return ContactMethod.Phone;

            case 4:
                return ContactMethod.Fax;

            default:
                System.out.println("Choose a number from 1 to 4, please!");
        }
        return null;
    }
}
