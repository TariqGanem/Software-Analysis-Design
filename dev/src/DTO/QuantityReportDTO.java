package DTO;

import java.util.HashMap;
import java.util.Map;

public class QuantityReportDTO {
    private Map<Integer, Map<Integer, Double>> discounts;

    public QuantityReportDTO(Map<Integer, Map<Integer, Double>> discounts) {
        this.discounts = discounts;
    }

    @Override
    public String toString() {
        String output = "--------------------Quantity Report--------------------\n";
        for (int id:discounts.keySet()) {
            for (int quantity:discounts.get(id).keySet()) {
                output += "Item's id: " + id +
                        ", Item's discount quantity : " + quantity +
                        ", Item's discount price: " + discounts.get(id).get(quantity) + "\n";
            }
        }
        output += "-------------------------------------------------------" + "\n";
        return output;
    }
}
