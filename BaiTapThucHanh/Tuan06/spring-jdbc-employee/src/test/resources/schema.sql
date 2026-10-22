CREATE TABLE departments (id INT AUTO_INCREMENT PRIMARY KEY, name VARCHAR(255) NOT NULL);
CREATE TABLE positions (id INT AUTO_INCREMENT PRIMARY KEY, title VARCHAR(255) NOT NULL);
CREATE TABLE employees (
    id INT AUTO_INCREMENT PRIMARY KEY, name VARCHAR(255) NOT NULL, role VARCHAR(255), age INT, salary DOUBLE,
    department_id INT, position_id INT,
    FOREIGN KEY (department_id) REFERENCES departments(id),
    FOREIGN KEY (position_id) REFERENCES positions(id)
);
