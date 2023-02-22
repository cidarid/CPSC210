package persistence;

import model.*;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class JsonWriteTest {
    private final static String TEST_LOCATION = "./data/";
    private final static String INVALID_FILE_NAME = "my\0illegal:fileName";

    @Test
    void testInvalidFile() {
        try {
            Recipe r = new Recipe(INVALID_FILE_NAME);
            JsonWrite.saveRecipe(r);
            fail("IOException was expected");
        } catch (IOException e) {
            // pass
        }
    }

    @Test
    void testInvalidFileFields() {
        Recipe r = new Recipe(INVALID_FILE_NAME);
        r.setPrice(5);
    }

    @Test
    void testWriteEmptyRecipe() {
        try {
            Recipe r = new Recipe("Empty recipe");
            Recipe r1 = new Recipe("Recipe");
            r1.addIngredient(new Ingredient("saw"));
            r1.addIngredient(new Ingredient("needle"));
            r1.setDescription("Chinese and spicy with saws and needles");
            r1.setPrice(800);
            JsonWrite.saveRecipe(r);
            JsonWrite.saveRecipe(r1);

            r = JsonRead.getRecipe("Empty recipe");
            assertEquals("Empty recipe", r.getName());
            assertEquals(0, r.getIngredients().size());
            assertEquals("", r.getDescription());
            assertEquals(0, r.getPrice());
        } catch (IOException e) {
            fail("Exception should not have been thrown");
        }
    }

    @Test
    void testWriteGeneralRecipe() {
        try {
            Recipe r = new Recipe("Recipe");
            r.addIngredient(new Ingredient("saw"));
            r.addIngredient(new Ingredient("needle"));
            r.setDescription("Chinese and spicy with saws and needles");
            r.setPrice(800);
            JsonWrite.saveRecipe(r);

            r = JsonRead.getRecipe("Recipe");
            assertEquals("Recipe", r.getName());
            List<Ingredient> ingredients = r.getIngredients();
            assertEquals(2, ingredients.size());
            assertEquals("saw", ingredients.get(0).getName());
            assertEquals("needle", ingredients.get(1).getName());
            assertEquals("Chinese and spicy with saws and needles", r.getDescription());
            assertEquals(800, r.getPrice());

        } catch (IOException e) {
            fail("Exception should not have been thrown");
        }
    }

    @Test
    void testCreateDuplicateRecipes() {
        try {
            Recipe r = new Recipe("Duplicate me");
            Recipe r1 = new Recipe("Duplicate me");
            boolean bothSaved = JsonWrite.saveRecipe(r) && JsonWrite.saveRecipe(r1);
            assertFalse(bothSaved);
        } catch (IOException e) {
            fail("No IO Exception expected");
        }
    }

    @Test
    void testRenameRecipe() {
        Recipe r = new Recipe("Name1");
        try {
            JsonWrite.saveRecipe(r);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        r.setName("Name2");
        try {
            JsonRead.getRecipe("Name2");
        } catch (IOException e) {
            fail("Name2 does not exist.");
        }
         try {
             JsonRead.getRecipe("Name1");
             fail("Name1 still exists.");
         } catch (IOException e) {
             // pass
         }
    }

    @Test
    void testModifyRecipeFields() {
        Recipe r = new Recipe("Modify fields");
        try {
            JsonWrite.saveRecipe(r);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        r.setName("Modified");
        r.setPrice(10);
        r.setDescription("Modified description");
        Recipe recipeFromFile;
        try {
            recipeFromFile = JsonRead.getRecipe("Modified");
        } catch (IOException e) {
            fail("No such recipe exists to modify");
            return;
        }
        assertEquals("Modified", recipeFromFile.getName());
        assertEquals(10, recipeFromFile.getPrice());
        assertEquals("Modified description", recipeFromFile.getDescription());
    }

    @Test
    void testRenameRecipeAlreadyExists() {
        Recipe r = new Recipe("Exists");
        Recipe r2 = new Recipe("AlsoExists");
        try {
            JsonWrite.saveRecipe(r);
            JsonWrite.saveRecipe(r2);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        r.setName("AlsoExists");
    }

    @Test
    void testGetRecipeLocation() {
        Recipe r = new Recipe("Locate Me");
        try {
            JsonWrite.saveRecipe(r);
        } catch (IOException e) {
            fail("No IO exception should happen here");
        }
        assertEquals("./data/locate_me.json", JsonIO.getRecipeLocation("Locate Me"));
    }


    @Test
    void deleteInvalidRecipe() {
        JsonWrite.deleteRecipe("I don't exist", false);
    }

    @Test
    void deleteValidRecipe() {
        Recipe r = new Recipe("Locate Me");
        try {
            JsonWrite.saveRecipe(r);
        } catch (IOException e) {
            fail("No IO exception should happen here");
        }
        JsonWrite.deleteRecipe("Locate Me", true);
    }

    @Test
    void fulfillingJacocosWhims() {
        assertNotNull(new JsonIO());
        assertNotNull(new JsonWrite());
        assertNotNull(new JsonRead());
    }

    @Test
    void testAddingIngredient() {
        Recipe r = new Recipe("ingredient test recipe");
        try {
            JsonWrite.saveRecipe(r);
        } catch (IOException e) {
            fail("IO exception when saving recipe");
        }
        try {
            JsonWrite.addIngredient(r, new Ingredient("test"));
        } catch (IOException e) {
            fail("IO exception when adding ingredient");
        }
        Recipe recipeFromFile;
        try {
            recipeFromFile = JsonRead.getRecipe("ingredient test recipe");
        } catch (IOException e) {
            fail("No such recipe exists to modify");
            return;
        }
        assertEquals(recipeFromFile.getIngredients().get(0).getName(), "test");
    }

    @Test
    void testDeletingIngredients() {
        Recipe r = new Recipe("delete ingredient test recipe");
        Ingredient i = new Ingredient("to be deleted");
        r.addIngredient(i);
        try {
            JsonWrite.saveRecipe(r, true);
        } catch (IOException e) {
            fail("IO exception when saving recipe");
        }
        try {
            JsonWrite.deleteIngredient(r, i);
        } catch (IOException e) {
            fail("IO exception when deleting ingredient");
        }
        Recipe recipeFromFile;
        try {
            recipeFromFile = JsonRead.getRecipe("delete ingredient test recipe");
        } catch (IOException e) {
            fail("No such recipe exists to modify");
            return;
        }
        assertEquals(0, recipeFromFile.getIngredients().size());
        try {
            JsonWrite.deleteIngredient(r, i);
        } catch (IOException e) {
            fail("IO exception when deleting ingredient.");
        }
        // pass
    }

    @Test
    void testDeletingInvalidIngredient() {
        Recipe r = new Recipe("delete invalid ingredient test recipe");
        Ingredient i = new Ingredient("to be deleted");
        r.addIngredient(i);
        try {
            JsonWrite.saveRecipe(r, true);
        } catch (IOException e) {
            fail("IO exception when saving recipe");
        }
        try {
            JsonWrite.deleteIngredient(r, new Ingredient("not real"));
        } catch (IOException e) {
            fail("IO exception when deleting ingredient");
        }
        Recipe recipeFromFile;
        try {
            recipeFromFile = JsonRead.getRecipe("delete invalid ingredient test recipe");
        } catch (IOException e) {
            fail("No such recipe exists to modify");
            return;
        }
        assertEquals(1, recipeFromFile.getIngredients().size());
    }
}
