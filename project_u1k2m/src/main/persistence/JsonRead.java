/*
Some of this code is based on JSONSerializationDemo provided by the instructors.
 */

package persistence;

import model.Ingredient;
import model.Recipe;
import org.json.JSONArray;
import org.json.JSONObject;

import java.io.File;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.stream.Stream;

// Handles reading from disk with JSON
public class JsonRead extends JsonIO {
    // EFFECTS: reads source file as string and returns it
    static String readFile(String source) throws IOException {
        StringBuilder contentBuilder = new StringBuilder();

        try (Stream<String> stream = Files.lines(Paths.get(source), StandardCharsets.UTF_8)) {
            stream.forEach(s -> contentBuilder.append(s));
        } catch (Exception e) {
            throw new IOException("Read failed.");
        }

        return contentBuilder.toString();
    }

    // EFFECTS: parses recipe from JSON object and returns it
    public static Recipe parseRecipe(JSONObject jsonObject) {
        String name = jsonObject.getString("name");
        Recipe r = new Recipe(name);
        addIngredients(r, jsonObject);
        r.setPrice(jsonObject.getInt("price"));
        r.setDescription(jsonObject.getString("description"));
        return r;
    }

    // MODIFIES: r
    // EFFECTS: parses ingredients from JSON object and adds them to recipe
    private static void addIngredients(Recipe r, JSONObject jsonObject) {
        JSONArray jsonArray = jsonObject.getJSONArray("ingredients");
        for (Object json : jsonArray) {
            JSONObject nextIngredient = (JSONObject) json;
            addIngredient(r, nextIngredient);
        }
    }

    // MODIFIES: r
    // EFFECTS: parses ingredient from JSON object and adds it to recipe
    private static void addIngredient(Recipe r, JSONObject jsonObject) {
        String name = jsonObject.getString("name");
        int price = jsonObject.getInt("price");
        String quantity = jsonObject.getString("quantity");
        Ingredient i = new Ingredient(name);
        i.setPrice(price);
        i.setQuantity(quantity);
        r.addIngredient(i);
    }

    // EFFECTS: reads recipe from file and returns it;
    // throws IOException if an error occurs reading data from file
    public static Recipe getRecipe(String name) throws IOException {
        JSONObject jsonObject;
        try {
            jsonObject = getRecipeAsJson(name);
        } catch (IOException e) {
            throw new IOException("Error getting recipe");
        }
        return parseRecipe(jsonObject);
    }

    // Effects: Gets recipe from File f
    public static Recipe getRecipe(File f) {
        String jsonData;
        try {
            jsonData = readFile(f.getPath());
        } catch (IOException e) {
            System.out.println("Unable to get recipe from file.");
            return null;
        }
        JSONObject recipeObject = new JSONObject(jsonData);
        return parseRecipe(recipeObject);
    }

    // EFFECTS: Gets recipe as JSON from its name, if a recipe with that name
    // doesn't exist it throws an exception
    public static JSONObject getRecipeAsJson(String name) throws IOException {
        try {
            if (!recipeExists(name)) {
                throw new IOException("No such recipe");
            }
            String source = parseLocation(name);
            String jsonData = readFile(source);
            return new JSONObject(jsonData);
        } catch (IOException e) {
            throw new IOException("Error getting recipe");
        }
    }
}
