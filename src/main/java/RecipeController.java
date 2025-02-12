import javax.swing.*;
import java.awt.event.ActionEvent;


import org.jooq.Result;
import org.jooq.Record;

/**
 * The RecipeController class handles the interactions between the RecipeView and the RecipeModel.
 * It sets up the actions for various user interface components and ensures the defined actions
 * in the RecipeModel are executed in response to user interactions. This class follows the
 * MVC (Model-View-Controller) design pattern acting as the controller layer.
 */
public class RecipeController {
    private final RecipeModel recipeModel;
    private final RecipeView recipeView;

    private final JTextField idField, nameField, cuisineField, categoryField, instructionsField,
            nutritionField, cookingTimeField, ingredientField;
    //private final String idField;
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
    private static final String NOTHING_UPDATED = "Nothing has been updated.";

    public RecipeController() {
        this.recipeView = new RecipeView();
        this.recipeModel = new RecipeModel();

        //this.tableModel = recipeView.getTableModel();
        this.idField = recipeView.getIdField();
        this.nameField = recipeView.getNameField();
        this.cuisineField = recipeView.getCuisineField();
        this.categoryField = recipeView.getCategoryField();
        this.instructionsField = recipeView.getInstructionsField();
        this.nutritionField = recipeView.getNutritionField();
        this.cookingTimeField = recipeView.getCookingTimeField();
        this.ingredientField = recipeView.getIngredientField();

        recipeModel.setTableModel(recipeView.getTableModel());


        initializeButtonActions();
    }

    private void initializeButtonActions() {
        //load recipes
        setButtonAction(recipeView.getLoadButton(), event -> {
            try {
                Result<Record> result = recipeModel.reloadRecipes(event);
                if (result != null) {
                    recipeView.loadTable(result);
                    recipeView.showSuccessDialog(SUCCESS_RECIPE_LOADED);
                } else {
                    recipeView.showErrorDialog(ERROR_LOADING_RECIPES);
                }
            } catch (Exception ex) {
                // Handle any unexpected exception that might occur during reload
                ex.printStackTrace();
                recipeView.showErrorDialog("An unexpected error occurred: " + ex.getMessage());
            }
        });
        //delete all recipes
        setButtonAction(recipeView.getDeleteAllRecipeButton(), event -> {
            try {
                Result<Record> result = recipeModel.deleteAllRecipes(event);
                if (result != null) {
                    recipeView.loadTable(result);
                    recipeView.showSuccessDialog(SUCCESS_ALL_RECIPES_DELETED);
                } else {
                    recipeView.showErrorDialog(ERROR_DELETING_ALL_RECIPES);
                }
            } catch (Exception ex) {
                // Handle any unexpected exception that might occur during reload
                ex.printStackTrace();
                recipeView.showErrorDialog("An unexpected error occurred: " + ex.getMessage());
            }
        });
        //delete recipe
        setButtonAction(recipeView.getDeleteRecipeButton(), event -> {
            try {
                Result<Record> result = recipeModel.deleteRecipe(event, idField);
                if(result  != null){
                    recipeView.loadTable(result);
                    recipeView.showSuccessDialog(SUCCESS_RECIPE_DELETED);
                } else {
                    recipeView.showErrorDialog(ERROR_DELETING_RECIPE);
                }
            } catch (Exception ex) {
                ex.printStackTrace();
                recipeView.showErrorDialog("An unexpected error occurred: " + ex.getMessage());
            }
        });
        //add recipe
        setButtonAction(recipeView.getAddRecipeButton(), event -> {
            try {
                Result<Record> result = recipeModel.addRecipe(event, idField, nameField, cuisineField, categoryField, instructionsField, nutritionField, cookingTimeField, ingredientField);
                if(result  != null){
                    recipeView.loadTable(result);
                    recipeView.showSuccessDialog(SUCCESS_RECIPE_ADDED);
                } else {
                    recipeView.showErrorDialog(ERROR_ADDING_RECIPE);
                }
            } catch (Exception ex) {
                ex.printStackTrace();
                recipeView.showErrorDialog("An unexpected error occurred: " + ex.getMessage());
            }
        });
        //filter recipes
        setButtonAction(recipeView.getFilterRecipeButton(), event -> {
            try {
                Result<Record> result = recipeModel.filterRecipes(event, idField, nameField, cuisineField, categoryField, instructionsField, nutritionField, cookingTimeField, ingredientField);
                if(result  != null){
                    recipeView.loadTable(result);
                    recipeView.showSuccessDialog(SUCCESS_RECIPE_FILTERED);
                } else {
                    recipeView.showErrorDialog(ERROR_FILTERING_RECIPE);
                }
            } catch (Exception ex) {
                ex.printStackTrace();
                recipeView.showErrorDialog("An unexpected error occurred: " + ex.getMessage());
            }
        });
        //update recipe
        setButtonAction(recipeView.getUpdateRecipeButton(), event -> {
            try {
                Result<Record> result = recipeModel.updateRecipe(event, idField, nameField, cuisineField, categoryField, instructionsField, nutritionField, cookingTimeField, ingredientField);
                if(result != null && !result.isEmpty()){
                    recipeView.loadTable(result);
                    recipeView.showSuccessDialog(SUCCESS_RECIPE_UPDATED);
                } else {
                    recipeView.showErrorDialog(ERROR_UPDATING_RECIPE);
                }
            } catch (Exception ex) {
                ex.printStackTrace();
                recipeView.showErrorDialog("An unexpected error occurred: " + ex.getMessage());
            }
        });
        //go back
        setButtonAction(recipeView.getBackButton(), event -> {
            recipeModel.backToMenu(event);
            recipeView.closeView();
        });



    }

    public boolean backToMenu(ActionEvent e) {
        new MenuController(new MenuView());
        return true;
    }


    private void setButtonAction(JButton button, RecipeAction recipeAction) {
        button.addActionListener(recipeAction::execute);
    }


    @FunctionalInterface
    interface RecipeAction {
        void execute(ActionEvent event);
    }
}