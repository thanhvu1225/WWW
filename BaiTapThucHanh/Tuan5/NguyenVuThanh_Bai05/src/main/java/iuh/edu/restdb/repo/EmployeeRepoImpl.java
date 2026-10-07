
package iuh.edu.restdb.repo;

import iuh.edu.restdb.model.Employee;
import iuh.edu.restdb.util.DBUtil;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class EmployeeRepoImpl implements EmployeeRepo{

    @Override
    public List<Employee> getAll() {
        List<Employee> list = new ArrayList<>();
        String sql = "select id, name, department_id,  salary from employees";
        try (Connection conn= DBUtil.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql);
             ResultSet rs = ps.executeQuery();
        ){
            while (rs.next()){
                list.add(new Employee(
                        rs.getInt("id"),
                        rs.getString("name"),
                        rs.getInt("department_id"),
                        rs.getDouble("salary")
                ));
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return list;
    }

    @Override
    public List<Employee> getByDepartmentId(int id) {
        List<Employee> list = new ArrayList<>();
        String sql = "select id, name, department_id,  salary from employees where department_id = ?";
        try (Connection conn= DBUtil.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql);

        ){
            ps.setInt(1, id);
            try (ResultSet rs = ps.executeQuery()){
                while (rs.next()){
                    list.add(new Employee(
                            rs.getInt("id"),
                            rs.getString("name"),
                            rs.getInt("department_id"),
                            rs.getDouble("salary")
                    ));
                }
            }

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return list;
    }

    @Override
    public Employee getById(int id) {
        String sql = "select id, name, department_id,  salary from employees where id = ?";
        try (Connection conn= DBUtil.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)
        ){
            ps.setInt(1, id);
            try (ResultSet rs = ps.executeQuery()){
                if(rs.next()){
                    return new Employee(
                            rs.getInt("id"),
                            rs.getString("name"),
                            rs.getInt("department_id"),
                            rs.getDouble("salary")
                    );
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return null;
    }

    @Override
    public boolean save(Employee emp) {
        String sql = "insert into employees (name, department_id, salary) values (?, ?, ?)";
        try (Connection conn = DBUtil.getConnection();
            PreparedStatement ps = conn.prepareStatement(sql)
        ){
            ps.setString(1, emp.getName());
            ps.setInt(2, emp.getDepartmentId());
            ps.setDouble(3, emp.getSalary());
            return ps.executeUpdate() > 0;

        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    @Override
    public boolean update(Employee emp) {
        String sql = "update employees set name = ?, department_id = ?, salary = ? where id = ?";
        try (Connection conn = DBUtil.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)
        ){
            ps.setString(1, emp.getName());
            ps.setInt(2, emp.getDepartmentId());
            ps.setDouble(3, emp.getSalary());
            ps.setInt(4, emp.getId());
            return ps.executeUpdate() > 0;

        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    @Override
    public boolean delete(int id) {
        String sql = "delete from employees where id = ?";
        try (Connection conn = DBUtil.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)
        ){
            ps.setInt(1, id);
            return ps.executeUpdate() > 0;

        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }
}
