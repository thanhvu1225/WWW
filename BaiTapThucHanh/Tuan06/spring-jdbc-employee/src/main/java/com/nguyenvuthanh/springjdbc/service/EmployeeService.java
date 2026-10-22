package com.nguyenvuthanh.springjdbc.service;

import com.nguyenvuthanh.springjdbc.dto.DepartmentStat;
import com.nguyenvuthanh.springjdbc.dto.EmployeeForm;
import com.nguyenvuthanh.springjdbc.dto.EmployeeSearchCriteria;
import com.nguyenvuthanh.springjdbc.dto.LookupOption;
import com.nguyenvuthanh.springjdbc.model.Employee;
import com.nguyenvuthanh.springjdbc.repository.EmployeeJdbcRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class EmployeeService {
    private final EmployeeJdbcRepository repository;

    public EmployeeService(EmployeeJdbcRepository repository) {
        this.repository = repository;
    }

    public List<Employee> findAll() { return repository.findAll(); }
    public List<Employee> search(EmployeeSearchCriteria criteria) { return repository.search(criteria); }
    public Employee findById(Integer id) {
        return repository.findById(id).orElseThrow(() -> new IllegalArgumentException("Không tìm thấy nhân viên ID " + id));
    }
    public List<LookupOption> departments() { return repository.findDepartments(); }
    public List<LookupOption> positions() { return repository.findPositions(); }
    public List<DepartmentStat> departmentStats() { return repository.findDepartmentStats(); }
    public List<Employee> topPaid(Integer departmentId) {
        return departmentId == null ? List.of() : repository.findTopPaidByDepartment(departmentId, 5);
    }
    public List<LookupOption> emptyDepartments() { return repository.findEmptyDepartments(); }

    public EmployeeForm getForm(Integer id) {
        Employee employee = findById(id);
        EmployeeForm form = new EmployeeForm();
        form.setId(employee.getId());
        form.setName(employee.getName());
        form.setRole(employee.getRole());
        form.setAge(employee.getAge());
        form.setSalary(employee.getSalary());
        form.setDepartmentId(employee.getDepartmentId());
        form.setPositionId(employee.getPositionId());
        return form;
    }

    @Transactional
    public Employee save(Integer id, EmployeeForm form) {
        Employee employee = id == null ? new Employee() : findById(id);
        employee.setId(id);
        employee.setName(form.getName().trim());
        employee.setRole(form.getRole().trim());
        employee.setAge(form.getAge());
        employee.setSalary(form.getSalary());
        employee.setDepartmentId(form.getDepartmentId());
        employee.setPositionId(form.getPositionId());
        return repository.save(employee);
    }

    @Transactional
    public void delete(Integer id) {
        findById(id);
        repository.deleteById(id);
    }
}
