package business.orderProcessing;

import business.productCatalog.Product;
import dao.CategoryDAO;
import dao.ProductDAO;

import java.util.ArrayList;
import java.util.List;

public class Cart extends ItemContainer {

    public void clear() {
        items = new ArrayList<>();
    }

    public void generateOrder() {

    }

    public List<Product> searchProducts(String searchText, String categoryName) {
        CategoryDAO categoryDAO = new CategoryDAO();
        int categoryCode = categoryDAO.getCategoryCodeByName(categoryName);
        ProductDAO productDAO = new ProductDAO();
        List<Product> searchProducts = productDAO.getProductsBySearchNameCategoryCode(searchText, categoryCode);
        return searchProducts;
    }
}
