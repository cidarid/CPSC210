package ui;

import model.Event;
import model.EventLog;
import model.Ingredient;
import model.Recipe;
import persistence.JsonRead;
import persistence.JsonWrite;

import javax.swing.*;
import java.awt.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

// RecipeFrame works as a frame to store/create/delete all the information related to my application
public class RecipeFrame extends JFrame {


    private static final String RECIPE_STORE = "./data/";
    File dataFolder;

    // Stores all other panels
    private JPanel mainPanel;
    // Home panel, lists recipes
    private JPanel homePanel;
    // Panel for editing or creating recipes
    private JPanel editPanel;
    // Panel to display a recipe in detail
    private JPanel detailPanel;

    // homePanel components
    // Displays all saved recipes in a scroll view
    private JScrollPane recipesScrollPane;
    // Creates a new recipe when clicked
    private JButton newRecipeButton;
    // Edits selected recipe when clicked
    private JButton editButton;
    // Deletes selected recipe when clicked, with confirmation
    private JButton deleteButton;
    // Lists recipes
    private JList<Recipe> recipeList;
    private final DefaultListModel<Recipe> recipeListModel = new DefaultListModel<>();

    // editPanel components
    private JTextField nameEditField;
    private JTextField priceEditField;
    private JTextArea descriptionEditField;
    private JButton cancelButton;
    private JButton saveButton;

    // detailPanel components
    private JList<Ingredient> ingredientsDetail;
    private JTextArea descriptionDetail;
    private JLabel priceDetail;
    private JLabel nameDetail;
    private JButton doneButton;
    private JButton viewButton;
    private JList<Ingredient> ingredientList;
    private final DefaultListModel<Ingredient> ingredientListModel = new DefaultListModel<>();
    private JButton deleteIngredientButton;
    private JPanel ingredientPanel;
    private JTextField ingredientNameField;
    private JTextField ingredientPriceField;
    private JTextField ingredientQuantityField;
    private JButton addButton;

    private Recipe activeRecipe;
    private Ingredient activeIngredient;
    private JButton cancelIngredient;
    private JButton saveIngredient;
    private JButton editIngredientButton;
    private JPanel newIngredientPanel;
    private JPanel editIngredientPanel;
    private JButton saveEditIngredientButton;
    private JButton cancelIngredientEditButton;
    private JLabel headerLabel;

    // MODIFIES: this
    // EFFECTS: Initializes recipe frame
    public RecipeFrame(String name) {
        super(name);
        start();
    }

    // MODIFIES: this
    // REQUIRES: recipebook.png to be in the ./img folder
    // EFFECTS: Sets up initial variables
    private void start() {
        dataFolder = new File(RECIPE_STORE);
        recipeList.setModel(recipeListModel);
        ingredientsDetail.setModel(ingredientListModel);
        ingredientList.setModel(ingredientListModel);
        refreshMainPanel();
        initListeners();
        Image icon = Toolkit.getDefaultToolkit().getImage("./img/recipebook.png");
        icon = icon.getScaledInstance(111, 113, 0);
        headerLabel.setIcon(new ImageIcon(icon));
    }

    // MODIFIES: this
    // EFFECTS: Initializes listeners for buttons
    private void initListeners() {
        deleteButton.addActionListener(e -> deleteSelectedRecipe());
        editButton.addActionListener(e -> editSelectedRecipe());
        newRecipeButton.addActionListener(e -> createNewRecipe());
        viewButton.addActionListener(e -> viewSelectedRecipe());
        saveButton.addActionListener(e -> saveRecipeEdits());
        cancelButton.addActionListener(e -> activatePanel(homePanel));
        doneButton.addActionListener(e -> activatePanel(homePanel));
        addButton.addActionListener(e -> activateNewIngredientPanel());
        editIngredientButton.addActionListener(e -> editSelectedIngredient());
        deleteIngredientButton.addActionListener(e -> deleteSelectedIngredient());
        cancelIngredient.addActionListener(e -> activatePanel(editPanel));
        saveIngredient.addActionListener(e -> saveNewIngredient());
        saveEditIngredientButton.addActionListener(e -> saveEditedIngredient());
        cancelIngredientEditButton.addActionListener(e -> activatePanel(detailPanel));
        this.addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                printEventLog();
                super.windowClosing(e);
            }
        });
    }

    // EFFECTS: Initializes the new recipe page with blank inputs and activates the edit panel
    private void createNewRecipe() {
        nameEditField.setText("");
        priceEditField.setText("");
        descriptionEditField.setText("");
        activatePanel(editPanel);
    }

    // EFFECTS: Opens the edit recipe panel filled in with the selected recipe's fields
    private void editSelectedRecipe() {
        activeRecipe = getSelectedRecipe();
        updateEditPanel(activeRecipe);
        activatePanel(editPanel);
    }

    // EFFECTS: Deletes the selected recipe
    private void deleteSelectedRecipe() {
        activeRecipe = getSelectedRecipe();
        deleteRecipe(activeRecipe, true);
        activeRecipe.removedFromList();
    }

    // MODIFIES: this
    // EFFECTS: Deletes a specific recipe r
    private void deleteRecipe(Recipe r, boolean saveToLog) {
        if (r != null) {
            JsonWrite.deleteRecipe(r.getName(), saveToLog);
        }
        recipeListModel.removeElement(r);
    }

    // EFFECTS: Deletes the selected ingredient
    private void deleteSelectedIngredient() {
        activeIngredient = getSelectedIngredient();
        deleteIngredient(activeIngredient);
    }

    // MODIFIES: this
    // EFFECTS: Deletes a specific ingredient i
    private void deleteIngredient(Ingredient i) {
        try {
            deleteIngredient(activeRecipe, i);
            ingredientListModel.removeElement(i);
            i.removedFromList();
        } catch (IOException e) {
            JOptionPane.showMessageDialog(mainPanel, "Error deleting ingredient.");
        }
    }

    // EFFECTS: Public facing method to delete a specific ingredient i from recipe r
    public void deleteIngredient(Recipe r, Ingredient i) throws IOException {
        if (r != null && i != null) {
            JsonWrite.deleteIngredient(r, i);
        }
    }

    // MODIFIES: this
    // EFFECTS: Opens the detail panel, populating it with the selected recipe's values
    private void viewSelectedRecipe() {
        activeRecipe = getSelectedRecipe();
        updateDetailPanel(activeRecipe);
        activatePanel(detailPanel);
    }

    // MODIFIES: this
    // EFFECTS: Save edits made to recipe in the edit panel
    private void saveRecipeEdits() {
        boolean saveToLog = true;
        if (activeRecipe != null) {
            deleteRecipe(activeRecipe, false);
            saveToLog = false;
        }
        activeRecipe = new Recipe(nameEditField.getText());
        try {
            activeRecipe.setPrice(Integer.parseInt(priceEditField.getText()));
        } catch (NumberFormatException e) {
            activeRecipe.setPrice(0);
        }
        activeRecipe.setDescription(descriptionEditField.getText());
        for (int i = 0; i < ingredientListModel.size(); i++) {
            activeRecipe.addIngredient(ingredientListModel.get(i));
        }
        saveRecipe(activeRecipe, saveToLog);
        activeRecipe = null;
        activatePanel(homePanel);
    }

    // EFFECTS: Saves recipe to disk
    // MODIFIES: this
    private void saveRecipe(Recipe r, boolean saveToLog) {
        try {
            JsonWrite.saveRecipe(r);
        } catch (IOException e) {
            System.out.println("Unable to save recipe.");
            return;
        }
        recipeListModel.addElement(r);
        if (saveToLog) {
            r.addedToList();
        }
    }

    // MODIFIES: this
    // EFFECTS: Populates main panel
    private void refreshMainPanel() {
        ArrayList<Recipe> recipes = getRecipeFiles();
        for (Recipe r : recipes) {
            recipeListModel.addElement(r);
        }
    }

    // EFFECTS: Populates edit panel with information from recipe r
    private void updateEditPanel(Recipe r) {
        nameEditField.setText(r.getName());
        priceEditField.setText(String.valueOf(r.getPrice()));
        descriptionEditField.setText(r.getDescription());
        ingredientListModel.clear();
        for (Ingredient i : r.getIngredients()) {
            ingredientListModel.addElement(i);
        }
    }

    // MODIFIES: this
    // EFFECTS: Populates detail panel with information from recipe r
    private void updateDetailPanel(Recipe r) {
        nameDetail.setText(r.getName());
        priceDetail.setText("$" + r.getPrice());
        descriptionDetail.setText(r.getDescription());
        ingredientListModel.clear();
        for (Ingredient i : r.getIngredients()) {
            System.out.println(i);
            ingredientListModel.addElement(i);
        }
    }

    // EFFECTS: Activates the requested panel
    private void activatePanel(JPanel panel) {
        homePanel.setVisible(false);
        editPanel.setVisible(false);
        detailPanel.setVisible(false);
        ingredientPanel.setVisible(false);
        panel.setVisible(true);
    }

    // EFFECTS: Returns main panel
    public JPanel getMainPanel() {
        return mainPanel;
    }

    // EFFECTS: Gets selected recipe from home screen's recipe list
    private Recipe getSelectedRecipe() {
        Recipe r = recipeList.getSelectedValue();
        if (r == null) {
            JOptionPane.showMessageDialog(mainPanel, "Please select a recipe.");
        }
        return r;
    }

    // EFFECTS: Gets selected recipe from edit screen's recipe list
    private Ingredient getSelectedIngredient() {
        Ingredient i = ingredientList.getSelectedValue();
        if (i == null) {
            JOptionPane.showMessageDialog(mainPanel, "Please select an ingredient.");
        }
        return i;
    }

    // MODIFIES: this
    // EFFECTS: Saves new ingredient to recipe being edited
    private void saveNewIngredient() {
        Ingredient i = new Ingredient(ingredientNameField.getText());
        i.setQuantity(ingredientQuantityField.getText());
        try {
            i.setPrice(Integer.parseInt(ingredientPriceField.getText()));
        } catch (NumberFormatException e) {
            i.setPrice(0);
        }
        ingredientListModel.addElement(i);
        i.addedToList();
        activatePanel(editPanel);
    }

    // EFFECTS: Opens ingredient edit window for selected ingredient
    private void editSelectedIngredient() {
        activeIngredient = getSelectedIngredient();
        updateEditIngredientPanel(activeIngredient);
        activateEditIngredientPanel();
    }

    // EFFECTS: Populates ingredient edit window for ingredient i
    private void updateEditIngredientPanel(Ingredient i) {
        ingredientNameField.setText(i.getName());
        ingredientQuantityField.setText(i.getQuantity());
        ingredientPriceField.setText(String.valueOf(i.getPrice()));
    }

    // EFFECTS: Activates edit ingredient panel
    private void activateEditIngredientPanel() {
        editIngredientPanel.setVisible(true);
        newIngredientPanel.setVisible(false);
        activatePanel(ingredientPanel);
    }

    // EFFECTS: Activates new ingredient panel
    private void activateNewIngredientPanel() {
        editIngredientPanel.setVisible(false);
        newIngredientPanel.setVisible(true);
        activatePanel(ingredientPanel);
    }

    // MODIFIES: this
    // EFFECTS: Saves edits made to ingredient
    private void saveEditedIngredient() {
        try {
            deleteIngredient(activeRecipe, activeIngredient);
        } catch (IOException e) {
            JOptionPane.showMessageDialog(mainPanel, "Unable to edit ingredient.");
            return;
        }
        for (int i = 0; i < ingredientListModel.size(); i++) {
            if (ingredientListModel.get(i).getName().equals(activeIngredient.getName())) {
                ingredientListModel.remove(i);
                break;
            }
        }
        saveNewIngredient();
    }

    // EFFECTS: Prints the event log to console
    private void printEventLog() {
        for (Event e : EventLog.getInstance()) {
            System.out.println(e.getDescription());
        }
    }

    // EFFECTS: Gets recipes from disk and parses them, returns the result as
    // an ArrayList of recipes
    private ArrayList<Recipe> getRecipeFiles() {
        ArrayList<Recipe> recipes = new ArrayList<>();
        File[] listOfFiles = dataFolder.listFiles();
        assert listOfFiles != null;
        if (listOfFiles.length == 0) {
            System.out.println("You have no recipes in your recipe book. ");
            return null;
        }
        for (File f : listOfFiles) {
            if (f.getName().endsWith(".json") && f.isFile()) {
                recipes.add(JsonRead.getRecipe(f));
            }
        }
        return recipes;
    }
}
