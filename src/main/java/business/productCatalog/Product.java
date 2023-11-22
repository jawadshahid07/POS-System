package business.productCatalog;

import java.util.HashSet;
import java.util.Set;

public class Product {
    private int code;
    private String name;
    private String description;
    private int stockQuantity;
    private double price;
    private Set<Category> categories;
    private Set<Item> items;

    public Product(int code, String name, String description, int stockQuantity, double price) {
        this.code = code;
        this.name = name;
        this.description = description;
        this.stockQuantity = stockQuantity;
        this.price = price;
        this.categories = new HashSet<>();
        this.items = new HashSet<>();
    }

    public int getCode() {
        return code;
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

    public Set<Category> getCategories() {
        return categories;
    }

    public Set<Item> getItems() {
        return items;
    }

    public void updateStock(int quantity) {
        this.stockQuantity += quantity;
    }

    public double getCurrentPrice() {
        return price;
    }

    public void addCategory(Category category) {
        categories.add(category);
        category.addItem(this);
    }

    public void removeCategory(Category category) {
        categories.remove(category);
        category.removeItem(this);
    }

    public void addItem(Item item) {
        items.add(item);
    }

    public void removeItem(Item item) {
        items.remove(item);
    }
}


