package nl.hu.bep.demo;

import nl.hu.bep.demo.ShopList.model.Boodschappenlijstje;
import org.junit.Test;

import static org.junit.jupiter.api.Assertions.*;


class BoodschappenlijstjeTest {

    @Test
    void isLijstjeGelijk() {
        Boodschappenlijstje b = new Boodschappenlijstje("GezinLijst");

        assertEquals("GezinLijst", b.getLijsteNaam());
    }
}