package Presentation_Layer;

import java.util.LinkedList;
import java.util.Scanner;

public class MainMenu extends Menu {
	private static MainMenu instance = null;
	//private SuppliersMenu suppliersMenu = null;
	//private OrdersMenu ordersMenu = null;
//private ContractsMenu contractsMenu = null;
	private MainMenu() {
		content = new LinkedList<String>();
		content.add("Welcome to Super-Lee!");
		content.add("Suppliers menu") ;
		
		content.add("Orders menu");
		content.add("Exit!");
		
		
		size = content.size() - 1;
	
	}
	
	public static MainMenu get_Instance() {
		
		if (instance == null) {
			instance =  new MainMenu();
		}
		return instance;
	}
	
	
	public int get_input() {
		// TODO Auto-generated method stub
		Scanner scanner = new Scanner(System.in);
		int input = 0;
		try {
			
		while (input > size || input < 1) {
			// print some note
			input = scanner.nextInt();
		}
		}
		catch (Exception e) {
			return -1;
		}
		finally {
			switch(input) {
		  case 1:
			  SuppliersMenu.get_Instance().run();
		  case 2:
			  OrdersMenu.get_Instance().run();
		  
		    
		}
		scanner.close();
		}
		return 0;
		
	}

	@Override
	void load() {
		// TODO Auto-generated method stub
		
	}

	

}
