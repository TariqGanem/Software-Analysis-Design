import static org.junit.Assert.*;

import java.time.LocalDate;

import org.junit.Before;

import BusinessLayer.ShiftPackage.Shift;
import Resources.Role;
import org.junit.Test;

public class ShiftTest {

	private Shift shift;
	
	@Before
	public void setup() {
		shift = new Shift(LocalDate.now(), true);
	}

	@Test
	public void testAssignEmployee() {

		try{
			shift.assignEmployee(Role.Stocker, "1234");
			fail("should assign shift manager first.");
		}catch (Exception ignored){}
		shift.assignEmployee(Role.ShiftManager, "1234");
		assertEquals(shift.isAssignedToShift("1234"), Role.ShiftManager);
	}

	@Test
	public void testRemoveFromShift() {
		shift.assignEmployee(Role.ShiftManager, "1234");
		assertEquals(shift.isAssignedToShift("1234"), Role.ShiftManager);
		shift.assignEmployee(Role.Cashier, "2345");
		assertEquals(shift.isAssignedToShift("2345"), Role.Cashier);
		shift.removeFromShift("2345");
		assertNull(shift.isAssignedToShift("2345"));
	}
}