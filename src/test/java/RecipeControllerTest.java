import Recipe.RecipeController;
import Recipe.RecipeMessage;
import Recipe.RecipeModel;
import Recipe.RecipeView;
import org.jooq.Result;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.jooq.Record;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;

import java.awt.event.ActionEvent;
import java.util.Map;

import static org.mockito.Mockito.*;

public class RecipeControllerTest {
    private RecipeModel recipeModelMock;
    private RecipeView recipeViewMock;
    private RecipeController recipeController;

    @BeforeEach
    void setUp() {
        recipeModelMock = mock(RecipeModel.class);
        recipeViewMock = mock(RecipeView.class);
        when(recipeViewMock.getTableModel()).thenReturn(new DefaultTableModel());
        mockButtons();

        recipeController = new RecipeController(recipeViewMock, recipeModelMock);
    }

    private void mockButtons() {
        when(recipeViewMock.getLoadButton()).thenReturn(mock(JButton.class));
        when(recipeViewMock.getDeleteAllRecipeButton()).thenReturn(mock(JButton.class));
        when(recipeViewMock.getDeleteRecipeButton()).thenReturn(mock(JButton.class));
        when(recipeViewMock.getAddRecipeButton()).thenReturn(mock(JButton.class));
        when(recipeViewMock.getFilterRecipeButton()).thenReturn(mock(JButton.class));
        when(recipeViewMock.getUpdateRecipeButton()).thenReturn(mock(JButton.class));
        when(recipeViewMock.getBackButton()).thenReturn(mock(JButton.class));
    }

    @Test
    void testHandleLoadRecipes() {
        Result<Record> dummyResult = mock(Result.class);
        when(recipeModelMock.reloadRecipes()).thenReturn(dummyResult);
        ActionEvent event = new ActionEvent(new Object(), 0, "Load");
        recipeController.handleLoadRecipes(event);

        verify(recipeModelMock, times(1)).reloadRecipes();
        verify(recipeViewMock, times(1)).loadTable(dummyResult);
        verify(recipeViewMock, times(1)).showSuccessDialog(RecipeMessage.SUCCESS_RECIPE_LOADED);
    }

    @Test
    void testHandleDeleteAllRecipes() {
        ActionEvent event = new ActionEvent(new Object(), 0, "DeleteAll");
        when(recipeModelMock.deleteAllRecipes()).thenReturn(mock(Result.class));
        recipeController.handleDeleteAllRecipes(event);
        verify(recipeModelMock, times(1)).deleteAllRecipes();
        verify(recipeViewMock, times(1)).showSuccessDialog(RecipeMessage.SUCCESS_ALL_RECIPES_DELETED);
    }


    @Test
    void testHandleDeleteRecipe() {
        ActionEvent event = new ActionEvent(new Object(), 0, "Delete");
        when(recipeViewMock.getFormData()).thenReturn(Map.of("id", "123"));
        when(recipeModelMock.deleteRecipe("123")).thenReturn(mock(Result.class));
        recipeController.handleDeleteRecipe(event);
        verify(recipeModelMock, times(1)).deleteRecipe("123");
        verify(recipeViewMock, times(1)).showSuccessDialog(RecipeMessage.SUCCESS_RECIPE_DELETED);
    }


    @Test
    void testHandleAddRecipe() {
        ActionEvent event = new ActionEvent(new Object(), 0, "Add");
        when(recipeViewMock.getFormData()).thenReturn(Map.of("name", "New Recipe"));
        when(recipeModelMock.addRecipe(anyMap())).thenReturn(mock(Result.class));
        recipeController.handleAddRecipe(event);

        verify(recipeModelMock, times(1)).addRecipe(anyMap());
        verify(recipeViewMock, times(1)).showSuccessDialog(RecipeMessage.SUCCESS_RECIPE_ADDED);
    }


    @Test
    void testHandleUpdateRecipe() {
        ActionEvent event = new ActionEvent(new Object(), 0, "Update");
        Result<Record> dummyResult = mock(Result.class);
        when(dummyResult.isEmpty()).thenReturn(false);
        when(recipeModelMock.updateRecipe(anyMap())).thenReturn(dummyResult);

        recipeController.handleUpdateRecipe(event);

        verify(recipeViewMock, times(1)).loadTable(dummyResult);
        verify(recipeViewMock, times(1)).showSuccessDialog(RecipeMessage.SUCCESS_RECIPE_UPDATED);
    }

    @Test
    void testHandleUpdateRecipe_NoResult() {
        ActionEvent event = new ActionEvent(new Object(), 0, "Update");

        Result<Record> emptyResult = mock(Result.class);
        when(emptyResult.isEmpty()).thenReturn(true);
        when(recipeModelMock.updateRecipe(anyMap())).thenReturn(emptyResult);

        recipeController.handleUpdateRecipe(event);

        verify(recipeViewMock, times(1)).showErrorDialog(RecipeMessage.ERROR_UPDATING_RECIPE);
    }
}
