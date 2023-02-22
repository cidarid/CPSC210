/*
Some of this code is based on JSONSerializationDemo provided by the instructors.
 */

package persistence;

import java.io.File;

public class JsonIO {
    protected static final int TAB = 4;
    protected static final String directory = "./data/";

    // EFFECTS: Creates a save location for a recipe based on the name of the recipe
    // and returns the save location
    protected static String parseLocation(String recipeName) {
        String fileName = recipeName
                .replace(" ", "_")
                .toLowerCase();
        return directory + fileName + ".json";
    }

    // EFFECTS: Returns whether a recipe exists (true or false)
    protected static Boolean recipeExists(String recipeName) {
        String location = parseLocation(recipeName);
        File file = new File(location);
        return file.exists();
    }

    // EFFECTS: Public facing method for getting the location of a recipe with name of name
    public static String getRecipeLocation(String name) {
        if (!recipeExists(name)) {
            System.out.println("That recipe does not exist.");
            return null;
        }
        return parseLocation(name);
    }
}
