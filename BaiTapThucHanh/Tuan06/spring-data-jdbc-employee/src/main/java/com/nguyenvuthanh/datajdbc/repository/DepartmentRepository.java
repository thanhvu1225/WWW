package com.nguyenvuthanh.datajdbc.repository;

import com.nguyenvuthanh.datajdbc.model.Department;
import org.springframework.data.repository.CrudRepository;

public interface DepartmentRepository extends CrudRepository<Department, Integer> {}
