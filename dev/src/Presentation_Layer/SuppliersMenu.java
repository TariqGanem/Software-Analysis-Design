package Presentation_Layer;

import java.util.LinkedList;
import java.util.Scanner;

public class SuppliersMenu extends Menu{

	
	private static SuppliersMenu instance = null;

	
	private SuppliersMenu() {
		content = new LinkedList<String>();
		content.add("Suppliers menu:");
		content.add("Show all suppliers") ;
		content.add("Add supplier");
		content.add("delete supplier");
		content.add("Back");
		content.add("Exit!");
		size = content.size() - 1;
	
	}
	
	public static SuppliersMenu get_Instance() {
		
		if (instance == null) {
			instance = new SuppliersMenu();
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
		  case 3:
			  MainMenu.get_Instance().run();
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
