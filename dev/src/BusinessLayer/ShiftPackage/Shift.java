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
		List<String> lst = positions.get(Role.ShiftManager);
		if((lst==null || lst.isEmpty()) && !skill.equals(Role.ShiftManager))
			throw new IllegalArgumentException("no shift without shift manager.");
		if(isAssignedToShift(id) != null)
			throw new IllegalArgumentException("this employee already assigned to this shift");
		List<String> ids = positions.get(skill);
		if(ids == null)
			ids = new ArrayList<String>();
		ids.add(id);
		positions.put(skill, ids);
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
	
	public Map<Role, List<String>> getPositions(){
		return new HashMap<Role, List<String>>(positions);
	}
	
//	/////////////////////////////////////////////////////////
//	public List<String> getAssignedEmployees(){
//		List<String> ans = new ArrayList<String>();
//		for (List<String> lst : positions.values())
//			ans.addAll(lst);
//		return ans;
//	}
//	
	public Role isAssignedToShift(String id) {
		for (Role role : positions.keySet())
			if(positions.get(role).contains(id))
				return role;
		return null;
	}
}
