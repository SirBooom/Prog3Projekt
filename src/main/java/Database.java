import com.example.generated.tables.records.RecipeRecord;
import org.jooq.DSLContext;
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
    // creates a table called Recipe if Table does not exist
    public static void createRecipeTable(){
        try {
            getConnection()
                    .createTableIfNotExists("Recipe")
                    .column("ID" , SQLDataType.INTEGER.identity(true))
                    .column("name" , SQLDataType.VARCHAR(25))
                    .column("cuisine" , SQLDataType.CLOB)
                    .column("category" , SQLDataType.CLOB)
                    .column("instructions" , SQLDataType.CLOB)
                    .column("nutrition", SQLDataType.INTEGER)
                    .column("cookingTime", SQLDataType.CLOB)
                    .column("ingredient", SQLDataType.CLOB)
                    .constraint(DSL.primaryKey("ID"))
                    .execute();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // insert a new recipe
    public static boolean insertRecipe(int id, String name, String cuisine, String category,
            String instructions, int nutrition, String cookingTime, String ingredient) {
        try {
            getConnection()
                    .insertInto(RECIPE)
                    .columns(RECIPE.ID, RECIPE.NAME, RECIPE.CUISINE, RECIPE.CATEGORY,
                            RECIPE.INSTRUCTIONS, RECIPE.NUTRITION, RECIPE.COOKINGTIME,
                            RECIPE.INGREDIENT)
                    .values(id, name, cuisine, category, instructions, nutrition, cookingTime,
                            ingredient)
                    .execute();
            return true;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    //  delete a recipe by ID
    public static void deleteRecipe(int id) {
        try {
            getConnection()
                    .deleteFrom(RECIPE)
                    .where(RECIPE.ID.eq(id))
                    .execute();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static void updateRecipe(int id, String name, String cuisine, String category,
            String instructions, int nutrition, String cookingTime, String ingredient) {
        try {
            UpdateSetFirstStep<RecipeRecord> context = getConnection().update(RECIPE);

            if (name != null) {
                context.set(RECIPE.NAME, name).where(RECIPE.ID.eq(id))
                        .execute();
            }

            if (cuisine != null) {
                context.set(RECIPE.CUISINE, cuisine).where(RECIPE.ID.eq(id)).execute();
            }
            if (category != null) {
                context.set(RECIPE.CATEGORY, category).where(RECIPE.ID.eq(id)).execute();
            }
            if (instructions != null) {
                context.set(RECIPE.INSTRUCTIONS, instructions).where(RECIPE.ID.eq(id)).execute();
            }
            if (nutrition > 0) {
                context.set(RECIPE.NUTRITION, nutrition).where(RECIPE.ID.eq(id)).execute();
            }
            if (cookingTime != null) {
                context.set(RECIPE.COOKINGTIME, cookingTime).where(RECIPE.ID.eq(id)).execute();
            }
            if (ingredient != null) {
                context.set(RECIPE.INGREDIENT, ingredient)
                        .where(RECIPE.ID.eq(id)).execute();
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // delete all recipes
    public static void deleteAllRecipes() {
        try {
            int lastRecipeId = getConnection().select().from(RECIPE).orderBy(RECIPE.ID.desc())
                    .limit(1).fetchOne().get(RECIPE.ID);
            for (int recipeID = 1; recipeID <= lastRecipeId; recipeID++) {
                deleteRecipe(recipeID);
            }
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
    public static void filterByNameAlphabeticalOrder(){
        try {
            int lastRecipeId = getConnection().select().from(RECIPE).orderBy(RECIPE.ID.desc()).limit(1).fetchOne().get(RECIPE.ID);
            getConnection().select().from(RECIPE).orderBy(RECIPE.ID.desc()).limit(lastRecipeId).fetch();

        } catch (SQLException e) {
            e.printStackTrace();

        }
    }
}
*/
    /*

    public static String getAllRecipes() {
        StringBuilder result = new StringBuilder();
        try {
            Result<Record> records = getConnection()
                    .select()
                    .from(RECIPE)
                    .fetch();

            for (Record record : records) {
                Integer id = record.getValue(RECIPE.ID);
                String name = record.getValue(RECIPE.NAME);
                result.append("ID: ").append(id).append(", Name: ").append(name).append("\n");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return result.toString();
    }
*/
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

