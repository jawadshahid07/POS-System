package business.userAuth;

import business.orderProcessing.Item;
import business.orderProcessing.Order;
import business.productCatalog.Product;
import dao.ProductDAO;

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
                ProductDAO productDAO = new ProductDAO();
                for (Item item: order.getItemsList()) {
                    Product product = item.getProduct();
                    product.setStockQuantity(product.getStockQuantity() - item.getQuantityOrdered());
                    productDAO.editProduct(product);
                }
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
