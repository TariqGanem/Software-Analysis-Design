import static org.junit.Assert.*;
import BusinessLayer.ShiftPackage.Shift;
import BusinessLayer.ShiftPackage.ShiftController;
import Resources.Role;
import org.junit.Before;
import org.junit.Test;

import java.util.Calendar;
import java.util.Date;

public class ShiftControllerTest {
    private ShiftController sc;

    @Before
    public void setup(){
        sc = new ShiftController();
    }

    @Test
    public void testGetShift() {
        Shift shift;
        sc.addShift(Calendar.getInstance().getTime(),false );
        try{
            sc.getShift(Calendar.getInstance().getTime(), true);
            fail("there shoud not be such shift");
        }catch (Exception ignored){}
        shift = sc.getShift(Calendar.getInstance().getTime(), false);
        assertNotNull(shift);
    }

    @Test
    public void testAssignToShift() {
        sc.addShift(Calendar.getInstance().getTime(),false );
        try{
            sc.AssignToShift("1234", Role.ShiftManager);
            fail("activate a shift first");
        }catch (Exception ignored){}
        Shift s = sc.getShift(Calendar.getInstance().getTime(), false);
        boolean ans = sc.AssignToShift("1234", Role.ShiftManager);
        assertTrue(ans);
        assertEquals(s.isAssignedToShift("1234"), Role.ShiftManager);
        try{
            sc.AssignToShift("1234", Role.ShiftManager);
            fail("should warn the manager that the amount of shift manager is over full");
        }catch (Exception ignored){}
        assertEquals(s.isAssignedToShift("1234"), Role.ShiftManager);
    }

    @Test
    public void testRemoveFromShift() {
        sc.addShift(Calendar.getInstance().getTime(),false );
        try{
            sc.removeFromShift("1234");
            fail("should'nt be able to remove from empty shift.");
        }catch (Exception e){}
        try{
            sc.AssignToShift("1234", Role.ShiftManager);
            sc.removeShift(Calendar.getInstance().getTime(), false);
            sc.removeFromShift("1234");
            fail("should'nt be able to remove with no active shift.");
        }catch (Exception e){}
        sc.addShift(Calendar.getInstance().getTime(), false);
        sc.AssignToShift("1234", Role.ShiftManager);
        assertTrue(sc.removeFromShift("1234"));
    }

    @Test
    public void testAddShift() {
        Calendar cal = Calendar.getInstance();
        cal.setTime(cal.getTime());
        int day = cal.get(Calendar.DAY_OF_WEEK);
        Date date = new Date(cal.getTime().getTime() + 7 - day);//saturday
        try{
            sc.addShift(date, false);
            fail("day num 1-6.");
        }catch (Exception ignored){}
        date = new Date(cal.getTime().getTime() + 6 - day);
        System.out.println(date);
        try{
            sc.addShift(date, false);
            fail("no shift on friday night. should've fail.");
        }catch (Exception ignored){}
        assertTrue(sc.addShift(date, true));
    }

    @Test
    public void testRemoveShift() {
        Calendar cal = Calendar.getInstance();
        cal.setTime(cal.getTime());
        int day = cal.get(Calendar.DAY_OF_WEEK);
        Date date = new Date(cal.getTime().getTime() + 5 - day);
        sc.addShift(date, true);
        assertTrue(sc.removeShift(date, true));
        assertFalse(sc.removeShift(date, true));
    }
}