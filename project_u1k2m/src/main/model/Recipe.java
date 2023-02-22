package model;

import org.json.JSONArray;
import org.json.JSONObject;
import persistence.JsonRead;
import persistence.JsonWrite;

import java.io.IOException;
import java.util.ArrayList;

// Represents a recipe having a name, ingredients, a price, and a description
public class Recipe {
    private String name;
    private ArrayList<Ingredient> ingredients;
    private int price;
    private String description;

    // EFFECTS: name on recipe is set to passed name, all other variables are initialized
    public Recipe(String name) {
        this.name = name;
        ingredients = new ArrayList<>();
        price = 0;
        description = "";
    }

    @Override
    public String toString() {
        return name;
    }

    public String getName() {
        return name;
    }

    // EFFECTS: Changes the name of a recipe. This also changes the name of the file,
    // as the name of the file is based on the name of the recipe. Will have no effect if
    // a recipe with the name to change to already exists
    public void setName(String name) {
        try {
            JsonRead.getRecipe(name);
            System.out.println("A recipe with this name already exists.");
            return;
        } catch (IOException e) {
            // pass
        }
        JsonWrite.deleteRecipe(this.name, false);
        this.name = name;
        try {
            JsonWrite.saveRecipe(this, true);
        } catch (IOException e) {
            // pass
        }
    }

    public ArrayList<Ingredient> getIngredients() {
        return ingredients;
    }

    public void addIngredient(Ingredient ingredient) {
        ingredients.add(ingredient);
    }

    public void removeIngredient(Ingredient ingredient) {
        ingredients.remove(ingredient);
    }

    // EFFECTS: If user has created a new default price for a recipe, return that,
    // if not, return the sum of all ingredient prices
    public int getPrice() {
        // User has overridden default price
        if (price != 0) {
            return price;
        }
        int priceOfIngredients = 0;
        for (Ingredient i : ingredients) {
            priceOfIngredients += i.getPrice();
        }
        return priceOfIngredients;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    // EFFECTS: Helper method to print an ArrayList in a user readable format,
    // returned as a String
    public String printList(ArrayList<?> list) {
        if (list.size() == 0) {
            return "None";
        }
        StringBuilder s = new StringBuilder();
        for (int i = 0; i < list.size() - 1; i++) {
            s.append(list.get(i)).append(", ");
        }
        s.append(list.get(list.size() - 1));
        return s.toString();
    }

    // EFFECTS: Prints out all relevant recipe fields in a user readable format,
    // returned as a String
    public String detail() {
        return String.format("%s\n"
                + "Ingredients: %s\n"
                + "$%d\n"
                + "%s\n", name, printList(ingredients), price, description);
    }

    // EFFECTS: Helper method, returns this in a JSONObject format
    public JSONObject toJson() {
        JSONObject json = new JSONObject();
        json.put("name", name);
        json.put("ingredients", ingredientsToJson());
        json.put("price", price);
        json.put("description", description);
        return json;
    }

    // EFFECTS: Helper method, returns ingredients of this in a JSONArray format
    public JSONArray ingredientsToJson() {
        JSONArray array = new JSONArray();

        for (Ingredient i : ingredients) {
            array.put(i.toJson());
        }

        return array;
    }

    // EFFECTS: Adds event to event log when this recipe is added to the recipe list
    public void addedToList() {
        EventLog.getInstance().logEvent(new Event("Added recipe " + name + " to recipe book list."));
    }

    // EFFECTS: Adds event to event log when this recipe is added to the recipe list
    public void removedFromList() {
        EventLog.getInstance().logEvent(new Event("Removed recipe " + name + " from recipe book list."));
    }
}
