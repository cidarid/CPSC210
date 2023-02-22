package persistence;

import model.*;
import org.junit.jupiter.api.Test;

import java.io.File;
import java.io.IOException;

import static org.junit.jupiter.api.Assertions.*;

public class JsonReadTest {

    @Test
    void testGetRecipeAsFile() {
        try {
            JsonWrite.saveRecipe(new Recipe("File"));
        } catch (IOException e) {
            fail("IO Exception");
        }
        JsonRead.getRecipe(new File("./data/file.json"));

    }

    @Test
    void testGetInvalidRecipeAsFile() {
        assertNull(JsonRead.getRecipe(new File("./data/idontexist.json")));
    }

    @Test
    void testGetInvalidRecipe() {
        try {
            JsonRead.getRecipe("I don't exist");
            fail("This recipe shouldn't exist");
        } catch (IOException e) {
            // pass
        }
    }

    @Test
    void testReadingInvalidPath() {
        try {
            JsonRead.readFile("asj.\\/.json");
        } catch (Exception e) {
            //pass
        }
    }
}
