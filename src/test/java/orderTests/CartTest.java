package orderTests;

import business.orderProcessing.Cart;
import business.orderProcessing.Item;
import business.orderProcessing.Order;
import business.productCatalog.Product;
import dao.CategoryDAO;
import dao.ProductDAO;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.*;

public class CartTest {

    @Test
    public void testCartOperations() {
        Cart cart = new Cart();

        List<Product> products = createSampleProducts();

        Product product1 = products.get(0);
        Product product2 = products.get(1);

        Item item1 = new Item(product1, 3);
        Item item2 = new Item(product2, 2);

        cart.add(item1);
        cart.add(item2);

        assertEquals(2, cart.getItemsList().size());

        cart.update(item1, 5);

        assertEquals(5, item1.getQuantityOrdered());

        cart.remove(item2);

        assertEquals(1, cart.getItemsList().size());

        cart.clear();

        assertTrue(cart.getItemsList().isEmpty());
    }

    @Test
    public void testGenerateOrderSuccess() {
        Cart cart = new Cart();

        List<Product> products = createSampleProducts();

        Product product1 = products.get(0);
        Product product2 = products.get(1);

        Item item1 = new Item(product1, 3);
        Item item2 = new Item(product2, 2);

        cart.add(item1);
        cart.add(item2);

        Order order = cart.generateOrder();

        assertNotNull(order);
    }

    @Test
    public void testGenerateOrderFail() {
        Cart cart = new Cart();

        List<Product> products = createSampleProducts();

        Product product1 = products.get(0);
        Product product2 = products.get(1);

        Item item1 = new Item(product1, 5000);
        Item item2 = new Item(product2, 5);

        cart.add(item1);
        cart.add(item2);

        Order order = cart.generateOrder();

        assertNull(order);
    }

    @Test
    public void testSearchProducts() {
        Cart cart = new Cart();

        List<Product> searchResults = cart.searchProducts("Product 1", "Category 1");

        assertEquals(1, searchResults.size());
        assertEquals("Product 1", searchResults.get(0).getName());
    }

    private List<Product> createSampleProducts() {
        List<Product> products = new ArrayList<>();

        Product product1 = new Product();
        product1.setCode(4);
        products.add(product1);

        Product product2 = new Product();
        product2.setCode(5);
        products.add(product2);


        return products;
    }
}

