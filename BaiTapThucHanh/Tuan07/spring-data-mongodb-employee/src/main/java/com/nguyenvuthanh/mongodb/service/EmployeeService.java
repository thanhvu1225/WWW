package com.nguyenvuthanh.mongodb.service;

import com.nguyenvuthanh.mongodb.dto.DepartmentStat;
import com.nguyenvuthanh.mongodb.dto.EmployeeForm;
import com.nguyenvuthanh.mongodb.dto.EmployeeSearchCriteria;
import com.nguyenvuthanh.mongodb.dto.LookupOption;
import com.nguyenvuthanh.mongodb.model.Department;
import com.nguyenvuthanh.mongodb.model.Employee;
import com.nguyenvuthanh.mongodb.model.Position;
import com.nguyenvuthanh.mongodb.repository.DepartmentRepository;
import com.nguyenvuthanh.mongodb.repository.EmployeeRepository;
import com.nguyenvuthanh.mongodb.repository.PositionRepository;
import org.bson.Document;
import org.springframework.data.domain.Sort;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.aggregation.Aggregation;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.function.Function;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

@Service
public class EmployeeService {
    private final EmployeeRepository employeeRepository;
    private final DepartmentRepository departmentRepository;
    private final PositionRepository positionRepository;
    private final MongoTemplate mongoTemplate;

    public EmployeeService(EmployeeRepository employeeRepository, DepartmentRepository departmentRepository,
                           PositionRepository positionRepository, MongoTemplate mongoTemplate) {
        this.employeeRepository = employeeRepository;
        this.departmentRepository = departmentRepository;
        this.positionRepository = positionRepository;
        this.mongoTemplate = mongoTemplate;
    }

    public List<Employee> findAll() { return enrich(employeeRepository.findAll(Sort.by("id"))); }

    public List<Employee> search(EmployeeSearchCriteria c) {
        Query query = new Query().with(Sort.by("_id"));
        if (hasText(c.getId())) query.addCriteria(Criteria.where("_id").is(c.getId().trim()));
        if (hasText(c.getName())) query.addCriteria(Criteria.where("name").regex(Pattern.quote(c.getName().trim()), "i"));
        if (c.getMinAge() != null || c.getMaxAge() != null) {
            Criteria age = Criteria.where("age");
            if (c.getMinAge() != null) age = age.gte(c.getMinAge());
            if (c.getMaxAge() != null) age = age.lte(c.getMaxAge());
            query.addCriteria(age);
        }
        if (c.getMinSalary() != null) query.addCriteria(Criteria.where("salary").gt(c.getMinSalary()));
        if (c.getDepartmentId() != null) query.addCriteria(Criteria.where("department_id").is(c.getDepartmentId()));
        if (c.getPositionId() != null) query.addCriteria(Criteria.where("position_id").is(c.getPositionId()));
        return enrich(mongoTemplate.find(query, Employee.class));
    }

    public Employee findById(String id) {
        Employee employee = employeeRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Không tìm thấy nhân viên ID " + id));
        return enrich(List.of(employee)).getFirst();
    }

    public List<LookupOption> departments() {
        return departmentRepository.findAll(Sort.by("name")).stream().map(d -> new LookupOption(d.getId(), d.getName())).toList();
    }
    public List<LookupOption> positions() {
        return positionRepository.findAll(Sort.by("id")).stream().map(p -> new LookupOption(p.getId(), p.getTitle())).toList();
    }

    public List<DepartmentStat> departmentStats() {
        Aggregation aggregation = Aggregation.newAggregation(
                Aggregation.group("departmentId").count().as("employeeCount")
                        .avg("salary").as("averageSalary").sum("salary").as("totalSalary"));
        Map<Integer, Document> grouped = mongoTemplate.aggregate(aggregation, "employees", Document.class)
                .getMappedResults().stream().collect(Collectors.toMap(d -> d.getInteger("_id"), Function.identity()));
        return departmentRepository.findAll(Sort.by("id")).stream().map(department -> {
            Document values = grouped.get(department.getId());
            if (values == null) return new DepartmentStat(department.getId(), department.getName(), 0L, 0D, 0D);
            Number count = values.get("employeeCount", Number.class);
            Number average = values.get("averageSalary", Number.class);
            Number total = values.get("totalSalary", Number.class);
            return new DepartmentStat(department.getId(), department.getName(), count.longValue(),
                    average.doubleValue(), total.doubleValue());
        }).toList();
    }

    public List<Employee> topPaid(Integer departmentId) {
        return departmentId == null ? List.of() : enrich(employeeRepository.findTop5ByDepartmentIdOrderBySalaryDesc(departmentId));
    }

    public List<LookupOption> emptyDepartments() {
        Set<Integer> used = employeeRepository.findAll().stream().map(Employee::getDepartmentId).collect(Collectors.toSet());
        return departments().stream().filter(d -> !used.contains(d.id())).toList();
    }

    public EmployeeForm getForm(String id) {
        Employee e = findById(id);
        EmployeeForm form = new EmployeeForm();
        form.setId(e.getId()); form.setName(e.getName()); form.setRole(e.getRole()); form.setAge(e.getAge());
        form.setSalary(e.getSalary()); form.setDepartmentId(e.getDepartmentId()); form.setPositionId(e.getPositionId());
        return form;
    }

    public Employee save(String id, EmployeeForm form) {
        Employee e = id == null ? new Employee() : employeeRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Không tìm thấy nhân viên ID " + id));
        e.setId(id); e.setName(form.getName().trim()); e.setRole(form.getRole().trim()); e.setAge(form.getAge());
        e.setSalary(form.getSalary()); e.setDepartmentId(form.getDepartmentId()); e.setPositionId(form.getPositionId());
        return enrich(List.of(employeeRepository.save(e))).getFirst();
    }

    public void delete(String id) {
        if (!employeeRepository.existsById(id)) throw new IllegalArgumentException("Không tìm thấy nhân viên ID " + id);
        employeeRepository.deleteById(id);
    }

    private List<Employee> enrich(List<Employee> employees) {
        Map<Integer, Department> departments = departmentRepository.findAll().stream()
                .collect(Collectors.toMap(Department::getId, Function.identity()));
        Map<Integer, Position> positions = positionRepository.findAll().stream()
                .collect(Collectors.toMap(Position::getId, Function.identity()));
        employees.forEach(e -> {
            Department d = departments.get(e.getDepartmentId());
            Position p = positions.get(e.getPositionId());
            e.setDepartmentName(d == null ? "Chưa phân phòng" : d.getName());
            e.setPositionTitle(p == null ? "Chưa có chức vụ" : p.getTitle());
        });
        return employees;
    }
    private static boolean hasText(String value) { return value != null && !value.isBlank(); }
}
