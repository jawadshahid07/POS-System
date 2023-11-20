CREATE DATABASE pos_system;
USE pos_system;

CREATE TABLE users (
    id INT PRIMARY KEY AUTO_INCREMENT,
    name VARCHAR(255),
    username VARCHAR(255) UNIQUE,
    password VARCHAR(255),
    role VARCHAR(255)
);

-- Insert admin user
INSERT INTO users (name, username, password, role) VALUES ('Admin', 'admin', 'admin', 'Manager');

-- Insert staff user
INSERT INTO users (name, username, password, role) VALUES ('Staff', 'staff', 'staff', 'SalesAssistant');

