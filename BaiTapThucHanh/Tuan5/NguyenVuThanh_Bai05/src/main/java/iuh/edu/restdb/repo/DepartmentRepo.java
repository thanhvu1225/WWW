
package iuh.edu.restdb.repo;



import iuh.edu.restdb.model.Department;

import java.util.List;

public interface DepartmentRepo {
    List<Department> getAll();
    List<Department> searchByName(String keyword);
    Department getById(int id);
    boolean save(Department dept);
    boolean update(Department dept);
    boolean delete(int id);
}
