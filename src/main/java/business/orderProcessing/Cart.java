package business.orderProcessing;

import business.productCatalog.Product;
import dao.CategoryDAO;
import dao.ProductDAO;

import java.util.ArrayList;
import java.util.List;

public class Cart extends ItemContainer {

    public Cart() {
        super();
    }
    public void clear() {
        items = new ArrayList<>();
    }

    public Order generateOrder() {
        ProductDAO productDAO = new ProductDAO();
        for (Item i : items) {
            Product product = productDAO.getProductById(i.getProduct().getCode());
            if (i.getQuantityOrdered() > product.getStockQuantity()) {
                return null;
            }
        }
        return new Order(items);
    }

    public List<Product> searchProducts(String searchText, String categoryName) {
        CategoryDAO categoryDAO = new CategoryDAO();
        int categoryCode = categoryDAO.getCategoryCodeByName(categoryName);
        ProductDAO productDAO = new ProductDAO();
        List<Product> searchProducts = productDAO.getProductsBySearchNameCategoryCode(searchText, categoryCode);
        return searchProducts;
    }
}
