import DataAccessLayer.dbMaker;
import PresentationLayer.EmployeeMenu.EmployeeModule;
import PresentationLayer.ShipmentsMenu.Menu;

public class Main {
    public static void main(String[] args) {

        new dbMaker().initialize();

        Menu app = Menu.getInstance();
        EmployeeModule.main(args);//TODO - replace with function
        app.run();


    }
}
