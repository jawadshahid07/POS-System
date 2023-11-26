package orderTests;

import business.orderProcessing.Cart;
import business.orderProcessing.Item;
import business.orderProcessing.ItemContainer;
import business.productCatalog.Product;
import org.junit.Test;

import java.util.List;
import java.util.ArrayList;

import static org.junit.Assert.*;

public class ItemContainerTest {

    @Test
    public void testItemContainerOperations() {
        Cart cart = new Cart();

        List<Item> items = createSampleItems();


        for (Item item : items) {
            cart.add(item);
        }

        assertEquals(3, cart.getItemsList().size());

        Item itemToUpdate = items.get(0);
        cart.update(itemToUpdate, 5);

        assertEquals(5, itemToUpdate.getQuantityOrdered());

        Item itemToRemove = items.get(1);
        cart.remove(itemToRemove);

        assertEquals(2, cart.getItemsList().size());

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

