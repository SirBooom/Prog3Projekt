import Recipe.RecipeModel;
import database.RecipeDatabase;
import org.jooq.Result;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.jooq.Record;

import java.sql.SQLException;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

public class RecipeModelTest {
    private RecipeDatabase recipeDatabaseMock;
    private RecipeModel recipeModel;

    @BeforeEach
    public void setup(){
        recipeDatabaseMock = mock(RecipeDatabase.class);
        recipeModel = new RecipeModel(recipeDatabaseMock);
    }

    @Test
    public void testReloadRecipes(){
        Result<Record> dummyResult = mock(Result.class);
        when(recipeDatabaseMock.fetchAllRecipes()).thenReturn(dummyResult);
        Result<Record> result = recipeModel.reloadRecipes();
        verify(recipeDatabaseMock, times(1)).fetchAllRecipes();
        assertEquals(dummyResult, result, "Das Ergebnis sollte dem Dummy-Ergebnis entsprechen.");
    }

    @Test
    public void testAddRecipe() {
        Map<String, String> recipeData = Map.of(
                "id", "1",
                "name", "Test Recipe",
                "cuisine", "Italian",
                "category", "Main Course",
                "instructions", "Cook the pasta",
                "nutrition", "300",
                "cookingTime", "30",
                "ingredient", "Pasta"
        );

        Result<Record> dummyResult = mock(Result.class);
        when(recipeDatabaseMock.insertRecipe(
                1, "Test Recipe", "Italian", "Main Course", "Cook the pasta", 300, "30", "Pasta"
        )).thenReturn(dummyResult);

        Result<Record> result = recipeModel.addRecipe(recipeData);

        verify(recipeDatabaseMock, times(1)).insertRecipe(1, "Test Recipe", "Italian", "Main Course", "Cook the pasta", 300, "30", "Pasta");
        assertEquals(dummyResult, result, "Das Ergebnis sollte dem Dummy-Ergebnis entsprechen.");
    }

    @Test
    public void testDeleteRecipe() {
        String recipeId = "1";
        Result<Record> dummyResult = mock(Result.class);
        when(recipeDatabaseMock.deleteRecipe(1)).thenReturn(dummyResult);

        Result<Record> result = recipeModel.deleteRecipe(recipeId);

        verify(recipeDatabaseMock, times(1)).deleteRecipe(1);
        assertEquals(dummyResult, result, "Das Ergebnis sollte dem Dummy-Ergebnis entsprechen.");
    }

    @Test
    public void testDeleteAllRecipes() {
        Result<Record> dummyResult = mock(Result.class);
        when(recipeDatabaseMock.deleteAllRecipes()).thenReturn(dummyResult);

        Result<Record> result = recipeModel.deleteAllRecipes();

        verify(recipeDatabaseMock, times(1)).deleteAllRecipes();
        assertEquals(dummyResult, result, "Das Ergebnis sollte dem Dummy-Ergebnis entsprechen.");
    }

    @Test
    public void testUpdateRecipe() throws SQLException {
        Map<String, String> recipeData = Map.of(
                "id", "1",
                "name", "Updated Recipe",
                "cuisine", "Italian",
                "category", "Main Course",
                "instructions", "Updated instructions",
                "nutrition", "350",
                "cookingTime", "45",
                "ingredient", "Updated ingredient"
        );

        Result<Record> dummyResult = mock(Result.class);
        when(recipeDatabaseMock.updateRecipe(
                1, "Updated Recipe", "Italian", "Main Course", "Updated instructions", 350, "45", "Updated ingredient"
        )).thenReturn(dummyResult);

        Result<Record> result = recipeModel.updateRecipe(recipeData);

        verify(recipeDatabaseMock, times(1)).updateRecipe(1, "Updated Recipe", "Italian", "Main Course", "Updated instructions", 350, "45", "Updated ingredient");
        assertEquals(dummyResult, result, "Das Ergebnis sollte dem Dummy-Ergebnis entsprechen.");
    }

    @Test
    public void testFilterRecipes() {
        Map<String, String> filterCriteria = Map.of(
                "id", "1",
                "name", "Test Recipe",
                "cuisine", "Italian",
                "category", "Main Course",
                "instructions", "Cook the pasta",
                "nutrition", "300",
                "cookingTime", "30",
                "ingredient", "Pasta"
        );

        Result<Record> dummyResult = mock(Result.class);
        when(recipeDatabaseMock.filterRecipes(
                1, "Test Recipe", "Italian", "Main Course", "Cook the pasta", 300, "30", "Pasta"
        )).thenReturn(dummyResult);

        Result<Record> result = recipeModel.filterRecipes(filterCriteria);

        verify(recipeDatabaseMock, times(1)).filterRecipes(1, "Test Recipe", "Italian", "Main Course", "Cook the pasta", 300, "30", "Pasta");
        assertEquals(dummyResult, result, "Das Ergebnis sollte dem Dummy-Ergebnis entsprechen.");
    }


}
