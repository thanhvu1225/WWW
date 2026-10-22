package com.nguyenvuthanh.datajdbc.repository;

import com.nguyenvuthanh.datajdbc.model.Employee;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface EmployeeRepository extends CrudRepository<Employee, Integer> {
    List<Employee> findByNameContainingIgnoreCase(String name);
    List<Employee> findByAgeBetween(Integer minAge, Integer maxAge);
    List<Employee> findBySalaryGreaterThan(Double salary);
    List<Employee> findByPositionId(Integer positionId);
    List<Employee> findByDepartmentIdAndPositionId(Integer departmentId, Integer positionId);
    List<Employee> findTop5ByDepartmentIdOrderBySalaryDesc(Integer departmentId);
}
