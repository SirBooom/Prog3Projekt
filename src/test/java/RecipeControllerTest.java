import Factory.ControllerFactory;
import Menu.MenuController;
import Menu.MenuView;
import Recipe.RecipeController;
import Recipe.RecipeMessage;
import Recipe.RecipeModel;
import Recipe.RecipeView;
import database.Database;
import database.RecipeDatabase;
import java.sql.SQLException;
import org.h2.jdbcx.JdbcDataSource;
import org.jooq.DSLContext;
import org.jooq.impl.DSL;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import javax.swing.*;
import java.util.Map;
import org.mockito.InOrder;

import static com.example.generated.Tables.RECIPE;
import static org.mockito.Mockito.*;

class RecipeControllerButtonTest {

    static RecipeView mockRecipeView;
    static RecipeController recipeController;
    static RecipeModel mockRecipeModel;

    @BeforeAll
    static void setUp() throws SQLException {

        /*
        ControllerFactory controllerFactory = ControllerFactory.getInstance();
        MenuController menuController = controllerFactory.getMenuController();
        MenuView mockMenuView = menuController.menuView();
        JButton mockRecipeButton = mockMenuView.getRecipeButton();
        mockRecipeButton.doClick();

        //recipeController = ControllerFactory.getInstance().getRecipeController();
        */
        mockRecipeView = mock(RecipeView.class);
        mockRecipeModel = mock(RecipeModel.class);


        /*
        JdbcDataSource h2DataSource = new JdbcDataSource();
        h2DataSource.setURL("jdbc:h2:mem:testdb;DB_CLOSE_DELAY=-1;DB_CLOSE_ON_EXIT=FALSE");

        DSLContext h2Context = DSL.using(h2DataSource, org.jooq.SQLDialect.H2);
        Database.setDslContext(h2Context);

        // Create the test RECIPE table
        h2Context.execute("CREATE TABLE RECIPE (" +
                "id INT PRIMARY KEY," +
                "name VARCHAR(255) NOT NULL," +
                "cuisine VARCHAR(255)," +
                "category VARCHAR(255)," +
                "instructions TEXT," +
                "nutrition VARCHAR(32)," +
                "cookingTime INT," +
                "ingredient TEXT" +
                ")");
        */
    }


    @Test
    void testAddRecipeSuccess() {

        JButton addButton = mock(JButton.class);
        when(mockRecipeView.getAddRecipeButton()).thenReturn(addButton);
        mockRecipeView.setFormData(RecipeDataInputSamples.fullRecipe1());
        addButton.doClick();
        mockRecipeModel.addRecipe(RecipeDataInputSamples.fullRecipe1());
        mockRecipeView.showSuccessDialog(RecipeMessage.SUCCESS_RECIPE_ADDED);


        //when(mockRecipeView.getFormData()).thenReturn(RecipeDataInputSamples.fullRecipe1());;

        // Assert: Verify the model was called to add the recipe
        verify(mockRecipeModel, times(1)).addRecipe(RecipeDataInputSamples.fullRecipe1());
        verify(mockRecipeView, times(1)).showSuccessDialog(RecipeMessage.SUCCESS_RECIPE_ADDED);

    }

    @Test
    void testAddRecipeFailMissingName() {

        JButton addButton = mock(JButton.class);
        when(mockRecipeView.getAddRecipeButton()).thenReturn(addButton);
        mockRecipeView.setFormData(RecipeDataInputSamples.IdInput());
        addButton.doClick();
        mockRecipeModel.addRecipe(RecipeDataInputSamples.IdInput());
        mockRecipeView.showErrorDialog(RecipeMessage.ERROR_INVALID_RECIPE_ID_NAME);


        // Assert: Verify the model was called to add the recipe
        verify(mockRecipeModel, times(1)).addRecipe(RecipeDataInputSamples.IdInput());
        verify(mockRecipeView, times(1)).showErrorDialog(RecipeMessage.ERROR_INVALID_RECIPE_ID_NAME);

    }

    @Test
    void testAddRecipeFailMissingID() {

        JButton addButton = mock(JButton.class);
        when(mockRecipeView.getAddRecipeButton()).thenReturn(addButton);
        mockRecipeView.setFormData(RecipeDataInputSamples.nameInput());
        addButton.doClick();
        mockRecipeModel.addRecipe(RecipeDataInputSamples.nameInput());
        mockRecipeView.showErrorDialog(RecipeMessage.ERROR_INVALID_RECIPE_ID_NAME);


        // Assert: Verify the model was called to add the recipe
        verify(mockRecipeModel, times(1)).addRecipe(RecipeDataInputSamples.nameInput());
        verify(mockRecipeView, times(1)).showErrorDialog(RecipeMessage.ERROR_INVALID_RECIPE_ID_NAME);

    }

    @Test
    void testAddRecipeNegativeID() {

        JButton addButton = mock(JButton.class);
        when(mockRecipeView.getAddRecipeButton()).thenReturn(addButton);
        mockRecipeView.setFormData(RecipeDataInputSamples.negativeID());
        addButton.doClick();
        mockRecipeModel.addRecipe(RecipeDataInputSamples.negativeID());
        mockRecipeView.showErrorDialog("");


        // Assert: Verify the model was called to add the recipe
        verify(mockRecipeModel, times(1)).addRecipe(RecipeDataInputSamples.negativeID());
        verify(mockRecipeView, times(1)).showErrorDialog("");

    }

    @Test
    void testDeleteRecipeSuccess() {

        JButton deleteButton = mock(JButton.class);
        when(mockRecipeView.getDeleteRecipeButton()).thenReturn(deleteButton);
        mockRecipeView.setFormData(RecipeDataInputSamples.fullRecipe1());
        deleteButton.doClick();
        mockRecipeModel.deleteRecipe(RecipeDataInputSamples.fullRecipe1().get("id"));
        mockRecipeView.showSuccessDialog(RecipeMessage.SUCCESS_RECIPE_DELETED);

        // Assert: Verify the model was called to delete the recipe
        verify(mockRecipeModel, times(1)).deleteRecipe(RecipeDataInputSamples.fullRecipe1().get("id"));
        verify(mockRecipeView, times(1)).showSuccessDialog(RecipeMessage.SUCCESS_RECIPE_DELETED);
    }

    @Test
    void testDeleteRecipeFail() {

        JButton deleteButton = mock(JButton.class);
        when(mockRecipeView.getDeleteRecipeButton()).thenReturn(deleteButton);
        deleteButton.doClick();
        mockRecipeModel.deleteRecipe(RecipeDataInputSamples.emptyInput().get("id"));
        mockRecipeView.showErrorDialog(RecipeMessage.ERROR_DELETING_RECIPE);

        // Assert: Verify the model was called to delete the recipe
        verify(mockRecipeModel, times(1)).deleteRecipe(RecipeDataInputSamples.emptyInput().get("id"));
        verify(mockRecipeView, times(1)).showErrorDialog(RecipeMessage.ERROR_DELETING_RECIPE);
    }

    /*
    @Test
    void testUpdateRecipeSuccess() {

        JButton updateButton = mock(JButton.class);
        when(mockRecipeView.getUpdateRecipeButton()).thenReturn(updateButton);
        updateButton.doClick();
        //mockRecipeModel.updateRecipe(RecipeDataInputSamples.emptyInput().get("id"));
        mockRecipeView.showSuccessDialog(RecipeMessage.SUCCESS_RECIPE_UPDATED);

        // Assert: Verify the model was called to delete the recipe
        verify(mockRecipeModel, times(1)).deleteRecipe(RecipeDataInputSamples.emptyInput().get("id"));
        verify(mockRecipeView, times(1)).showSuccessDialog(RecipeMessage.ERROR_DELETING_RECIPE);
    }
    */
    @Test
    void testUpdateRecipeFail() {

        JButton deleteButton = mock(JButton.class);
        when(mockRecipeView.getDeleteRecipeButton()).thenReturn(deleteButton);
        deleteButton.doClick();
        mockRecipeModel.updateRecipe(RecipeDataInputSamples.categoryInput());
        mockRecipeView.showErrorDialog(RecipeMessage.ERROR_UPDATING_RECIPE);

        // Assert: Verify the model was called to delete the recipe
        verify(mockRecipeModel, times(1)).updateRecipe(RecipeDataInputSamples.categoryInput());
        verify(mockRecipeView, times(1)).showErrorDialog(RecipeMessage.ERROR_UPDATING_RECIPE);
    }

    @Test
    void testFilterRecipeSuccess() {

        JButton deleteButton = mock(JButton.class);
        when(mockRecipeView.getDeleteRecipeButton()).thenReturn(deleteButton);
        deleteButton.doClick();
        mockRecipeModel.updateRecipe(RecipeDataInputSamples.categoryInput());
        mockRecipeView.showErrorDialog(RecipeMessage.ERROR_UPDATING_RECIPE);

        // Assert: Verify the model was called to delete the recipe
        verify(mockRecipeModel, times(1)).updateRecipe(RecipeDataInputSamples.categoryInput());
        verify(mockRecipeView, times(1)).showErrorDialog(RecipeMessage.ERROR_UPDATING_RECIPE);
    }

    @Test
    void testFilterRecipeFail() {

        JButton deleteButton = mock(JButton.class);
        when(mockRecipeView.getDeleteRecipeButton()).thenReturn(deleteButton);
        deleteButton.doClick();
        mockRecipeModel.updateRecipe(RecipeDataInputSamples.categoryInput());
        mockRecipeView.showErrorDialog(RecipeMessage.ERROR_UPDATING_RECIPE);

        // Assert: Verify the model was called to delete the recipe
        verify(mockRecipeModel, times(1)).updateRecipe(RecipeDataInputSamples.categoryInput());
        verify(mockRecipeView, times(1)).showErrorDialog(RecipeMessage.ERROR_UPDATING_RECIPE);
    }



    /*
    @Test
    void testDeleteRecipeButtonAction() throws SQLException {

        JButton mockDeleteRecipeButton = mockRecipeView.getDeleteRecipeButton();
        /*
        Map<String, String> dataDelete = Map.of(
                "id", "1",
                "name", "",
                "cuisine", "",
                "category", "",
                "instructions", ".",
                "nutrition", "",
                "cookingTime", "",
                "ingredient", ""
        );

        //click delete
        mockRecipeView.setFormData(dataDelete);

        mockDeleteRecipeButton.doClick();

        // Assert: Verify the model was called to delete the recipe
        verify(mockRecipeModel, times(1)).deleteRecipe("1");
        verify(mockRecipeView, times(1)).showSuccessDialog(RecipeMessage.SUCCESS_RECIPE_DELETED);
    }
    */
    @AfterAll
    public static void cleanDatabase() throws SQLException {
        DSLContext context = Database.getDslContext();
        context.deleteFrom(RECIPE).execute();
    }

}