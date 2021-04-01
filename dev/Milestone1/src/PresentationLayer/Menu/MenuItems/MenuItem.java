package PresentationLayer.Menu.MenuItems;

import BusinessLayer.Facade;

public abstract class MenuItem {
    private String name;
    private Facade facade;

    public MenuItem(String name) {
        this.name = name;
    }

    public abstract void activate();

    public String getName() {
        return name;
    }
}
