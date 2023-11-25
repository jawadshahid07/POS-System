package business.productCatalog;

import dao.CategoryDAO;
import dao.ProductDAO;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class Category {
    private int code;
    private String name;
    private String description;

    public Category(String name, String description) {
        this.name = name;
        this.description = description;
    }
    public Category(int code, String name, String description) {
        this.code = code;
        this.name = name;
        this.description = description;
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


    public void addCategory(Category category) {
        CategoryDAO categoryDAO = new CategoryDAO();
        categoryDAO.addCategory(category);
    }

    public void removeCategory(Category category) {
        CategoryDAO categoryDAO = new CategoryDAO();
        categoryDAO.removeCategory(category.getCode());
    }

    public boolean addProduct(Product product) {
        ProductDAO productDAO = new ProductDAO();
        List<Product> products = productDAO.getAllProducts();
        for (Product p: products) {
            if (p.getName().equals(product.getName())) {
                return false;
            }
        }
        productDAO.addProduct(product);
        return true;
    }
    public void removeProduct(int productID){
        ProductDAO productDAO = new ProductDAO();
        productDAO.removeProduct(productID);
    }

    public void editProduct(Product product) {
        ProductDAO productDAO = new ProductDAO();
        productDAO.editProduct(product);
    }

    public int getCategoryCode(String categoryName) {
        CategoryDAO categoryDAO = new CategoryDAO();
        return categoryDAO.getCategoryCodeByName(categoryName);
    }

    public List<Category> loadCategories() {
        CategoryDAO categoryDAO = new CategoryDAO();
        List<Category> allCategories = categoryDAO.getAllCategories();

        return allCategories;
    }
}


