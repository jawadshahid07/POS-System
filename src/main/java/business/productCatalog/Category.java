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
    private Set<Product> products;
    private Set<Category> subcategories;

    public Category(String name, String description) {
        this.name = name;
        this.description = description;
        this.products = new HashSet<>();
        this.subcategories = new HashSet<>();
    }
    public Category(int code, String name, String description) {
        this.code = code;
        this.name = name;
        this.description = description;
        this.products = new HashSet<>();
        this.subcategories = new HashSet<>();
    }

    public Category() {

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

    public Set<Product> getProducts() {
        return products;
    }

    public Set<Category> getSubcategories() {
        return subcategories;
    }
    public void setProducts(Set<Product> products) {
        this.products = products;
    }

    public void addCategory(Category category) {
        CategoryDAO categoryDAO = new CategoryDAO();
        categoryDAO.addCategory(category);
    }

    public void removeCategory(Category category) {
        CategoryDAO categoryDAO = new CategoryDAO();
        categoryDAO.removeCategory(category.getCode());
    }

    public void addProduct(Product product) {
        ProductDAO productDAO = new ProductDAO();
        productDAO.addProduct(product);
    }
    public void removeProduct(int productID){
        ProductDAO productDAO = new ProductDAO();
        productDAO.removeProduct(productID);
    }

    public int getCategoryCode(String categoryName) {
        CategoryDAO categoryDAO = new CategoryDAO();
        return categoryDAO.getCategoryCodeByName(categoryName);
    }
}


