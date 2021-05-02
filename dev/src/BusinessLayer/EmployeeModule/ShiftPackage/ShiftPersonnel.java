package BusinessLayer.EmployeeModule.ShiftPackage;

import java.util.HashMap;
import java.util.Map;

import DataAccessLayer.EmployeeModule.DALController;
import Resources.Role;;

public class ShiftPersonnel {
    private Map<Role, Integer>[] empQtty;
    private DALController dalController;

    public ShiftPersonnel(DALController dalController) {
        this.dalController = dalController;
        empQtty = new Map[14];
        for (int i = 0; i < 11; i++) {
            empQtty[i] = new HashMap<>();
        }
        empQtty[11] = null;
        empQtty[12] = null;
        empQtty[13] = null;

        //insert default data
        for (Map<Role, Integer> map : empQtty) {
            if (map != null) {
                map.put(Role.StoreManager, 1);
                map.put(Role.Cashier, 1);
                map.put(Role.HRManager, 1);
                map.put(Role.StoreManagerAssistant, 1);
                map.put(Role.Stocker, 1);
                map.put(Role.ShiftManager, 1);
                map.put(Role.StoreKeeper, 1);
            }
        }
    }

    public void setQtty(int day, boolean isMorning, Role skill, Integer qtty) {
        int index = isMorning ? day - 1 : day + 5;
        if (day > 7)
            throw new IndexOutOfBoundsException("Day greater than 7.");
        if (index > 10 || index < 0)
            throw new IndexOutOfBoundsException("No such shift.");
        if (qtty < 0)
            throw new IllegalArgumentException("Quantity above or equal zero.");
        if(skill.equals(Role.ShiftManager) && qtty == 0)
            throw new IllegalArgumentException("Each shift must have at least one Shift Manager.");
        if (empQtty[index].containsKey(skill))
            empQtty[index].replace(skill, qtty);
        else empQtty[index].put(skill, qtty);
    }

    public Map<Role, Integer> getQtty(int day, boolean isMorning) {
        int index = isMorning ? day - 1 : day + 5;
		if (day > 7)
			throw new IndexOutOfBoundsException("Day greater than 7.");
        if (index > 10 || index < 0)
            throw new IndexOutOfBoundsException("No such shift.");
        return empQtty[index];
    }
}
