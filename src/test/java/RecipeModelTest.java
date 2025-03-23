/*
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
        recipeModel = new RecipeModel();
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
*/

import Recipe.RecipeModel;
import org.jooq.DSLContext;
import org.jooq.Result;
import org.jooq.Record;
import org.jooq.SQLDialect;
import org.jooq.impl.DSL;
import org.junit.jupiter.api.*;

import javax.swing.table.DefaultTableModel;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;

import static com.example.generated.Tables.RECIPE;
import static org.junit.jupiter.api.Assertions.*;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class RecipeModelTest {

    private DSLContext context;
    private RecipeModel model;

    @BeforeAll
    void setUpAll() throws SQLException {
        context = createDSLContext(); // From the RecipeDatabaseTest example
        context.createTable(RECIPE).columns(RECIPE.ID, RECIPE.NAME, RECIPE.CUISINE, RECIPE.CATEGORY, RECIPE.INSTRUCTIONS, RECIPE.NUTRITION, RECIPE.COOKINGTIME, RECIPE.INGREDIENT).execute();
        model = new RecipeModel(context);
        DefaultTableModel tableModel = new DefaultTableModel();
        model.setTableModel(tableModel);
    }

    @BeforeEach
    void setUp() throws SQLException {
        model.deleteAllRecipes();
    }


    ////////////////////////////////////////// -- Add RECIPE -- //////////////////////////////////////////

    @Test
    void testAddRecipe_FullRecipe() {
        Result<Record> result = model.addRecipe(RecipeDataHelper.fullRecipe1());
        assertNotNull(result);
        assertEquals(1, result.size());
        assertRecipeDataEquals(result.getFirst(), RecipeDataHelper.fullRecipe1());
    }

    @Test
    void testAddRecipe_PartialData() {
        Map<String, String> partialData = Map.of("id", "4", "name", "Test Recipe");
        Result<Record> result = model.addRecipe(partialData);
        assertNotNull(result);
        assertEquals(1, result.size());
        Record addedRecipe = result.getFirst();
        assertEquals("Test Recipe", addedRecipe.get(RECIPE.NAME));
        assertNull(addedRecipe.get(RECIPE.CUISINE));
        assertNull(addedRecipe.get(RECIPE.CATEGORY));
    }

    @Test
    void testAddRecipe_EmptyInput() {
        assertThrows(IllegalArgumentException.class, () -> model.addRecipe(RecipeDataHelper.emptyInput()));
    }

    @Test
    void testAddRecipe_NegativeID() {
        assertThrows(RuntimeException.class, () -> model.addRecipe(RecipeDataHelper.negativeID()));
    }

    @Test
    void testAddRecipe_NullName() {
        assertThrows(RuntimeException.class, () -> model.addRecipe(RecipeDataHelper.IdInput()));
    }

    @Test
    void testAddRecipe_existingID() {
        model.addRecipe(RecipeDataHelper.fullRecipe3()); // Add a recipe with ID 1
        assertThrows(RuntimeException.class, () -> model.addRecipe(RecipeDataHelper.existingID())); // Attempt to add a duplicate
    }

    @Test
    void testAddRecipe_existingName() {
        model.addRecipe(RecipeDataHelper.fullRecipe1()); // Add a recipe with ID 1
        assertThrows(RuntimeException.class, () -> model.addRecipe(RecipeDataHelper.existingName())); // Attempt to add a duplicate
    }

    ////////////////////////////////////////// -- Delete RECIPE -- //////////////////////////////////////////

    @Test
    void testDeleteRecipe_NonExisting() { assertThrows(RuntimeException.class, () -> model.deleteRecipe("999"));
    }

    @Test
    void testDeleteRecipe_NullID() { assertThrows(IllegalArgumentException.class, () -> model.deleteRecipe(""));
    }

    @Test
    void testDeleteRecipe() {
        model.addRecipe(Map.of("id", "1", "name", "Test")); // Add a recipe first

        Result<Record> beforeDelete = model.reloadRecipes();
        assertEquals(1,beforeDelete.size());

        model.deleteRecipe("1");
        Result<Record> afterDelete = model.reloadRecipes();
        assertEquals(0, afterDelete.size());
    }

    ////////////////////////////////////////// -- Update RECIPE -- //////////////////////////////////////////

    @Test
    void testUpdateRecipe_FullUpdate() {
        model.addRecipe(RecipeDataHelper.fullRecipe1());

        Map<String, String> newRecipe1 = new HashMap<>(RecipeDataHelper.fullRecipe2());
        newRecipe1.put("id", "1");
        model.updateRecipe(newRecipe1);
        Record updatedRecipe = context.selectFrom(RECIPE).where(RECIPE.ID.eq(1)).fetchOne();
        assertNotNull(updatedRecipe);
        assertRecipeDataEquals(updatedRecipe,newRecipe1);
    }

    @Test
    void testUpdateRecipe_PartialUpdate() {
        model.addRecipe(RecipeDataHelper.fullRecipe1());
        Map<String, String> updateData = new HashMap<>(RecipeDataHelper.fullRecipe1());
        updateData.put("name", "Updated Pizza");
        model.updateRecipe(updateData);
        Record updatedRecipe = context.selectFrom(RECIPE).where(RECIPE.ID.eq(1)).fetchOne();
        assertNotNull(updatedRecipe);
        assertEquals("Updated Pizza", updatedRecipe.get(RECIPE.NAME));
    }

    @Test
    void testUpdateRecipe_NonExisting() {
        Map<String, String> updateData = new HashMap<>(RecipeDataHelper.fullRecipe1());
        updateData.put("id", "999");
        assertThrows(RuntimeException.class, () -> model.updateRecipe(updateData));
    }

    @Test
    void testUpdateRecipe_negativeID() {
        Map<String, String> updateData = new HashMap<>(RecipeDataHelper.negativeID());
        assertThrows(RuntimeException.class, () -> model.updateRecipe(updateData));
    }

    @Test
    void testUpdateRecipe_nullID() {
        Map<String, String> updateData = new HashMap<>(RecipeDataHelper.emptyInput());
        assertThrows(IllegalArgumentException.class, () -> model.updateRecipe(updateData));
    }

    ////////////////////////////////////////// -- Filter RECIPE -- //////////////////////////////////////////
    @Test
    void testFilterRecipes_multipleFilters() {
        model.addRecipe(RecipeDataHelper.fullRecipe1());
        model.addRecipe(RecipeDataHelper.fullRecipe2());
        model.addRecipe(RecipeDataHelper.fullRecipe3());


        //different filter combinations
        assertEquals(2, model.filterRecipes(Map.of("cuisine", "American")).size());
        assertEquals(1, model.filterRecipes(Map.of("category", "Main Course")).size());
        assertEquals(1, model.filterRecipes(Map.of("name", "Grilled Cheese Sandwich")).size());
        assertEquals(0, model.filterRecipes(Map.of("cuisine", "French")).size());  //No French recipes
    }

    ////////////////////////////////////////// -- Helper Functions -- //////////////////////////////////////////

    private void assertRecipeDataEquals(Record record, Map<String, String> expectedData) {
        assertEquals(Integer.parseInt(expectedData.get("id")), record.get(RECIPE.ID));
        assertEquals(expectedData.get("name"), record.get(RECIPE.NAME));
        assertEquals(expectedData.get("cuisine"), record.get(RECIPE.CUISINE));
        assertEquals(expectedData.get("category"), record.get(RECIPE.CATEGORY));
        assertEquals(expectedData.get("instructions"), record.get(RECIPE.INSTRUCTIONS));
        assertEquals(Integer.parseInt(expectedData.get("nutrition")), record.get(RECIPE.NUTRITION));
        assertEquals(expectedData.get("cookingTime"), record.get(RECIPE.COOKINGTIME));
        assertEquals(expectedData.get("ingredient"), record.get(RECIPE.INGREDIENT));
    }

    private DSLContext createDSLContext() throws SQLException {
        Connection connection = DriverManager.getConnection("jdbc:h2:mem:testdb");
        return DSL.using(connection, SQLDialect.H2); //Replace with your dialect
    }


    @AfterAll
    void tearDownAll() throws SQLException {
        context.dropTable(RECIPE).execute();
    }

}