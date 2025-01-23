import org.jooq.DSLContext;
import org.jooq.Result;
import org.jooq.SQLDialect;
import org.jooq.impl.DSL;


import java.sql.Connection;
import java.sql.DriverManager;
import static com.example.generated.Tables.*;

import static org.jooq.impl.DSL.table;

public class Skeleton {
    public static void main(String[] args) {
        String userName = "root";
        String password = "";
        String url = "jdbc:SQLite:rezeptverwaltungsdb.db";

        System.setProperty("org.jooq.no-logo", "true");
        System.setProperty("org.jooq.no-tips", "true");

        // Connection is the only JDBC resource that we need
        // PreparedStatement and ResultSet are handled by jOOQ, internally
        try {
            /*
            for(int i = 1; i <= 10; i++ ) {
                Database.insertRecipe(i, "pasta"+ i, "Italian", "dessert", "Cook then eat", 100,
                        "15 Mins", "noodles");
            }
            */
            //Database.deleteAllRecipes();

            Connection connection = DriverManager.getConnection(url);
            DSLContext create = DSL.using(connection, SQLDialect.SQLITE);
            Result<org.jooq.Record> result = create.select().from(RECIPE).fetch();
            System.out.println(result);
        }

        // For the sake of this tutorial, let's keep exception handling simple
        catch (Exception e) {
            e.printStackTrace();
        }
    }
}
