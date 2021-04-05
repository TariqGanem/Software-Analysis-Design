import static org.junit.Assert.*;
import BusinessLayer.ShiftPackage.Shift;
import BusinessLayer.ShiftPackage.ShiftController;
import Resources.Role;
import org.junit.Before;
import org.junit.Test;

import java.time.LocalDate;
import java.util.Calendar;

public class ShiftControllerTest {
    private ShiftController sc;

    @Before
    public void setup(){
        sc = new ShiftController();
    }

    @Test
    public void testGetShift() {
        Shift shift;
        sc.addShift(LocalDate.now(),false );
        try{
            sc.getShift(LocalDate.now(), true);
            fail("there shoud not be such shift");
        }catch (Exception ignored){}
        shift = sc.getShift(LocalDate.now(), false);
        assertNotNull(shift);
    }

    @Test
    public void testAssignToShift() {
        sc.addShift(LocalDate.now(),false );
        Shift s = sc.getShift(LocalDate.now(), false);
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
        sc.addShift(LocalDate.now(),false );
        try{
            sc.removeFromShift("1234");
            fail("should'nt be able to remove from empty shift.");
        }catch (Exception ignored){}
        try{
            sc.AssignToShift("1234", Role.ShiftManager);
            sc.removeShift(LocalDate.now(), false);
            sc.removeFromShift("1234");
            fail("should'nt be able to remove with no active shift.");
        }catch (Exception ignored){}
        sc.addShift(LocalDate.now(), false);
        sc.AssignToShift("1234", Role.ShiftManager);
        assertTrue(sc.removeFromShift("1234"));
    }

    @Test
    public void testAddShift() {
        int day = (LocalDate.now().getDayOfWeek().getValue() + 1) % 7;
        LocalDate date = LocalDate.now().plusDays(7 - day);//saturday
        try{
            sc.addShift(date, false);
            fail("day num 1-6.");
        }catch (Exception ignored){}
        date = LocalDate.now().plusDays(6 - day);
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
        LocalDate date = LocalDate.now().plusDays(5 - day);
        sc.addShift(date, true);
        assertTrue(sc.removeShift(date, true));
        try {
            sc.removeShift(date, true);
            fail("Removed shift which doesn't exist.");
        } catch (Exception ignored) {}
    }
}