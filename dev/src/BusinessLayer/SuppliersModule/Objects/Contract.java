package BusinessLayer.SuppliersModule.Objects;

import DTOPackage.ContractDTO;
import DTOPackage.ItemDTO;

import java.util.HashMap;
import java.util.Map;

public class Contract {
    private Map<Integer, Item> items;
    private QuantityReport report;
    private boolean selfPickup;
    private FixedOrder fixedOrder;

    public Contract(boolean selfPickup) {
        this.items = new HashMap<>();
        this.report = new QuantityReport();
        this.selfPickup = selfPickup;
        fixedOrder = null;
    }

    public void setFixedOrder(FixedOrder fixedOrder) throws Exception {
        if (this.fixedOrder == null)
            this.fixedOrder = fixedOrder;
        else
            throw new Exception("Order already exists!");
    }


    public Contract(ContractDTO contract) {
        items = new HashMap<>();
        for (Map.Entry<Integer, ItemDTO> item :
                contract.getItems().entrySet()) {
            items.put(item.getKey(), new Item(item.getValue()));
        }
        report = new QuantityReport(contract.getReport());
        selfPickup = contract.isSelfPickup();
    }

    /***
     * Adding a new discount to the quantity report.
     * @param item_id is the id of the item.
     * @param quantity is the quantity of the item.
     * @param new_price is the price of the item.
     * @throws Exception if there is no a quantity report so that's an error.
     * also if there is a discount with this quantity then that's an error.
     */
    public void AddDiscount(int item_id, int quantity, double new_price) throws Exception {
        if (!items.containsKey(item_id))
            throw new Exception("The contract doesn't have an item with this id!!!");
        if (report == null)
            throw new Exception("The contract doesn't have a quantity report!!!");
        report.AddDiscount(item_id, quantity, new_price);
    }

    /***
     * Removing a discount from the quantity report.
     * @param item_id is the id of the item.
     * @throws Exception if there is no a quantity report so that's an error.
     * also if there is no discount for this item with this id so that's an error.
     */
    public void RemoveItemQuantity(int item_id) throws Exception {
        if (!items.containsKey(item_id))
            throw new Exception("The contract doesn't have an item with this id!!!");
        if (report == null)
            throw new Exception("The contract doesn't have a quantity report!!!");
        report.RemoveItemQuantity(item_id);
    }

    /***
     * Adding a new item to the contract.
     * @param item_id is the id of the item.
     * @param name is the name of the item.
     * @param price is the price of the item.
     * @throws Exception if there is already an item with this id in the contract so that's an error.
     */
    public void AddItem(int item_id, String name, double price, double weight) throws Exception {
        if (items.containsKey(item_id))
            throw new Exception("The contract already have an item with this id!!!");
        items.put(item_id, new Item(item_id, name, price, weight));
    }

    /***
     * Removing a current item from the contract.
     * @param item_id is the id of the item.
     * @throws Exception if there is already an item with this id in the contract so that's an error.
     */
    public void RemoveItem(int item_id) throws Exception {
        if (!items.containsKey(item_id))
            throw new Exception("The contract doesn't have an item with this id!!!");
        items.remove(item_id);
        if (report.hasDiscount(item_id))
            report.RemoveItemQuantity(item_id);
    }

    /***
     * Changing the price of a current item in the contract.
     * @param item_id is the id of the item.
     * @param price is the price of the item.
     * @throws Exception if there is already an item with this id in the contract so that's an error.
     */
    public void ChangePrice(int item_id, double price) throws Exception {
        if (!items.containsKey(item_id))
            throw new Exception("The contract doesn't have an item with this id!!!");
        items.get(item_id).ChangePrice(price);
    }

    public Map<Integer, Item> getItems() {
        return items;
    }

    public QuantityReport getReport() {
        return report;
    }

    public boolean isSelfPickup() {
        return selfPickup;
    }

    public boolean isIncluding(int itemID) {
        return items.containsKey(itemID);
    }


    public double finalPrice(String name, int quantity) {
        int item_id = -1;
        double price = 0;
        for (int id : items.keySet()) {
            if (name == items.get(id).getName())
                item_id = id;
        }
        int quant = quantity;
        int maxdisc = 0;
        while (quant == 0) {
            for (int amount : report.getDiscounts().get(item_id).keySet()) {
                if (amount <= quant) {
                    maxdisc = amount;
                }
            }
            if (maxdisc == 0)
                price += maxdisc * report.getDiscounts().get(item_id).get(maxdisc);
            else
                price += quant * items.get(item_id).getPrice();
            quant -= maxdisc;
            maxdisc = 0;
        }
        return price;
    }
}
