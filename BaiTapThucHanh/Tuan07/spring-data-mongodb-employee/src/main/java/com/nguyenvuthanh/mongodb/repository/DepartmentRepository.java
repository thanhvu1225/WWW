package com.nguyenvuthanh.mongodb.repository;

import com.nguyenvuthanh.mongodb.model.Department;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface DepartmentRepository extends MongoRepository<Department, Integer> {}
