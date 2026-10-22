package com.nguyenvuthanh.datajpa.model;

import jakarta.persistence.*;

@Entity
@Table(name = "employees")
public class Employee {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    @Column(nullable = false)
    private String name;
    private String role;
    private Integer age;
    private Double salary;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "department_id")
    private Department department;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "position_id")
    private Position position;

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
    public Department getDepartment() { return department; }
    public void setDepartment(Department department) { this.department = department; }
    public Position getPosition() { return position; }
    public void setPosition(Position position) { this.position = position; }
    public Integer getDepartmentId() { return department == null ? null : department.getId(); }
    public Integer getPositionId() { return position == null ? null : position.getId(); }
    public String getDepartmentName() { return department == null ? "Chưa phân phòng" : department.getName(); }
    public String getPositionTitle() { return position == null ? "Chưa có chức vụ" : position.getTitle(); }
}
