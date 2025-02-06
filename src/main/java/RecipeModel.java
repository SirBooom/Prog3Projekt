import static com.example.generated.Tables.RECIPE;
import java.awt.event.ActionListener;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import javax.swing.table.DefaultTableModel;

/**
 * The RecipeManager class provides a graphical user interface for managing recipes.
 * It allows users to perform CRUD (Create, Read, Update, Delete) operations on a database of recipes.
 * Recipes are displayed in a table, and users can perform actions like adding, deleting,
 * updating, and loading recipes through the provided interface components.
 * This class extends JFrame to create a GUI application window.
 */
public class RecipeModel{

    private static DefaultTableModel tableModel;
    //private DefaultTableModel tableModel;
    private static RecipeView  recipeView;
    private static JTextField idField, nameField, cuisineField, categoryField, instructionsField,
            nutritionField, cookingTimeField, ingredientField, balanceField;

    public RecipeModel(RecipeView rv) {
        recipeView = rv;
        tableModel = rv.getTableModel();
        idField = rv.getIdField();
        nameField = rv.getNameField();
        cuisineField = rv.getCuisineField();
        categoryField = rv.getCategoryField();
        instructionsField = rv.getInstructionsField();
        nutritionField = rv.getNutritionField();
        cookingTimeField = rv.getCookingTimeField();
        ingredientField = rv.getIngredientField();

    }

    // create text box with a label
    private JTextField createInputField(JPanel panel, String labelText) {
        JLabel label = new JLabel(labelText);
        JTextField textField = new JTextField();
        panel.add(label);
        panel.add(textField);
        return textField;

    }

    // load recipes from the db (useful when relaunching or after filtering)
    public static void loadRecipes(ActionEvent e) {
        try {
            tableModel.setRowCount(0); // Clear existing rows
            Database.getConnection()
                    .select()
                    .from(RECIPE)
                    .fetch()
                    .forEach(record -> tableModel.addRow(new Object[]{
                            record.getValue(RECIPE.ID),
                            record.getValue(RECIPE.NAME),
                            record.getValue(RECIPE.CUISINE),
                            record.getValue(RECIPE.CATEGORY),
                            record.getValue(RECIPE.INSTRUCTIONS),
                            record.getValue(RECIPE.NUTRITION),
                            record.getValue(RECIPE.COOKINGTIME),
                            record.getValue(RECIPE.INGREDIENT),
                    }));
        } catch (Exception ex) {
            ex.printStackTrace();
            JOptionPane.showMessageDialog(recipeView, "Error loading recipes.");
        }
    }

    // add a recipe to the db
    public static void addRecipe(ActionEvent e) {
        try {
            Database.insertRecipe(
                    Integer.parseInt(idField.getText()),
                    nameField.getText().isEmpty() ? null : nameField.getText(),
                    cuisineField.getText().isEmpty() ? null : cuisineField.getText(),
                    categoryField.getText().isEmpty() ? null : categoryField.getText(),
                    instructionsField.getText().isEmpty() ? null : instructionsField.getText(),
                    nutritionField.getText().isEmpty() ? 0 : Integer.parseInt(nutritionField.getText()),
                    cookingTimeField.getText().isEmpty() ? null : cookingTimeField.getText(),
                    ingredientField.getText().isEmpty() ? null : ingredientField.getText()
            );
            loadRecipes(null); // Refresh table
            JOptionPane.showMessageDialog(recipeView, "Recipe added successfully.");
        } catch (Exception ex) {
            ex.printStackTrace();
            JOptionPane.showMessageDialog(recipeView, "Error adding recipe.");
        }
    }

    // delete a recipe by ID
    public static void deleteRecipe(ActionEvent e) {
        int id = Integer.parseInt(idField.getText());
        try {
            Database.deleteRecipe(id);
            loadRecipes(null); // Refresh table
            JOptionPane.showMessageDialog(recipeView, "Recipe deleted successfully.");
        } catch (Exception ex) {
            ex.printStackTrace();
            JOptionPane.showMessageDialog(recipeView, "Error deleting recipe.");
        }
    }

    // delete all recipes in the db
    public static void deleteAllRecipes(ActionEvent e) {
        try {
            Database.deleteAllRecipes();
            loadRecipes(null); // Refresh table
            JOptionPane.showMessageDialog(recipeView, "Recipe list has been cleared");
        } catch (Exception ex) {
            ex.printStackTrace();
            JOptionPane.showMessageDialog(recipeView, "Error deleting recipes.");
        }
    }

    // update at least 1 value of a recipe
    public static void updateRecipe(ActionEvent e) {
        boolean updated = false;
        try {
            updated = Database.updateRecipe(
                    Integer.parseInt(idField.getText()),
                    nameField.getText().trim().isEmpty() ? null : nameField.getText(),
                    cuisineField.getText().trim().isEmpty() ? null : cuisineField.getText(),
                    categoryField.getText().trim().isEmpty() ? null : categoryField.getText(),
                    instructionsField.getText().trim().isEmpty() ? null : instructionsField.getText(),
                    nutritionField.getText().trim().isEmpty() ? 0 : Integer.parseInt(nutritionField.getText()),
                    cookingTimeField.getText().trim().isEmpty() ? null : cookingTimeField.getText(),
                    ingredientField.getText().trim().isEmpty() ? null : instructionsField.getText()
            );
            if(updated) {
                loadRecipes(null); // Refresh table
                JOptionPane.showMessageDialog(recipeView, "Recipe updated successfully.");
            } else {
                JOptionPane.showMessageDialog(recipeView, "Nothing has been updated.");
            }
        } catch (Exception ex) {
            if(!updated)
            ex.printStackTrace();
            JOptionPane.showMessageDialog(recipeView, "Error updating recipe.");
        }
    }

    // filter recipes by 1 or multiple attributes
    public static void filterRecipes(ActionEvent e) {
            // Create a new JFrame for filtering
            JFrame filterFrame = new JFrame("Filter Recipes");
            filterFrame.setSize(300, 300);
            filterFrame.setLayout(new GridLayout(8, 2)); // 7 fields + Filter button

            // Add input fields for all 7 fields
            JTextField filterNameField = new JTextField();
            JTextField filterCuisineField = new JTextField();
            JTextField filterCategoryField = new JTextField();
            JTextField filterInstructionsField = new JTextField();
            JTextField filterNutritionField = new JTextField();
            JTextField filterCookingTimeField = new JTextField();
            JTextField filterIngredientField = new JTextField();

            // Add labels and text fields to the frame
            filterFrame.add(new JLabel("Name:"));
            filterFrame.add(filterNameField);

            filterFrame.add(new JLabel("Cuisine:"));
            filterFrame.add(filterCuisineField);

            filterFrame.add(new JLabel("Category:"));
            filterFrame.add(filterCategoryField);

            filterFrame.add(new JLabel("Instructions:"));
            filterFrame.add(filterInstructionsField);

            filterFrame.add(new JLabel("Nutrition:"));
            filterFrame.add(filterNutritionField);

            filterFrame.add(new JLabel("Cooking Time:"));
            filterFrame.add(filterCookingTimeField);

            filterFrame.add(new JLabel("Ingredient:"));
            filterFrame.add(filterIngredientField);

            // Add Filter button
            JButton filterButton = new JButton("Filter");
            filterButton.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent actionEvent) {
                    try {
                        // Fetch input from fields
                        String name = filterNameField.getText().trim().isEmpty() ? null : filterNameField.getText();
                        String cuisine = filterCuisineField.getText().trim().isEmpty() ? null : filterCuisineField.getText();
                        String category = filterCategoryField.getText().trim().isEmpty() ? null : filterCategoryField.getText();
                        String instructions = filterInstructionsField.getText().trim().isEmpty() ? null : filterInstructionsField.getText();
                        int nutrition = filterNutritionField.getText().trim().isEmpty() ? 0 : Integer.parseInt(filterNutritionField.getText());
                        String cookingTime = filterCookingTimeField.getText().trim().isEmpty() ? null : filterCookingTimeField.getText();
                        String ingredient = filterIngredientField.getText().trim().isEmpty() ? null : filterIngredientField.getText();

                        // Call the database filtering method
                        tableModel.setRowCount(0); // Clear the table before filtering
                        Database.filterRecipesJframe(name, cuisine, category, instructions, nutrition, cookingTime, ingredient, tableModel);
                        JOptionPane.showMessageDialog(filterFrame, "Recipes filtered successfully!");
                        filterFrame.dispose(); // Close the frame
                    } catch (Exception ex) {
                        ex.printStackTrace();
                        JOptionPane.showMessageDialog(filterFrame, "Error filtering recipes.");
                    }
                }
            });

            filterFrame.add(new JLabel()); // Empty space to align button
            filterFrame.add(filterButton);

            // Show the filter frame
            filterFrame.setVisible(true);
        }

    // go back to main menu
    public static void backToMenu(ActionEvent e) {
        recipeView.dispose();
        new MenuController(new MenuView());

    }

}
