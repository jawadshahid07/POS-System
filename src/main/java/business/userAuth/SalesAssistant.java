package business.userAuth;

import business.orderProcessing.Order;

import java.util.ArrayList;
import java.util.List;

public class SalesAssistant extends Role {
    private List<Order> orders;

    public SalesAssistant() {
        super();
        orders = new ArrayList<>();
    }

    public void processOrder() {
        for (Order order : orders) {
            if (order.getStatus()) {
                order.generateInvoice();
                order.setStatus(false);
            }

        }
    }

    public void addOrder(Order order) {
        orders.add(order);
    }

    @Override
    public String permissions() {
        return "SalesAssistant";
    }
}
