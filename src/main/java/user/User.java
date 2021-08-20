package user;

import java.util.Objects;

public class User {
    private String username;
    private String hashedPassword;
    private long id;
    private boolean isAdmin;
    private String firstName;
    private String lastName;

    public User(long id, String username, String hashedPassword, boolean isAdmin, String firstName,
                String lastName) {
        this(username, hashedPassword, isAdmin, firstName, lastName);
        this.id = id;
    }

    public User(String username, String hashedPassword, boolean isAdmin, String firstName,
                String lastName) {
        this.username = username;
        this.hashedPassword = hashedPassword;
        this.isAdmin = isAdmin;
        this.firstName = firstName;
        this.lastName = lastName;
    }


    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return hashedPassword;
    }

    public long getId() {
        return id;
    }

    public boolean isAdmin() {
        return isAdmin;
    }

    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setId(long userId) {
        this.id = userId;
    }

    @Override
    public String toString() {
        return "User{" +
                "username='" + username + '\'' +
                ", hashedPassword='" + hashedPassword + '\'' +
                ", id=" + id +
                ", isAdmin=" + isAdmin +
                ", firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        User user = (User) o;
        return id == user.id;
    }


}