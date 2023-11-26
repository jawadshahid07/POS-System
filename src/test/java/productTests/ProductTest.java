package productTests;

import business.productCatalog.Product;
import dao.ProductDAO;
import org.junit.Test;

import java.io.File;
import java.io.FileNotFoundException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import static org.junit.Assert.*;

public class ProductTest {

    @Test
    public void testUpdateStock() {
        Product product = new Product("TestProduct", "Description", 10, 20.0, 1);
        product.updateStock(5);
        assertEquals(15, product.getStockQuantity());
    }

    @Test
    public void testGetProductsByCategory() {
        Product product1 = new Product("Product1", "Description1", 10, 20.0, 1);

        ProductDAO productDAO = new ProductDAO();
        productDAO.addProduct(product1);

        List<Product> products = product1.getProductsByCategory("All Categories");
        boolean flag = false;
        for (Product product : products) {
            if (product.getName().equals("Product1")) {
                flag = true;
                break;
            }
        }
        assertTrue(flag);

        try {
            product1 = productDAO.getProductByName("Product1");
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        productDAO.removeProduct(product1.getCode());
    }

    @Test
    public void testFilterExpiredProducts() {
        Product product1 = new Product("ExpiredProduct", "Description", 10, 20.0, 1);
        Product product2 = new Product("NotExpiredProduct", "Description2", 15, 25.0, 1);

        product1.setExpirationDate("2022-01-01");
        product2.setExpirationDate("2025-01-01");

        ProductDAO productDAO = new ProductDAO();
        productDAO.addProduct(product1);
        productDAO.addProduct(product2);

        ArrayList<Product> expiredProducts = product1.filterExpiredProducts();
        boolean expiredFlag = false, notExpiredFlag = true;
        for (Product product : expiredProducts) {
            if (product.getName().equals("ExpiredProduct")) {
                expiredFlag = true;
            }
            if (product.getName().equals("NotExpiredProduct")) {
                notExpiredFlag = false;
            }
        }
        assertTrue(expiredFlag);
        assertTrue(notExpiredFlag);


        try {
            product1 = productDAO.getProductByName("ExpiredProduct");
            product2 = productDAO.getProductByName("NotExpiredProduct");
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        productDAO.removeProduct(product1.getCode());
        productDAO.removeProduct(product2.getCode());
    }
}
