import static com.example.generated.Tables.RECIPE;
import java.awt.event.ActionListener;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;

/**
 The RecipeView class is responsible for the (GUI) of recipes.
 It provides an interface where users can perform various operations on a recipes such as adding, deleting, etc...
 The class displays recipes in a table and includes input fields and buttons for managing recipes.
 */
public class RecipeView extends JFrame {

    protected JTable recipeTable;
    private final DefaultTableModel tableModel;

    private JTextField idField, nameField, cuisineField, categoryField, instructionsField,
            nutritionField, cookingTimeField, ingredientField;

    private JButton loadButton;
    private JButton addRecipeButton;
    private JButton deleteRecipeButton;
    private JButton updateRecipeButton;
    private JButton filterRecipeButton;
    private JButton deleteAllRecipeButton;
    private JButton backButton;

    //protected ActionListener loadListener, addListener, deleteListener, deleteAllListener, updateListener, filterListener, backListener;
    //private RecipeModel recipeModel;

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
        JPanel buttons = displayButtons();
        /*
        // shows the balance (mainly for visual reasons (not functional))
        JLabel balanceLabel = new JLabel("Balance:\t" + 1000.5 + " EUR");
        balanceLabel.setFont(new Font("Camibri", Font.BOLD, 15));
        backButton.add(balanceLabel);
        setVisible(true);
        balanceLabel.setBorder(BorderFactory.createEmptyBorder(0,0,0,0));
        */
        SwingUtilities.invokeLater(() -> new RecipeModel(this));
        SwingUtilities.invokeLater(() -> new RecipeController(this));

        setVisible(true);
    }

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
    private JPanel displayButtons() {
        
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
        return buttonPanel;
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

    public JTable getRecipeTable() {
        return recipeTable;
    }

    public DefaultTableModel getTableModel() {
        return tableModel;
    }

    public JTextField getIdField() {
        return idField;
    }

    public JTextField getNameField() {
        return nameField;
    }

    public JTextField getCuisineField() {
        return cuisineField;
    }

    public JTextField getCategoryField() {
        return categoryField;
    }

    public JTextField getInstructionsField() {
        return instructionsField;
    }

    public JTextField getNutritionField() {
        return nutritionField;
    }

    public JTextField getCookingTimeField() {
        return cookingTimeField;
    }

    public JTextField getIngredientField() {
        return ingredientField;
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

}


/*

// Set listeners for Controller actions
    public void setLoadListener(ActionListener loadListener) {
        this.loadListener = loadListener;
    }

    public void setAddListener(ActionListener addListener) {
        this.addListener = addListener;
    }

    public void setDeleteListener(ActionListener deleteListener) {
        this.deleteListener = deleteListener;
    }

    public void setUpdateListener(ActionListener updateListener) {
        this.updateListener = updateListener;
    }

    public void setFilterListener(ActionListener filterListener) {
        this.filterListener = filterListener;
    }

    public void setBackListener(ActionListener backListener) {
        this.backListener = backListener;
    }
 */
