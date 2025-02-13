import com.example.generated.tables.records.RecipeRecord;
import org.h2.jdbcx.JdbcDataSource;
import org.jooq.DSLContext;
import org.jooq.Record;
import org.jooq.Result;
import org.jooq.impl.DSL;
import org.junit.jupiter.api.*;
import org.junit.jupiter.api.extension.ExtendWith;
import java.sql.SQLException;
import java.util.List;
import static com.example.generated.Tables.RECIPE;
import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(H2TestDatabaseExtension.class)
public class DatabaseTestNew {
    private RecipeRepository recipeRepository;
    private DSLContext context;

    @BeforeEach
    public void setup() throws SQLException {
        context = DatabaseNew.getDslContext();
        recipeRepository = new RecipeRepository(context);
    }

    @Test
    public void testInsert() {
        recipeRepository.insertRecipe(1,"Name", "Cuisine", "Category", "Instruction", 10,"12min","Ingredient");
        recipeRepository.insertRecipe(2,"NameSecond", "Cuisine", "Category", "Instruction", 10,"12min","Ingredient");
        Record recordID1 = context.selectFrom(RECIPE).where(RECIPE.ID.equal(1)).fetchOne();
        Record recordID2 = context.selectFrom(RECIPE).where(RECIPE.ID.equal(2)).fetchOne();
        assertNotNull(recordID1);
        assertEquals("Cuisine", recordID1.getValue(RECIPE.CUISINE));
        assertEquals(10, recordID1.getValue(RECIPE.NUTRITION));
        assertNotNull(recordID2);
        assertEquals("NameSecond", recordID2.getValue(RECIPE.NAME));
    }

    @Test
    public void testUpdateRecipe() throws SQLException {
        recipeRepository.insertRecipe(1,"Name", "Cuisine", "Category", "Instruction", 10,"12min","Ingredient");
        recipeRepository.updateRecipe(1,null,"NewCuisine", "Category", "Instruction" , 5, "12min", "Ingredient");
        Record record = context.selectFrom(RECIPE).where(RECIPE.ID.equal(1)).fetchOne();
        assertNotNull(record);
        assertEquals("NewCuisine", record.getValue(RECIPE.CUISINE));
        assertEquals(5, record.getValue(RECIPE.NUTRITION));
        assertEquals("Name", record.getValue(RECIPE.NAME));
    }

    @Test
    public void showTable() {
        recipeRepository.insertRecipe(1,"Name", "Cuisine", "Category", "Instruction", 10,"12min","Ingredient");
        List<RecipeRecord> records = context.selectFrom(RECIPE).fetch();
        assertNotNull(records);
        records.forEach(System.out::println);
    }

    @Test
    public void testDeleteAllRecipes() {
        recipeRepository.insertRecipe(1,"Name", "Cuisine", "Category", "Instruction", 10,"12min","Ingredient");
        recipeRepository.insertRecipe(2,"NameSecond", "Cuisine", "Category", "Instruction", 10,"12min","Ingredient");
        recipeRepository.deleteAllRecipes();
        int rowCount = context.fetchCount(RECIPE);
        assertEquals(0, rowCount);
    }

    @Test
    public void testDeleteRecipe() {
        recipeRepository.insertRecipe(1,"Name", "Cuisine", "Category", "Instruction", 10,"12min","Ingredient");
        recipeRepository.insertRecipe(2,"NameSecond", "Cuisine", "Category", "Instruction", 10,"12min","Ingredient");
        assertEquals(2, context.fetchCount(RECIPE));
        recipeRepository.deleteRecipe(1);
        assertEquals(1, context.fetchCount(RECIPE));
        var recipeRecord = context.select().from(RECIPE).fetchOne();
        assertNotNull(recipeRecord);
        assertEquals("NameSecond", recipeRecord.getValue(RECIPE.NAME));
    }

    @Test
    public void testFilterRecipes() {
        recipeRepository.insertRecipe(1,"Name", "Cuisine", "Category", "Instruction", 10,"12min","Ingredient");
        recipeRepository.insertRecipe(2,"NameSecond", "Cuisine", "Category", "Instruction", 10,"12min","Ingredient");
        recipeRepository.insertRecipe(3,"NameThird", "Cuisine", "Category", "Instruction", 10,"12min","Ingredient");
        var result = recipeRepository.filterRecipes(null,"NameThird",null,null,null,null,null,null);
        assertEquals(1, result.size());
        for (Record record : result) {
            assertEquals("NameThird", record.get(RECIPE.NAME));
        }
    }
}

