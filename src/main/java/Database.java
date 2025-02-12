import com.example.generated.tables.records.RecipeRecord;
import javax.swing.table.DefaultTableModel;
import org.jooq.DSLContext;
import org.jooq.Record;
import org.jooq.Result;
import org.jooq.UpdateSetFirstStep;
import org.jooq.impl.DSL;
import org.jooq.impl.SQLDataType;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import static com.example.generated.Tables.RECIPE;

public class Database {

    private static DSLContext context;

    // JDBC URL for SQLite (replace with your database path)
    private static final String url = "jdbc:sqlite:rezeptverwaltungsdb.db";

    // Establish a connection and return DSLContext
    public static DSLContext getConnection() throws SQLException {
        if (context == null) {
            Connection connection = DriverManager.getConnection(url);
            context = DSL.using(connection);
        }
        return context;
    }


    // reutrns the current database of Recipes
    public static Result<Record> fetchAllRecipes() {
        try {
            return getConnection()
                    .select()
                    .from(RECIPE)
                    .fetch();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    // insert a new recipe
    public static Result<Record> insertRecipe(int id, String name, String cuisine, String category,
            String instructions, Integer nutrition, String cookingTime, String ingredient) {
        try {
            getConnection()
                    .insertInto(RECIPE)
                    .columns(RECIPE.ID, RECIPE.NAME, RECIPE.CUISINE, RECIPE.CATEGORY,
                            RECIPE.INSTRUCTIONS, RECIPE.NUTRITION, RECIPE.COOKINGTIME,
                            RECIPE.INGREDIENT)
                    .values(id, name, cuisine, category, instructions, nutrition, cookingTime,
                            ingredient)
                    .execute();
            return fetchAllRecipes();
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
    }

    //  delete a recipe by ID
    public static Result<Record> deleteRecipe(int id) {
        try {
            getConnection()
                    .deleteFrom(RECIPE)
                    .where(RECIPE.ID.eq(id))
                    .execute();
            return fetchAllRecipes();
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
    }

    // update values of a recipe
    public static Result<Record> updateRecipe(Integer id, String name, String cuisine,
            String category,
            String instructions, Integer nutrition, String cookingTime, String ingredient) {

        boolean updated = false;
        try {
            UpdateSetFirstStep<RecipeRecord> context = getConnection().update(RECIPE);
            if (!(getConnection().fetchExists(RECIPE, RECIPE.ID.eq(id)))) {
                throw new SQLException();
            }
            ;
            if (name != null) {
                context.set(RECIPE.NAME, name).where(RECIPE.ID.eq(id))
                        .execute();
                updated = true;
            }

            if (cuisine != null) {
                context.set(RECIPE.CUISINE, cuisine).where(RECIPE.ID.eq(id)).execute();
                updated = true;
            }
            if (category != null) {
                context.set(RECIPE.CATEGORY, category).where(RECIPE.ID.eq(id)).execute();
                updated = true;
            }
            if (instructions != null) {
                context.set(RECIPE.INSTRUCTIONS, instructions).where(RECIPE.ID.eq(id)).execute();
                updated = true;
            }
            if (nutrition > 0) {
                context.set(RECIPE.NUTRITION, nutrition).where(RECIPE.ID.eq(id)).execute();
                updated = true;
            }
            if (cookingTime != null) {
                context.set(RECIPE.COOKINGTIME, cookingTime).where(RECIPE.ID.eq(id)).execute();
                updated = true;
            }
            if (ingredient != null) {
                context.set(RECIPE.INGREDIENT, ingredient)
                        .where(RECIPE.ID.eq(id)).execute();
                updated = true;
            }
            return fetchAllRecipes();
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
    }

    // delete all recipes
    public static Result<Record> deleteAllRecipes() {
        try {
            int lastRecipeId = getConnection().select().from(RECIPE).orderBy(RECIPE.ID.desc())
                    .limit(1).fetchOne().get(RECIPE.ID);
            for (int recipeID = 1; recipeID <= lastRecipeId; recipeID++) {
                deleteRecipe(recipeID);
            }
            return fetchAllRecipes();
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
    }

    // filter recipes by at least 1 attribute (delete this method)
    public static Result<Record> filterRecipes(Integer id, String name, String cuisine,
            String category,
            String instructions, Integer nutrition, String cookingTime, String ingredient) {
        try {
            return getConnection()
                    .select()
                    .from(RECIPE)
                    .where((id == null || id <= 0) ? DSL.trueCondition() : RECIPE.ID.eq(id))
                    .and((name == null || name.isEmpty() ? DSL.trueCondition()
                            : RECIPE.NAME.eq(name)))
                    .and((cuisine == null || cuisine.isEmpty() ? DSL.trueCondition()
                            : RECIPE.CUISINE.eq(cuisine)))
                    .and((category == null || category.isEmpty() ? DSL.trueCondition()
                            : RECIPE.CATEGORY.eq(category)))
                    .and((instructions == null || instructions.isEmpty() ? DSL.trueCondition()
                            : RECIPE.INSTRUCTIONS.eq(instructions)))
                    .and(((nutrition == null || nutrition <= 0) ? DSL.trueCondition() : RECIPE.NUTRITION.eq(nutrition)))
                    .and((cookingTime == null || cookingTime.isEmpty() ? DSL.trueCondition()
                            : RECIPE.COOKINGTIME.eq(cookingTime)))
                    .and((ingredient == null || ingredient.isEmpty() ? DSL.trueCondition()
                            : RECIPE.INGREDIENT.eq(ingredient)))
                    .fetch();
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
        //System.out.println(context.select().from(RECIPE).fetch());
    }



    //TESTING PURPOSES//
    // creates a table called Recipe if Table does not exist
    public static void createRecipeTable() {
        try {
            getConnection()
                    .createTableIfNotExists("Recipe")
                    .column("ID", SQLDataType.INTEGER.identity(true))
                    .column("name", SQLDataType.VARCHAR(25))
                    .column("cuisine", SQLDataType.CLOB)
                    .column("category", SQLDataType.CLOB)
                    .column("instructions", SQLDataType.CLOB)
                    .column("nutrition", SQLDataType.INTEGER)
                    .column("cookingTime", SQLDataType.CLOB)
                    .column("ingredient", SQLDataType.CLOB)
                    .constraint(DSL.primaryKey("ID"))
                    .execute();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // Possibility, to change the DSLContext for testing with a h2 memory database
    public static void setContext(DSLContext newContext) {
        context = newContext;
    }
}
    /*
    public static void closeConnection() {
        try {
            if (context != null) {
                context.configuration().connectionProvider().acquire().close();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    */

