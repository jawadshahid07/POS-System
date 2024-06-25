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

-- for maintaining categories in database
CREATE TABLE categories (
    code INT AUTO_INCREMENT PRIMARY KEY,
    name VARCHAR(255),
    description VARCHAR(255)
);
-- for maintaining products in database
CREATE TABLE products (
    code INT AUTO_INCREMENT PRIMARY KEY,
    name VARCHAR(255),
    description VARCHAR(255),
    stockQuantity INT,
    price DECIMAL(10, 2),
    categoryCode INT,
    alertQuantity INT, 
    dateTracked VARCHAR(255),
    FOREIGN KEY (categoryCode) REFERENCES categories(code) ON UPDATE CASCADE ON DELETE CASCADE
);

CREATE TABLE orders (
    orderID INT AUTO_INCREMENT PRIMARY KEY,
    totalAmount DOUBLE,
    orderDate VARCHAR(255)
);

CREATE TABLE orderedItems (
    itemID INT PRIMARY KEY AUTO_INCREMENT,
    orderID INT,
    productCode INT ,
    quantityOrdered INT,
    FOREIGN KEY (orderID) REFERENCES orders(orderID) ON UPDATE CASCADE ON DELETE CASCADE,
    FOREIGN KEY (productcode) REFERENCES products(code) ON UPDATE CASCADE ON DELETE CASCADE
);