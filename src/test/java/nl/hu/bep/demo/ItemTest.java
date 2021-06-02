package nl.hu.bep.demo;

import nl.hu.bep.demo.ShopList.model.Item;
import org.junit.Test;

import static org.junit.jupiter.api.Assertions.*;

class ItemTest {

    @Test
    void isItemGelijk() {
        Item i = new Item("Appel");

        assertEquals("Appel", i.getItemNaam());
    }

}