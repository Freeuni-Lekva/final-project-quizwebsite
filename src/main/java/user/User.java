package user;

public class User {

    private String username;
    private String password;
    private int id;
    private String firstName;
    private String lastName;
    private boolean isAdmin;

    public User(String username, String password, int id, String firstName, String lastName, boolean isAdmin){
        this.username = username;
        this.id = id;
        this.firstName = firstName;
        this.lastName = lastName;
        this.password = password;
        this.isAdmin = isAdmin;
    }

    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }

    public int getId() {
        return id;
    }

    public String getFullName() {
        return firstName + " " + lastName;
    }

    public boolean isAdmin() {
        return isAdmin;
    }

}
