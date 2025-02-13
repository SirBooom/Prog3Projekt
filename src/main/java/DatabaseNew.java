import com.example.generated.tables.records.RecipeRecord;
import org.jooq.*;
import org.jooq.Record;
import org.jooq.impl.DSL;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import static com.example.generated.Tables.RECIPE;

public class DatabaseNew {
    private static DSLContext dslContext;
    private static final String URL = "jdbc:sqlite:rezeptverwaltungsdb.db";

    private DatabaseNew() {
        throw new UnsupportedOperationException("Utility class");
    }

    public static DSLContext getDslContext() throws SQLException {
        if (dslContext == null) {
            Connection connection = DriverManager.getConnection(URL);
            dslContext = DSL.using(connection);
        }
        return dslContext;
    }

    public static void setDslContext(DSLContext context) {
        dslContext = context;
    }
}

class RecipeRepository {
    private final DSLContext context;

    public RecipeRepository(DSLContext context) {
        this.context = context;
    }

    public Result<Record> fetchAllRecipes() {
        return context.select().from(RECIPE).fetch();
    }

    public Result<Record> insertRecipe(int id, String name, String cuisine, String category,
                                                 String instructions, Integer nutrition, String cookingTime, String ingredient) {
        context.insertInto(RECIPE)
                .columns(RECIPE.ID, RECIPE.NAME, RECIPE.CUISINE, RECIPE.CATEGORY,
                        RECIPE.INSTRUCTIONS, RECIPE.NUTRITION, RECIPE.COOKINGTIME,
                        RECIPE.INGREDIENT)
                .values(id, name, cuisine, category, instructions, nutrition, cookingTime, ingredient)
                .execute();
        return fetchAllRecipes();
    }

    public Result<Record> deleteRecipe(int id) {
        context.deleteFrom(RECIPE).where(RECIPE.ID.eq(id)).execute();
        return fetchAllRecipes();
    }

    public Result<Record> updateRecipe(Integer id, String name, String cuisine,
                                                 String category, String instructions, Integer nutrition,
                                                 String cookingTime, String ingredient) throws SQLException {
        if (!context.fetchExists(RECIPE, RECIPE.ID.eq(id))) {
            throw new SQLException("Rezept mit der ID " + id + " existiert nicht.");
        }

        UpdateSetMoreStep<RecipeRecord> updateQuery = context.update(RECIPE).set(RECIPE.ID, id);

        if (name != null) updateQuery.set(RECIPE.NAME, name);
        if (cuisine != null) updateQuery.set(RECIPE.CUISINE, cuisine);
        if (category != null) updateQuery.set(RECIPE.CATEGORY, category);
        if (instructions != null) updateQuery.set(RECIPE.INSTRUCTIONS, instructions);
        if (nutrition != null && nutrition > 0) updateQuery.set(RECIPE.NUTRITION, nutrition);
        if (cookingTime != null) updateQuery.set(RECIPE.COOKINGTIME, cookingTime);
        if (ingredient != null) updateQuery.set(RECIPE.INGREDIENT, ingredient);

        updateQuery.where(RECIPE.ID.eq(id)).execute();
        return fetchAllRecipes();
    }

    public Result<Record> deleteAllRecipes() {
        context.deleteFrom(RECIPE).execute();
        return fetchAllRecipes();
    }

    public Result<Record> filterRecipes(Integer id, String name, String cuisine, String category,
                                        String instructions, Integer nutrition, String cookingTime, String ingredient) {
        Condition condition = DSL.trueCondition();
        if (id != null && id > 0) condition = condition.and(RECIPE.ID.eq(id));
        if (name != null && !name.isEmpty()) condition = condition.and(RECIPE.NAME.eq(name));
        if (cuisine != null && !cuisine.isEmpty()) condition = condition.and(RECIPE.CUISINE.eq(cuisine));
        if (category != null && !category.isEmpty()) condition = condition.and(RECIPE.CATEGORY.eq(category));
        if (instructions != null && !instructions.isEmpty()) condition = condition.and(RECIPE.INSTRUCTIONS.eq(instructions));
        if (nutrition != null && nutrition > 0) condition = condition.and(RECIPE.NUTRITION.eq(nutrition));
        if (cookingTime != null && !cookingTime.isEmpty()) condition = condition.and(RECIPE.COOKINGTIME.eq(cookingTime));
        if (ingredient != null && !ingredient.isEmpty()) condition = condition.and(RECIPE.INGREDIENT.eq(ingredient));

        return context.select().from(RECIPE).where(condition).fetch();
    }
}