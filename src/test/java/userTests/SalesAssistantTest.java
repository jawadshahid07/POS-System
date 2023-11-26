package userTests;

import business.orderProcessing.Item;
import business.orderProcessing.Order;
import business.productCatalog.Product;
import business.userAuth.Role;
import business.userAuth.SalesAssistant;
import dao.OrderDAO;
import dao.ProductDAO;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.*;

public class SalesAssistantTest {

//    @Test
//    public void testProcessOrder() {
//        SalesAssistant salesAssistant = new SalesAssistant();
//        Product product = new Product("TestProduct", "Description", 10, 5, 1);
//        Item item = new Item(product, 3);
//        List<Item> items = new ArrayList<>();
//        items.add(item);
//        Order order = new Order(items);
//        salesAssistant.addOrder(order);
//
//        List<String> restockNames = salesAssistant.processOrder();
//
//        assertTrue(restockNames.isEmpty());
//    }

    @Test
    public void testAddOrder() {
        SalesAssistant salesAssistant = new SalesAssistant();
        Product product = new Product("TestProduct", "Description", 10, 5, 1);
        Item item = new Item(product, 3);
        List<Item> items = new ArrayList<>();
        items.add(item);
        Order order = new Order(items);
        salesAssistant.addOrder(order);

        assertTrue(salesAssistant.getOrders().contains(order));
    }

    @Test
    public void testPermissions() {
        SalesAssistant salesAssistant = new SalesAssistant();
        assertEquals("SalesAssistant", salesAssistant.permissions());
    }
}
