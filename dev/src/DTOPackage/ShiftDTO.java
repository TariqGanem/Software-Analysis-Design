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

    public String describeShift(){
        String desc = "";
        String isMorning = this.isMorning ? "Morning" : "Evening";
        desc = date.toString() + " " + isMorning;
        return desc;
    }
}
