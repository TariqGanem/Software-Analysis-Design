import PresentationLayer.EmployeesMenu.InitializeData;
import PresentationLayer.EmployeesMenu.PresentationController;
import PresentationLayer.ShipmentsMenu.DataInitializer;
import PresentationLayer.ShipmentsMenu.Menu;
import PresentationLayer.StoreMenu.initMapper;
import PresentationLayer.SuppliersMenu.SupplierMenu;


public class initializer {

    public void initialize() {
        new InitializeData().initializeData(PresentationController.getInstance());
        new DataInitializer(Menu.getInstance().getFacade()).initialize();
        new SupplierMenu().init();
        new initMapper().initialize();
    }
}
