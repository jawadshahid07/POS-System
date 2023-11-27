# Pharmacy POS System

Welcome to the Pharmacy Point of Sale (POS) System! This application is designed to streamline the operations of a pharmacy, offering user-friendly interfaces for both managers and sales assistants. Below, you'll find details on the features and functionalities of the system.

## Table of Contents

1. [Features](#features)
2. [User Authentication](#user-authentication)
3. [Product Catalog](#product-catalog)
4. [Inventory Management](#inventory-management)
5. [Generate Reports](#generate-reports)
6. [Sales Assistant Interface](#sales-assistant-interface)
7. [Manager Interface](#manager-interface)
8. [Getting Started](#getting-started)
9. [Tech Stack](#tech-stack)
10. [Contributing](#contributing)
11. [License](#license)

## Features

### 1. User Authentication

- **Login Screen:** A login screen prompts users to enter their username and password.
- **Role-Based UI:** Based on credentials, the system displays either the Sales Assistant or Manager UI.

### 2. Product Catalog

- **Category Management:**
  - Add or delete categories (code, name, description).
- **Product Management:**
  - List all products sorted by category.
  - Add, edit, or delete product details (code, name, description, price, stock quantity).

### 3. Inventory Management

- **Product List:**
  - Display a list of products with expiration date and alert quantity.
- **Expired Items:**
  - View and delete a list of expired items.
- **Set Alert Quantity:**
  - Set the alert quantity for products.
- **Restock Products:**
  - Read and update product quantities from an external CSV file.

### 4. Generate Reports

- **Sales Reports:**
  - Generate PDF reports for daily, weekly, or monthly sales.
  - Tables include items for each order.
- **Inventory Reports:**
  - Generate PDF reports with a table of product details (code, name, description, price, quantity, expiration date).

### 5. Sales Assistant Interface

- **Product Search:**
  - Search and select products using a search bar and category dropdown.
- **Shopping Cart:**
  - Add, remove, and clear items in the cart.
  - Display total amount for confirmation.
- **Order Processing:**
  - Enter customer name and confirm order.
  - Generate a PDF receipt with order details.

### 6. Manager Interface

- **Main Menu:**
  - Buttons to navigate to Product Catalog, Inventory Management, or Generate Reports.
  - Role-specific access control.

## Getting Started

1. Clone the repository.
2. Open the project in your preferred Java IDE.
3. Run the MySQL query using a suitable database tool such as MySQL workbench.
4. Edit the JDBC credentials (server url, username, password) in the DbConnection class to your credentials for the MySQL server url, username, password.
5. Run the application.
6. Log in using the provided sample users' credentials.

- **Manager**
  - **Username:** admin
  - **Password:** admin

- **Sales Assistant**
  - **Username:** staff
  - **Password:** staff

## Tech Stack

- **Java:** Core language for application development.
- **Java Swing:** GUI library for creating interactive interfaces.
- **JDBC:** For database connectivity using MySQL
- **MySQL:** Database access

## Contributing

Contributions are welcome! Please follow the [contribution guidelines](CONTRIBUTING.md).

## License

This project is licensed under the [MIT License](LICENSE).

Feel free to reach out for any questions or support. Happy coding!
