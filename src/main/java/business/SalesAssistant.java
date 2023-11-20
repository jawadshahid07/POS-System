package business;

public class SalesAssistant extends Role {
    private String orders;

    public SalesAssistant() {
        super();
        this.name = "SalesAssistant";
    }

    public void processOrders() {
        // Implementation for processing orders
    }

    @Override
    public void permissions() {
        // Implementation of sales assistant's permissions
    }
}
