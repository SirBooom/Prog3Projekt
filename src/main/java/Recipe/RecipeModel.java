package Recipe;

import database.Database;
import database.RecipeDatabase;
import org.jooq.DSLContext;
import org.jooq.Record;
import org.jooq.Result;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.swing.table.DefaultTableModel;
import java.util.Map;

/**
 * The RecipeModel class handles the logic and data manipulation for managing recipes.
 * It bridges the RecipeView (via the controller) and the database layer (RecipeDatabase).
 * This version decouples it from UI components (like JTextField).
 */
public class RecipeModel {
    private static final Logger logger = LoggerFactory.getLogger(RecipeModel.class);
    private final RecipeDatabase recipeDatabase;
    private DefaultTableModel tableModel;

    public RecipeModel(DSLContext context) {
        try {
            recipeDatabase = new RecipeDatabase(context);
        } catch (Exception e) {
            throw new RuntimeException("Error initializing RecipeModel: " + e.getMessage(), e);
        }
        this.tableModel = null;
    }

    /**
     * Reload all recipes from the database.
     */
    public Result<Record> reloadRecipes() {
        tableModel.setRowCount(0);
        try {
            return recipeDatabase.showRecipes();
        } catch (Exception ex) {
            throw new RuntimeException("Error reloading recipes: " + ex.getMessage(), ex);
        }
    }

    /**
     * Add a new recipe using the provided data.
     * @param data A map containing the recipe properties. Keys are column names.
     * @return Result containing the inserted recipe.
     */
    public Result<Record> addRecipe(Map<String, String> data) {
        try {
            return  recipeDatabase.insertRecipe(
                    Integer.parseInt(data.get("id")),
                    getStringOrNull(data.get("name")),
                    getStringOrNull(data.get("cuisine")),
                    getStringOrNull(data.get("category")),
                    getStringOrNull(data.get("instructions")),
                    parseIntegerOrNull(data.get("nutrition")),
                    getStringOrNull(data.get("cookingTime")),
                    getStringOrNull(data.get("ingredient"))
            );
        } catch (NumberFormatException ex) {
            throw new IllegalArgumentException("Invalid input: ID must be an integer.", ex);
        } catch (Exception ex) {
            throw new RuntimeException("Error adding recipe: " + ex.getMessage(), ex);
        }
    }


    /**
     * Delete a single recipe based on its ID.
     * @param id The recipe ID to delete.
     * @return The result after deletion.
     */
    public Result<Record> deleteRecipe(String id) {
        try {
            int parsedId = Integer.parseInt(id);
            return recipeDatabase.deleteRecipe(parsedId);
        } catch (NumberFormatException ex) {
            throw new IllegalArgumentException("Invalid input: ID must be an integer.", ex);
        } catch (Exception ex) {
            throw new RuntimeException("Error deleting recipe: " + ex.getMessage(), ex);
        }
    }

    /**
     * Delete all recipes from the database.
     */
    public Result<Record> deleteAllRecipes() {
        try {
            return recipeDatabase.deleteAllRecipes();
        } catch (Exception ex) {
            throw new RuntimeException("Error deleting all recipes: " + ex.getMessage(), ex);
        }
    }

    /**
     * Update an existing recipe with the provided data.
     * @param data A map containing the recipe properties.
     * @return Result containing the updated recipe.
     */
    public Result<Record> updateRecipe(Map<String, String> data) {
        try {
            return recipeDatabase.updateRecipe(
                    Integer.parseInt(data.get("id")),
                    getStringOrNull(data.get("name")),
                    getStringOrNull(data.get("cuisine")),
                    getStringOrNull(data.get("category")),
                    getStringOrNull(data.get("instructions")),
                    parseIntegerOrNull(data.get("nutrition")),
                    getStringOrNull(data.get("cookingTime")),
                    getStringOrNull(data.get("ingredient"))
            );
        } catch (NumberFormatException ex) {
            throw new IllegalArgumentException("Invalid input: ID must be an integer.", ex);
        } catch (Exception ex) {
            throw new RuntimeException("Error updating recipe: " + ex.getMessage(), ex);
        }
    }

    /**
     * Filter recipes based on provided data.
     * Only non-null or non-empty fields are included in the filter.
     * @param data A map containing the filter data.
     * @return The result after applying the filter.
     */
    public Result<Record> filterRecipes(Map<String, String> data) {
        try {
            return recipeDatabase.filterRecipes(
                    parseIntegerOrDefault(data.get("id")),
                    getStringOrNull(data.get("name")),
                    getStringOrNull(data.get("cuisine")),
                    getStringOrNull(data.get("category")),
                    getStringOrNull(data.get("instructions")),
                    parseIntegerOrDefault(data.get("nutrition")),
                    getStringOrNull(data.get("cookingTime")),
                    getStringOrNull(data.get("ingredient"))
            );
        } catch (Exception ex) {
            throw new RuntimeException("Error filtering recipes: " + ex.getMessage(), ex);
        }
    }

    /**
     * Convert an empty string to null.
     */
    private String getStringOrNull(String value) {
        return (value == null || value.trim().isEmpty()) ? null : value.trim();
    }

    /**
     * Parse an integer or return null if invalid.
     */
    private Integer parseIntegerOrNull(String value) {
        try {
            return value == null || value.trim().isEmpty() ? null : Integer.parseInt(value.trim());
        } catch (NumberFormatException ex) {
            return null; // Or throw an exception depending on your needs.
        }
    }

    /**
     * Parse an integer or return a zero if invalid.
     */
    private int parseIntegerOrDefault(String value) {
        try {
            return value == null || value.trim().isEmpty() ? 0 : Integer.parseInt(value.trim());
        } catch (NumberFormatException ex) {
            return 0; // Or throw an exception depending on your needs.
        }
    }

    /**
     * Set the DefaultTableModel for updating the view.
     */
    public void setTableModel(DefaultTableModel tableModel) {
        this.tableModel = tableModel;
    }

}