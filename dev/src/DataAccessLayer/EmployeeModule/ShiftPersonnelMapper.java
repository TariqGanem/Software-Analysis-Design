package DataAccessLayer.EmployeeModule;

import Resources.Role;

import java.sql.Connection;
import java.util.Map;

public class ShiftPersonnelMapper {
    private static ShiftPersonnelMapper instance = null;
    private Connection con;
    private Map<Role, Integer>[] empQtty;

    public static ShiftPersonnelMapper getInstance(Connection con) {
        if (instance == null)
            instance = new ShiftPersonnelMapper(con);
        return instance;
    }

    private ShiftPersonnelMapper(Connection con) {
        this.con = con;
        empQtty = getPersonnelMapper();
    }

    public Map<Role, Integer>[] getPersonnelMapper() {
        /*
        GET FROM THE DB
         */
        return null;
    }

}
