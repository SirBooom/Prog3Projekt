import javax.swing.*;
import org.jooq.Record;
import org.jooq.Result;
import java.awt.event.ActionEvent;
import javax.swing.table.DefaultTableModel;

/**
 * The RecipeModel class handles the logic and data manipulation for managing recipes
 * through the RecipeView interface. It acts as a bridge between the user interface
 * (RecipeView) and the storage layer (Database), providing functionalities like
 * adding, updating, deleting, reloading, and filtering recipes.
 */
public class RecipeModel {

    private DefaultTableModel tableModel;

    public RecipeModel() {
        this.tableModel = null;
    }

    private String getTrimmedTextOrNull(JTextField field) {
        String text = field.getText().trim();
        return text.isEmpty() ? null : text;
    }

    public Result<Record> reloadRecipes(ActionEvent e) {
        tableModel.setRowCount(0); // Clear existing rows
        try {
            return Database.fetchAllRecipes();
        } catch (Exception ex) {
            return null;
        }
    }

    public Result<Record> addRecipe(ActionEvent e, JTextField idField, JTextField nameField, JTextField cuisineField, JTextField categoryField, JTextField instructionsField, JTextField nutritionField, JTextField cookingTimeField, JTextField ingredientField) {
        try {
            return Database.insertRecipe(
                    Integer.parseInt(idField.getText()),
                    getTrimmedTextOrNull(nameField),
                    getTrimmedTextOrNull(cuisineField),
                    getTrimmedTextOrNull(categoryField),
                    getTrimmedTextOrNull(instructionsField),
                    nutritionField.getText().isEmpty() ? null
                            : Integer.parseInt(nutritionField.getText()),
                    getTrimmedTextOrNull(cookingTimeField),
                    getTrimmedTextOrNull(ingredientField)
            );
            //reloadRecipes(null);
            //JOptionPane.showMessageDialog(recipeView, SUCCESS_RECIPE_ADDED);
        } catch (Exception ex) {
            ex.printStackTrace();
            //showErrorDialog(ERROR_ADDING_RECIPE);
            return null;
        }
    }

    public Result<Record> deleteRecipe(ActionEvent e, JTextField idToDelete) {
        try {
            int parsedId = Integer.parseInt(idToDelete.getText());
            return Database.deleteRecipe(parsedId);
        } catch (Exception ex) {
            ex.printStackTrace();
            return null;
        }
    }

    public Result<Record> deleteAllRecipes(ActionEvent e) {
        try {
            return Database.deleteAllRecipes();
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return reloadRecipes(null);
    }

    public Result<Record> updateRecipe(ActionEvent e, JTextField idField, JTextField nameField, JTextField cuisineField, JTextField categoryField, JTextField instructionsField, JTextField nutritionField, JTextField cookingTimeField, JTextField ingredientField) {
        boolean updated = false;
        try {
            return Database.updateRecipe(
                    Integer.parseInt(idField.getText()),
                    getTrimmedTextOrNull(nameField),
                    getTrimmedTextOrNull(cuisineField),
                    getTrimmedTextOrNull(categoryField),
                    getTrimmedTextOrNull(instructionsField),
                    nutritionField.getText().isEmpty() ? 0
                            : Integer.parseInt(nutritionField.getText()),
                    getTrimmedTextOrNull(cookingTimeField),
                    getTrimmedTextOrNull(ingredientField)
            );
        } catch (Exception ex) {

            return null;
        }

    }

    public Result<Record> filterRecipes(ActionEvent e, JTextField idField, JTextField nameField, JTextField cuisineField, JTextField categoryField, JTextField instructionsField, JTextField nutritionField, JTextField cookingTimeField, JTextField ingredientField) {
        try {
            return Database.filterRecipes(
                    idField.getText().isEmpty() ? 0
                            : Integer.parseInt(idField.getText()),
                    getTrimmedTextOrNull(nameField),
                    getTrimmedTextOrNull(cuisineField),
                    getTrimmedTextOrNull(categoryField),
                    getTrimmedTextOrNull(instructionsField),
                    nutritionField.getText().isEmpty() ? 0
                            : Integer.parseInt(nutritionField.getText()),
                    getTrimmedTextOrNull(cookingTimeField),
                    getTrimmedTextOrNull(ingredientField)
            );
        } catch (Exception ex) {
            ex.printStackTrace();
            return null;
        }
    }

    public void setTableModel(DefaultTableModel tableModel) {
        this.tableModel = tableModel;
    }
}
