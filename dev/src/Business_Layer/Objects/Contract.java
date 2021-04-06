package Business_Layer.Objects;

import DTO.ContractDTO;
import DTO.ItemDTO;

import java.util.*;

public class Contract {
    private Map<Integer, Item> items;
    private QuantityReport report = null;
    private boolean selfPickup;

    public Contract(boolean selfPickup) {
        this.items = new HashMap<>();
        this.selfPickup = selfPickup;
    }

    public ContractDTO DTO() {
        Map<Integer, ItemDTO> items_dto = new HashMap<>();
        for (Integer id : items.keySet()) {
            items_dto.put(id, items.get(id).DTO());
        }
        return new ContractDTO(items_dto, report != null ? report.DTO() : null, selfPickup);
    }

    public void AddQuantityReport() throws Exception {
        if(report != null)
            throw new Exception("There's already a quantity report!!!");
        report = new QuantityReport();
    }

    public void AddDiscount(int item_id, int quantity, double new_price) throws Exception {
        if (!items.containsKey(item_id))
            throw new Exception("The contract doesn't have an item with this id!!!");
        if (report == null)
            throw new Exception("The contract doesn't have a quantity report!!!");
        report.AddDiscount(item_id, quantity, new_price);
    }

    public void RemoveItemQuantity(int item_id) throws Exception {
        if (!items.containsKey(item_id))
            throw new Exception("The contract doesn't have an item with this id!!!");
        if (report == null)
            throw new Exception("The contract doesn't have a quantity report!!!");
        report.RemoveItemQuantity(item_id);
    }

    public void AddItem(int item_id, String name, double price) throws Exception {
        if (items.containsKey(item_id))
            throw new Exception("The contract already have an item with this id!!!");
        items.put(item_id, new Item(item_id, name, price));
    }

    public void RemoveItem(int item_id) throws Exception {
        if (!items.containsKey(item_id))
            throw new Exception("The contract doesn't have an item with this id!!!");
        items.remove(item_id);
        if (report != null)
            report.RemoveItemQuantity(item_id);
    }

    public void ChangePrice(int item_id, double price) throws Exception {
        if (!items.containsKey(item_id))
            throw new Exception("The contract doesn't have an item with this id!!!");
        items.get(item_id).ChangePrice(price);
    }
}
