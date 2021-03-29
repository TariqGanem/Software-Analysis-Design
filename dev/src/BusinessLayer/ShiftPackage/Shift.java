package BusinessLayer.ShiftPackage;

import java.util.*;

public class Shift {
	private Date date;
	private boolean isMorning;
	private Map<String, List<String>> positions;
	
	public Shift(Date date, boolean isMorning) {
		this.date = date;
		this.isMorning = isMorning;
		positions = new HashMap<String, List<String>>();
	}
	
	public int assignEmployee(String skill, String id) {
		List<String> ids = positions.get(skill);
		if(ids == null)
			ids = positions.put(skill, new ArrayList<String>());
		ids.add(id);
		return ids.size();
	}
	
	public boolean removeFromShift(String id) {
		for (List<String> l : positions.values())
			if(l.contains(id))
				return l.remove(id);
		return false;
	}
	
	public Date getDate() {
		return date;
	}
	
	public boolean isMorning() {
		return isMorning;
	}
}
