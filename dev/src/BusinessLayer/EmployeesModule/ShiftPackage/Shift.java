package BusinessLayer.EmployeesModule.ShiftPackage;

import Resources.Role;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Shift {
    private LocalDate date;
    private boolean isMorning;
    private Map<Role, List<String>> positions;

    public Shift(LocalDate date, boolean isMorning) {
        this.date = date;
        this.isMorning = isMorning;
        positions = new HashMap<Role, List<String>>();
    }

    public Shift(LocalDate date, boolean isMorning, Map<Role, List<String>> positions) {
        this.date = date;
        this.isMorning = isMorning;
        this.positions = positions;
    }

    public int assignEmployee(Role skill, String id) {
        List<String> lst = positions.get(Role.ShiftManager);
        if ((lst == null || lst.isEmpty()) && !skill.equals(Role.ShiftManager))
            throw new IllegalArgumentException("Please enter a shift manager before adding employees.");
        if (isAssignedToShift(id) != null)
            throw new IllegalArgumentException("this employee already assigned to this shift");
        List<String> ids = positions.get(skill);
        if (ids == null)
            ids = new ArrayList<String>();
        ids.add(id);
        positions.put(skill, ids);
        return ids.size();
    }

    public boolean removeFromShift(String id) {
        for (Role role : positions.keySet()) {
            List<String> l = positions.get(role);
            if (l.contains(id)) {
                if (role.equals(Role.ShiftManager) & l.size() == 1)
                    throw new IllegalArgumentException("Add another shift manager before removing the last!");
                return l.remove(id);
            }
        }
        return false;
    }

    public LocalDate getDate() {
        return date;
    }

    public boolean isMorning() {
        return isMorning;
    }

    public Map<Role, List<String>> getPositions() {
        return new HashMap<>(positions);
    }

    public Role isAssignedToShift(String id) {
        for (Role role : positions.keySet())
            if (positions.get(role).contains(id))
                return role;
        return null;
    }
}
