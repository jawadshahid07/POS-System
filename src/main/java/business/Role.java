package business;

public abstract class Role {
    protected String log;
    protected String name;

    public abstract void permissions();
    public String getName() {
        return this.name;
    }
}
