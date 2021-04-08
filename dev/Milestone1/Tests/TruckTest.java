import BusinessLayer.Facade;
import BusinessLayer.Objects.Truck;
import BusinessLayer.Response;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;

public class TruckTest {

    private Facade data;

    @Before
    public void setUp() {
        data = new Facade();
    }

    @Test
    public void testAddTruck(){
        data.addTruck("12345", "BMW", 6.5, 12);
        assertEquals(1, data.getAlltrucks().getValue().size());
        tearDown();
    }

    @Test
    public void testTruckWeight(){
        Response res =  data.addTruck("12345", "BMW", 6.5, 6.2);
        assertEquals(res.getMsg(), "Couldn't add new truck - Illegal truck weight");
        assertEquals(0, data.getAlltrucks().getValue().size());
        tearDown();
    }

    @Test
    public void testAddTruckWithSameId(){

    }

    @After
    public void tearDown(){
        data = new Facade();
    }
}