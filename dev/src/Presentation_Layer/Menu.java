package Presentation_Layer;

import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

public abstract class Menu {
	protected List<String> content;
	protected int size;
	
	
	public void show() {
		
		Iterator iter = content.iterator();
		
		
		
		
		System.out.println(iter.next());
		for(int i = 1; i <= size ;i++) {
			if(iter.hasNext())
				System.out.println(i+". "+iter.next());
			else
				break;
		}
	}
	
	abstract int get_input();
	abstract void load();

	public void run() {
		// TODO Auto-generated method stub
		show();
		while (get_input() != 0);
	}
}
