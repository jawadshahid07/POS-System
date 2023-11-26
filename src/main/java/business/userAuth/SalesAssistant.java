package business.userAuth;

import business.orderProcessing.Item;
import business.orderProcessing.Order;
import business.productCatalog.Product;
import com.thoughtworks.qdox.model.expression.Or;
import dao.OrderDAO;
import dao.ProductDAO;

import java.util.ArrayList;
import java.util.List;

public class SalesAssistant extends Role {
    private List<Order> orders;

    public SalesAssistant() {
        super();
        orders = new ArrayList<>();
    }

    public List<String> processOrder() {
        List<String> restockNames = new ArrayList<>();
        for (Order order : orders) {
            if (order.getStatus()) {
                ProductDAO productDAO = new ProductDAO();
                for (Item item: order.getItemsList()) {
                    Product product = item.getProduct();
                    product.setStockQuantity(product.getStockQuantity() - item.getQuantityOrdered());
                    if (product.getStockQuantity() <= product.getAlertQuantity()) {
                        restockNames.add(product.getName());
                    }
                    productDAO.editProduct(product);
                }
                order.generateInvoice();
                OrderDAO orderDAO = new OrderDAO();
                orderDAO.saveOrder(order);
                order.setStatus(false);
            }
        }
        return restockNames;
    }

    public void addOrder(Order order) {
        orders.add(order);
    }
    public List<Order> getOrders() { return orders; }

    @Override
    public String permissions() {
        return "SalesAssistant";
    }
}
