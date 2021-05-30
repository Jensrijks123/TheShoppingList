package nl.hu.bep.demo.domeinlaag;

import org.junit.Test;

import static org.junit.jupiter.api.Assertions.*;


class BoodschappenlijstjeTest {

    @Test
    void isLijstjeGelijk() {
        Boodschappenlijstje b = new Boodschappenlijstje("GezinLijst");

        assertEquals("GezinLijst", b.getLijsteNaam());
    }
}