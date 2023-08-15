CREATE TABLE employee (
    employee_id INT PRIMARY KEY AUTO_INCREMENT,
    pet_store_id INT,
    employee_first_name VARCHAR(255) NOT NULL,
    employee_last_name VARCHAR(255) NOT NULL,
    employee_phone VARCHAR(20) NOT NULL,
    employee_job_title VARCHAR(255) NOT NULL,
    FOREIGN KEY (pet_store_id) REFERENCES pet_store(pet_store_id)

CREATE TABLE customer (
    customer_id INT PRIMARY KEY AUTO_INCREMENT,
    customer_first_name VARCHAR(255) NOT NULL,
    customer_last_name VARCHAR(255) NOT NULL,
    customer_phone VARCHAR(20) NOT NULL

CREATE TABLE pet_store_customer (
    pet_store_id INT,
    customer_id INT,
    PRIMARY KEY (pet_store_id, customer_id),
    FOREIGN KEY (pet_store_id) REFERENCES pet_store(pet_store_id),
    FOREIGN KEY (customer_id) REFERENCES customer(customer_id)
);


