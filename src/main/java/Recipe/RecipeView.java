package Recipe;

import static com.example.generated.Tables.RECIPE;
import java.util.HashMap;
import java.util.Map;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import MVC.View;
import java.awt.*;
import org.jooq.Record;
import org.jooq.Result;

/**
 * The RecipeView class represents the graphical user interface (GUI) for managing recipes.
 * It is built as an extension of the JFrame class and serves for display purposes ONLY
 */
public class RecipeView extends View {

    protected JTable recipeTable;
    private DefaultTableModel tableModel;

    private JTextField idField, nameField, cuisineField, categoryField, instructionsField,
            nutritionField, cookingTimeField, ingredientField;

    //private JButton loadButton;
    private JButton addRecipeButton;
    private JButton deleteRecipeButton;
    private JButton updateRecipeButton;
    private JButton filterRecipeButton;
    private JButton deleteAllRecipeButton;


    public RecipeView() {

        setupFrame();

        // create layout table for data
        setupRecipeTable();

        // fields to modify the table (incl. loading, adding, deleting, updating, etc...)
        displayInputFields();

        // create buttons for user to interact with
        createButtons();

    }


    ///////////////////////////////////// --- Construct The Recipe MVC.View --- /////////////////////////////////////
    @Override
    protected void setupFrame() {
        setTitle("Recipes Manager");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(1440, 880);
        setLayout(new BorderLayout());
    }

    private void setupRecipeTable() {
        tableModel = new DefaultTableModel(new Object[]{"ID", "Name", "Cuisine", "Category",
                "Instructions", "Nutrition", "Cooking Time", "Ingredient"}, 0);

        recipeTable = new JTable(tableModel);
        add(new JScrollPane(recipeTable), BorderLayout.CENTER);
    }

    private void displayInputFields() {

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

    @Override
    protected void createButtons() {

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

    private JTextField createInputField(JPanel panel, String labelText) {
        JLabel label = new JLabel(labelText);
        JTextField textField = new JTextField();
        panel.add(label);
        panel.add(textField);
        return textField;
    }

    /// ////////////////////////////////// --- Obtain The Visual Data --- /////////////////////////////////////

    public DefaultTableModel getTableModel() {
        return tableModel;
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

    /// ////////////////////////////////// --- View Methods --- /////////////////////////////////////

    public void loadTable(Result<Record> result) {
        tableModel.setRowCount(0);
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

}

