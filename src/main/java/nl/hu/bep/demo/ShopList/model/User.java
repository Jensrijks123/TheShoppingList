package nl.hu.bep.demo.ShopList.model;

import java.security.Principal;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class User implements Principal {

    private static List<User> allUsers;

    static {
        allUsers = new ArrayList<>();
        allUsers.add(new User("djensman", "jens.rijks@student.hu.nl", "djensman", "admin"));
    }

    private String username;
    private String email;
    private String password;
    private String role;

    public User(String username, String email, String password, String role) {
        this.username = username;
        this.email = email;
        this.password = password;
        this.role = role;
    }

    public static String validateLogin(String username, String password) {
        for (User user : allUsers) {
            if (user.username.equals(username) && user.password.equals(password)) {
                System.out.println(user.username.equals(username));
                return user.role;
            }
        }
        return null;
    }

    public static User getUserByUserName(String username) {
        for (User user : allUsers) {
            if (user.username.equals(username)) {
                System.out.println(user.username.equals(username));
                return user;
            }
        }
        return null;
    }

    public String getName() {
        return username;
    }

    public String getRole() {
        return role;
    }

    public void emailChecker() {
        if (email.contains("@") & email.contains(".")){
            System.out.println("Goede Email!");
        } else {
            System.out.println("Dit is geen geldige email!");
        }
    }

    public void usernameChecker() {
        if (!username.contains("[a-zA-Z]")){
            System.out.println("Correcte username!");
        } else {
            System.out.println("Foute username!");
        }
    }
//    public boolean equals(Object o) {
//        if (this == o) return true;
//        if (o == null || getClass() != o.getClass()) return false;
//        User user = (User) o;
//        return username.equals(user.username);
//    }
//
//    @Override
//    public int hashCode() {
//        return Objects.hash(username);
//    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public boolean checkPassword(String password) {
        return this.password.equals(password);
    }
}
