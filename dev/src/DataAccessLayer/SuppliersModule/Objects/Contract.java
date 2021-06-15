package DataAccessLayer.SuppliersModule.Objects;

import DTOPackage.ContractDTO;
import DTOPackage.ItemDTO;

import java.util.HashMap;
import java.util.Map;

public class Contract {
    private Map<Integer, Item> items;
    private QuantityReport report;
    private boolean selfPickup;

    public Contract(Map<Integer, Item> items, QuantityReport report, boolean selfPickup) {
        this.items = items;
        this.report = report;
        this.selfPickup = selfPickup;
    }


    public Contract(ContractDTO contractDTO) {
        this.items = new HashMap<>();
        for (Map.Entry<Integer, ItemDTO> item : contractDTO.getItems().entrySet()) {
            this.items.put(item.getKey(), new Item(item.getValue()));
        }

        this.report = new QuantityReport(contractDTO.getReport());
        this.selfPickup = contractDTO.isSelfPickup();
    }

    public void addDiscount(int itemid, int quant, double disc) {
        if (items.containsKey(itemid)) {
            report.addDiscount(itemid, quant, disc);
        }
    }

    public void addItem(int itemid, String name, double price, double weight) {
        if (!items.containsKey(itemid)) {
            items.put(itemid, new Item(itemid, name, price, weight));
        }
    }

    public void removeDiscount(int itemid, int quant) {
        if (items.containsKey(itemid)) {
            report.removeDiscount(itemid, quant);
        }
    }

    public void removeItem(int itemid) {
        if (items.containsKey(itemid)) {
            items.remove(itemid);
            report.removeDiscount(itemid);
        }
    }

    public void updatePrice(int itemid, double price) {
        if (items.containsKey(itemid)) {
            items.get(itemid).updatePrice(price);
        }
    }

    public boolean isSelfPickup() {
        return selfPickup;
    }

    public Map<Integer, Item> getItems() {
        return items;
    }

    public QuantityReport getReport() {
        return report;
    }
}
