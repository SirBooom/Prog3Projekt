package Recipe;

import Factory.ControllerFactory;
import java.awt.event.ActionEvent;
import java.sql.SQLException;
import java.util.Map;
import javax.swing.*;

import Template.RecipeView;
import org.jooq.Record;
import org.jooq.Result;

/**
 * This class handles the interactions between RecipeView and RecipeModel. It sets up actions for UI
 * components and controls the entire process.
 */
public record RecipeController(RecipeView recipeView, RecipeModel recipeModel) {

    public RecipeController(RecipeView recipeView, RecipeModel recipeModel) {
        this.recipeModel = recipeModel;
        this.recipeView = recipeView;

        recipeModel.setTableModel(recipeView.getTableModel());
        initializeButtonActions();
    }

    public void initializeButtonActions() {
        Map<JButton, RecipeAction> actions = Map.of(
                recipeView.getLoadButton(), this::handleLoadRecipes,
                recipeView.getDeleteAllRecipeButton(), this::handleDeleteAllRecipes,
                recipeView.getDeleteRecipeButton(), this::handleDeleteRecipe,
                recipeView.getAddRecipeButton(), this::handleAddRecipe,
                recipeView.getFilterRecipeButton(), this::handleFilterRecipes,
                recipeView.getUpdateRecipeButton(), this::handleUpdateRecipe,
                recipeView.getBackButton(), this::handleBackToMenu
        );
        // assign actions to buttons.
        actions.forEach(this::setButtonAction);
    }

    private void handleLoadRecipes(ActionEvent event) {
        processModelAction(
                recipeModel::reloadRecipes,
                RecipeMessage.SUCCESS_RECIPE_LOADED,
                RecipeMessage.ERROR_LOADING_RECIPES
        );
    }

    private void handleDeleteAllRecipes(ActionEvent event) {
        processModelAction(
                recipeModel::deleteAllRecipes,
                RecipeMessage.SUCCESS_ALL_RECIPES_DELETED,
                RecipeMessage.ERROR_DELETING_ALL_RECIPES
        );
    }

    private void handleDeleteRecipe(ActionEvent event) {
        processModelAction(
                () -> recipeModel.deleteRecipe(recipeView.getFormData().get("id")),
                RecipeMessage.SUCCESS_RECIPE_DELETED,
                RecipeMessage.ERROR_DELETING_RECIPE
        );
    }

    private void handleAddRecipe(ActionEvent event) {
        Map<String, String> formData = recipeView.getFormData();
        String errorMessage = (formData.get("id") == null || formData.get("name") == null)
                ? RecipeMessage.ERROR_ADDING_RECIPE
                : RecipeMessage.ERROR_INVALID_RECIPE_ID_NAME;

        processModelAction(
                () -> recipeModel.addRecipe(formData),
                RecipeMessage.SUCCESS_RECIPE_ADDED,
                errorMessage
        );
    }

    private void handleFilterRecipes(ActionEvent event) {
        processModelAction(
                () -> recipeModel.filterRecipes(recipeView.getFormData()),
                RecipeMessage.SUCCESS_RECIPE_FILTERED,
                RecipeMessage.ERROR_FILTERING_RECIPE
        );
    }

    private void handleUpdateRecipe(ActionEvent event) {
        Map<String, String> formData = recipeView.getFormData();
            processModelAction(
                    () -> recipeModel.updateRecipe(formData),
                    RecipeMessage.SUCCESS_RECIPE_UPDATED,
                    RecipeMessage.ERROR_UPDATING_RECIPE);
    }

    private void handleBackToMenu(ActionEvent event) {
        recipeView.closeView();
        try {
            ControllerFactory.getInstance().getMenuController().show();
        } catch (SQLException ex) {
            handleUnexpectedError(ex);
        }
    }

    private void setButtonAction(JButton button, RecipeAction recipeAction) {
        button.addActionListener(recipeAction::execute);
    }

    /**
     * Führt eine ModelAction aus, aktualisiert die View & zeigt Feedback.
     *
     * @param recipeModelAction Die auszuführende Aktion auf dem Model.
     * @param successMessage    Erfolgsnachricht bei erfolgreicher Ausführung.
     * @param errorMessage      Fehlermeldung bei Misserfolg oder fehlendem Ergebnis.
     */
    private void processModelAction(RecipeModelAction recipeModelAction, String successMessage,
            String errorMessage) {
        try {
            Result<Record> result = recipeModelAction.execute();
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

    public void handleUnexpectedError(Exception ex) {
        recipeView.showErrorDialog(ex.getMessage());
    }

    public void show() {
        recipeView.showView();
    }

    /**
     * Funktionales Interface, das eine Aktion repräsentiert, die durch eine Benutzerinteraktion in
     * dem view ausgelöst wird, z.B. click auf einen Button. Die Aktion ist dazu gedacht, das
     * Ereignis im Controller zu verarbeiten.
     */
    @FunctionalInterface
    private interface RecipeAction {

        /**
         * Führt die Aktion basierend auf einem vom Benutzer ausgelösten Ereignis aus.
         *
         * @param event Das ActionEvent, das mit der Interaktion des Benutzers verknüpft ist z.B.
         *              ein button click.
         */
        void execute(ActionEvent event);
    }

    /**
     * Funktionales Interface, das eine Aktion auf dem Model repräsentiert, typischerweise
     * einhergehend mit Datenabruf, einfügen, löschen, updaten, etc.
     */
    @FunctionalInterface
    private interface RecipeModelAction {

        /**
         * Führt die Datenbankoperation auf dem Model aus.
         *
         * @return ein Result<Record> Objekt, dass das Ergebnis der Enum.Operation repräsentiert z.B.
         * Abfrageergebnis.
         * @throws Exception Falls bei der Enum.Operation etwas schief läuft gibt z.B. Datenbankfehler.
         */
        Result<Record> execute() throws Exception;
    }
}