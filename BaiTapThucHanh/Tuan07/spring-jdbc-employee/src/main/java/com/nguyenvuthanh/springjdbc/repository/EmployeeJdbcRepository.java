package com.nguyenvuthanh.springjdbc.repository;

import com.nguyenvuthanh.springjdbc.dto.DepartmentStat;
import com.nguyenvuthanh.springjdbc.dto.EmployeeSearchCriteria;
import com.nguyenvuthanh.springjdbc.dto.LookupOption;
import com.nguyenvuthanh.springjdbc.model.Employee;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

import java.sql.PreparedStatement;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Repository
public class EmployeeJdbcRepository {
    private final JdbcTemplate jdbcTemplate;
    private final NamedParameterJdbcTemplate namedJdbcTemplate;

    private final RowMapper<Employee> employeeRowMapper = (rs, rowNum) -> {
        Employee employee = new Employee();
        employee.setId(rs.getInt("id"));
        employee.setName(rs.getString("name"));
        employee.setRole(rs.getString("role"));
        employee.setAge(rs.getInt("age"));
        employee.setSalary(rs.getDouble("salary"));
        employee.setDepartmentId(rs.getInt("department_id"));
        employee.setPositionId(rs.getInt("position_id"));
        employee.setDepartmentName(rs.getString("department_name"));
        employee.setPositionTitle(rs.getString("position_title"));
        return employee;
    };

    public EmployeeJdbcRepository(JdbcTemplate jdbcTemplate, NamedParameterJdbcTemplate namedJdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
        this.namedJdbcTemplate = namedJdbcTemplate;
    }

    private String selectWithRelations() {
        return """
                SELECT e.id, e.name, e.role, e.age, e.salary, e.department_id, e.position_id,
                       d.name AS department_name, p.title AS position_title
                FROM employees e
                LEFT JOIN departments d ON d.id = e.department_id
                LEFT JOIN positions p ON p.id = e.position_id
                """;
    }

    public List<Employee> findAll() {
        return jdbcTemplate.query(selectWithRelations() + " ORDER BY e.id", employeeRowMapper);
    }

    public Optional<Employee> findById(Integer id) {
        return jdbcTemplate.query(selectWithRelations() + " WHERE e.id = ?", employeeRowMapper, id)
                .stream().findFirst();
    }

    public List<Employee> search(EmployeeSearchCriteria criteria) {
        StringBuilder sql = new StringBuilder(selectWithRelations()).append(" WHERE 1=1");
        MapSqlParameterSource params = new MapSqlParameterSource();
        if (criteria.getId() != null) {
            sql.append(" AND e.id = :id");
            params.addValue("id", criteria.getId());
        }
        if (criteria.getName() != null && !criteria.getName().isBlank()) {
            sql.append(" AND LOWER(e.name) LIKE LOWER(:name)");
            params.addValue("name", "%" + criteria.getName().trim() + "%");
        }
        if (criteria.getMinAge() != null) {
            sql.append(" AND e.age >= :minAge");
            params.addValue("minAge", criteria.getMinAge());
        }
        if (criteria.getMaxAge() != null) {
            sql.append(" AND e.age <= :maxAge");
            params.addValue("maxAge", criteria.getMaxAge());
        }
        if (criteria.getMinSalary() != null) {
            sql.append(" AND e.salary > :minSalary");
            params.addValue("minSalary", criteria.getMinSalary());
        }
        if (criteria.getDepartmentId() != null) {
            sql.append(" AND e.department_id = :departmentId");
            params.addValue("departmentId", criteria.getDepartmentId());
        }
        if (criteria.getPositionId() != null) {
            sql.append(" AND e.position_id = :positionId");
            params.addValue("positionId", criteria.getPositionId());
        }
        sql.append(" ORDER BY e.id");
        return namedJdbcTemplate.query(sql.toString(), params, employeeRowMapper);
    }

    public Employee save(Employee employee) {
        if (employee.getId() == null) {
            KeyHolder keyHolder = new GeneratedKeyHolder();
            jdbcTemplate.update(connection -> {
                PreparedStatement statement = connection.prepareStatement(
                        "INSERT INTO employees(name, role, age, salary, department_id, position_id) VALUES (?, ?, ?, ?, ?, ?)",
                        Statement.RETURN_GENERATED_KEYS);
                fillStatement(statement, employee);
                return statement;
            }, keyHolder);
            Number key = keyHolder.getKey();
            if (key != null) employee.setId(key.intValue());
        } else {
            jdbcTemplate.update("""
                    UPDATE employees SET name=?, role=?, age=?, salary=?, department_id=?, position_id=?
                    WHERE id=?
                    """, employee.getName(), employee.getRole(), employee.getAge(), employee.getSalary(),
                    employee.getDepartmentId(), employee.getPositionId(), employee.getId());
        }
        return findById(employee.getId()).orElse(employee);
    }

    private void fillStatement(PreparedStatement statement, Employee employee) throws java.sql.SQLException {
        statement.setString(1, employee.getName());
        statement.setString(2, employee.getRole());
        statement.setInt(3, employee.getAge());
        statement.setDouble(4, employee.getSalary());
        statement.setInt(5, employee.getDepartmentId());
        statement.setInt(6, employee.getPositionId());
    }

    public void deleteById(Integer id) {
        jdbcTemplate.update("DELETE FROM employees WHERE id = ?", id);
    }

    public List<LookupOption> findDepartments() {
        return jdbcTemplate.query("SELECT id, name FROM departments ORDER BY name",
                (rs, rowNum) -> new LookupOption(rs.getInt("id"), rs.getString("name")));
    }

    public List<LookupOption> findPositions() {
        return jdbcTemplate.query("SELECT id, title FROM positions ORDER BY id",
                (rs, rowNum) -> new LookupOption(rs.getInt("id"), rs.getString("title")));
    }

    public List<DepartmentStat> findDepartmentStats() {
        return jdbcTemplate.query("""
                SELECT d.id, d.name, COUNT(e.id) AS employee_count,
                       COALESCE(AVG(e.salary), 0) AS average_salary,
                       COALESCE(SUM(e.salary), 0) AS total_salary
                FROM departments d
                LEFT JOIN employees e ON e.department_id = d.id
                GROUP BY d.id, d.name
                ORDER BY d.id
                """, (rs, rowNum) -> new DepartmentStat(rs.getInt("id"), rs.getString("name"),
                rs.getLong("employee_count"), rs.getDouble("average_salary"), rs.getDouble("total_salary")));
    }

    public List<Employee> findTopPaidByDepartment(Integer departmentId, int limit) {
        return jdbcTemplate.query(selectWithRelations()
                        + " WHERE e.department_id = ? ORDER BY e.salary DESC LIMIT ?",
                employeeRowMapper, departmentId, limit);
    }

    public List<LookupOption> findEmptyDepartments() {
        return jdbcTemplate.query("""
                SELECT d.id, d.name FROM departments d
                LEFT JOIN employees e ON e.department_id = d.id
                WHERE e.id IS NULL ORDER BY d.name
                """, (rs, rowNum) -> new LookupOption(rs.getInt("id"), rs.getString("name")));
    }
}
