package com.nguyenvuthanh.mongodb.repository;

import com.nguyenvuthanh.mongodb.model.Employee;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;

public interface EmployeeRepository extends MongoRepository<Employee, String> {
    List<Employee> findByNameContainingIgnoreCase(String name);
    List<Employee> findByAgeBetween(Integer minAge, Integer maxAge);
    List<Employee> findBySalaryGreaterThan(Double salary);
    List<Employee> findByPositionId(Integer positionId);
    List<Employee> findByDepartmentIdAndPositionId(Integer departmentId, Integer positionId);
    List<Employee> findTop5ByDepartmentIdOrderBySalaryDesc(Integer departmentId);
}
