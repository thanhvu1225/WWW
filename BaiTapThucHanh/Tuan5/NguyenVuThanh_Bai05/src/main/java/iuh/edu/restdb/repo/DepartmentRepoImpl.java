
package iuh.edu.restdb.repo;

import iuh.edu.restdb.model.Department;
import iuh.edu.restdb.util.DBUtil;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class DepartmentRepoImpl implements DepartmentRepo{

    @Override
    public List<Department> getAll() {
        List<Department> list = new ArrayList<>();
        String sql = "select id, name from departments";
        try (Connection conn = DBUtil.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql);
             ResultSet rs = ps.executeQuery();
        ){
            while(rs.next()){
                list.add(new Department(
                        rs.getInt("id"),
                        rs.getString("name")
                ));
            }

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return list;
    }

    @Override
    public List<Department> searchByName(String keyword) {
        List<Department> list = new ArrayList<>();
        String sql = "select id, name from departments where name like ?";
        try (Connection conn = DBUtil.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql);

        ){
            ps.setString(1, keyword);
            try (ResultSet rs = ps.executeQuery()){
                while(rs.next()){
                    list.add(new Department(
                            rs.getInt("id"),
                            rs.getString("name")
                    ));
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return list;
    }

    @Override
    public Department getById(int id) {
        String sql = "select id, name from departments where id = ?";
        try (Connection conn = DBUtil.getConnection();
            PreparedStatement ps = conn.prepareStatement(sql);
        ){
            ps.setInt(1, id);
            try (ResultSet rs = ps.executeQuery()){
                if(rs.next()){
                    return new Department(
                            rs.getInt("id"),
                            rs.getString("name")
                    );
                }
            }

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return null;
    }

    @Override
    public boolean save(Department dept) {
        String sql = "insert into departments (name) value (?)";
        try (Connection conn = DBUtil.getConnection();
            PreparedStatement ps = conn.prepareStatement(sql);
        ){
            ps.setString(1, dept.getName());
            return ps.executeUpdate() > 0;

        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    @Override
    public boolean update(Department dept) {
        String sql = "update departments set name = ? where id = ?";
        try (Connection conn = DBUtil.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql);
        ){
            ps.setString(1, dept.getName());
            ps.setInt(2, dept.getId());
            return ps.executeUpdate() > 0;

        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    @Override
    public boolean delete(int id) {
        String sql = "delete from departments where id = ?";
        try (Connection conn = DBUtil.getConnection();
            PreparedStatement ps = conn.prepareStatement(sql);
        ){
            ps.setInt(1, id);
            return ps.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }
}
