package PresentationLayer;

import BusinessLayer.Facade;
import DTOs.TruckDTO;

public class DataInitializer {

    Facade facade;
    public DataInitializer(Facade f){
        facade = f;
    }


    public void initialize() {
        initTrucks();
    }

    public void initTrucks(){
        facade.addTruck(new TruckDTO("HRY1234", "FH16",6, 12));
        facade.addTruck(new TruckDTO("HYTE779", "GH67",5, 9));
        facade.addTruck(new TruckDTO("7783624", "EIF",8, 13));
    }
}
