package business.orderProcessing;

import business.productCatalog.Product;

public class Item {
    private Product product;
    private int quantityOrdered;
    private double price;

    public Item(Product product, int quantityOrdered) {
        this.product = product;
        this.quantityOrdered = quantityOrdered;
        this.price = product.getCurrentPrice(); // Initialize price based on the current price of the product
    }

    public Product getProduct() {
        return product;
    }

    public int getQuantityOrdered() {
        return quantityOrdered;
    }

    public double getPrice() {
        return price;
    }

    public double total() {
        return quantityOrdered * price;
    }

    public void updateQuantity(int qty) {
        quantityOrdered = qty;
    }
}

