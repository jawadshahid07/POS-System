package business.productCatalog;

import java.util.HashSet;
import java.util.Set;

public class Category {
    private int code;
    private String name;
    private String description;
    private Set<Product> items;
    private Set<Category> subcategories;

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
        subcategories.add(category);
    }

    public void removeCategory(Category category) {
        subcategories.remove(category);
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
}


