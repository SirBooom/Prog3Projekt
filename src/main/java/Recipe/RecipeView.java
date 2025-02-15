package Recipe;

import static com.example.generated.Tables.RECIPE;
import java.util.HashMap;
import java.util.Map;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import org.jooq.Record;
import org.jooq.Result;

/**
 * The Recipes.RecipeView class represents the graphical user interface (GUI) for managing recipes.
 * It is built as an extension of the JFrame class and provides various components for
 * performing actions such as loading, adding, updating, deleting, filtering, and displaying recipe data.
 */
public class RecipeView extends JFrame {

    protected JTable recipeTable;
    private final DefaultTableModel tableModel;

    private JTextField idField, nameField, cuisineField, categoryField, instructionsField,
            nutritionField, cookingTimeField, ingredientField;
    private JTextField filterNameField, filterCuisineField, filterCategoryField, filterInstructionsField,
            filterNutritionField, filterCookingTimeField, filterIngredientField;

    private JButton loadButton;
    private JButton addRecipeButton;

    private JButton deleteRecipeButton;
    private JButton updateRecipeButton;
    private JButton filterRecipeButton;
    private JButton deleteAllRecipeButton;
    private JButton backButton;


    public RecipeView() {
        setTitle("Recipes System");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(1440, 880);
        setLayout(new BorderLayout());

        // db table
        tableModel = new DefaultTableModel(new Object[]{"ID", "Name", "Cuisine", "Category",
                "Instructions", "Nutrition", "Cooking Time", "Ingredient"}, 0);
        recipeTable = new JTable(tableModel);
        add(new JScrollPane(recipeTable), BorderLayout.CENTER);

        displayInputFields();
        displayButtons();
        /*
        // shows the balance (mainly for visual reasons (not functional))
        JLabel balanceLabel = new JLabel("Balance:\t" + 1000.5 + " EUR");
        balanceLabel.setFont(new Font("Camibri", Font.BOLD, 15));
        backButton.add(balanceLabel);
        setVisible(true);
        balanceLabel.setBorder(BorderFactory.createEmptyBorder(0,0,0,0));
        */
        setVisible(true);
    }


    ///////////////////////////////////// --- Construct The Recipe View --- /////////////////////////////////////

    // fields to modify the table (incl. loading, adding, deleting, updating, etc...)

    private void displayInputFields() {
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

    private void displayButtons() {

        JPanel buttonPanel = new JPanel();
        buttonPanel.setLayout(new BoxLayout(buttonPanel, BoxLayout.Y_AXIS));

        //load, add, delete, update, filter, deleteAll buttons
        buttonPanel.add(Box.createVerticalStrut(80));
        loadButton = addButton(buttonPanel, "Load Recipes");
        buttonPanel.add(Box.createVerticalStrut(50));
        addRecipeButton = addButton(buttonPanel, "Add Recipe");
        buttonPanel.add(Box.createVerticalStrut(50));
        deleteRecipeButton = addButton(buttonPanel, "Delete Recipe");
        buttonPanel.add(Box.createVerticalStrut(50));
        updateRecipeButton = addButton(buttonPanel, "Update Recipe");
        buttonPanel.add(Box.createVerticalStrut(50));
        filterRecipeButton = addButton(buttonPanel, "Filter Recipes");
        buttonPanel.add(Box.createVerticalStrut(50));
        deleteAllRecipeButton = addButton(buttonPanel, "Delete all Recipes");
        buttonPanel.add(Box.createVerticalStrut(80));
        add(buttonPanel, BorderLayout.EAST);

        //back button
        JPanel backPanel = new JPanel();
        backPanel.setLayout(new BoxLayout(backPanel, BoxLayout.X_AXIS));
        backButton = addButton(backPanel, "Back");
        backPanel.add(Box.createVerticalStrut(40));
        add(backPanel, BorderLayout.NORTH);
    }



    // create text box with a label
    private JTextField createInputField(JPanel panel, String labelText) {
        JLabel label = new JLabel(labelText);
        JTextField textField = new JTextField();
        panel.add(label);
        panel.add(textField);
        return textField;
    }

    private JButton addButton(JPanel panel, String buttonText) {
        JButton button = new JButton(buttonText);
        panel.add(button);
        return button;
    }

    ///////////////////////////////////// --- Obtain The Visual Data --- /////////////////////////////////////

    public DefaultTableModel getTableModel() {
        return tableModel;
    }

    public JButton getLoadButton() {
        return loadButton;
    }

    public JButton getAddRecipeButton() {
        return addRecipeButton;
    }

    public JButton getDeleteRecipeButton() {
        return deleteRecipeButton;
    }

    public JButton getUpdateRecipeButton() {
        return updateRecipeButton;
    }

    public JButton getFilterRecipeButton() {
        return filterRecipeButton;
    }

    public JButton getDeleteAllRecipeButton() {
        return deleteAllRecipeButton;
    }

    public JButton getBackButton() {
        return backButton;
    }

    public Map<String, String> getFormData() {
        Map<String, String> formData = new HashMap<>();
        formData.put("id", idField.getText());
        formData.put("name", nameField.getText());
        formData.put("cuisine", cuisineField.getText());
        formData.put("category", categoryField.getText());
        formData.put("instructions", instructionsField.getText());
        formData.put("nutrition", nutritionField.getText());
        formData.put("cookingTime", cookingTimeField.getText());
        formData.put("ingredient", ingredientField.getText());
        return formData;
    }

    ///////////////////////////////////// --- View Methods --- /////////////////////////////////////

    public void loadTable(Result<Record> result) {
        // Clear existing rows in the table model, if necessary
        tableModel.setRowCount(0);

        // Iterate over the query results and populate the table model
        result.forEach(record -> {
            tableModel.addRow(new Object[]{
                    record.getValue(RECIPE.ID),
                    record.getValue(RECIPE.NAME),
                    record.getValue(RECIPE.CUISINE),
                    record.getValue(RECIPE.CATEGORY),
                    record.getValue(RECIPE.INSTRUCTIONS),
                    record.getValue(RECIPE.NUTRITION),
                    record.getValue(RECIPE.COOKINGTIME),
                    record.getValue(RECIPE.INGREDIENT)
            });
        });
    }

    public void closeView(){
        this.dispose();
    }

    public void showErrorDialog(String message) {
        JOptionPane.showMessageDialog(this, message);
    }

    public void showSuccessDialog(String message) {
        JOptionPane.showMessageDialog(this, message);
    }



}

