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
    private final RecipeView recipeView;

    private final JTextField idField, nameField, cuisineField, categoryField, instructionsField,
            nutritionField, cookingTimeField, ingredientField;

    public RecipeModel() {
        this.tableModel = null;
        this.recipeView = null;
        this.idField = null;
        this.nameField =null;
        this.cuisineField = null;
        this.categoryField = null;
        this.instructionsField = null;
        this.nutritionField = null;
        this.cookingTimeField = null;
        this.ingredientField = null;
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
            //showErrorDialog(ERROR_LOADING_RECIPES);
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
            //reloadRecipes(null);
            //JOptionPane.showMessageDialog(recipeView, SUCCESS_RECIPE_DELETED);
        } catch (Exception ex) {
            ex.printStackTrace();
            return null;
            //showErrorDialog(ERROR_DELETING_RECIPE);
        }
    }

    public Result<Record> deleteAllRecipes(ActionEvent e) {
        try {
            return Database.deleteAllRecipes();
            //JOptionPane.showMessageDialog(recipeView, SUCCESS_ALL_RECIPES_DELETED);
        } catch (Exception ex) {
            ex.printStackTrace();
            //showErrorDialog(ERROR_DELETING_RECIPE);
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
            //reloadRecipes(null);
            //String message = updated ? SUCCESS_RECIPE_UPDATED : NOTHING_UPDATED;
            //JOptionPane.showMessageDialog(recipeView, message);
        } catch (Exception ex) {
            /*
            if (!updated) {
                ex.printStackTrace();
            }
            */
            return null;
            //showErrorDialog(ERROR_UPDATING_RECIPE);
        }

    }

    public Result<Record> filterRecipes(ActionEvent e, JTextField idField, JTextField nameField, JTextField cuisineField, JTextField categoryField, JTextField instructionsField, JTextField nutritionField, JTextField cookingTimeField, JTextField ingredientField) {
        try {
            //tableModel.setRowCount(0); // Clear the table before filtering
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
            //JOptionPane.showMessageDialog(filterFrame, "Recipes filtered successfully!");
            //filterFrame.dispose(); // Close the frame
        } catch (Exception ex) {
            ex.printStackTrace();
            return null;
            //showErrorDialog("Error filtering recipes.");
        }
    }

    public boolean backToMenu(ActionEvent e) {
        new MenuController(new MenuView());
        return true;
    }

    public void setTableModel(DefaultTableModel tableModel) {
        this.tableModel = tableModel;
    }
}
