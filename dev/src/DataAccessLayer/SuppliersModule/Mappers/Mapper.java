package DataAccessLayer.SuppliersModule.Mappers;

import java.sql.Connection;
import java.sql.DriverManager;

public abstract class Mapper {
    protected Connection connection = null;

    public Mapper() {
        try {
            Class.forName("org.sqlite.JDBC");
            connection = DriverManager.getConnection("jdbc:sqlite:superLee.db");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
