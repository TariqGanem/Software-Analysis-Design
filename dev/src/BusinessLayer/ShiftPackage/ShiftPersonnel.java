package BusinessLayer.ShiftPackage;

import java.util.Map;

import Resources.Role;;

public class ShiftPersonnel {
	private Map<Role, Integer>[] empQtty;
	
	public ShiftPersonnel() {
		empQtty = new Map[11];
		//insert default data
		
//		for (Map<Role, Integer> map : empQtty) {
//			map.put(Role.IUGIUT, 1);
//			map.put(Role.IUIUY, 1);
//			map.put(Role.JGI, 1);
//			map.put(Role.KJH, 1);
//			map.put(Role.UTIU, 1);
//		}
	}
	
	public void setQtty(int day, boolean isMorning, Role skill, Integer qtty) {
		int index = isMorning ? day - 1 : day + 5;
		if(index > 10 || index < 0)
			throw new IndexOutOfBoundsException("no such shift.");
		if(!empQtty[index].containsKey(skill))
			empQtty[index].replace(skill, qtty);
		else empQtty[index].put(skill, qtty);
	}
	
	public Integer getQtty(int day, boolean isMorning, Role skill) {
		int index = isMorning ? day - 1 : day + 5;
		if(index > 10 || index < 0)
			throw new IndexOutOfBoundsException("no such shift.");
		if(!empQtty[index].containsKey(skill))
			return 0;
		return empQtty[index].get(skill);
	}
}
