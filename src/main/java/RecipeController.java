import java.awt.event.ActionEvent;
import javax.swing.*;
import org.jooq.Record;
import org.jooq.Result;

/**
 * The RecipeController class handles the interactions between RecipeView and RecipeModel.
 * It sets up actions for user interface components and facilitates MVC design pattern roles.
 */
public class RecipeController {

    private final RecipeModel recipeModel;
    private final RecipeView recipeView;

    private final JTextField idField, nameField, cuisineField, categoryField, instructionsField,
            nutritionField, cookingTimeField, ingredientField;

    private static final String ERROR_LOADING_RECIPES = "Error loading recipes.";
    private static final String ERROR_ADDING_RECIPE = "Error adding recipe.";
    private static final String ERROR_DELETING_RECIPE = "Error deleting recipe.";
    private static final String ERROR_DELETING_ALL_RECIPES = "Error deleting all recipes.";
    private static final String ERROR_FILTERING_RECIPE = "Error filtering recipes.";
    private static final String ERROR_UPDATING_RECIPE = "Error updating recipe.";
    private static final String SUCCESS_RECIPE_LOADED = "Recipes loaded successfully.";
    private static final String SUCCESS_RECIPE_ADDED = "Recipe added successfully.";
    private static final String SUCCESS_RECIPE_DELETED = "Recipe deleted successfully.";
    private static final String SUCCESS_ALL_RECIPES_DELETED = "Recipe list has been cleared.";
    private static final String SUCCESS_RECIPE_FILTERED = "Recipes filtered successfully.";
    private static final String SUCCESS_RECIPE_UPDATED = "Recipe updated successfully.";

    public RecipeController(RecipeView recipeView, RecipeModel recipeModel) {
        this.recipeModel = recipeModel;
        this.recipeView = recipeView;
        idField = recipeView.getIdField();
        nameField = recipeView.getNameField();
        cuisineField = recipeView.getCuisineField();
        categoryField = recipeView.getCategoryField();
        instructionsField = recipeView.getInstructionsField();
        nutritionField = recipeView.getNutritionField();
        cookingTimeField = recipeView.getCookingTimeField();
        ingredientField = recipeView.getIngredientField();

        recipeModel.setTableModel(recipeView.getTableModel());
        initializeButtonActions();
    }

    private void initializeButtonActions() {
        setButtonAction(recipeView.getLoadButton(), this::handleLoadRecipes);
        setButtonAction(recipeView.getDeleteAllRecipeButton(), this::handleDeleteAllRecipes);
        setButtonAction(recipeView.getDeleteRecipeButton(), this::handleDeleteRecipe);
        setButtonAction(recipeView.getAddRecipeButton(), this::handleAddRecipe);
        setButtonAction(recipeView.getFilterRecipeButton(), this::handleFilterRecipes);
        setButtonAction(recipeView.getUpdateRecipeButton(), this::handleUpdateRecipe);
        setButtonAction(recipeView.getBackButton(), this::handleBackToMenu);
    }

    // Button action handlers

    private void handleLoadRecipes(ActionEvent event) {
        processModelAction(
                () -> recipeModel.reloadRecipes(event),
                SUCCESS_RECIPE_LOADED,
                ERROR_LOADING_RECIPES
        );
    }

    private void handleDeleteAllRecipes(ActionEvent event) {
        processModelAction(
                () -> recipeModel.deleteAllRecipes(event),
                SUCCESS_ALL_RECIPES_DELETED,
                ERROR_DELETING_ALL_RECIPES
        );
    }

    private void handleDeleteRecipe(ActionEvent event) {
        processModelAction(
                () -> recipeModel.deleteRecipe(event, idField),
                SUCCESS_RECIPE_DELETED,
                ERROR_DELETING_RECIPE
        );
    }

    private void handleAddRecipe(ActionEvent event) {
        processModelAction(
                () -> recipeModel.addRecipe(
                        event, idField, nameField, cuisineField, categoryField, instructionsField,
                        nutritionField, cookingTimeField, ingredientField
                ),
                SUCCESS_RECIPE_ADDED,
                ERROR_ADDING_RECIPE
        );
    }

    private void handleFilterRecipes(ActionEvent event) {
        processModelAction(
                () -> recipeModel.filterRecipes(
                        event, idField, nameField, cuisineField, categoryField, instructionsField,
                        nutritionField, cookingTimeField, ingredientField
                ),
                SUCCESS_RECIPE_FILTERED,
                ERROR_FILTERING_RECIPE
        );
    }

    private void handleUpdateRecipe(ActionEvent event) {
        try {
            Result<Record> result = recipeModel.updateRecipe(
                    event, idField, nameField, cuisineField, categoryField, instructionsField,
                    nutritionField, cookingTimeField, ingredientField
            );
            if (result != null && !result.isEmpty()) {
                recipeView.loadTable(result);
                recipeView.showSuccessDialog(SUCCESS_RECIPE_UPDATED);
            } else {
                recipeView.showErrorDialog(ERROR_UPDATING_RECIPE);
            }
        } catch (Exception ex) {
            handleUnexpectedError(ex);
        }
    }

    private void handleBackToMenu(ActionEvent event) {
        recipeView.closeView();
        ControllerFactory.getInstance().getMenuController().show();
    }

    // Utility methods for reducing redundancy

    private void setButtonAction(JButton button, RecipeAction recipeAction) {
        button.addActionListener(recipeAction::execute);
    }

    private void processModelAction(ModelAction modelAction, String successMessage, String errorMessage) {
        try {
            Result<Record> result = modelAction.execute();
            if (result != null) {
                recipeView.loadTable(result);
                recipeView.showSuccessDialog(successMessage);
            } else {
                recipeView.showErrorDialog(errorMessage);
            }
        } catch (Exception ex) {
            handleUnexpectedError(ex);
        }
    }

    private void handleUnexpectedError(Exception ex) {
        ex.printStackTrace();
        recipeView.showErrorDialog("An unexpected error occurred: " + ex.getMessage());
    }

    public void show(){
        recipeView.setVisible(true);
    }

    // Functional interfaces for clean abstractions

    @FunctionalInterface
    private interface RecipeAction {
        void execute(ActionEvent event);
    }

    @FunctionalInterface
    private interface ModelAction {
        Result<Record> execute() throws Exception;
    }
}