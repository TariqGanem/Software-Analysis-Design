package BusinessLayer.ShiftPackage;

import java.time.LocalDate;
import java.util.*;

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

	public Shift getShift(LocalDate date, boolean isMorning) {
		for (Shift shift : shifts) {
			if(shift.getDate().equals(date) && shift.isMorning()==isMorning){
				activeShift = shift;
				return shift;
			}
		}
		throw new NoSuchElementException("there is no shift at the time you want.");
	}

	public boolean AssignToShift(String id, Role skill) {
		if(activeShift == null)
			throw new NullPointerException("need a shift to assign this employee to.");
		int amountPlanned = sp.getQtty(getDay(activeShift.getDate()), activeShift.isMorning()).get(skill);
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

	public void definePersonnelForShift(int day, boolean isMorning, Role skill, int qtty) {
		sp.setQtty(day, isMorning, skill, qtty);
	}

	public boolean addShift(LocalDate date, boolean isMorning) {
		int day = getDay(date);
		int index = isMorning ? day - 1 : day + 5;
		if(index > 10)
			throw new IllegalArgumentException("this shift is on rest day");
		activeShift = new Shift(date, isMorning);
		return shifts.add(activeShift);
	}
	
	public boolean removeShift(LocalDate date, boolean isMorning) {
		boolean success = shifts.remove(getShift(date, isMorning));
		if(success)
			activeShift = null;
		return success;
	}

	public int getDay(LocalDate date){
		return (date.getDayOfWeek().getValue() + 1) % 7;
	}

	public Map<Shift, Role> getEmpShifts(String id){
		Map<Shift, Role> empShifts = new HashMap();
		for (Shift shift : shifts) {
			Role role = shift.isAssignedToShift(id);
			if(role != null)
				empShifts.put(shift, role);
		}
		return empShifts;
	}

	public Map<Role,Integer> getPersonnelForShift(int day, boolean isMorning){
		return sp.getQtty(day, isMorning);
	}
}