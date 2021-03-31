package BusinessLayer.ShiftPackage;

import java.util.*;
import Resources.Role;

public class Shift {
	private Date date;
	private boolean isMorning;
	private Map<Role, List<String>> positions;
	
	public Shift(Date date, boolean isMorning) {
		this.date = date;
		this.isMorning = isMorning;
		positions = new HashMap<Role, List<String>>();
	}
	
	public int assignEmployee(Role skill, String id) {
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
	
	
	/////////////////////////////////////////////////////////
	public List<String> getAssignedEmployees(){
		List<String> ans = new ArrayList<String>();
		for (List<String> lst : positions.values())
			ans.addAll(lst);
		return ans;
	}
	
	public Role isAssignedToShift(String id) {
		for (Role role : positions.keySet())
			if(positions.get(role).contains(id))
				return role;
		return null;
//		for (List<String> lst : positions.values())
//			if(lst.contains(id))
//				return true;
//		return false;
	}
}
