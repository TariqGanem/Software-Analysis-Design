package PresentationLayer.StoreMenu;

import BusinessLayer.StoreModule.Application.Facade;

public class initMapper {

    public void initialize() {
        Facade facade = Facade.getInstance();
        facade.addingCategory("Milk");
        try {
            facade.addsubCategory("Milk", "tnuva0", 1);
            facade.addsubCategory("Milk", "tnuva1", 1);
            facade.addsubCategory("Milk", "tnuva2", 1);
            facade.addsubCategory("tnuva0", "5per", 2);
            facade.addsubCategory("tnuva2", "7per", 2);
            facade.addingCategory("fish");
            facade.addingCategory("chocolate");
            facade.addsubCategory("fish", "fish1", 1);
            facade.addsubCategory("fish1", "fish2", 2);
            facade.addItem("tuna", "fish1", 10, 20, 8, 20, "30/05/2021", 10, "starkest");
            facade.addItem("tuna2", "fish2", 60, 10, 8, 20, "08/05/2021", 14, "starkest");
            facade.addItem("kitkat", "chocolate", 5, 10, 8, 10, "08/10/2021", 13, "strauss");
            facade.AddNewAmount("fish1", "tuna", 15, "08/05/2021");
            facade.AddNewAmount("fish2", "tuna2", 55, "30/05/2021");
            facade.addsubCategory("tnuva0", "3per", 2);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
