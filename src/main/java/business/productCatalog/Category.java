package business.productCatalog;

import dao.CategoryDAO;
import dao.ProductDAO;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class Category {
    private int code;
    private String name;
    private String description;
    private Set<Product> items;
    private Set<Category> subcategories;

    public Category(String name, String description) {
        this.name = name;
        this.description = description;
        this.items = new HashSet<>();
        this.subcategories = new HashSet<>();
    }
    public Category(int code, String name, String description) {
        this.code = code;
        this.name = name;
        this.description = description;
        this.items = new HashSet<>();
        this.subcategories = new HashSet<>();
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

    public Set<Product> getItems() {
        return items;
    }

    public Set<Category> getSubcategories() {
        return subcategories;
    }

    public void addCategory(Category category) {
        CategoryDAO categoryDAO = new CategoryDAO();
        categoryDAO.addCategory(category);
    }

    public void removeCategory(Category category) {
        CategoryDAO categoryDAO = new CategoryDAO();
        categoryDAO.removeCategory(category.getCode());
    }

    public void addItem(Product item) {
        items.add(item);
        for (Item productItem : item.getItems()) {
            items.add(productItem.getProduct());
        }
    }

    public void removeItem(Product item) {
        items.remove(item);
        for (Item productItem : item.getItems()) {
            items.remove(productItem.getProduct());
        }
    }

    public void setProducts(Set<Product> products) {
        this.items = products;
    }

    public static void addProduct(String name, String description, int quantity, double price, List<String> categoryNames) {
        ProductDAO productDAO = new ProductDAO();
        //Product p = new Product();
        //productDAO.addProduct(new Product);
    }
}


