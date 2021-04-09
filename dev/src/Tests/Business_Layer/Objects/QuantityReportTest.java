package Business_Layer.Objects;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

class QuantityReportTest {
    private static QuantityReport quantityReport;
    @BeforeAll
    static void setUp() {  // done
        try {
            quantityReport = new QuantityReport();
        }
        catch (Exception e){
            fail();
        }

    }

    @AfterEach
    void tearDown() {  // done
        try{
            quantityReport = new QuantityReport();
        }
        catch (Exception e){
            fail();
        }
    }

    @Test
    void addDiscount() {  // done
        try{
            int itemID1 = 1;
            int itemID2 = 2;
            int quantity1 = 100;
            int quantity2 = 200;
            double price1 = 0.20;
            double price2 = 0.50;
            quantityReport.AddDiscount(itemID1,quantity1, price1);
            quantityReport.AddDiscount(itemID2, quantity2, price2);
            assertEquals(2, quantityReport.getDiscounts().size());
            assertTrue(quantityReport.getDiscounts().containsKey(itemID1));
            assertTrue(quantityReport.getDiscounts().containsKey(itemID2));
            Exception exception = null;
            try{
                quantityReport.AddDiscount(itemID1,quantity1,price1);
            }
            catch (Exception e){
                exception = e;
            }
            assertNotNull(exception);
        }
        catch (Exception e){
            fail();
        }
    }

    @Test
    void removeItemQuantity() {  // done
        try{
            int itemID1 = 1;
            int itemID2 = 2;
            int itemID404 = 404;
            int quantity1 = 100;
            int quantity2 = 200;
            double price1 = 0.20;
            double price2 = 0.50;
            quantityReport.AddDiscount(itemID1,quantity1, price1);
            quantityReport.AddDiscount(itemID2, quantity2, price2);

            Exception exception = null;
            try {
                quantityReport.RemoveItemQuantity(itemID404);
            }
            catch (Exception e){
                exception = e;
            }
            assertNotNull(exception);

            quantityReport.RemoveItemQuantity(itemID1);
            assertEquals(1, quantityReport.getDiscounts().size());
            assertFalse(quantityReport.getDiscounts().containsKey(itemID1));
            quantityReport.RemoveItemQuantity(itemID2);
            assertEquals(0, quantityReport.getDiscounts().size());
            assertFalse(quantityReport.getDiscounts().containsKey(itemID2));
        }
        catch (Exception e){
            fail();
        }
    }

    @Test
    void getDiscounts() {  // done
        try {
            int itemID1 = 1;
            int itemID2 = 2;
            int quantity1 = 100;
            int quantity2 = 200;
            double discount1 = 0.20;
            double discount2 = 0.50;
            quantityReport.AddDiscount(itemID1,quantity1, discount1);
            quantityReport.AddDiscount(itemID2, quantity2, discount2);

            Map<Integer, Map<Integer, Double>> discounts = quantityReport.getDiscounts();
            assertEquals(2, discounts.size());
            assertTrue(discounts.containsKey(itemID1));
            assertTrue(discounts.containsKey(itemID2));

            assertTrue(discounts.get(itemID1).containsKey(quantity1));
            assertEquals(discount1, discounts.get(itemID1).get(quantity1));
            assertTrue(discounts.get(itemID2).containsKey(quantity2));
            assertEquals(discount2, discounts.get(itemID2).get(quantity2));

        }
        catch (Exception e){
            fail();
        }
    }
}