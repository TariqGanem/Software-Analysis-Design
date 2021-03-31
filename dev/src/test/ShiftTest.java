package test;

import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.junit.Before;

import BusinessLayer.ShiftPackage.Shift;
import Resources.Role;

public class ShiftTest {

	private Shift shift;
	
	@Before
	private setup() {
		shift = new Shift(new Date, isMorning)
	}
	
	public int testAssignEmployee(Role skill, String id) {
		
	}
	
	public boolean testRemoveFromShift(String id) {
		
	}
}