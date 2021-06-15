package DataAccessLayer.StoreModule.Mappers;

import DataAccessLayer.StoreModule.objects.DefectsDl;
import DataAccessLayer.dbMaker;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.LinkedList;
import java.util.List;

public class DefectsMapper {
    private static DefectsMapper instance = null;
    private Mapper memory;


    private DefectsMapper() {
        memory = Mapper.getInstance();
    }

    public static DefectsMapper getInstance() {
        if (instance == null)
            instance = new DefectsMapper();
        return instance;
    }

    public void insertDefect(int id, String iname, String cname, String defectreason, int amount) throws Exception {
        String sql = "INSERT INTO " + dbMaker.defectstbl + "(id, iname,cname,defectreason,amount) VALUES (?,?,?,?,?)";
        try (PreparedStatement pstmt = dbMaker.connect().prepareStatement(sql)) {
            pstmt.setInt(1, id);
            pstmt.setString(2, iname);
            pstmt.setString(3, cname);
            pstmt.setString(4, defectreason);
            pstmt.setInt(5, amount);
            pstmt.executeUpdate();
        } catch (Exception e) {
            throw new Exception(e.getMessage());
        }
    }

    /// restore defect and add to mapper defects list

    public void restoreAllDefects() throws Exception {
        String sql = "SELECT * FROM " + dbMaker.defectstbl;
        List<DefectsDl> def = new LinkedList<>();
        try (Statement stmt = dbMaker.connect().createStatement()) {
            ResultSet rs = stmt.executeQuery(sql);
            while (rs.next()) {
                DefectsDl defect = new DefectsDl(rs.getInt(1),
                        rs.getString(2),
                        rs.getString(3),
                        rs.getString(4),
                        rs.getInt(5));
                def.add(defect);
            }
            memory.setDefects(def);
        } catch (Exception e) {
            throw new Exception(e.getMessage());
        }
    }
}