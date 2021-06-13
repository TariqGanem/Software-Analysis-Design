package DataAccessLayer.StoreModule.Mappers;

import DataAccessLayer.StoreModule.InitMapper;
import DataAccessLayer.StoreModule.objects.CategoryDl;
import DataAccessLayer.StoreModule.objects.ReportsDl;

import java.sql.*;
import java.util.LinkedList;
import java.util.List;
public class CategoryMapper {

    private static CategoryMapper instance = null;
    private Mapper memory;


    private CategoryMapper()
    {
        memory=Mapper.getInstance() ;
    }
    public static CategoryMapper getInstance() {
        if (instance == null)
            instance = new CategoryMapper();
        return instance;
    }

    public void insertCategory(String cname,int level, int discount1,String uppercat) throws Exception {

        try (Connection conn = DriverManager.getConnection(InitMapper.path)) {
            String sql = "INSERT INTO " + InitMapper.categorytbl + " (cname, level, discount1,uppercat) VALUES (?,?,?,?)";
            PreparedStatement pstmt = conn.prepareStatement(sql);

            pstmt.setString(1, cname);
            pstmt.setInt(2, level);
            pstmt.setInt(3, discount1);
            pstmt.setString(4, uppercat);
            pstmt.executeUpdate();
        } catch (Exception e) {
            throw new Exception(e.getMessage());
        }
    }

    public void updateCategoryDiscount(String cname, int discount) throws Exception { //add&remove Discount!
        String sql = "UPDATE " + InitMapper.categorytbl + " SET discount1 = ? WHERE cname= ?";
        try (Connection conn = InitMapper.connect();
             PreparedStatement pStmt = conn.prepareStatement(sql)) {
            pStmt.setInt(1, discount);
            pStmt.setString(2, cname);
            pStmt.executeUpdate();
        } catch (Exception e) {
            throw new Exception(e.getMessage());
        }
    }

    public void deleteCategory(String cname) throws Exception {
        String sql = "DELETE FROM " + InitMapper.categorytbl + " WHERE cname = ?";
        try (Connection conn = InitMapper.connect();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, cname);
            pstmt.executeUpdate();
        } catch (Exception e) {
            throw new Exception(e.getMessage());
        }
    }

    public void restoreAllCategories() throws Exception {
        String sql = "SELECT * FROM " + InitMapper.categorytbl;
        List<CategoryDl> rep = new LinkedList<CategoryDl>();
        try (Connection conn = InitMapper.connect();
             Statement stmt = conn.createStatement()) {
            ResultSet rs = stmt.executeQuery(sql);
            while (rs.next()) {
                CategoryDl cat= new CategoryDl(rs.getString(1),
                        rs.getInt(2),
                        rs.getInt(3),
                        rs.getString(4));
                rep.add(cat);
            }
            memory.setCategories(rep);
        } catch (Exception e) {
            throw new Exception(e.getMessage());
        }
    }

}