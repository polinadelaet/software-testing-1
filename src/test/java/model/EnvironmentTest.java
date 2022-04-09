package model;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class EnvironmentTest {

    private Environment environment;

    @BeforeEach
    void init() {
        environment = new Environment();
    }

    @Test
    void isEnvironmentEmpty() {
        assertFalse(environment.isEmpty());
    }

    @Test
    void isAllThingsCreate() {
        assertEquals(environment.getNumberOfThings(), 2);
    }

    @Test
    void cannotTakeBottleIfAlreadyHoldingAnotherOne() throws IllegalStateException {
        IllegalStateException thrown = Assertions.assertThrows(IllegalStateException.class, () -> {
            environment.getFord().tryTakeBottle();
        });
    }

    // если Форд не держит бутылку с рыбкой, то он не может предложить ее засунуть в ухо Артуру
    @Test
    void testCanFordMakeAnOffer() throws IllegalStateException {
        IllegalStateException thrown = Assertions.assertThrows(IllegalStateException.class, () -> {
            environment.getFord().setIsHoldingBottle(false);
            environment.getFord().doOffer();
        });
    }

    @Test
    void arthurIsNotConfidentIfThereIsNoSimpleThing() {
        assertTrue(!(environment.getArtur().isConfident()) && !(environment.isThereSimpleThing()));
    }
}