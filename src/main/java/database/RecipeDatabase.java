package database;

import static com.example.generated.Tables.RECIPE;
import com.example.generated.tables.records.RecipeRecord;
import java.sql.SQLException;
import java.util.Optional;
import org.jooq.Condition;
import org.jooq.DSLContext;
import org.jooq.Record;
import org.jooq.Result;
import org.jooq.UpdateSetMoreStep;
import org.jooq.impl.DSL;

public class RecipeDatabase {
    private final DSLContext context;

    public RecipeDatabase(DSLContext context) {
        if (context == null) {
            throw new IllegalArgumentException("DSLContext cannot be null");
        }
        this.context = context;
    }

    public Result<Record> showRecipes() {
        return context.select().from(RECIPE).fetch();
    }

    public Result<Record> insertRecipe(int id, String name, String cuisine, String category,
                                                 String instructions, Integer nutrition, String cookingTime, String ingredient) {
        if (id < 1 || name == null) {
            throw new IllegalArgumentException("ID and name cannot be invalid or null");
        }

        context.insertInto(RECIPE)
                .columns(RECIPE.ID, RECIPE.NAME, RECIPE.CUISINE, RECIPE.CATEGORY,
                        RECIPE.INSTRUCTIONS, RECIPE.NUTRITION, RECIPE.COOKINGTIME,
                        RECIPE.INGREDIENT)
                .values(id, name, cuisine, category, instructions, nutrition, cookingTime, ingredient)
                .execute();
        return showRecipes();
    }

    public Result<Record> deleteRecipe(int id) {
        if (!context.fetchExists(RECIPE, RECIPE.ID.eq(id))) {
            throw new IllegalArgumentException("Recipe with ID " + id + " does not exist.");
        }
        context.deleteFrom(RECIPE).where(RECIPE.ID.eq(id)).execute();
        return showRecipes();
    }

    public Result<Record> updateRecipe(Integer id, String name, String cuisine,
                                                 String category, String instructions, Integer nutrition,
                                                 String cookingTime, String ingredient) throws SQLException {

        if (!context.fetchExists(RECIPE, RECIPE.ID.eq(id))) {
            throw new SQLException("Recipe with ID " + id + " is invalid.");
        }

        UpdateSetMoreStep<RecipeRecord> updateQuery = context.update(RECIPE).set(RECIPE.ID, id);

        Optional.ofNullable(name).ifPresent(value -> updateQuery.set(RECIPE.NAME, value));
        Optional.ofNullable(cuisine).ifPresent(value -> updateQuery.set(RECIPE.CUISINE, value));
        Optional.ofNullable(category).ifPresent(value -> updateQuery.set(RECIPE.CATEGORY, value));
        Optional.ofNullable(instructions).ifPresent(value -> updateQuery.set(RECIPE.INSTRUCTIONS, value));
        Optional.ofNullable(nutrition).filter(n -> n > 0).ifPresent(value -> updateQuery.set(RECIPE.NUTRITION, value));
        Optional.ofNullable(cookingTime).ifPresent(value -> updateQuery.set(RECIPE.COOKINGTIME, value));
        Optional.ofNullable(ingredient).ifPresent(value -> updateQuery.set(RECIPE.INGREDIENT, value));

        updateQuery.where(RECIPE.ID.eq(id)).execute();
        return showRecipes();
    }

    public Result<Record> deleteAllRecipes() {
        context.deleteFrom(RECIPE).execute();
        return showRecipes();
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