package nl.hu.bep.demo.ShopList.model;

import java.io.Serializable;
import java.security.Principal;
import java.util.Objects;

public class MyUser implements Principal, Serializable {
    private String username;
    private String password;
    private String role;

    public MyUser(String username, String password, String role) {
        this.username = username;
        this.password = password;
        this.role = role;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        MyUser myUser = (MyUser) o;
        return username.equals(myUser.username);
    }

    @Override
    public int hashCode() {
        return Objects.hash(username);
    }

    @Override
    public String getName() {
        return username;
    }

    public String getRole() {
        return role;
    }

    public boolean checkPassword(String password) {
        return this.password.equals(password);
    }
}
