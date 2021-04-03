package Presentation_Layer;

import java.util.LinkedList;
import java.util.Scanner;

public class OrdersMenu extends Menu{

	
	private static OrdersMenu instance = null;

	
	private OrdersMenu() {
		content = new LinkedList<String>();
		size = content.size() - 1;
	}
	
	
	public static OrdersMenu get_Instance() {
		
		if (instance == null) {
			instance = new OrdersMenu();
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
		    // code block
		    break;
		  case 2:
		    // code block
		    break;
		  default:
		    // code block
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
