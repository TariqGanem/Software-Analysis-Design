package BusinessLayer.EmployeesModule.ShiftPackage;

import APIs.ShipmentsEmployeesAPI;
import BusinessLayer.Response;
import BusinessLayer.ResponseT;
import DataAccessLayer.EmployeesModule.DALController;
import Resources.Role;

import java.time.LocalDate;
import java.util.*;

public class ShiftController {
    private List<Shift> shifts;
    private ShiftPersonnel sp;
    private Shift activeShift;
    private DALController dalController;
    private LocalDate maxDateLoaded;

    public ShiftController(DALController dalController) {
        shifts = new ArrayList<Shift>();
        sp = new ShiftPersonnel(dalController);
        activeShift = null;
        this.dalController = dalController;
        maxDateLoaded = LocalDate.now().minusDays(1);
    }

    public Shift getShift(LocalDate date, boolean isMorning) {
        for (Shift shift : shifts) {
            if (shift.getDate().equals(date) && shift.isMorning() == isMorning) {
                activeShift = shift;
                return shift;
            }
        }
        ResponseT<Shift> res = dalController.getShift(date, isMorning);
        if (res.getErrorOccurred())
            return null;
        return res.getValue();
    }

    public List<Shift> getShifts(int daysFromToday) {
        LocalDate topDate = LocalDate.now().plusDays(daysFromToday);
        List<Shift> lst;
        if (topDate.isBefore(maxDateLoaded)) {
            lst = new ArrayList<>();
            for (Shift shift : shifts) {
                if (shift.getDate().isAfter(LocalDate.now().plusDays(daysFromToday)) || shift.getDate().isBefore(LocalDate.now()))
                    continue;
                lst.add(shift);
            }
        } else {
            ResponseT<List<Shift>> res = dalController.getShifts(daysFromToday);
            if (res.getErrorOccurred())
                throw new NoSuchElementException("an error occurred while loading from database.");
            lst = res.getValue();
            for (Shift shift : lst) {
                shifts.removeIf(shift1 -> shift1.getDate().isEqual(shift.getDate()) && shift1.isMorning() == shift.isMorning());
                shifts.add(shift);
            }
        }
        return lst;
    }

    public boolean AssignToShift(String id, Role skill) {
        if (activeShift == null)
            throw new NullPointerException("need a shift to assign this employee to.");
        int amountPlanned = sp.getQtty(getDay(activeShift.getDate()), activeShift.isMorning()).get(skill);
        int actualAmount = activeShift.assignEmployee(skill, id);
        if (actualAmount > amountPlanned)
            throw new IndexOutOfBoundsException("Note that you only needed " + amountPlanned + " " + skill + "s\nAnd now the amount is - " + actualAmount);
        Response res = dalController.insertToShift(activeShift, skill, id);
        if (res.getErrorOccurred())
            throw new NoSuchElementException("an error occurred while updating the database");
        return true;
    }

    public boolean removeFromShift(String id) {
        List<String> StoreKeeperIDs;
        if (activeShift == null)
            throw new NullPointerException("need a shift to remove this employee from.");

        if (activeShift.getPositions().get(Role.Driver).contains(id))
            new ShipmentsEmployeesAPI().deleteShipmentWithDriver(id, activeShift.getDate(), activeShift.isMorning());
        else if ((StoreKeeperIDs = activeShift.getPositions().get(Role.StoreKeeper)).contains(id))
            if (StoreKeeperIDs.size() == 1)
                new ShipmentsEmployeesAPI().deleteShipmentWithStoreKeeper(activeShift.getDate(), activeShift.isMorning());

        if (!activeShift.removeFromShift(id))
            throw new IllegalArgumentException(id + " is not assigned to this shift.");
        Response res = dalController.removeFromShift(activeShift, id);
        if (res.getErrorOccurred())
            throw new NoSuchElementException("an error occurred while removing from the database.");
        return true;
    }

    public void definePersonnelForShift(int day, boolean isMorning, Role skill, int qtty) {
        sp.setQtty(day, isMorning, skill, qtty);
    }

    public boolean addShift(LocalDate date, boolean isMorning) {
        int day = getDay(date);
        int index = isMorning ? day - 1 : day + 5;
        if (day >= 7)
            throw new IndexOutOfBoundsException("This is a saturday or a day larger than 7.");
        if (index > 10)
            throw new IllegalArgumentException("this shift is on rest day");
        for (Shift shift : shifts)
            if (shift.getDate().isEqual(date) && shift.isMorning() == isMorning)
                throw new IllegalArgumentException("this shift is already exists");
        activeShift = new Shift(date, isMorning);
        Response res = dalController.setShift(activeShift);
        if (res.getErrorOccurred())
            throw new IllegalArgumentException("an error occurred while adding the shift to the database.");
        return shifts.add(activeShift);

    }

    public boolean removeShift(LocalDate date, boolean isMorning) {
        Response res = dalController.deleteShift(date, isMorning);
        if (res.getErrorOccurred())
            return false;

        Shift shift = getShift(date, isMorning);
        if (shift == null)
            return false;

        for (String id : shift.getPositions().get(Role.Driver)) {
            new ShipmentsEmployeesAPI().deleteShipmentWithDriver(id, shift.getDate(), shift.isMorning());
        }
        for (String id : shift.getPositions().get(Role.StoreKeeper)) {
            new ShipmentsEmployeesAPI().deleteShipmentWithStoreKeeper(shift.getDate(), shift.isMorning());
        }

        boolean success = shifts.remove(shift);
        if (success)
            activeShift = null;
        return success;
    }

    public int getDay(LocalDate date) {
        int day = (date.getDayOfWeek().getValue() + 1) % 7;
        if (day == 0) return 7;
        return day;
    }

    public Map<Shift, Role> getEmpShifts(String id) {
        Map<Shift, Role> empShifts = new HashMap();
        ResponseT<Map<Shift, Role>> res = dalController.getEmpShifts(id);

        for (Shift shift : shifts) {
            Role role = shift.isAssignedToShift(id);
            if (role != null)
                empShifts.put(shift, role);
        }

        if (!res.getErrorOccurred()) {
            for (Shift s : res.getValue().keySet()) {
                if (shifts.stream().noneMatch(x -> x.getDate().equals(s.getDate()) && x.isMorning() == s.isMorning())) {
                    shifts.add(s);
                    empShifts.put(s, res.getValue().get(s));
                }
            }
        }
        return empShifts;
    }

    public Map<Role, Integer> getPersonnelForShift(int day, boolean isMorning) {
        return sp.getQtty(day, isMorning);
    }

    public boolean API_isRoleAssignedToShift(LocalDate date, boolean isMorning, Role role) {
        try {
            List<String> IDs = getShift(date, isMorning).getPositions().get(role);
            return IDs != null && IDs.size() > 0;
        } catch (Exception ignored) {
            return false;
        }
    }

    public boolean API_isDriverAssignedToShift(LocalDate date, boolean isMorning, String ID) {
        try {
            List<String> IDs = getShift(date, isMorning).getPositions().get(Role.Driver);
            return IDs != null && IDs.size() > 0;
        } catch (Exception ignored) {
            return false;
        }
    }

    public List<String> API_getAvailableDrivers(LocalDate date, boolean isMorning) {
        ResponseT<List<String>> res = dalController.getAvailableDrivers(date, isMorning);
        if (!res.getErrorOccurred()) {
            return res.getValue();
        }
        return new ArrayList<>();
    }
}
