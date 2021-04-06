package DTO;

import java.util.HashMap;
import java.util.Map;

public class ContractDTO {
    private Map<Integer, ItemDTO> items;
    private QuantityReportDTO report = null;
    private boolean selfPickup;

    public ContractDTO(Map<Integer, ItemDTO> items, QuantityReportDTO report, boolean selfPickup) {
        this.items = items;
        this.report = report;
        this.selfPickup = selfPickup;
    }

    @Override
    public String toString() {
        String output = "===================================================" + "\n";
        output += "self Pickup: " + selfPickup + "\n";
        output +=       "  <--------------------Items-------------------->" + "\n";
        for (int id: items.keySet()) {
            output += items.get(id).toString();
        }
        if(report != null)
            output += report.toString();
        output += "===================================================" + "\n";
        return output;
    }
}
