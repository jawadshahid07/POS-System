package productTests;

import business.productCatalog.Product;
import dao.ProductDAO;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.*;

public class ProductTest {

    @Test
    public void testGetAlertQuantity() {
        Product product = new Product();
        product.setAlertQuantity(10);
        assertEquals(10, product.getAlertQuantity());
    }

    @Test
    public void testSetExpirationDate() {
        Product product = new Product();
        product.setExpirationDate("2023-12-31");
        assertEquals("2023-12-31", product.getExpirationDate());
    }

    @Test
    public void testUpdateStock() {
        Product product = new Product("TestProduct", "Description", 10, 20.0, 1);
        product.updateStock(5);
        assertEquals(15, product.getStockQuantity());
    }

    @Test
    public void testGetProductsByCategory() {
        Product product1 = new Product("Product1", "Description1", 10, 20.0, 1);
        Product product2 = new Product("Product2", "Description2", 15, 25.0, 2);

        ProductDAO productDAO = new ProductDAO();
        productDAO.addProduct(product1);
        productDAO.addProduct(product2);

        List<Product> products = product1.getProductsByCategory("All Categories");
        assertTrue(products.contains(product1));
        assertTrue(products.contains(product2));

        // Clean up the test data
        productDAO.removeProduct(product1.getCode());
        productDAO.removeProduct(product2.getCode());
    }

    @Test
    public void testFilterExpiredProducts() {
        Product product1 = new Product("ExpiredProduct1", "Description", 10, 20.0, 1);
        Product product2 = new Product("NotExpiredProduct2", "Description", 15, 25.0, 2);

        // Set an expired date for product1
        product1.setExpirationDate("2022-01-01");

        ProductDAO productDAO = new ProductDAO();
        productDAO.addProduct(product1);
        productDAO.addProduct(product2);

        ArrayList<Product> expiredProducts = product1.filterExpiredProducts();
        assertTrue(expiredProducts.contains(product1));
        assertFalse(expiredProducts.contains(product2));

        // Clean up the test data
        productDAO.removeProduct(product1.getCode());
        productDAO.removeProduct(product2.getCode());
    }
}

