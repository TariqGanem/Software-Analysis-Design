package Presentation_Layer;

import Business_Layer.Application.Facade;
import Business_Layer.Application.Response.Response;

import java.util.Scanner;

public class OrdersMenu implements Menu{
	private final Facade facade = Facade.getInstance();

	@Override
	public void Print_Menu() {
		boolean terminate = false;
		Scanner sc = new Scanner(System.in);
		int opt;
		while (!terminate) {
			ordersMenu_options();
			opt = Integer.parseInt(sc.nextLine());
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
					manageOrderMenu_options();
					break;

				case 6:
					terminate = true;
					break;
				default:
					System.out.println("Enter a number between 1 to 6.");
			}
		}
	}

	private void ordersMenu_options(){
		System.out.println(
				"======Orders Menu======"+"\n" +
						"1.Create order"+"\n" +
						"2.Submit order"+"\n" +
						"3.Complete order"+"\n" +
						"4.Cancel order"+"\n" +
						"5.Manage order"+"\n" +
						"6.Back"
		);
	}

	private void createOrderMenu(){
		Scanner sc = new Scanner(System.in);
		System.out.println("Enter supplier id to ask order from: \n");
		int supplierID = Integer.parseInt(sc.nextLine());



	}

	private void submitOrderMenu(){
		Scanner sc = new Scanner(System.in);
		System.out.println("Enter order's id to submit: \n");
		int orderID = sc.nextInt();

		Response response = facade.CompleteOrder(orderID);
		if (response.isError()){
			System.out.println(response.getErrorMessage());
		}
		else {
			System.out.println("Order was canceled successfully!\n");
		}

		System.out.println("Press enter to continue");
		try{System.in.read();}
		catch(Exception e){}
	}
	private void completeOrderMenu(){
		Scanner sc = new Scanner(System.in);
		System.out.println("Enter order's id to complete: \n");
		int orderID = sc.nextInt();

		Response response = facade.CompleteOrder(orderID);
		if (response.isError()){
			System.out.println(response.getErrorMessage());
		}
		else {
			System.out.println("Order was canceled successfully!\n");
		}

		System.out.println("Press enter to continue");
		try{System.in.read();}
		catch(Exception e){}
	}
	private void cancelOrderMenu(){
		Scanner sc = new Scanner(System.in);
		System.out.println("Enter order's id to cancel: \n");
		int orderID = sc.nextInt();

		Response response = facade.CancelOrder(orderID);
		if (response.isError()){
			System.out.println(response.getErrorMessage());
		}
		else {
			System.out.println("Order was canceled successfully!\n");
		}

		System.out.println("Press enter to continue");
		try{System.in.read();}
		catch(Exception e){}
	}
	private void manageOrderMenu_options(){
		System.out.println(
				"======Manage Order Menu======"+"\n" +
						"1.Add item"+"\n" +
						"2.Remove item"+"\n" +
						"3.Edit item"+"\n" +
						"4.Reschedule order"+"\n" +
						"5.Back"+"\n"
		);
	}

}
