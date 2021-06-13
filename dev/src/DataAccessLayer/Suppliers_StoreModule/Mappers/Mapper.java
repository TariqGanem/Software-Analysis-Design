package DataAccessLayer.Mappers;

import DTO.ContractDTO;

import java.sql.*;
import java.util.Map;

public abstract class Mapper {
    protected Connection connection = null;

    public Mapper(){
        try {
            Class.forName("org.sqlite.JDBC");
            connection = DriverManager.getConnection("jdbc:sqlite:database.db");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
