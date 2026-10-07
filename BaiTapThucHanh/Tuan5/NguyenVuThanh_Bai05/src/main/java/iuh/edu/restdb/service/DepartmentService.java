
package iuh.edu.restdb.service;

import iuh.edu.restdb.model.Department;
import iuh.edu.restdb.repo.DepartmentRepo;
import iuh.edu.restdb.repo.DepartmentRepoImpl;

import java.util.List;

public class DepartmentService {
    private final DepartmentRepo departmentRepo;

    public DepartmentService() {
        this.departmentRepo = new DepartmentRepoImpl();
    }

    public List<Department> getAll() {
        return departmentRepo.getAll();
    }

    public List<Department> searchByName(String keyword) {
        return departmentRepo.searchByName(keyword);
    }

    public Department getById(int id) {
        return departmentRepo.getById(id);
    }

    public boolean save(Department dept) {
        return departmentRepo.save(dept);
    }

    public boolean update(Department dept) {
        return departmentRepo.update(dept);
    }

    public boolean delete(int id) {
        return departmentRepo.delete(id);
    }
}
