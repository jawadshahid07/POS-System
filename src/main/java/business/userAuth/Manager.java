package business.userAuth;

public class Manager extends Role {
    public Manager() {
        super();
    }
    @Override
    public String permissions() {
        return "Manager";
    }
}
