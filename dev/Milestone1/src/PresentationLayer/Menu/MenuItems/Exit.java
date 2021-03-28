package PresentationLayer.Menu.MenuItems;

public class Exit extends MenuItem {
    public Exit(String name) {
        super(name);
    }

    @Override
    public void activate() {
        System.out.println("The application exited successfully!");
        System.exit(0);
    }
}
