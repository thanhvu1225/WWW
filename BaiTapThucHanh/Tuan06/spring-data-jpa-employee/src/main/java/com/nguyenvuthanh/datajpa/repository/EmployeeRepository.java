package com.nguyenvuthanh.datajpa.repository;

import com.nguyenvuthanh.datajpa.model.Employee;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import java.util.List;

public interface EmployeeRepository extends JpaRepository<Employee, Integer>, JpaSpecificationExecutor<Employee> {
    List<Employee> findByNameContainingIgnoreCase(String name);
    List<Employee> findByAgeBetween(Integer minAge, Integer maxAge);
    List<Employee> findBySalaryGreaterThan(Double salary);
    List<Employee> findByPosition_Id(Integer positionId);
    List<Employee> findByDepartment_IdAndPosition_Id(Integer departmentId, Integer positionId);
    List<Employee> findTop5ByDepartment_IdOrderBySalaryDesc(Integer departmentId);
}
