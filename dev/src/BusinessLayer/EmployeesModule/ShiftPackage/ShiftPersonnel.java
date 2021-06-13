package BusinessLayer.EmployeesModule.ShiftPackage;

import BusinessLayer.ResponseT;
import DataAccessLayer.EmployeesModule.DALController;
import Resources.Role;

import java.util.HashMap;
import java.util.Map;

;

public class ShiftPersonnel {
    private Map<Role, Integer>[] empQtty;
    private DALController dalController;

    public ShiftPersonnel(DALController dalController) {
        this.dalController = dalController;
        empQtty = new Map[14];
        for (int i = 0; i < 14; i++) {
            empQtty[i] = new HashMap<>();
        }

        ResponseT<Map<Role, Integer>[]> res = dalController.getShiftPersonnel();
        if (!res.getErrorOccurred() && res.getValue()[0].size() > 0) {
            empQtty = res.getValue();

        } else {
            //insert default data
            for (int i = 0; i < 14; i++) {
                for (Role r : Role.values()) {
                    empQtty[i].put(r, 1);
                    dalController.setShiftPersonnel(i, r, 1);
                }
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
        if (skill.equals(Role.ShiftManager) && qtty == 0)
            throw new IllegalArgumentException("Each shift must have at least one Shift Manager.");
        if (empQtty[index].containsKey(skill)) {
            empQtty[index].replace(skill, qtty);
            dalController.updateShiftPersonnel(index, skill, qtty);
        } else {
            empQtty[index].put(skill, qtty);
            dalController.setShiftPersonnel(index, skill, qtty);
        }
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
