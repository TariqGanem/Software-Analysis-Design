import static org.junit.Assert.*;
import BusinessLayer.ShiftPackage.ShiftPersonnel;
import Resources.Role;
import org.junit.Before;
import org.junit.Test;


public class ShiftPersonnelTest {
    private ShiftPersonnel sp;
    @Before
    private void setup(){
        sp = new ShiftPersonnel();
    }

    @Test
    public void testSetQtty() {
        try{
            sp.setQtty(6,false, Role.Cashier, 2);
            fail("no shift on friday night. should've fail.");
        }catch (Exception ignored){}
        try{
            sp.setQtty(8,true, Role.HRManager, 2);
            fail("day num 1-6.");
        }catch (Exception ignored){}
        try{
            sp.setQtty(6,false, Role.StoreManagerAssistant, -1);
            fail("negative quantity.");
        }catch (Exception ignored){}
        sp.setQtty(6,true, Role.Stocker, 2);
        assertTrue(sp.getQtty(6,true, Role.Stocker)== 2);
        sp.setQtty(6,true, Role.Stocker, 1);
        assertTrue(sp.getQtty(6,true, Role.Stocker)== 1);
    }

    @Test
    public void testGetQtty() {
        try{
            sp.getQtty(6,false, Role.Stocker);
            fail("no shift on friday night. should've fail.");
        }catch (Exception ignored){}
        try{
            sp.getQtty(8,true, Role.Stocker);
            fail("day num 1-6.");
        }catch (Exception ignored){}
        try{
            sp.getQtty(6,false, Role.Stocker);
            fail("negative quantity.");
        }catch (Exception ignored){}
        assertTrue(sp.getQtty(6,true, Role.Stocker) == 1);//check default values
        sp.setQtty(6,true, Role.Stocker, 2);
        assertTrue(sp.getQtty(6,true, Role.Stocker) == 2);
    }
}