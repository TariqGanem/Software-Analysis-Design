package DTOPackage;

import Resources.Role;

import java.util.Date;
import java.util.List;
import java.util.Map;

public class ShiftDTO {
    public Date date;
    public boolean isMorning;
    public Map<Role, List<String>> positions;
}
