package business.productCatalog;

import dao.CategoryDAO;
import dao.ProductDAO;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class Product {
    private int code;
    private String name;
    private String description;
    private int stockQuantity;
    private double price;
    private int categoryCode;
    private int alertQuantity;
    private String expirationDate;


    public Product(String name, String description, int stockQuantity, double price, int categoryCode) {
        this.name = name;
        this.description = description;
        this.stockQuantity = stockQuantity;
        this.price = price;
        this.categoryCode = categoryCode;
        this.alertQuantity = -1;
    }

    public Product() {
        this.alertQuantity = -1;
    }

    public Product(int code, String name, String description, int stockQuantity, double price, int categoryCode) {
        this.code = code;
        this.name = name;
        this.description = description;
        this.stockQuantity = stockQuantity;
        this.price = price;
        this.categoryCode = categoryCode;
        this.alertQuantity = -1;
    }

    public int getAlertQuantity() {
        return alertQuantity;
    }

    public void setAlertQuantity(int alertQuantity) {
        this.alertQuantity = alertQuantity;
    }
    public String getExpirationDate() {
        return expirationDate;
    }
    public void setExpirationDate(String expirationDate) {
        this.expirationDate = expirationDate;
    }

    public int getCode() {
        return code;
    }
    public void setCode(int code) {
        this.code = code;
    }

    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }

    public int getStockQuantity() {
        return stockQuantity;
    }
    public void setStockQuantity(int stockQuantity) {
        this.stockQuantity = stockQuantity;
    }

    public double getPrice() {
        return price;
    }
    public int getCategoryCode() {return categoryCode;}
    public void setCategoryCode(int categoryCode) {
        this.categoryCode = categoryCode;
    }
    public int getCategoryCodeById(int code) {
        ProductDAO productDAO = new ProductDAO();
        Product product = productDAO.getProductById(code);
        return product.getCategoryCode();
    }

    public Product getProductById(int code) {
        ProductDAO productDAO = new ProductDAO();
        return productDAO.getProductById(code);
    }

    public void updateStock(int quantity) {
        this.stockQuantity += quantity;
    }

    public double getCurrentPrice() {
        return price;
    }

    public List<Product> getProductsByCategory(String categoryName) {
        ProductDAO productDAO = new ProductDAO();
        if (categoryName.equals("All Categories")) {
            return productDAO.getAllProducts();
        }
        else {
            CategoryDAO categoryDAO = new CategoryDAO();
            int catCode = categoryDAO.getCategoryCodeByName(categoryName);
            return productDAO.getProductsByCategoryCode(catCode);
        }
    }

    public void setName(String productName) {
        this.name = productName;
    }

    public void setDescription(String productDescription) {
        this.description = productDescription;
    }
    public void setPrice(double price) {
        this.price = price;
    }
}


