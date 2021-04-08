package Business_Layer.Objects;

import DTO.Status;
import org.junit.jupiter.api.*;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.*;

class OrderTest {
    private static Order order;
    @BeforeAll
    static void setUp() { //done
        try {
            order = new Order(11, Status.Active, LocalDate.now().plusDays(30));
        }
        catch (Exception e){
            fail("Got an unexpected exception!");
        }
    }

    @AfterAll
    static void tearDown() { // done
        try {
            order = new Order(11, Status.Active, LocalDate.now().plusDays(30));
        }
        catch (Exception e){
            fail("Got an unexpected exception!");
        }
    }

    @Test
    void getId() {  // done
        try {
            assertEquals(11, order.getId());
        }
        catch (Exception e){
            fail("Got an unexpected exception!");
        }
    }

    @Test
    void getStatus() { // done
        try {
            assertEquals(Status.Active, order.getStatus());
            order.setNewStatus(Status.Canceled);
            assertEquals(Status.Canceled, order.getStatus());
            order.setNewStatus(Status.Active);
            assertEquals(Status.Active, order.getStatus());
            order.setNewStatus(Status.Completed);
            assertEquals(Status.Completed, order.getStatus());
        }
        catch (Exception e){
            fail("Got an unexpected exception!");
        }
    }

    @Test
    void getPlacementDate() { // done
        try {
            assertEquals(LocalDate.now(),order.getPlacementDate());
        }
        catch (Exception e){
            fail("Got an unexpected exception!");
        }
    }

    @Test
    void getOrderDate() { //done
        try {
            assertEquals(LocalDate.now().plusDays(30),order.getOrderDate());
        }
        catch (Exception e){
            fail("Got an unexpected exception!");
        }
    }

    @Test
    void getItems() { // done
        try {
            assertTrue(order.getItems().isEmpty());
            Item item1 = new Item(1, "alis", 5.5);
            Item item2 = new Item(2, "bob", 0.99);
            order.addItem(item1);
            assertEquals(1, order.getItems().size());

            order.addItem(item2);
            assertEquals(2, order.getItems().size());
            assertTrue(order.getItems().containsKey(item1.getId()));
            assertTrue(order.getItems().containsKey(item2.getId()));
            assertTrue(order.getItems().containsValue(item1));
            assertTrue(order.getItems().containsValue(item2));


        }
        catch (Exception e){
            fail("Got an unexpected exception!");
        }
    }

    @Test
    void setNewDate() { // done
        try {
            Exception exception = null;
            LocalDate date = LocalDate.now();
            try {

                order.setNewDate(date);
                date = LocalDate.now().plusDays(30);
                order.setNewDate(date);
            }
            catch (Exception e){
                exception = e;
            }
            assertEquals(LocalDate.now().plusDays(30), order.getOrderDate());
            assertNotNull(exception);
            date = date.plusDays(7);
            order.setNewDate(date);
            assertEquals(date, order.getOrderDate());
        }
        catch (Exception e){
            fail("Got an unexpected exception!");
        }
    }

    @Test
    void setNewStatus() {  // done
        try {
            order.setNewStatus(Status.Canceled);
            assertEquals(Status.Canceled, order.getStatus());
            Exception exception = null;
            try {
                order.setNewStatus(Status.Completed);
            } catch (Exception e) {
                exception = e;
            }
            assertNotNull(exception);
            try {
                exception = null;
                order.setNewStatus(Status.Active);
                order.setNewStatus(Status.Completed);
            } catch (Exception e) {
                exception = e;
            }
            assertNull(exception);
            assertEquals(Status.Completed, order.getStatus());
        }
        catch (Exception e){
            fail("Got an unexpected exception!");
        }
    }

    @Test
    void addItem() { // done
        try {
            Item item1 = new Item(1, "alis", 5.5);
            Item item2 = new Item(2, "bob", 0.99);
            order.addItem(item1);
            assertTrue(order.getItems().containsKey(item1.getId()));
            assertTrue(order.getItems().containsValue(item1));

            order.addItem(item2);
            assertTrue(order.getItems().containsValue(item2));
            assertTrue(order.getItems().containsKey(item2.getId()));
        }
        catch (Exception e){
            fail("Got an unexpected exception!");
        }

    }

    @Test
    void removeItem() { // done
        try{
            Item item1 = new Item(1, "alis", 5.5);
            Item item2 = new Item(2, "bob", 0.99);
            Item item404 = new Item(404, "404", 404);
            Exception exception = null;

            try{
                order.removeItem(item404);
            }
            catch (Exception e){
                exception = e;
            }
            assertNotNull(exception);

            order.addItem(item1);
            order.addItem(item2);
            order.removeItem(item1);
            assertFalse(order.getItems().containsKey(item1.getId()));
            assertFalse(order.getItems().containsValue(item1));
            assertEquals(1, order.getItems().size());
            assertTrue(order.getItems().containsValue(item2));
            assertTrue(order.getItems().containsKey(item2.getId()));
            order.removeItem(item2);
            assertEquals(0, order.getItems().size());
            assertFalse(order.getItems().containsKey(item2.getId()));
            assertFalse(order.getItems().containsValue(item2));
        }
        catch (Exception e ){
            fail("Got an unexpected exception!");
        }

    }
}