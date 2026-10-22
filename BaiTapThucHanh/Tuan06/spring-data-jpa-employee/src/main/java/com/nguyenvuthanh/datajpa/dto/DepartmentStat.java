package com.nguyenvuthanh.datajpa.dto;

public record DepartmentStat(
        Integer departmentId,
        String departmentName,
        Long employeeCount,
        Double averageSalary,
        Double totalSalary) {}

