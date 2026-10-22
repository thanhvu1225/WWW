package com.nguyenvuthanh.datajpa.dto;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PositiveOrZero;

public class EmployeeForm {
    private Integer id;

    @NotBlank(message = "Tên nhân viên không được để trống")
    private String name;

    @NotBlank(message = "Vai trò không được để trống")
    private String role;

    @NotNull(message = "Tuổi không được để trống")
    @Min(value = 18, message = "Tuổi phải từ 18")
    @Max(value = 70, message = "Tuổi không được quá 70")
    private Integer age;

    @NotNull(message = "Lương không được để trống")
    @PositiveOrZero(message = "Lương không được âm")
    private Double salary;

    @NotNull(message = "Hãy chọn phòng ban")
    private Integer departmentId;

    @NotNull(message = "Hãy chọn chức vụ")
    private Integer positionId;

    public Integer getId() { return id; }
    public void setId(Integer id) { this.id = id; }
    public String getName() { return name; }
    public void setName(String name) { this.name = name; }
    public String getRole() { return role; }
    public void setRole(String role) { this.role = role; }
    public Integer getAge() { return age; }
    public void setAge(Integer age) { this.age = age; }
    public Double getSalary() { return salary; }
    public void setSalary(Double salary) { this.salary = salary; }
    public Integer getDepartmentId() { return departmentId; }
    public void setDepartmentId(Integer departmentId) { this.departmentId = departmentId; }
    public Integer getPositionId() { return positionId; }
    public void setPositionId(Integer positionId) { this.positionId = positionId; }
}

