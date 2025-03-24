import com.example.generated.tables.records.RecipeRecord;
import database.Database;
import database.RecipeDatabase;
import org.jooq.DSLContext;
import org.jooq.Record;
import org.junit.jupiter.api.*;
import org.junit.jupiter.api.extension.ExtendWith;
import java.sql.SQLException;
import java.util.List;
import static com.example.generated.Tables.RECIPE;
import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(H2TestDatabaseExtension.class)
public class RecipeDatabaseTest {
    private RecipeDatabase recipeDatabase;
    private DSLContext context;

    @BeforeEach
    public void setup() throws SQLException {
        context = Database.getDslContext();
        recipeDatabase = new RecipeDatabase(context);
    }

    ////////////////////////////////////////// -- insert -- //////////////////////////////////////////
    @Test
    public void testInsert() {
        recipeDatabase.insertRecipe(1,"Name", "Cuisine", "Category", "Instruction", 10,"12min","Ingredient");
        recipeDatabase.insertRecipe(2,"NameSecond", "Cuisine", "Category", "Instruction", 10,"12min","Ingredient");
        Record recordID1 = context.selectFrom(RECIPE).where(RECIPE.ID.equal(1)).fetchOne();
        Record recordID2 = context.selectFrom(RECIPE).where(RECIPE.ID.equal(2)).fetchOne();
        assertNotNull(recordID1);
        assertEquals("Cuisine", recordID1.getValue(RECIPE.CUISINE));
        assertEquals(10, recordID1.getValue(RECIPE.NUTRITION));
        assertNotNull(recordID2);
        assertEquals("NameSecond", recordID2.getValue(RECIPE.NAME));
    }

    @Test
    public void testInsertNoName() {
        assertThrows(IllegalArgumentException.class, () -> recipeDatabase.insertRecipe(1,null, "Cuisine", "Category", "Instruction", 10,"12min","Ingredient"));
    }

    @Test
    public void testInsertNegativeID() {
        assertThrows(IllegalArgumentException.class, () -> recipeDatabase.insertRecipe(-1,"Name", "Cuisine", "Category", "Instruction", 10,"12min","Ingredient"));
    }

    @Test
    public void testInsertExistingIDAndName() {
        recipeDatabase.insertRecipe(1,"Name", "Cuisine", "Category", "Instruction", 10,"12min","Ingredient");
        assertThrows(IllegalArgumentException.class, () -> recipeDatabase.insertRecipe(1,"Name", "Cuisine", "Category", "Instruction", 10,"12min","Ingredient"));

    }

    ////////////////////////////////////////// -- update -- //////////////////////////////////////////
    @Test
    public void testUpdateRecipe() throws SQLException {
        recipeDatabase.insertRecipe(1,"Name", "Cuisine", "Category", "Instruction", 10,"12min","Ingredient");
        recipeDatabase.updateRecipe(1,null,"NewCuisine", "Category", "Instruction" , 5, "12min", "Ingredient");
        Record record = context.selectFrom(RECIPE).where(RECIPE.ID.equal(1)).fetchOne();
        assertNotNull(record);
        assertEquals("NewCuisine", record.getValue(RECIPE.CUISINE));
        assertEquals(5, record.getValue(RECIPE.NUTRITION));
        assertEquals("Name", record.getValue(RECIPE.NAME));
    }

    @Test
    public void testUpdateRecipeNonExistingID() {
        assertThrows(IllegalArgumentException.class, () -> recipeDatabase.updateRecipe(1,"Name", "Cuisine", "Category", "Instruction", 10,"12min","Ingredient"));
        assertThrows(IllegalArgumentException.class, () -> recipeDatabase.updateRecipe(-1,"Name", "Cuisine", "Category", "Instruction", 10,"12min","Ingredient"));
    }

    ////////////////////////////////////////// -- Reload -- //////////////////////////////////////////
    @Test
    public void showTable() {
        recipeDatabase.insertRecipe(1,"Name", "Cuisine", "Category", "Instruction", 10,"12min","Ingredient");
        List<RecipeRecord> records = context.selectFrom(RECIPE).fetch();
        assertNotNull(records);
        records.forEach(System.out::println);
    }

    ////////////////////////////////////////// -- Delete all -- //////////////////////////////////////////
    @Test
    public void testDeleteAllRecipes() {
        recipeDatabase.insertRecipe(1,"Name", "Cuisine", "Category", "Instruction", 10,"12min","Ingredient");
        recipeDatabase.insertRecipe(2,"NameSecond", "Cuisine", "Category", "Instruction", 10,"12min","Ingredient");
        recipeDatabase.deleteAllRecipes();
        int rowCount = context.fetchCount(RECIPE);
        assertEquals(0, rowCount);
    }

    ////////////////////////////////////////// -- Delete -- //////////////////////////////////////////
    @Test
    public void testDeleteRecipe() {
        recipeDatabase.insertRecipe(1,"Name", "Cuisine", "Category", "Instruction", 10,"12min","Ingredient");
        recipeDatabase.insertRecipe(2,"NameSecond", "Cuisine", "Category", "Instruction", 10,"12min","Ingredient");
        assertEquals(2, context.fetchCount(RECIPE));
        recipeDatabase.deleteRecipe(1);
        assertEquals(1, context.fetchCount(RECIPE));
        var recipeRecord = context.select().from(RECIPE).fetchOne();
        assertNotNull(recipeRecord);
        assertEquals("NameSecond", recipeRecord.getValue(RECIPE.NAME));
    }

    @Test
    public void testDeleteRecipeNonExistent() {
        assertThrows(IllegalArgumentException.class, () -> recipeDatabase.deleteRecipe(1));
    }

    ////////////////////////////////////////// -- Filter -- //////////////////////////////////////////
    @Test
    public void testFilterRecipes() {
        recipeDatabase.insertRecipe(1,"Name", "Cuisine", "Category", "Instruction", 10,"12min","Ingredient");
        recipeDatabase.insertRecipe(2,"NameSecond", "Cuisine", "Category", "Instruction", 10,"12min","Ingredient");
        recipeDatabase.insertRecipe(3,"NameThird", "Cuisine", "Category", "Instruction", 10,"12min","Ingredient");
        var result = recipeDatabase.filterRecipes(null,"NameThird",null,null,null,null,null,null);
        assertEquals(1, result.size());
        for (Record record : result) {
            assertEquals("NameThird", record.get(RECIPE.NAME));
        }
    }
}

