package ui;

import model.Ingredient;
import model.Recipe;
import persistence.*;

import javax.swing.*;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;

// Recipe book application
public class RecipeBook {
    private static final String RECIPE_STORE = "./data/";
    File dataFolder;

    public RecipeBook() {
        runApp();
    }


    // MODIFIES: this
    // EFFECTS: processes input
    private void runApp() {
        dataFolder = new File(RECIPE_STORE);

        //Creates a frame placeholder variable
        RecipeFrame frame = new RecipeFrame("Recipe Book");
        //Sets the content pane that will be shown
        frame.setContentPane(frame.getMainPanel());
        //Sets the close operation (shut down java app when the main window is closed)
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setExtendedState(JFrame.MAXIMIZED_BOTH);
        //Runs init on frame
        frame.pack();
        //Makes frame visible
        frame.setVisible(true);
    }
}
