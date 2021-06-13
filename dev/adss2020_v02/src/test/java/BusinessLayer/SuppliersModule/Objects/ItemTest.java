package BusinessLayer.SuppliersModule.Objects;

import org.junit.jupiter.api.*;

import static org.junit.jupiter.api.Assertions.*;

class ItemTest {
    private static Item item;


    @BeforeAll
    static void setUp() {
        try {
            item = new Item(1, "shuku", 3.99,12);
        }
        catch (Exception e){
            fail("Got an unexpected exception!");
        }
    }

    @AfterEach
    void tearDown() {
        try {
            item = new Item(1, "shuku", 3.99,12);
        }
        catch (Exception e){
            fail("Got an unexpected exception!");
        }
    }

    @Test
    void getId() {
        try {
            assertEquals(1, item.getId());
        }
        catch (Exception e){
            fail("Got an unexpected exception!");
        }
    }

    @Test
    void getPrice() {
        try{
            assertEquals(3.99, item.getPrice());
        }
        catch (Exception e){
            fail("Got an unexpected exception!");
        }

    }

    @Test
    void getName() {
        try{
            assertEquals("shuku", item.getName());
        }
        catch (Exception e){
            fail("Got an unexpected exception!");
        }

    }

    @Test
    void changePrice() {
        try{
            item.ChangePrice(2.99);
            assertEquals(2.99, item.getPrice());
        }
        catch (Exception e){
            fail("Got an unexpected exception!");
        }

    }
}