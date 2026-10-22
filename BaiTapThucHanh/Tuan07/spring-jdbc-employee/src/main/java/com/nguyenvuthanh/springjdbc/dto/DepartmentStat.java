package com.nguyenvuthanh.springjdbc.dto;

public record DepartmentStat(
        Integer departmentId,
        String departmentName,
        Long employeeCount,
        Double averageSalary,
        Double totalSalary) {}
