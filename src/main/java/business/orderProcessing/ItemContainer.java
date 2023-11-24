package business.orderProcessing;

import java.util.ArrayList;
import java.util.List;

public abstract class ItemContainer {
    protected int id;
    protected List<Item> items;

    public ItemContainer() {
        items = new ArrayList<>();
    }
    public void add(Item item) {
        items.add(item);
    }
    public void remove(Item item) {
        items.remove(item);
    }

    public void update(Item item, int qty) {
        for (Item i : items) {
            if (item == i) {
                item.updateQuantity(qty);
            }
        }
    }

    public double total() {
        double grandTotal = 0;
        for (Item i : items) {
            grandTotal += i.total();
        }
        return grandTotal;
    }

    public List<Item> getItemsList() {
        return items;
    }
}
