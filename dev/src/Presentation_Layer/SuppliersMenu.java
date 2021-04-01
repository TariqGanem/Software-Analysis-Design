package Presentation_Layer;

public class SuppliersMenu implements Menu{

	
	private static SuppliersMenu instance = null;
	
	private SuppliersMenu() {
		// TODO Auto-generated constructor stub
	}
	
	public static SuppliersMenu get_Instance() {
		
		if (instance == null) {
			instance = new SuppliersMenu();
		}
		return instance;
	}
	
	
	@Override
	public void show() {
		throw new UnsupportedOperationException();
	}

}
