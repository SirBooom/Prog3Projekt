package Recipe;

import Factory.ControllerFactory;
import Menu.MenuController;
import Menu.MenuView;

import java.awt.event.ActionEvent;
import java.sql.SQLException;
import java.util.Map;
import javax.swing.*;
import org.jooq.Record;
import org.jooq.Result;

/**
 * The Recipes.RecipeController class handles the interactions between Recipes.RecipeView and Recipes.RecipeModel.
 * It sets up actions for user interface components and facilitates MVC design pattern roles.
 */
public class RecipeController {

    private final RecipeModel recipeModel;
    private final RecipeView recipeView;


    public RecipeController(RecipeView recipeView, RecipeModel recipeModel) {
        this.recipeModel = recipeModel;
        this.recipeView = recipeView;

        recipeModel.setTableModel(recipeView.getTableModel());
        initializeButtonActions();
    }

    private void initializeButtonActions() {
        Map<JButton, RecipeAction> actions = Map.of(
                recipeView.getLoadButton(), this::handleLoadRecipes,
                recipeView.getDeleteAllRecipeButton(), this::handleDeleteAllRecipes,
                recipeView.getDeleteRecipeButton(), this::handleDeleteRecipe,
                recipeView.getAddRecipeButton(), this::handleAddRecipe,
                recipeView.getFilterRecipeButton(), this::handleFilterRecipes,
                recipeView.getUpdateRecipeButton(), this::handleUpdateRecipe,
                recipeView.getBackButton(), this::handleBackToMenu
        );
        // Assign actions to buttons.
        actions.forEach(this::setButtonAction);
    }

    // Button action handlers

    public void handleLoadRecipes(ActionEvent event) {
        processModelAction(
                recipeModel::reloadRecipes,
                RecipeMessage.SUCCESS_RECIPE_LOADED,
                RecipeMessage.ERROR_LOADING_RECIPES
        );
    }

    public void handleDeleteAllRecipes(ActionEvent event) {
        processModelAction(
                recipeModel::deleteAllRecipes,
                RecipeMessage.SUCCESS_ALL_RECIPES_DELETED,
                RecipeMessage.ERROR_DELETING_ALL_RECIPES
        );
    }

    public void handleDeleteRecipe(ActionEvent event) {
        processModelAction(
                () -> recipeModel.deleteRecipe(recipeView.getFormData().get("id")),
                RecipeMessage.SUCCESS_RECIPE_DELETED,
                RecipeMessage.ERROR_DELETING_RECIPE
        );
    }

    public void handleAddRecipe(ActionEvent event) {
        processModelAction(
                () -> recipeModel.addRecipe(recipeView.getFormData()),

                RecipeMessage.SUCCESS_RECIPE_ADDED,
                RecipeMessage.ERROR_ADDING_RECIPE
        );
    }

    private void handleFilterRecipes(ActionEvent event) {
        processModelAction(
                () -> recipeModel.filterRecipes(recipeView.getFormData()),
                RecipeMessage.SUCCESS_RECIPE_FILTERED,
                RecipeMessage.ERROR_FILTERING_RECIPE
        );
    }

    public void handleUpdateRecipe(ActionEvent event) {
        try {
            Result<Record> result = recipeModel.updateRecipe(recipeView.getFormData());
            if (result != null && !result.isEmpty()) {
                recipeView.loadTable(result);
                recipeView.showSuccessDialog(RecipeMessage.SUCCESS_RECIPE_UPDATED);
            } else {
                recipeView.showErrorDialog(RecipeMessage.ERROR_UPDATING_RECIPE);
            }
        } catch (Exception ex) {
            handleUnexpectedError(ex);
        }
    }

    private void handleBackToMenu(ActionEvent event) {
        recipeView.closeView();
        try {
            ControllerFactory.getInstance().getMenuController().show();
        } catch (SQLException ex) {
            recipeView.showErrorDialog("Failed to return to the menu: " + ex.getMessage());
            ex.printStackTrace();
        }
    }

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