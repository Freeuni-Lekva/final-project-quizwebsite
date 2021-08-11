package user;

import quiz.Quiz;

import java.time.LocalDate;
import java.util.SortedMap;

public class User {
    private String username;
    private String password;
    private long id;
    private boolean isAdmin;
    private String firstName;
    private String lastName;

    public User(String username, String password, int id, boolean isAdmin, String firstName,
                String lastName) {
        this.username = username;
        this.password = password;
        this.id = id;
        this.isAdmin = isAdmin;
        this.firstName = firstName;
        this.lastName = lastName;
    }


    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }

    public long getId() {
        return id;
    }

    public boolean isAdmin() {
        return isAdmin;
    }

    public String getFullName() {
        return firstName + " " + lastName;
    }

    @Override
    public String toString() {
        return "User{" +
                "username='" + username + '\'' +
                ", password='" + password + '\'' +
                ", id=" + id +
                ", isAdmin=" + isAdmin +
                ", firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                '}';
    }
}
