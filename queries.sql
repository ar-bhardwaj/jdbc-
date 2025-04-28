-- # UC 1 - Ability to create a payroll service database

create database payroll_service;

show databases;

use payroll_service;




-- # UC 2 - Ability to create a employee payroll table in the payroll service database

CREATE TABLE employee_payroll (
    id INT unsigned NOT NULL AUTO_INCREMENT,
    name VARCHAR(50),
    salary DOUBLE,
    start_date DATE,
    PRIMARY KEY (id)
);




-- # UC 3 - Ability to create employee payroll data in the payroll service database

INSERT INTO employee_payroll VALUES
    (1, 'Aditi', 0.0, '2000-01-01'),
    (2, 'Arvind', 1.0, '2001-10-01'),
    (3, 'Ayushi', 1.0, '2004-09-10');




-- # UC 4 - Ability to retrieve all the employee payroll data

SELECT * FROM employee_payroll;
-- +--+------+------+----------+
-- |id|name  |salary|start_date|
-- +--+------+------+----------+
-- |1 |Aditi |0     |2000-01-01|
-- |2 |Arvind |1     |2001-10-01|
-- |3 |Ayushi|1     |2004-09-10|
-- +--+------+------+----------+




-- # UC 5 - Ability to retrieve salary data for a particular employee as well as all employees who have joined in a particular data range

SELECT salary FROM employee_payroll WHERE name='Aditi';
-- +------+
-- |salary|
-- +------+
-- |0     |
-- +------+

SELECT * FROM employee_payroll WHERE start_date BETWEEN CAST('2001-01-01' AS DATE) AND DATE(NOW());
-- +--+------+------+----------+
-- |id|name  |salary|start_date|
-- +--+------+------+----------+
-- |2 |Arvind |1     |2001-10-01|
-- |3 |Ayushi|1     |2004-09-10|
-- +--+------+------+----------+




-- # UC 6 - Ability to add Gender to Employee Payroll Table and Update the Rows to reflect the correct Employee Gender

ALTER TABLE employee_payroll ADD COLUMN gender VARCHAR(1);

UPDATE employee_payroll SET gender='M' WHERE id BETWEEN 1 AND 3;
-- +--+------+------+----------+------+
-- |id|name  |salary|start_date|gender|
-- +--+------+------+----------+------+
-- |1 |Aditi |0     |2000-01-01|M     |
-- |2 |Arvind |1     |2001-10-01|M     |
-- |3 |Ayushi|1     |2004-09-10|M     |
-- +--+------+------+----------+------+




-- # UC 7 - Ability to find sum, average, min, max and number of male and female employees

SELECT gender, COUNT(id) FROM employee_payroll GROUP BY gender;
-- +------+---------+
-- |gender|COUNT(id)|
-- +------+---------+
-- |M     |3        |
-- +------+---------+

SELECT SUM(salary) from employee_payroll;
-- +-----------+
-- |SUM(salary)|
-- +-----------+
-- |2          |
-- +-----------+




-- # UC 8 - extend employee_payroll data to store employee information like employee phone, address and department

ALTER TABLE employee_payroll
ADD phone VARCHAR(15),
ADD email VARCHAR(100),
ADD address VARCHAR(255) DEFAULT 'N/A',
ADD department VARCHAR(100) NOT NULL;




-- # UC 9 - extend employee_payroll table to have Basic Pay, Deductions, Taxable Pay, Income Tax, Net Pay

ALTER TABLE employee_payroll
ADD basic_pay DECIMAL(10,2),
ADD deductions DECIMAL(10,2),
ADD taxable_pay DECIMAL(10,2),
ADD income_tax DECIMAL(10,2),
ADD net_pay DECIMAL(10,2);




-- # UC 10 - make Terissa as part of Sales and Marketing Department

INSERT INTO employee_payroll (
    name, gender, salary, start_date, phone, email, address, department,
    basic_pay, deductions, taxable_pay, income_tax, net_pay
) VALUES (
             'Terissa', 'F', 85000.00, '2022-05-01', '1234567890', 'terissa@example.com', '123 Elm St', 'Sales',
             80000.00, 5000.00, 75000.00, 10000.00, 65000.00
         ), (
    'Terissa', 'F', 85000.00, '2022-05-01', '1234567890', 'terissa@example.com', '123 Elm St', 'Marketing',
    80000.00, 5000.00, 75000.00, 10000.00, 65000.00
);