import BusinessLayer.ShipmentsModule.Facade;
import BusinessLayer.ShipmentsModule.Response;
import DTOPackage.ItemDTO;
import DataAccessLayer.dbMaker;
import PresentationLayer.MainMenu;

import java.text.SimpleDateFormat;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

public class Main {
    public static void main(String[] args)  {

        //Running the application [Shipments-Employees Module]
        MainMenu app = MainMenu.getInstance();
        app.run();

    }
}
