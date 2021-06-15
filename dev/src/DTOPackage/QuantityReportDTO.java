package DTOPackage;

import BusinessLayer.SuppliersModule.Objects.QuantityReport;

import java.util.Map;

public class QuantityReportDTO {
    private final Map<Integer, Map<Integer, Double>> discounts;

    public QuantityReportDTO(Map<Integer, Map<Integer, Double>> discounts) {
        this.discounts = discounts;
    }

    public QuantityReportDTO(QuantityReport quantityReport) {
        discounts = quantityReport.getDiscounts();
    }

    public QuantityReportDTO(DataAccessLayer.SuppliersModule.Objects.QuantityReport quantityReport) {
        this.discounts = quantityReport.getDiscounts();
    }

    @Override
    public String toString() {
        String output = "--------------------Quantity Report--------------------\n";
        for (int id : discounts.keySet()) {
            for (int quantity : discounts.get(id).keySet()) {
                output += "Item's id: " + id +
                        ", Item's discount quantity : " + quantity +
                        ", Item's discount price: " + discounts.get(id).get(quantity) + "\n";
            }
        }
        output += "-------------------------------------------------------" + "\n";
        return output;
    }

    public Map<Integer, Map<Integer, Double>> getDiscounts() {
        return discounts;
    }
}
