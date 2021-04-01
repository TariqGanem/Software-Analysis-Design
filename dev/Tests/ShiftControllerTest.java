import static org.junit.Assert.*;
import BusinessLayer.ShiftPackage.Shift;
import BusinessLayer.ShiftPackage.ShiftController;
import Resources.Role;
import org.junit.Before;
import org.junit.Test;

import java.util.Calendar;
import java.util.Date;
import java.util.NoSuchElementException;

public class ShiftControllerTest {
    private ShiftController sc;

    @Before
    public void setup(){
        sc = new ShiftController();
        sc.addShift(Calendar.getInstance().getTime(),false );
    }

    @Test
    public void testGetShift() {
        Shift shift;
        try{
            sc.getShift(Calendar.getInstance().getTime(), true);
            fail("there shoud not be such shift");
        }catch (Exception ignored){}
        shift = sc.getShift(Calendar.getInstance().getTime(), false);
        assertNotNull(shift);
    }

    @Test
    public void testAssignToShift() {
        try{
            sc.AssignToShift("1234", Role.ShiftManager);
            fail("activate a shift first");///////////////////////////////////
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
    public void testRemoveFromShift(String id) {
//        if(activeShift == null)
//            throw new NullPointerException("need a shift to remove this employee from.");
//        if(!activeShift.removeFromShift(id))
//            throw new IllegalArgumentException(id + " is not assigned to this shift.");
//        return true;
    }

    @Test
    public void testDefinePersonnelForShift(int day, boolean isMorning, Role skill, int qtty) {
//        sp.setQtty(day, isMorning, skill, qtty);
//        return false;
    }

    @Test
    public void testAddShift(Date date, boolean isMorning) {
//        int day = getDay(date);
//        int index = isMorning ? day - 1 : day + 5;
//        if(index > 10)
//            throw new IllegalArgumentException("this shift is on rest day");
//        return shifts.add(new Shift(date, isMorning));
    }

    @Test
    public void testRemoveShift() {
//        return shifts.remove(getShift(date, isMorning));
    }
}