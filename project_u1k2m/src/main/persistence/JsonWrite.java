/*
Some of this code is based on JSONSerializationDemo provided by the instructors.
 */

package persistence;

import model.*;
import java.io.*;

import org.json.JSONArray;
import org.json.JSONObject;

// Handles writing to disk with JSON
public class JsonWrite extends JsonIO {
    // EFFECTS: Saves recipe r. If overwrite is true, will overwrite an existing file
    // on disk if necessary, if not, will return if a recipe with the same name as the
    // recipe being saved already exists
    public static boolean saveRecipe(Recipe r, boolean overwrite) throws IOException {
        if (recipeExists(r.getName()) && !overwrite) {
            System.out.println("A recipe with this name already exists.");
            return false;
        }
        JSONObject object = r.toJson();
        PrintWriter writer = new PrintWriter(new FileWriter(parseLocation(r.getName())));
        writer.print(object.toString(TAB));
        writer.close();

        EventLog.getInstance().logEvent(new Event("Saved recipe " + r.getName() + " to file."));
        return true;
    }

    // EFFECTS: Saves recipe r to disk without overwriting.
    public static boolean saveRecipe(Recipe r) throws IOException {
        return saveRecipe(r, false);
    }

    // EFFECTS: Deletes recipe called a name of name, if it exists.
    // If not, notifies the user and takes no action.
    public static void deleteRecipe(String name, boolean saveToLog) {
        String source = parseLocation(name);
        File fileToDelete = new File(source);
        boolean deleted = fileToDelete.delete();
        if (!deleted) {
            System.out.println("The recipe you have tried to delete does not exist.");
        } else if (saveToLog) {
            EventLog.getInstance().logEvent(new Event("Deleted recipe " + name + "."));
        }
    }

    // EFFECTS: Deletes ingredient i from recipe r, if both exist.
    public static void deleteIngredient(Recipe r, Ingredient i) throws IOException {
        JSONObject recipeAsJson = JsonRead.getRecipeAsJson(r.getName());
        JSONArray jsonArray = recipeAsJson.getJSONArray("ingredients");
        for (int j = 0; j < jsonArray.length(); j++) {
            JSONObject o = jsonArray.getJSONObject(j);
            String passedName = i.getName();
            String objectName = (String) o.get("name");
            if (objectName.equals(passedName)) {
                jsonArray.remove(j);
                r.getIngredients().remove(i);
                break;
            }
        }
        saveRecipe(r, true);
        EventLog.getInstance().logEvent(new Event("Deleted ingredient " + i.getName() + " from recipe " + r.getName()));
    }

    // EFFECTS: Adds ingredient i to recipe r, if both exist.
    public static void addIngredient(Recipe r, Ingredient i) throws IOException {
        r.addIngredient(i);
        saveRecipe(r, true);
        EventLog.getInstance().logEvent(new Event("Added ingredient " + i.getName() + " to recipe " + r.getName()));
    }
}
