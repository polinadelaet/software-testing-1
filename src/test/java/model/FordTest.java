package model;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class FordTest {

    private Ford ford;

    @BeforeEach
    void init() {
        ford = new Ford();
    }

    @Test
    void isFordBetelgeusian() {
        assertEquals(ford.getBirthplace(), Birthplace.BETELGEUSE);
    }

    @Test
    void tryTakeOneBottle() {
        ford.tryTakeBottle();
        assertTrue(ford.isHasHold());

    }

    @Test
    void tryTakeTwoBottles() {
        IllegalStateException thrown = Assertions.assertThrows(IllegalStateException.class, () -> {
            ford.tryTakeBottle();
            ford.tryTakeBottle();
        });
    }

    @Test
    void doOfferWithoutBottle() {
        IllegalStateException thrown = Assertions.assertThrows(IllegalStateException.class, () -> ford.doOffer());
    }
}