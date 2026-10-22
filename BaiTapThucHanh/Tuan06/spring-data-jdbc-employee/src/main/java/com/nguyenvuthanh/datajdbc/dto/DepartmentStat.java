package com.nguyenvuthanh.datajdbc.dto;

public record DepartmentStat(
        Integer departmentId,
        String departmentName,
        Long employeeCount,
        Double averageSalary,
        Double totalSalary) {}

