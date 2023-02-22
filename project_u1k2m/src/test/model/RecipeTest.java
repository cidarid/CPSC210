package model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import persistence.JsonWrite;

import java.io.IOException;
import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;

class RecipeTest {
    private Recipe r;
    private Ingredient i1, i2;

    @BeforeEach
    void beforeEach() {
        r = new Recipe("Test");
        i1 = new Ingredient("Ham");
        i2 = new Ingredient("Cheese");
    }
    @Test
    void testConstructor() {
        assertEquals(r.getName(), "Test");
        assertEquals(r.getIngredients().size(), 0);
        assertEquals(r.getPrice(), 0);
        assertEquals(r.getDescription(), "");
    }

    @Test
    void testChangeName() {
        r.setName("Name");
        assertEquals(r.getName(), "Name");
    }

    @Test
    void testAddIngredients() {
        r.addIngredient(i1);
        assertEquals(r.getIngredients().get(0), i1);
    }

    @Test
    void testRemoveIngredients() {
        r.addIngredient(i1);
        r.removeIngredient(i1);
        assertFalse(r.getIngredients().contains(i1));
    }

    @Test
    void testPriceFromIngredients() {
        i1.setPrice(30);
        i2.setPrice(40);
        r.addIngredient(i1);
        r.addIngredient(i2);
        assertEquals(r.getPrice(), 70);
    }

    @Test
    void testPriceOverride() {
        r.setPrice(100);
        assertEquals(r.getPrice(), 100);
    }

    @Test
    void testDescription() {
        r.setDescription("Not really food.");
        assertEquals(r.getDescription(), "Not really food.");
    }

    @Test
    void testDetail() {
        r.addIngredient(new Ingredient("Ham"));
        String expected = r.getName() + "\n" +
                "Ingredients: " + r.printList(r.getIngredients()) + "\n" +
                "$" + r.getPrice() + "\n" +
                r.getDescription() + "\n";
        assertEquals(r.detail(), expected);
    }

    @Test
    void testToString() {
        r.setName("String");
        assertEquals(r.toString(), "String");
    }

    @Test
    void testPrintMultipleItems() {
        r.addIngredient(i1);
        r.addIngredient(i2);
        assertEquals(" " + i1.getName() + ",  " + i2.getName(), r.printList(r.getIngredients()));
    }

    @Test
    void testEmptyListPrinter() {
        assertEquals("None", r.printList(new ArrayList<>()));
    }

    @Test
    void testBreakSetName() {
        try {
            JsonWrite.saveRecipe(r);
        } catch (IOException e) {
            // pass
        }
        r.setName("\0");
    }

    @Test
    void testingAdders() {
        r.addedToList();
        r.removedFromList();
    }
}