package orderTests;

import business.orderProcessing.Item;
import business.productCatalog.Product;
import org.junit.Test;

import static org.junit.Assert.*;

public class ItemTest {

    @Test
    public void testItemCreation() {
        Product product = new Product(1, "TestProduct", "TestDescription", 10, 25.0, 1);

        Item item = new Item(product, 3);

        assertNotNull(item);
        assertEquals(product, item.getProduct());
        assertEquals(3, item.getQuantityOrdered());
        double delta = 0.001;
        assertEquals(25.0, item.getPrice(), delta);
    }

    @Test
    public void testTotalCalculation() {
        Product product = new Product(1, "TestProduct", "TestDescription", 10, 25.0, 1);

        Item item = new Item(product, 3);

        double delta = 0.001;
        assertEquals(75.0, item.total(), delta);
    }

    @Test
    public void testUpdateQuantity() {
        Product product = new Product(1, "TestProduct", "TestDescription", 10, 25.0, 1);

        Item item = new Item(product, 3);

        item.updateQuantity(5);

        assertEquals(5, item.getQuantityOrdered());
    }
}

