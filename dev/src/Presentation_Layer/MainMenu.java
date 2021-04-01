package Presentation_Layer;

public class MainMenu implements Menu {
	private static MainMenu instance = null;
	private MainMenu() {
		// TODO Auto-generated constructor stub
	}
	
	public static MainMenu get_Instance() {
		
		if (instance == null) {
			instance =  new MainMenu();
		}
		return instance;
	}
	
	@Override
	public void show() {

	}

}
