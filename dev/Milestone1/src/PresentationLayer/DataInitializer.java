package PresentationLayer;

import BusinessLayer.Facade;
import DTOs.TruckDTO;

public class DataInitializer {

    Facade facade;

    public DataInitializer(Facade f) {
        facade = f;
    }


    public void initialize() {
        initTrucks();
        initDrivers();
    }

    public void initTrucks() {
        facade.addTruck("HRY1234", "FH16", 6, 12);
        facade.addTruck("HYTE779", "GH67", 5, 9);
        facade.addTruck("7783624", "EIF", 8, 13);
    }

    public void initDrivers() {
        facade.addDriver("20816222", "Yazan", 7);
        facade.addDriver("20877734", "Tariq", 9);
        facade.addDriver("31556888", "Kareem", 6);
    }
}
