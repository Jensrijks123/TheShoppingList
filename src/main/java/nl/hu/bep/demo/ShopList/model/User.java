package nl.hu.bep.demo.ShopList.model;

import com.sun.jdi.connect.spi.Connection;

import java.beans.Statement;
import java.security.Principal;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Time;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Objects;

import static java.lang.System.out;

public class User implements Principal {

    private static List<User> allUsers;

    static {
        allUsers = new ArrayList<>();
        allUsers.add(new User("djensman", "jens.rijks@student.hu.nl", "djensman", "user", 9992));
        allUsers.add(new User("admin", "admin@admin.com", "admin", "admin", 9991));
    }
    private int userid;
    private String username;
    private String email;
    private String password;
    private String role;


    public static User getAccount() {
        return huidigeAccount;
    }

    public static void setAccount(User huidigeAccount) {
        User.huidigeAccount = huidigeAccount;
    }

    private static User huidigeAccount;

    public User(String username, String email, String password, String role, int userid) {
        this.username = username;
        this.email = email;
        this.password = password;
        this.role = role;
        this.userid = userid;
    }

    public boolean addUser(User toAdd){
        if(!this.allUsers.contains(toAdd))  {
            return this.allUsers.add(toAdd);
        }
        return false;
    }

    public static String validateLogin(String userN, String passW) {
        for (User user : allUsers) {
            if (user.username.equals(userN) && user.password.equals(passW)) {
                return user.role;
            }
        }
        return null;
    }

    public static User getUserByUserName(String username) {
        for (User user : allUsers) {
            if (user.username.equals(username)) {
                return user;
            }
        }
        return null;
    }

//    public static User getUserName(String username) {
//        return user;
//    }

    public String getName() {
        return username;
    }

    public String getRole() {
        return role;
    }

    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        User user = (User) o;
        return username.equals(user.username);
    }

    @Override
    public int hashCode() {
        return Objects.hash(username);
    }

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

    public int getUserid() {
        return userid;
    }

    public void setUserid(int userid) {
        this.userid = userid;
    }

    public boolean checkPassword(String password) {
        return this.password.equals(password);
    }
}
