package nl.hu.bep.demo.ShopList.model;

public class Item {

    private String itemNaam;

    public Item(String itemNaam) {
        this.itemNaam = itemNaam;
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

    public String getItemNaam() {
        return itemNaam;
    }

    public void setItemNaam(String itemNaam) {
        this.itemNaam = itemNaam;
    }
}
