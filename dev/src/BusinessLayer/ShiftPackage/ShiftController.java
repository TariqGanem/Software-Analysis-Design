package BusinessLayer.ShiftPackage;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.NoSuchElementException;

import com.sun.xml.internal.bind.v2.TODO;

import Resources.Role;

public class ShiftController {
	private List<Shift> shifts;
	private ShiftPersonnel sp;
	private Shift activeShift;
	
	public ShiftController() {
		shifts = new ArrayList<Shift>();
		sp = new ShiftPersonnel();
		activeShift = null;
	}
	
	public Shift getShift(Date date, boolean isMorning) {
		for (Shift shift : shifts) 
			if(shift.getDate().equals(date) && shift.isMorning()==isMorning)
				return shift;
		throw new NoSuchElementException("there is no shift at the time you want.");
	}
		
	public boolean AssignToShift(String id, Role skill) {
		if(activeShift == null)
			throw new NullPointerException("need a shift to assign this employee to.");
		int amountPlanned = sp.getQtty(getDay(activeShift.getDate()), activeShift.isMorning(), skill);
		int actualAmount = activeShift.assignEmployee(skill, id);
		if(actualAmount > amountPlanned)
			throw new IndexOutOfBoundsException("note that you've planned " + amountPlanned + " " + skill + "\n and now the amount is - " + actualAmount);
		return true;
	}
	
	public boolean removeFromShift(String id) {
		if(activeShift == null)
			throw new NullPointerException("need a shift to remove this employee from.");
		if(!activeShift.removeFromShift(id))
			throw new IllegalArgumentException(id + " is not assigned to this shift.");
		return true;
	}
	
	public boolean definePersonnelForShift(int day, boolean isMorning, Role skill, int qtty) {
		sp.setQtty(day, isMorning, skill, qtty);
		return false;
	}
	
	public boolean addShift(Date date, boolean isMorning) {
		int day = getDay(date);
		int index = isMorning ? day - 1 : day + 5;
		if(index > 10)
			throw new IllegalArgumentException("this shift is on rest day");
		return shifts.add(new Shift(date, isMorning));
	}
	
	public boolean removeShift(Date date, boolean isMorning) {
		return shifts.remove(getShift(date, isMorning));
	}
	
	private int getDay(Date date) {
		Calendar cal = Calendar.getInstance();
	    cal.setTime(date);
	    return cal.get(Calendar.DAY_OF_WEEK);
	}
	
//	/////////////////////////////////////////////////////////////////
//	public Map<Role, Shift> getEmpShifts(String id){
//		Map<Role, Shift> empShifts = new HashMap<Role, Shift>();
//		for (Shift shift : shifts) {
//			Role role = shift.isAssignedToShift(id);
//			if(role != null)
//				empShifts.put(role, shift);
//		}
//		return empShifts;
//	}
	
}