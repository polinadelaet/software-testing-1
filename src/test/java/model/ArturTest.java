package model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class ArturTest {

    private Artur artur;

    @BeforeEach
    void init() {
        artur = new Artur();
    }

    @Test
    void isConfident() {
        assertFalse(artur.isConfident());
    }

    @Test
    void isArturEarthling() {
        assertEquals(artur.getBirthplace(), Birthplace.EARTH);
    }
}