package model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class IngredientTest {
    @Test
    void testConstructor() {
        Ingredient i = new Ingredient("Eggs");
        assertEquals(i.getName(), "Eggs");
        assertEquals(i.getPrice(), 0);
        assertEquals(i.getQuantity(), "");
    }

    @Test
    void testToString() {
        Ingredient i = new Ingredient("Hello");
        assertEquals(i.toString(), " Hello");
    }

    @Test
    void testSetters() {
        Ingredient i = new Ingredient("Lister");
        i.setName("Lister2");
        i.setQuantity("300g");
        assertEquals(i.getName(), "Lister2");
        assertEquals(i.getQuantity(), "300g");
    }

    @Test
    void testLoggers() {
        Ingredient i = new Ingredient("logger");
        i.addedToList();
        i.removedFromList();
    }
}
