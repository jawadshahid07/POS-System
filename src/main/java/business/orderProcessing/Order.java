package business.orderProcessing;

import java.util.Date;
import java.util.List;

public class Order extends ItemContainer {
    public Order(List<Item> items) {
        this.items = items;
    }
    private String customer;
    private Date timestamp;

    public void cancel() {

    }
    public void generateInvoice() {

    }
}
