package business;

public class User {
    private int id;
    private String name;
    private String username;
    private String password;
    private Role role;

    public void setId(int id) {
        this.id = id;
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }

    public Role getRole() {
        return role;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void setRole(Role role) {
        this.role = role;
    }

    public User(int id, String name, String username, String password, Role role) {
        this.id = id;
        this.name = name;
        this.username = username;
        this.password = password;
        this.role = role;
    }
    public User() {

    }

    public boolean login(String enteredUsername, String enteredPassword) {
        // Check login credentials
        return username.equals(enteredUsername) && password.equals(enteredPassword);
    }

    public void save() {
        // Save user information
    }
}
