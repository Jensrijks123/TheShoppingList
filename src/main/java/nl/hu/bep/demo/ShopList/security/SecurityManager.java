package nl.hu.bep.demo.ShopList.security;

import nl.hu.bep.demo.ShopList.model.MyUser;
import nl.hu.bep.demo.ShopList.model.User;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class SecurityManager implements Serializable {

    private static SecurityManager securityManager;

    public static SecurityManager getInstance(){
        if(securityManager==null) securityManager = new SecurityManager();
        return securityManager;
    }

    private SecurityManager(){}

    public boolean deserialize(SecurityManager securityManager){
        if(securityManager==null) return false;
        this.securityManager = securityManager;
        return true;
    }

    private List<User> allUsers = new ArrayList<>();

    public boolean registerUser(User toAdd){
        if(!this.allUsers.contains(toAdd))  return this.allUsers.add(toAdd);
        return false;
    }

    public User getUserByName(String username) {
        return this.allUsers.stream().filter(user -> user.getName().equals(username)).findFirst().orElse(null);
    }

    public String validateLogin(String username, String password){
        if(username==null || username.isBlank() || password == null || password.isBlank()) return null;
        var myUser = this.getUserByName(username);
        if(myUser == null) return null;
        return myUser.checkPassword(password) ? myUser.getRole() : null;
    }
}
