package persistence;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertNull;

public class JsonIOTest {
    @Test
    void testGetInvalidRecipeLocation() {
        assertNull(JsonIO.getRecipeLocation("I don't exist"));
    }

}
