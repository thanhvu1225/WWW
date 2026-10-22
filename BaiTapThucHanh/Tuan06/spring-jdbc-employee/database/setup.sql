CREATE DATABASE IF NOT EXISTS employeedb CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci;
USE employeedb;

CREATE TABLE IF NOT EXISTS departments (
    id INT PRIMARY KEY AUTO_INCREMENT,
    name VARCHAR(255) NOT NULL
);

CREATE TABLE IF NOT EXISTS positions (
    id INT PRIMARY KEY AUTO_INCREMENT,
    title VARCHAR(255) NOT NULL
);

CREATE TABLE IF NOT EXISTS employees (
    id INT PRIMARY KEY AUTO_INCREMENT,
    name VARCHAR(255) NOT NULL,
    role VARCHAR(255),
    age INT,
    salary DOUBLE,
    department_id INT,
    position_id INT,
    CONSTRAINT fk_employee_department FOREIGN KEY (department_id) REFERENCES departments(id),
    CONSTRAINT fk_employee_position FOREIGN KEY (position_id) REFERENCES positions(id)
);

INSERT INTO departments(id, name) VALUES
    (1, 'Công nghệ thông tin'), (2, 'Nhân sự'), (3, 'Kinh doanh'), (4, 'Kế toán')
ON DUPLICATE KEY UPDATE name = VALUES(name);

INSERT INTO positions(id, title) VALUES
    (1, 'Nhân viên'), (2, 'Trưởng nhóm'), (3, 'Trưởng phòng'), (4, 'Thực tập sinh')
ON DUPLICATE KEY UPDATE title = VALUES(title);

INSERT INTO employees(id, name, role, age, salary, department_id, position_id) VALUES
    (1, 'Nguyễn Hoàng Linh', 'Lập trình viên', 28, 22000000, 1, 1),
    (2, 'Trần Phan Hòa Bình', 'Lập trình viên', 31, 26000000, 1, 2),
    (3, 'Lê Minh Cường', 'Quản lý', 42, 45000000, 1, 3),
    (4, 'Phạm Thu Dung', 'Tuyển dụng', 27, 15000000, 2, 1),
    (5, 'Hoàng Văn Em', 'Quản lý', 39, 38000000, 2, 3),
    (6, 'Vũ Thị Phương', 'Kinh doanh', 25, 14000000, 3, 4),
    (7, 'Đặng Quốc Gia', 'Kinh doanh', 30, 20000000, 3, 1),
    (8, 'Bùi Thanh Hà', 'Kinh doanh', 34, 28000000, 3, 2),
    (9, 'Ngô Đức Huy', 'Lập trình viên', 23, 9000000, 1, 4),
    (10, 'Dương Gia Thảo', 'Kế toán', 36, 19500000, 4, 1)
ON DUPLICATE KEY UPDATE name = VALUES(name), role = VALUES(role), age = VALUES(age),
                        salary = VALUES(salary), department_id = VALUES(department_id), position_id = VALUES(position_id);
