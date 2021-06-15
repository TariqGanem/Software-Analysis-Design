package DTOPackage;

import BusinessLayer.SuppliersModule.Objects.Contract;
import BusinessLayer.SuppliersModule.Objects.Item;

import java.util.HashMap;
import java.util.Map;

public class ContractDTO {
    private final Map<Integer, ItemDTO> items;
    private final QuantityReportDTO report;
    private final boolean selfPickup;

    public ContractDTO(Map<Integer, ItemDTO> items, QuantityReportDTO report, boolean selfPickup) {
        this.items = items;
        this.report = report;
        this.selfPickup = selfPickup;
    }

    public ContractDTO(Contract contract) {
        items = new HashMap<>();
        for (Map.Entry<Integer, Item> item :
                contract.getItems().entrySet()) {
            items.put(item.getKey(), new ItemDTO(item.getValue()));
        }
        report = new QuantityReportDTO(contract.getReport());
        selfPickup = contract.isSelfPickup();
    }

    public ContractDTO(DataAccessLayer.SuppliersModule.Objects.Contract contract) {
        this.items = new HashMap<>();

        for (Map.Entry<Integer, DataAccessLayer.SuppliersModule.Objects.Item> itemEntry : contract.getItems().entrySet()) {
            this.items.put(itemEntry.getKey(), new ItemDTO(itemEntry.getValue()));
        }

        this.selfPickup = contract.isSelfPickup();
        this.report = new QuantityReportDTO(contract.getReport());
    }

    @Override
    public String toString() {
        String output = "===================================================" + "\n";
        output += "self Pickup: " + selfPickup + "\n";
        output += "  <--------------------Items-------------------->" + "\n";
        for (int id : items.keySet()) {
            output += items.get(id).toString();
        }
        if (report != null)
            output += report.toString();
        output += "===================================================" + "\n";
        return output;
    }

    public Map<Integer, ItemDTO> getItems() {
        return items;
    }

    public QuantityReportDTO getReport() {
        return report;
    }

    public boolean isSelfPickup() {
        return selfPickup;
    }
}
