package Presentation_Layer;

public class OrdersMenu implements Menu{

	
	private static OrdersMenu instance = null;
	
	private OrdersMenu() {
		// TODO Auto-generated constructor stub
	}
	
	
	public static OrdersMenu get_Instance() {
		
		if (instance == null) {
			instance = new OrdersMenu();
		}
		return instance;
	}
	
	
	@Override
	public void show() {
		throw new UnsupportedOperationException();
		
	}

}
