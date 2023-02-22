package model;

import org.json.JSONObject;

// Represents an ingredient having a name, a price, and a quantity
public class Ingredient {
    private String name;
    private int price;
    // Stores the quantity of the ingredient, i.e. 350g or 2 cups
    private String quantity;

    // EFFECTS: name on Ingredient is set to passed name, all other variables are initialized
    public Ingredient(String name) {
        this.name = name;
        price = 0;
        quantity = "";
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    public String getQuantity() {
        return quantity;
    }

    public void setQuantity(String quantity) {
        this.quantity = quantity;
    }

    // EFFECTS: Returns a string representation of this
    @Override
    public String toString() {
        return quantity + " " + name;
    }

    // EFFECTS: Helper method, returns this in a JSONObject format
    public JSONObject toJson() {
        JSONObject json = new JSONObject();
        json.put("name", name);
        json.put("price", price);
        json.put("quantity", quantity);
        return json;
    }

    // EFFECTS: Adds event to event log when this ingredient is added to the ingredient list
    public void addedToList() {
        EventLog.getInstance().logEvent(new Event("Added ingredient " + this.name + " to ingredient list."));
    }

    // EFFECTS: Removes event from event log when this ingredient is added to the ingredient list
    public void removedFromList() {
        EventLog.getInstance().logEvent(new Event("Removed ingredient " + this.name + " from ingredient list."));
    }
}
