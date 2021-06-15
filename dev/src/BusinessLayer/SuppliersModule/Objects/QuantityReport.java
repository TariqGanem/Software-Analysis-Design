package BusinessLayer.SuppliersModule.Objects;

import DTOPackage.QuantityReportDTO;

import java.util.HashMap;
import java.util.Map;

public class QuantityReport {
    private Map<Integer, Map<Integer, Double>> discounts;

    public QuantityReport() {
        discounts = new HashMap<>();
    }

    public QuantityReport(QuantityReportDTO quantityReport) {
        discounts = quantityReport.getDiscounts();
    }

    /***
     * Adding a new discount to the quantity report.
     * @param item_id is the id of the item.
     * @param quantity is the quantity of the item.
     * @param new_price is the price of the item.
     * @throws Exception also if there is a discount with this quantity then that's an error.
     */
    public void AddDiscount(int item_id, int quantity, double new_price) throws Exception {
        if (!discounts.containsKey(item_id))
            discounts.put(item_id, new HashMap<>());
        if (discounts.get(item_id).containsKey(quantity))
            throw new Exception("There's already a discount with this id and quantity!!!");
        discounts.get(item_id).put(quantity, new_price);
    }

    /***
     * Removing a discount from the quantity report.
     * @param item_id is the id of the item.
     * @throws Exception there is no discount for this item with this id so that's an error.
     */
    public void RemoveItemQuantity(int item_id) throws Exception {
        if (!discounts.containsKey(item_id))
            throw new Exception("The quantity report doesn't have this item!!!");
        discounts.remove(item_id);
    }

    public boolean hasDiscount(int item_id) {
        return discounts.containsKey(item_id);
    }

    public Map<Integer, Map<Integer, Double>> getDiscounts() {
        return discounts;
    }
}
