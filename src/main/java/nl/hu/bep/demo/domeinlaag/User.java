package nl.hu.bep.demo.domeinlaag;

public class User {

    private String username;
    private String email;
    private String wachtwoord;

    public User(String username, String email, String wachtwoord) {
        this.username = username;
        this.email = email;
        this.wachtwoord = wachtwoord;
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

    public String getUsername() {
        return username;
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

    public String getWachtwoord() {
        return wachtwoord;
    }

    public void setWachtwoord(String wachtwoord) {
        this.wachtwoord = wachtwoord;
    }
}
