package business.orderProcessing;

public class Item {
    private int quantityOrdered;
    private double price;

    public double total() {
        return quantityOrdered * price;
    }

    public void updateQuantity(int newQuantity) {
        quantityOrdered = newQuantity;
    }
}
