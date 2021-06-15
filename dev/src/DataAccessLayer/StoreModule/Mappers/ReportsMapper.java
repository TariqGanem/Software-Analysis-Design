package DataAccessLayer.StoreModule.Mappers;


import DataAccessLayer.StoreModule.objects.ReportsDl;
import DataAccessLayer.dbMaker;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.LinkedList;
import java.util.List;

public class ReportsMapper {
    private static ReportsMapper instance = null;
    private Mapper memory;


    private ReportsMapper() {
        memory = Mapper.getInstance();
    }

    public static ReportsMapper getInstance() {
        if (instance == null)
            instance = new ReportsMapper();
        return instance;
    }

    public void insertReport(int id, String title, String description, String cname, String date, String reportbudy) throws Exception {
        String sql = "INSERT INTO " + dbMaker.reportstbl + "(id, title,description,cname,date,reportbudy) VALUES (?,?,?,?,?,?)";
        try (PreparedStatement pstmt = dbMaker.connect().prepareStatement(sql)) {
            pstmt.setInt(1, id);
            pstmt.setString(2, title);
            pstmt.setString(3, description);
            pstmt.setString(4, cname);
            pstmt.setString(5, date);
            pstmt.setString(6, reportbudy);
            pstmt.executeUpdate();
        } catch (Exception e) {
            throw new Exception(e.getMessage());
        }
    }
    /// restore report and add to mapper reports list

    public void restoreAllReports() throws Exception {
        String sql = "SELECT * FROM " + dbMaker.reportstbl;
        List<ReportsDl> rep = new LinkedList<>();
        try (Statement stmt = dbMaker.connect().createStatement()) {
            ResultSet rs = stmt.executeQuery(sql);
            while (rs.next()) {
                ReportsDl report = new ReportsDl(rs.getInt(1),
                        rs.getString(2),
                        rs.getString(3),
                        rs.getString(4),
                        rs.getString(5),
                        rs.getString(6));
                rep.add(report);
            }
            memory.setReports(rep);
        } catch (Exception e) {
            throw new Exception(e.getMessage());
        }
    }

}