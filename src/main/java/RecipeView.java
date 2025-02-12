import static com.example.generated.Tables.RECIPE;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import org.jooq.Record;
import org.jooq.Result;

/**
 * The RecipeView class represents the graphical user interface (GUI) for managing recipes.
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

    ///////////////////////////////////// --- Filter Frame --- /////////////////////////////////////

    public JTextField getFilterNameField() {
        return filterNameField;
    }

    public JTextField getFilterCuisineField() {
        return filterCuisineField;
    }

    public JTextField getFilterCategoryField() {
        return filterCategoryField;
    }

    public JTextField getFilterInstructionsField() {
        return filterInstructionsField;
    }

    public JTextField getFilterNutritionField() {
        return filterNutritionField;
    }

    public JTextField getFilterCookingTimeField() {
        return filterCookingTimeField;
    }

    public JTextField getFilterIngredientField() {
        return filterIngredientField;
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

    /*
    public void filterButtonJFrame(){
        JFrame filterFrame = new JFrame("Filter Recipes");
        filterFrame.setSize(300, 300);
        filterFrame.setLayout(new GridLayout(8, 2)); // 7 fields + Filter button

        filterNameField = createFilterInputField(filterFrame, "Name:");
        filterCuisineField = createFilterInputField(filterFrame, "Cuisine:");
        filterCategoryField = createFilterInputField(filterFrame, "Category:");
        filterInstructionsField = createFilterInputField(filterFrame, "Instructions:");
        filterNutritionField = createFilterInputField(filterFrame, "Nutrition:");
        filterCookingTimeField = createFilterInputField(filterFrame, "Cooking Time:");
        filterIngredientField = createFilterInputField(filterFrame, "Ingredient:");

        JButton filterButton = new JButton("Filter");

        filterFrame.add(new JLabel()); // Empty space to align button layout
        filterFrame.add(filterButton);
        filterFrame.setVisible(true);
    }

    private JTextField createFilterInputField(JFrame frame, String labelText) {
        frame.add(new JLabel(labelText));
        JTextField textField = new JTextField();
        frame.add(textField);
        return textField;
    }
    */

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
