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

    public Product(String name, String description, int stockQuantity, double price, int categoryCode) {
        this.name = name;
        this.description = description;
        this.stockQuantity = stockQuantity;
        this.price = price;
        this.categoryCode = categoryCode;
    }

    public Product() {

    }

    public Product(int code, String name, String description, int stockQuantity, double price, int categoryCode) {
        this.code = code;
        this.name = name;
        this.description = description;
        this.stockQuantity = stockQuantity;
        this.price = price;
        this.categoryCode = categoryCode;
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

    public double getPrice() {
        return price;
    }
    public int getCategoryCode() {return categoryCode;}

    public void updateStock(int quantity) {
        this.stockQuantity += quantity;
    }

    public double getCurrentPrice() {
        return price;
    }

    public List<Product> getProductsByCategory(String categoryName) {
        CategoryDAO categoryDAO = new CategoryDAO();
        int catCode = categoryDAO.getCategoryCodeByName(categoryName);
        ProductDAO productDAO = new ProductDAO();
        return productDAO.getProductsByCategoryCode(catCode);
    }
}


