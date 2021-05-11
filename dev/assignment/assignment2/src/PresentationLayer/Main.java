package PresentationLayer;

import DataAccessLayer.InitMapper;

public class Main{
    public static void main(String[] args) {
        new InitMapper().initialize();
        MainMenu menu = new MainMenu();
        menu.showSpecificMenu();
    }
}
