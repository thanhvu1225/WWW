-- 1. Tạo cơ sở dữ liệu hrdb (nếu chưa có) và chọn database này
CREATE DATABASE IF NOT EXISTS hrdb CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci;
USE hrdb;

-- 2. Xóa bảng cũ nếu đã tồn tại (xóa employees trước vì có khóa ngoại)
DROP TABLE IF EXISTS employees;
DROP TABLE IF EXISTS departments;

-- 3. Tạo bảng departments (Phòng ban)
CREATE TABLE departments (
    id INT AUTO_INCREMENT PRIMARY KEY,
    name VARCHAR(100) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

-- 4. Tạo bảng employees (Nhân viên)
CREATE TABLE employees (
    id INT AUTO_INCREMENT PRIMARY KEY,
    name VARCHAR(100) NOT NULL,
    department_id INT,
    salary DOUBLE DEFAULT 0,
    CONSTRAINT fk_employees_departments 
        FOREIGN KEY (department_id) 
        REFERENCES departments(id) 
        ON DELETE SET NULL 
        ON UPDATE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

-- 5. Thêm dữ liệu mẫu cho bảng departments
INSERT INTO departments (id, name) VALUES
(1, 'Information Technology'),
(2, 'Human Resources'),
(3, 'Finance & Accounting'),
(4, 'Marketing & Sales');

-- 6. Thêm dữ liệu mẫu cho bảng employees
INSERT INTO employees (name, department_id, salary) VALUES
('Nguyễn Văn An', 1, 15000000),
('Trần Thị Bích', 1, 22000000),
('Lê Hoàng Long', 2, 12000000),
('Phạm Minh Đức', 3, 18000000),
('Võ Thu Thảo', 4, 14000000),
('Đặng Quốc Huy', 1, 25000000);

-- 7. Kiểm tra dữ liệu vừa tạo
SELECT * FROM departments;
SELECT * FROM employees;