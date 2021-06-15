package APIs;

import BusinessLayer.StoreModule.Application.Facade;
import BusinessLayer.StoreModule.Objects.ItemSpecs;

import java.util.List;

public class StoreShipmentAPI {

    public StoreShipmentAPI() {

    }

    public void updateAmounts(List<ItemSpecs> items) {
        for (ItemSpecs item : items) {
            Facade.getInstance().AddNewAmount(item.getCname(), item.getName(), item.getMinAmount(), "07/08/2025");
        }
    }
}
