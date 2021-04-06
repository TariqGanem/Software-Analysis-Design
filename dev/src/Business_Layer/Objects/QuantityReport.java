package Business_Layer.Objects;

import DTO.QuantityReportDTO;

import java.util.HashMap;
import java.util.Map;

public class QuantityReport {
    private Map<Integer, Map<Integer, Double>> discounts;

    public QuantityReport() {
        discounts = new HashMap<>();
    }

    public QuantityReportDTO DTO(){
        return new QuantityReportDTO(discounts);
    }
    public void AddDiscount(int item_id, int quantity, double new_price) throws Exception {
        if (!discounts.containsKey(item_id))
            discounts.put(item_id, new HashMap<>());
        if(discounts.get(item_id).containsKey(quantity))
            throw new Exception("There's already a discount with this id and quantity!!!");
        discounts.get(item_id).put(quantity,new_price);
    }

    public void RemoveItemQuantity(int item_id) throws Exception {
        if(!discounts.containsKey(item_id))
            throw new Exception("The quantity report doesn't have this item!!!");
        discounts.remove(item_id);
    }
}
