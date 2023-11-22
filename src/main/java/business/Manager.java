package business;

public class Manager extends Role {
    public Manager() {
        super();
    }
    @Override
    public String permissions() {
        return "Manager";
    }
}
