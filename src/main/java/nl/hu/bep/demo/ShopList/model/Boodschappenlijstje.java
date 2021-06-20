package nl.hu.bep.demo.ShopList.model;

import java.util.ArrayList;

public class Boodschappenlijstje {

    private int lijstid;
    private String lijsteNaam;
    private ArrayList<Item> items;
    private ArrayList<User> users;

    public static Boodschappenlijstje getLijst() {
        return huidigeLijst;
    }

    public static void setLijst(Boodschappenlijstje huidigeLijst) {
        Boodschappenlijstje.huidigeLijst = huidigeLijst;
    }

    private static Boodschappenlijstje huidigeLijst;

    public Boodschappenlijstje(String lijsteNaam, int lijstid) {
        this.lijsteNaam = lijsteNaam;
        this.lijstid = lijstid;
    }

    public boolean equals(Object obj) {
        boolean gelijkeObjecten = false;

        if (obj instanceof Boodschappenlijstje) {
            Boodschappenlijstje anderLijstje = (Boodschappenlijstje) obj;

            if (this.lijsteNaam.equals(anderLijstje.lijsteNaam)) {
                gelijkeObjecten = true;
            }
        }
        return gelijkeObjecten;
    }

    public int getLijstid() {
        return lijstid;
    }

    public void setLijstid(int lijstid) {
        this.lijstid = lijstid;
    }

    public String getLijsteNaam() {
        return lijsteNaam;
    }

    public void setLijsteNaam(String lijsteNaam) {
        this.lijsteNaam = lijsteNaam;
    }

    public ArrayList<Item> getItems() {
        return items;
    }

    public void setItems(ArrayList<Item> items) {
        this.items = items;
    }

    public ArrayList<User> getUsers() {
        return users;
    }

    public void setUsers(ArrayList<User> users) {
        this.users = users;
    }
}
