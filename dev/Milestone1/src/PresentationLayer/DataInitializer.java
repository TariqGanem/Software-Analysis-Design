package PresentationLayer;

import BusinessLayer.Facade;
import DTOs.TruckDTO;

import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.LocalDateTime;
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
        try{
            String sDate1 = "31/12/1998";
            Date date1 = new SimpleDateFormat("dd/MM/yyyy").parse(sDate1);
            System.out.println(date1);
        }
        catch (Exception e){}

        facade.addTruck("HRY1234", "FH16", 6000, 3000);
        facade.addTruck("HYTE779", "GH67", 5000, 2500);
        facade.addTruck("7783624", "EIF", 8000, 1560);
    }

    public void initDrivers() {
        facade.addDriver("20816222", "Yazan", 7000);
        facade.addDriver("20877734", "Tariq", 9000);
        facade.addDriver("31556888", "Kareem", 15000);
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
