package Presentation_Layer;

import java.awt.List;

public class ContractsMenu implements Menu{

	private static  ContractsMenu instance = null;
	private String[] content;
	private int size;
	private ContractsMenu() {
		
		this.size = 0;  // would be parsed from json.
		
		
		this.content = new String[size];
		
		
	}
	
	public static ContractsMenu get_Instance() {
		
		if (instance == null) {
			instance = new ContractsMenu();
		}
		return instance;
	}
	
	@Override
	public void show() {
		for(int i = 0; i < size ;i++) {
			System.out.println(i+1+". "+content[i]);
		}
	}

}
