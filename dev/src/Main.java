import PresentationLayer.MainMenu;

public class Main {
    public static void main(String[] args) {

        //Running the application [Shipments/Employees/Suppliers/Stock Modules]
        MainMenu app = MainMenu.getInstance();
        app.run();

    }
}
