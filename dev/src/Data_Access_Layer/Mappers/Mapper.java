package Data_Access_Layer.Mappers;

import java.sql.*;

public abstract class Mapper {
    protected Connection c = null;

    public Mapper(){
        try {
            Class.forName("org.sqlite.JDBC");
            c = DriverManager.getConnection("jdbc:sqlite:database.db");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
