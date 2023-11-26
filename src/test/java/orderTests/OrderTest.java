package orderTests;

import business.orderProcessing.Item;
import business.orderProcessing.Order;
import business.productCatalog.Product;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.*;

public class OrderTest {

    @Test
    public void testOrderCreation() {
        List<Item> items = createSampleItems();

        Order order = new Order(items);

        assertNotNull(order);
        assertEquals(3, order.getItemsList().size());
        assertTrue(order.getStatus());
    }

    @Test
    public void testOrderCancellation() {
        List<Item> items = createSampleItems();

        Order order = new Order(items);

        order.cancel();

        assertFalse(order.getStatus());
    }


    private List<Item> createSampleItems() {
        List<Item> items = new ArrayList<>();

        Product product1 = new Product(1, "Product1", "Description1", 10, 20.0, 1);

        Item item1 = new Item(product1, 3);

        Product product2 = new Product(2, "Product2", "Description2", 15, 25.0, 1);

        Item item2 = new Item(product2, 2);

        Product product3 = new Product(3, "Product3", "Description3", 8, 30.0, 1);

        Item item3 = new Item(product3, 1);

        items.add(item1);
        items.add(item2);
        items.add(item3);

        return items;
    }
}

