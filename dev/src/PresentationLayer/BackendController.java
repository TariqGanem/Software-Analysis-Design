
package PresentationLayer;

import java.util.Map;

import BusinessLayer.Facade;
import BusinessLayer.ShiftPackage.Shift;


public class BackendController {
    private Facade facade;
    private IOController ioController;

    public BackendController() {
        facade = new Facade();
        ioController = IOController.getInstance();
    }
}
