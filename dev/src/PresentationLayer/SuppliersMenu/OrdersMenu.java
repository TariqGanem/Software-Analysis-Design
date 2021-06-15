package PresentationLayer.SuppliersMenu;

import BusinessLayer.SuppliersModule.Application.Facade;
import BusinessLayer.SuppliersModule.Response.Response;

import java.time.LocalDate;
import java.util.Scanner;

public class OrdersMenu implements Menu {
    private final Facade facade = Facade.getInstance();

    @Override
    public void Print_Menu() {
        try {
            boolean terminate = false;
            Scanner sc = new Scanner(System.in);
            int opt;
            while (!terminate) {
                ordersMenu_options();
                opt = sc.nextInt();
                switch (opt) {
                    case 1:
                        createOrderMenu();
                        break;
                    case 2:
                        submitOrderMenu();
                        break;
                    case 3:
                        completeOrderMenu();
                        break;
                    case 4:
                        cancelOrderMenu();
                        break;

                    case 5:
                        System.out.println("Enter id of order you want to manage: ");
                        manageOrderMenu(sc.nextInt());
                        break;


                    case 6:
                        terminate = true;
                        break;
                    default:
                        System.out.println("Enter a number between 1 to 6!");
                }
            }
        } catch (Exception exception) {
            System.out.println("Wrong input, try again: ");
            Print_Menu();
        }
    }

    private void ordersMenu_options() {
        System.out.println(
                "======Orders Menu======" + "\n" +
                        "1.Open new fixed order" + "\n" +
                        "2.Submit order" + "\n" +
                        "3.Complete single order" + "\n" +
                        "4.Cancel single order" + "\n" +
                        "5.Manage order" + "\n" +
                        "6.Back"
        );
    }

    private void createOrderMenu() {  // done
        Scanner sc = new Scanner(System.in);
        int supplierID = -1;
        try {
            System.out.println("Enter supplier's id to ask order from: ");
            supplierID = sc.nextInt();
            if (facade.isSupplier(supplierID).getValue()) {
                System.out.println("Enter due date of the order in format such as 2007-12-03: ");
                LocalDate dueDate = LocalDate.parse(sc.next());
                Response response = facade.OpenFixedOrder(supplierID, dueDate);
                if (response.isError()) {
                    System.out.println(response.getErrorMessage());
                } else {
                    System.out.println("Order was opened successfully!\n");
                }
                System.out.println("Press enter to continue");
                try {
                    System.in.read();
                } catch (Exception e) {
                }
            } else {
                System.out.println("Enter a correct order's id or 0 to return back please: ");
                if (supplierID == 0) {
                    return;
                }
                createOrderMenu();
            }
        } catch (Exception exception) {
            System.out.println("Wrong input, try again: ");
            createOrderMenu();
        }

    }

    private void submitOrderMenu() {  // done
        int orderID = -1;
        Scanner sc = new Scanner(System.in);
        try {


            System.out.println("Enter order's id to submit: \n");
            orderID = sc.nextInt();
            if (facade.isOrder(orderID).getValue()) {
                Response response = facade.submitOrder(orderID);
                if (response.isError()) {
                    System.out.println(response.getErrorMessage());
                } else {
                    System.out.println(response.getValue());
                }

                System.out.println("Press enter to continue");
                try {
                    System.in.read();
                } catch (Exception e) {
                }
            } else {
                System.out.println("Enter a correct order's id or 0 to return back please: ");
                if (orderID == 0) {
                    return;
                }
                submitOrderMenu();
            }
        } catch (Exception exception) {


            System.out.println("Wrong input, try again: ");

            submitOrderMenu();
        }

    }

    private void completeOrderMenu() { // done
        Scanner sc = new Scanner(System.in);
        int orderID = -1;
        try {


            System.out.println("Enter order's id to complete: \n");
            orderID = sc.nextInt();
            if (facade.isOrder(orderID).getValue()) {
                Response response = facade.CompleteOrder(orderID);
                if (response.isError()) {
                    System.out.println(response.getErrorMessage());
                } else {
                    System.out.println(response.getValue());
                }

                System.out.println("Press enter to continue");
                try {
                    System.in.read();
                } catch (Exception e) {
                }
            } else {
                System.out.println("Enter a correct order's id or 0 to return back please: ");
                if (orderID == 0) {
                    return;
                }
                completeOrderMenu();
            }
        } catch (Exception exception) {

            System.out.println("Wrong input, try again: ");

            completeOrderMenu();
        }

    }

    private void cancelOrderMenu() {  // done
        int orderID = -1;
        Scanner sc = new Scanner(System.in);
        try {


            System.out.println("Enter order's id to cancel: \n");
            orderID = sc.nextInt();
            if (facade.isOrder(orderID).getValue()) {
                Response response = facade.CancelOrder(orderID);
                if (response.isError()) {
                    System.out.println(response.getErrorMessage());
                } else {
                    System.out.println("Order was canceled successfully!\n");
                }

                System.out.println("Press enter to continue");
                try {
                    System.in.read();
                } catch (Exception e) {
                }
            } else {
                System.out.println("Enter a correct order's id or 0 to return back please: ");
                if (orderID == 0) {
                    return;
                }
                cancelOrderMenu();
            }
        } catch (Exception exception) {
            System.out.println("Wrong input, try again: ");
            cancelOrderMenu();
        }


    }

    private void manageOrderMenu_options() {
        System.out.println(
                "======Manage Order Menu======" + "\n" +
                        "1.Add item" + "\n" +
                        "2.Remove item" + "\n" +
                        "3.Reschedule order" + "\n" +
                        "4.Back" + "\n"
        );
    }

    private void addItemMenu(int orderID) {  // done

        try {
            Scanner sc = new Scanner(System.in);
            System.out.println("Enter item's id to add to order: \n");
            int itemID = sc.nextInt();
            System.out.println("Enter quantity: \n");
            int quantity = sc.nextInt();


            Response response = facade.AddItemToOrder(orderID, itemID, quantity);
            if (response.isError()) {
                System.out.println(response.getErrorMessage());
            } else {
                System.out.println("item was added successfully!\n");
            }

            System.out.println("Press enter to continue");
            try {
                System.in.read();
            } catch (Exception e) {
            }
        } catch (Exception exception) {
            System.out.println("Wrong input, try again: ");
            addItemMenu(orderID);
        }


    }

    private void removeItemMenu(int orderID) {  // done
        try {
            Scanner sc = new Scanner(System.in);
            System.out.println("Enter item's id to remove from order: \n");
            int itemID = sc.nextInt();

            Response response = facade.RemoveItem(orderID, itemID);
            if (response.isError()) {
                System.out.println(response.getErrorMessage());
            } else {
                System.out.println("item was removed successfully!\n");
            }

            System.out.println("Press enter to continue");
            try {
                System.in.read();
            } catch (Exception e) {
            }
        } catch (Exception exception) {
            System.out.println("Wrong input, try again: ");
            removeItemMenu(orderID);
        }
    }
    //private void editItemMenu(int orderID){

    //try {
    //	Scanner sc = new Scanner(System.in);
    //	System.out.println("Enter item's id to remove from order: \n");
    //	int itemID = sc.nextInt();

    //Response response = facade.EditItemInOrder(orderID, itemID);
    //if (response.isError()){
    //	System.out.println(response.getErrorMessage());
    //}
    //	else {
    //		System.out.println("item was edited successfully!\n");
    //	}

    //System.out.println("Press enter to continue");
    //	try{System.in.read();}
    ///	catch(Exception e){}
    //}
    //catch (Exception exception){
    //	System.out.println("Wrong input, try again: ");
    //	editItemMenu(orderID);
    //	}

    //}

    private void rescheduleOrderMenu(int orderID) {  // done
        try {
            Scanner sc = new Scanner(System.in);
            System.out.println("Enter new due date of the order in format such as 2007-12-03: ");
            LocalDate dueDate = LocalDate.parse(sc.nextLine());
            Response response = facade.RescheduleOrder(orderID, dueDate);
            if (response.isError()) {
                System.out.println(response.getErrorMessage());
            } else {
                System.out.println("Order was rescheduled successfully!\n");
            }

            System.out.println("Press enter to continue");
            try {
                System.in.read();
            } catch (Exception e) {
            }
        } catch (Exception exception) {
            System.out.println("Wrong input, try again: ");
            rescheduleOrderMenu(orderID);
        }
    }

    private void manageOrderMenu(int orderID) {  // done

        try {
            Scanner sc = new Scanner(System.in);
            boolean terminate = false;

            if (facade.isOrder(orderID).getValue()) {
                int opt;
                while (!terminate) {
                    manageOrderMenu_options();
                    opt = Integer.parseInt(sc.nextLine());
                    switch (opt) {
                        case 1:
                            addItemMenu(orderID);
                            break;
                        case 2:
                            removeItemMenu(orderID);
                            break;
                        //case 3:
                        //editItemMenu(orderID);
                        //break;
                        case 3:
                            rescheduleOrderMenu(orderID);
                            break;

                        case 4:
                            terminate = true;
                            break;
                        default:
                            System.out.println("Enter a number between 1 to 5!");
                    }
                }

            } else {
                System.out.println("Enter a correct order's id or 0 to return back please: ");
                orderID = sc.nextInt();
                if (orderID == 0) {
                    return;
                }
                manageOrderMenu(orderID);
            }
        } catch (Exception exception) {
            System.out.println("Wrong input, try again: ");
            manageOrderMenu(orderID);
        }
    }

}
