
package iuh.edu.restdb.service;

import iuh.edu.restdb.model.Employee;
import iuh.edu.restdb.repo.EmployeeRepo;
import iuh.edu.restdb.repo.EmployeeRepoImpl;


import java.awt.*;
import java.util.List;

public class EmployeeService {
    private final EmployeeRepo employeeRepo;

    public EmployeeService() {
        this.employeeRepo = new EmployeeRepoImpl();
    }

    public List<Employee> getAll() {
        return employeeRepo.getAll();
    }

    public List<Employee> getByDepartmentId(int id) {
        return employeeRepo.getByDepartmentId(id);
    }

    public Employee getById(int id) {
        return employeeRepo.getById(id);
    }

    public boolean save(Employee emp) {
        return employeeRepo.save(emp);
    }

    public boolean update(Employee emp) {
        return employeeRepo.update(emp);
    }

    public boolean delete(int id) {
        return employeeRepo.delete(id);
    }
}
