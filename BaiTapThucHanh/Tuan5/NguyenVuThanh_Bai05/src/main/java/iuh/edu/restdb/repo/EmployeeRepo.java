
package iuh.edu.restdb.repo;


import iuh.edu.restdb.model.Employee;

import java.util.List;

public interface EmployeeRepo {
    List<Employee> getAll();
    List<Employee> getByDepartmentId(int id);
    Employee getById(int id);
    boolean save(Employee emp);
    boolean update(Employee emp);
    boolean delete(int id);
}
