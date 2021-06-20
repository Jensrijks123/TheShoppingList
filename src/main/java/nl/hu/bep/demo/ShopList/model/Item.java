package nl.hu.bep.demo.ShopList.model;

public class Item {

    private String itemNaam;
    private int itemId;

    public Item(String itemNaam, int itemId) {
        this.itemNaam = itemNaam;
        this.itemId = itemId;
    }

    public boolean equals(Object obj) {
        boolean gelijkeObjecten = false;

        if (obj instanceof Item) {
            Item anderItem = (Item) obj;

            if (this.itemNaam.equals(anderItem.itemNaam)) {
                gelijkeObjecten = true;
            }
        }
        return gelijkeObjecten;
    }

    public int getItemId() {
        return itemId;
    }

    public void setItemId(int itemId) {
        this.itemId = itemId;
    }

    public String getItemNaam() {
        return itemNaam;
    }

    public void setItemNaam(String itemNaam) {
        this.itemNaam = itemNaam;
    }
}
