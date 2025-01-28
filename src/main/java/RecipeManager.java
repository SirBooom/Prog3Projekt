import static com.example.generated.Tables.RECIPE;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * The RecipeManager class provides a graphical user interface for managing recipes.
 * It allows users to perform CRUD (Create, Read, Update, Delete) operations on a database of recipes.
 * Recipes are displayed in a table, and users can perform actions like adding, deleting,
 * updating, and loading recipes through the provided interface components.
 * This class extends JFrame to create a GUI application window.
 */
public class RecipeManager extends JFrame {

    private JTable recipeTable;
    private DefaultTableModel tableModel;

    private JTextField idField, nameField, cuisineField, categoryField, instructionsField,
            nutritionField, cookingTimeField, ingredientField, balanceField;

    public RecipeManager() {
        setTitle("Recipe Manager");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(1440, 880);
        setLayout(new BorderLayout());

        // db table
        tableModel = new DefaultTableModel(new Object[]{"ID", "Name", "Cuisine", "Category",
                "Instructions", "Nutrition", "Cooking Time", "Ingredient"}, 0);
        recipeTable = new JTable(tableModel);
        add(new JScrollPane(recipeTable), BorderLayout.CENTER);

        //create backButton
        JPanel backButton = new JPanel();
        backButton.setLayout(new BoxLayout(backButton, BoxLayout.X_AXIS));
        addButton(backButton, "Back", this::backToMenu);
        backButton.add(Box.createVerticalStrut(40));
        add(backButton, BorderLayout.NORTH);

        generateInputFields();

        // shows the balance (mainly for visual reasons (not functional))
        JLabel balanceLabel = new JLabel("Balance:\t" + 1000.5 + " EUR");
        balanceLabel.setFont(new Font("Camibri", Font.BOLD, 15));
        backButton.add(balanceLabel);
        setVisible(true);
        //balanceLabel.setBorder(BorderFactory.createEmptyBorder(0,0,0,0));

        visualButtons();
        //add(buttonPanel, BorderLayout.EAST);
        setVisible(true);
    }

    // fields to modify the table (incl. loading, adding, deleting, updating, etc...)
    private void generateInputFields(){
        // panel for inputs (texfields at bottom)
        JPanel inputPanel = new JPanel(new GridLayout(8, 2)); // Changed GridLayout to 9 rows
        idField = createInputField(inputPanel, "ID:");
        nameField = createInputField(inputPanel, "Name:");
        cuisineField = createInputField(inputPanel, "Cuisine:");
        categoryField = createInputField(inputPanel, "Category:");
        instructionsField = createInputField(inputPanel, "Instructions:");
        nutritionField = createInputField(inputPanel, "Nutrition:");
        cookingTimeField = createInputField(inputPanel, "Cooking Time:");
        ingredientField = createInputField(inputPanel, "Ingredient:");
        add(inputPanel, BorderLayout.SOUTH);
    }

    // Show buttons for user to interact with
    private void visualButtons(){

        // The functional buttons aside from back button we want to add
        JPanel buttonPanel = new JPanel();
        buttonPanel.setLayout(new BoxLayout(buttonPanel, BoxLayout.Y_AXIS));
        //addButton(buttonPanel, "back", this::backToMenu);
        buttonPanel.add(Box.createVerticalStrut(100));
        addButton(buttonPanel, "Load Recipes", this::loadRecipes);
        buttonPanel.add(Box.createVerticalStrut(50));
        addButton(buttonPanel, "Add Recipe", this::addRecipe);
        buttonPanel.add(Box.createVerticalStrut(50));
        addButton(buttonPanel, "Delete Recipe", this::deleteRecipe);
        buttonPanel.add(Box.createVerticalStrut(50));
        addButton(buttonPanel, "Update Recipe", this::updateRecipe);
        buttonPanel.add(Box.createVerticalStrut(50));
        addButton(buttonPanel, "Filter Recipes", this::filterRecipes);
        buttonPanel.add(Box.createVerticalStrut(50));
        addButton(buttonPanel, "Delete all Recipes", this::deleteAllRecipes);
        buttonPanel.add(Box.createVerticalStrut(100));
        add(buttonPanel, BorderLayout.EAST);

    }

    // create text box with a label
    private JTextField createInputField(JPanel panel, String labelText) {
        JLabel label = new JLabel(labelText);
        JTextField textField = new JTextField();
        panel.add(label);
        panel.add(textField);
        return textField;
    }

    // generate a button in the a JPanel
    private void addButton(JPanel panel, String buttonText, ActionListener action) {
        JButton button = new JButton(buttonText);
        button.addActionListener(action);
        panel.add(button);
    }

    // load recipes from the db (useful when relaunching or after filtering)
    private void loadRecipes(ActionEvent e) {
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
            JOptionPane.showMessageDialog(this, "Error loading recipes.");
        }
    }

    // add a recipe to the db
    private void addRecipe(ActionEvent e) {
        try {
            Database.insertRecipe(
                    Integer.parseInt(idField.getText()),
                    nameField.getText().isEmpty() ? null : nameField.getText(),
                    cuisineField.getText().isEmpty() ? null : cuisineField.getText(),
                    categoryField.getText().isEmpty() ? null : categoryField.getText(),
                    instructionsField.getText().isEmpty() ? null : instructionsField.getText(),
                    nutritionField.getText().isEmpty() ? 0
                            : Integer.parseInt(nutritionField.getText()),
                    cookingTimeField.getText().isEmpty() ? null : cookingTimeField.getText(),
                    ingredientField.getText().isEmpty() ? null : ingredientField.getText()
            );
            loadRecipes(null); // Refresh table
            JOptionPane.showMessageDialog(this, "Recipe added successfully.");
        } catch (Exception ex) {
            ex.printStackTrace();
            JOptionPane.showMessageDialog(this, "Error adding recipe.");
        }
    }

    // delete a recipe by ID
    private void deleteRecipe(ActionEvent e) {
        int id = Integer.parseInt(idField.getText());
        try {
            Database.deleteRecipe(id);
            loadRecipes(null); // Refresh table
            JOptionPane.showMessageDialog(this, "Recipe deleted successfully.");
        } catch (Exception ex) {
            ex.printStackTrace();
            JOptionPane.showMessageDialog(this, "Error deleting recipe.");
        }
    }

    // delete all recipes in the db
    private void deleteAllRecipes(ActionEvent e) {
        try {
            Database.deleteAllRecipes();
            loadRecipes(null); // Refresh table
            JOptionPane.showMessageDialog(this, "Recipe list has been cleared successfully.");
        } catch (Exception ex) {
            ex.printStackTrace();
            JOptionPane.showMessageDialog(this, "Error deleting recipes.");
        }
    }

    // update at least 1 value of a recipe
    private void updateRecipe(ActionEvent e) {
        boolean updated = false;
        try {
            updated = Database.updateRecipe(
                    Integer.parseInt(idField.getText()),
                    nameField.getText().trim().isEmpty() ? null : cuisineField.getText(),
                    cuisineField.getText().trim().isEmpty() ? null : cuisineField.getText(),
                    categoryField.getText().trim().isEmpty() ? null : categoryField.getText(),
                    instructionsField.getText().trim().isEmpty() ? null : instructionsField.getText(),
                    nutritionField.getText().trim().isEmpty() ? 0 : Integer.parseInt(nutritionField.getText()),
                    cookingTimeField.getText().trim().isEmpty() ? null : cookingTimeField.getText(),
                    ingredientField.getText().trim().isEmpty() ? null : instructionsField.getText()
            );
            if(updated) {
                loadRecipes(null); // Refresh table
                JOptionPane.showMessageDialog(this, "Recipe updated successfully.");
            } else {
                JOptionPane.showMessageDialog(this, "Nothing has been updated.");
            }
        } catch (Exception ex) {
            if(!updated)
            ex.printStackTrace();
            JOptionPane.showMessageDialog(this, "Error updating recipe.");
        }
    }

    // filter recipes by 1 or multiple attributes
    private void filterRecipes(ActionEvent e) {
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
    private void backToMenu(ActionEvent e) {
        dispose();
        new MenuManager();
    }

}
