package DataAccessLayer;

import BusinessLayer.ResponseT;
import BusinessLayer.ShiftPackage.Shift;

import java.sql.Connection;
import java.time.LocalDate;
import java.util.ArrayList;

public class ShiftMapper {
    private static ShiftMapper instance = null;
    private Connection con;

    public static ShiftMapper getInstance(Connection con) {
        if (instance == null)
            instance = new ShiftMapper(con);
        return instance;
    }

    private ShiftMapper(Connection con) {
        this.con = con;
    }

    public ResponseT<Shift> getShift(LocalDate date, boolean isMorning) {
        /*
        GET SHIFT FROM DB
         */
        return null;
    }
}
