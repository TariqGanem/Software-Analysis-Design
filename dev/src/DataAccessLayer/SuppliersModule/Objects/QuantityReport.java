package DataAccessLayer.SuppliersModule.Objects;


import DTOPackage.QuantityReportDTO;

import java.util.HashMap;
import java.util.Map;

public class QuantityReport {
    private final Map<Integer, Map<Integer, Double>> discounts;

    public QuantityReport() {
        this.discounts = new HashMap<>();
    }

    public QuantityReport(Map<Integer, Map<Integer, Double>> discounts) {
        this.discounts = discounts;
    }

    public QuantityReport(QuantityReportDTO quantityReportDTO) {
        this.discounts = quantityReportDTO.getDiscounts();
    }

    public void addDiscount(int itemid, int quant, double disc) {
        if (discounts.containsKey(itemid)) {
            if (!discounts.get(itemid).containsKey(quant)) {
                discounts.get(itemid).put(quant, disc);
            }
        } else {
            discounts.put(itemid, new HashMap<>());
            discounts.get(itemid).put(quant, disc);
        }
    }

    public void removeDiscount(int itemid, int quant) {
        if (discounts.containsKey(itemid)) {
            if (discounts.get(itemid).containsKey(quant)) {
                discounts.get(itemid).remove(quant);
            }
        }
    }

    public void removeDiscount(int itemid) {
        if (discounts.containsKey(itemid)) {
            discounts.remove(itemid);
        }
    }

    public Map<Integer, Map<Integer, Double>> getDiscounts() {
        return discounts;
    }
}
