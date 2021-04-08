package PresentationLayer;

import BusinessLayer.Facade;
import DTOs.TruckDTO;

import javax.xml.crypto.Data;
import java.util.Date;

public class DataInitializer {

    Facade facade;

    public DataInitializer(Facade f) {
        facade = f;
    }


    public void initialize() {
        initTrucks();
        initDrivers();
        initLocations();
        initShipments();
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

    public void initLocations() {
        facade.addLocation("Beer Sheva, Alexander Yenai 17", "0503313300", "Yazan");
        facade.addLocation("Tel Aviv, Merkaz 53", "0774353553", "Kareem");
        facade.addLocation("Haifa hof hakarmel", "055853535", "Tariq");
    }

    public void initShipments(){
        facade.arrangeDelivery(null,"14:00","Beer Sheva",null ,null);
    }
}
