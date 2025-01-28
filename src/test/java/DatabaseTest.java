import com.example.generated.tables.records.RecipeRecord;
import org.h2.jdbcx.JdbcDataSource;
import org.jooq.DSLContext;
import org.jooq.Record;
import org.jooq.Result;
import org.jooq.impl.DSL;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.sql.SQLException;
import java.util.List;
import static com.example.generated.Tables.RECIPE;
import static org.junit.jupiter.api.Assertions.*;

public class DatabaseTest {

    @BeforeEach
    public void connectToTestDatabase(){
        JdbcDataSource h2DataSource = new JdbcDataSource();
        h2DataSource.setURL("jdbc:h2:mem:testdb;DB_CLOSE_DELAY=-1;DB_CLOSE_ON_EXIT=FALSE");

        DSLContext h2Context = DSL.using(h2DataSource, org.jooq.SQLDialect.H2);

        Database.setContext(h2Context);

        Database.createRecipeTable();
    }


    @Test
    public void testInsert() throws SQLException {
        Database.insertRecipe(1,"Name", "Cuisine", "Category", "Instruction", 10,"12min","Ingredient");
        Database.insertRecipe(2,"NameSecond", "Cuisine", "Category", "Instruction", 10,"12min","Ingredient");
        Record recordID1 = Database.getConnection().selectFrom(RECIPE).where(RECIPE.ID.equal(1)).fetchOne();
        Record recordID2 = Database.getConnection().selectFrom(RECIPE).where(RECIPE.ID.equal(2)).fetchOne();
        assertNotNull(recordID1);
        assertEquals(recordID1.getValue(RECIPE.CUISINE),"Cuisine");
        assertEquals(recordID1.getValue(RECIPE.NUTRITION), 10);
        assertNotNull(recordID2);
        assertEquals(recordID2.getValue(RECIPE.NAME),"NameSecond");
    }

    @Test
    public void testUpdateRecipe() throws SQLException{
        Database.insertRecipe(1,"Name", "Cuisine", "Category", "Instruction", 10,"12min","Ingredient");
        Database.updateRecipe(1,null,"NewCuisine", "Category", "Instruction" , 5, "12min", "Ingredient");
        Record record = Database.getConnection().selectFrom(RECIPE).where(RECIPE.ID.equal(1)).fetchOne();
        assertNotNull(record);
        assertEquals(record.getValue(RECIPE.CUISINE),"NewCuisine");
        assertEquals(record.getValue(RECIPE.NUTRITION),5);
        assertEquals(record.getValue(RECIPE.NAME),"Name");
    }

    @Test
    public void showTable() throws SQLException {
        Database.insertRecipe(1,"Name", "Cuisine", "Category", "Instruction", 10,"12min","Ingredient");
        List<RecipeRecord> records = Database.getConnection().selectFrom(RECIPE).fetch();
        assertNotNull(records);
        records.forEach(System.out::println);
    }

    @Test
    public void testDeleteAllRecipes() throws SQLException {
        Database.insertRecipe(1,"Name", "Cuisine", "Category", "Instruction", 10,"12min","Ingredient");
        Database.insertRecipe(2,"NameSecond", "Cuisine", "Category", "Instruction", 10,"12min","Ingredient");
        Database.deleteAllRecipes();

        int rowCount = Database.getConnection().fetchCount(RECIPE);
        assertEquals(0, rowCount);

    }

    @Test
    public void testDeleteRecipe() throws SQLException{
        Database.insertRecipe(1,"Name", "Cuisine", "Category", "Instruction", 10,"12min","Ingredient");
        Database.insertRecipe(2,"NameSecond", "Cuisine", "Category", "Instruction", 10,"12min","Ingredient");

        int rowCount = Database.getConnection().fetchCount(RECIPE);
        assertEquals(2,rowCount);

        Database.deleteRecipe(1);
        int rowCountAfterDelete = Database.getConnection().fetchCount(RECIPE);
        assertEquals(1,rowCountAfterDelete);
        var recipeRecord = Database.getConnection().select().from(RECIPE).fetchOne();

        assert recipeRecord != null;
        assertEquals(recipeRecord.getValue(RECIPE.NAME), "NameSecond");
    }

    @Test
    public void testFilterRecipes() throws SQLException {
        Database.insertRecipe(1,"Name", "Cuisine", "Category", "Instruction", 10,"12min","Ingredient");
        Database.insertRecipe(2,"NameSecond", "Cuisine", "Category", "Instruction", 10,"12min","Ingredient");
        Database.insertRecipe(3,"NameThird", "Cuisine", "Category", "Instruction", 10,"12min","Ingredient");

        var result = Database.filterRecipes("NameThird",null,null,null,0,null,null);

        assertEquals(1,result.size());

        for(Record record : result){
            assertEquals("NameThird",record.get(RECIPE.NAME));
        }
    }

    @AfterEach
    public void cleanDatabase() throws SQLException {
        DSLContext context = Database.getConnection();
        context.dropTableIfExists(RECIPE).execute();
    }

}
