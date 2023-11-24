package business.userAuth;

import business.orderProcessing.Order;

import java.util.List;

public class SalesAssistant extends Role {
    private List<Order> orders;

    public SalesAssistant() {
        super();
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
