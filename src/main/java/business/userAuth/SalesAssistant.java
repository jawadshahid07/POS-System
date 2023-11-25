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
                order.setStatus(false);
            }
        }
        return restockNames;
    }

    public void addOrder(Order order) {
        orders.add(order);
    }

    @Override
    public String permissions() {
        return "SalesAssistant";
    }
}
