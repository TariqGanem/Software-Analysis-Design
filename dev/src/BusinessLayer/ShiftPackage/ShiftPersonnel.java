package BusinessLayer.ShiftPackage;

import java.util.Map;

public class ShiftPersonnel {
	private Map<String, Integer>[] empQtty;
	
	public ShiftPersonnel() {
		empQtty = new Map[11];
	}
	
	public void setQtty(int day, boolean isMorning, String skill, Integer qtty) {
		int index = isMorning ? day - 1 : day + 5;
		if(index > 10 || index < 0)
			throw new IndexOutOfBoundsException("no such shift.");
		if(!empQtty[index].containsKey(skill))
			empQtty[index].replace(skill, qtty);
		else empQtty[index].put(skill, qtty);
	}
	
	public Integer getQtty(int day, boolean isMorning, String skill) {
		int index = isMorning ? day - 1 : day + 5;
		if(index > 10 || index < 0)
			throw new IndexOutOfBoundsException("no such shift.");
		if(!empQtty[index].containsKey(skill))
			return 0;
		return empQtty[index].get(skill);
	}
}
