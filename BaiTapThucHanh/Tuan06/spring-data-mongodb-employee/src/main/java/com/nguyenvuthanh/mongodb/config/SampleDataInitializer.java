package com.nguyenvuthanh.mongodb.config;

import com.nguyenvuthanh.mongodb.model.Department;
import com.nguyenvuthanh.mongodb.model.Employee;
import com.nguyenvuthanh.mongodb.model.Position;
import com.nguyenvuthanh.mongodb.model.User;
import com.nguyenvuthanh.mongodb.repository.DepartmentRepository;
import com.nguyenvuthanh.mongodb.repository.EmployeeRepository;
import com.nguyenvuthanh.mongodb.repository.PositionRepository;
import com.nguyenvuthanh.mongodb.repository.UserRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.List;

@Configuration
public class SampleDataInitializer {
    @Bean
    CommandLineRunner seedMongoData(DepartmentRepository departments, PositionRepository positions,
                                    EmployeeRepository employees, UserRepository userRepository,
                                    PasswordEncoder passwordEncoder) {
        return args -> {
            if (departments.count() == 0) {
                departments.saveAll(List.of(
                        new Department(1, "Công nghệ thông tin"), new Department(2, "Nhân sự"),
                        new Department(3, "Kinh doanh"), new Department(4, "Kế toán")));
            }
            if (positions.count() == 0) {
                positions.saveAll(List.of(
                        new Position(1, "Nhân viên"), new Position(2, "Trưởng nhóm"),
                        new Position(3, "Trưởng phòng"), new Position(4, "Thực tập sinh")));
            }
            if (employees.count() == 0) {
                employees.saveAll(List.of(
                        employee("1", "Nguyễn Hoàng Linh", "Lập trình viên", 28, 22_000_000D, 1, 1),
                        employee("2", "Trần Phan Hòa Bình", "Lập trình viên", 31, 26_000_000D, 1, 2),
                        employee("3", "Lê Minh Cường", "Quản lý", 42, 45_000_000D, 1, 3),
                        employee("4", "Phạm Thu Dung", "Tuyển dụng", 27, 15_000_000D, 2, 1),
                        employee("5", "Hoàng Văn Em", "Quản lý", 39, 38_000_000D, 2, 3),
                        employee("6", "Vũ Thị Phương", "Kinh doanh", 25, 14_000_000D, 3, 4),
                        employee("7", "Đặng Quốc Gia", "Kinh doanh", 30, 20_000_000D, 3, 1),
                        employee("8", "Bùi Thanh Hà", "Kinh doanh", 34, 28_000_000D, 3, 2),
                        employee("9", "Ngô Đức Huy", "Lập trình viên", 23, 9_000_000D, 1, 4),
                        employee("10", "Dương Gia Thảo", "Kế toán", 36, 19_500_000D, 4, 1)));
            }
            // Seed user accounts from BaiTap WWW
            if (userRepository.count() == 0) {
                userRepository.saveAll(List.of(
                        new User("1", "admin1", passwordEncoder.encode("123"), "ADMIN"),
                        new User("2", "admin2", passwordEncoder.encode("123"), "ADMIN"),
                        new User("3", "user1", passwordEncoder.encode("111"), "USER"),
                        new User("4", "user2", passwordEncoder.encode("222"), "USER"),
                        new User("5", "guest1", passwordEncoder.encode("333"), "GUEST"),
                        new User("6", "guest2", passwordEncoder.encode("444"), "GUEST")
                ));
            }
        };
    }

    private static Employee employee(String id, String name, String role, int age, double salary,
                                     int departmentId, int positionId) {
        Employee employee = new Employee();
        employee.setId(id); employee.setName(name); employee.setRole(role); employee.setAge(age);
        employee.setSalary(salary); employee.setDepartmentId(departmentId); employee.setPositionId(positionId);
        return employee;
    }
}
