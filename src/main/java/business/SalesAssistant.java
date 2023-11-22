package business;

public class SalesAssistant extends Role {
    private String orders;

    public SalesAssistant() {
        super();
    }

    public void processOrders() {
        // Implementation for processing orders
    }

    @Override
    public String permissions() {
        return "SalesAssistant";
    }
}
