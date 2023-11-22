CREATE DATABASE IF NOT EXISTS pos_system;
USE pos_system;

-- for maintaining user data
CREATE TABLE users (
    id INT PRIMARY KEY AUTO_INCREMENT,
    name VARCHAR(255),
    username VARCHAR(255) UNIQUE,
    password VARCHAR(255),
    role VARCHAR(255)
);

-- insert admin user
INSERT INTO users (name, username, password, role) VALUES ('Admin', 'admin', 'admin', 'Manager');

-- insert staff user
INSERT INTO users (name, username, password, role) VALUES ('Staff', 'staff', 'staff', 'SalesAssistant');

-- for maintaining products in database
CREATE TABLE products (
    code INT AUTO_INCREMENT PRIMARY KEY,
    name VARCHAR(255),
    description VARCHAR(255),
    stockQuantity INT,
    price DECIMAL(10, 2)
);

-- for maintaining categories in database
CREATE TABLE categories (
    code INT AUTO_INCREMENT PRIMARY KEY,
    name VARCHAR(255),
    description VARCHAR(255)
);

-- intermediate table for many-to-many relationship: maintains all categories of all products.
CREATE TABLE product_category (
    productCode INT,
    categoryCode INT,
    PRIMARY KEY (productCode, categoryCode),
    FOREIGN KEY (productCode) REFERENCES products(code),
    FOREIGN KEY (categoryCode) REFERENCES categories(code)
);
