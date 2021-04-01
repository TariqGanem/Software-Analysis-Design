package DTOPackage;

import Resources.Role;
import java.util.Date;
import java.util.List;
import java.util.Map;

public class ShiftDTO {
    public final Date date;
    public final boolean isMorning;
    public final Map<Role, List<String>> positions;
    
    public ShiftDTO(Date date, boolean isMorning, Map<Role, List<String>> positions) {
    	this.date = date;
    	this.isMorning = isMorning;
    	this.positions = positions;
    }
}