import javax.xml.crypto.Data;
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
        new MenuController(new MenuView());
        //new MenuController(new MenuView());
        System.setProperty("org.jooq.no-logo", "true");
        System.setProperty("org.jooq.no-tips", "true");

        // Connection is the only JDBC resource that we need
        // PreparedStatement and ResultSet are handled by jOOQ, internally
        try {
            /*
            for(int i = 11; i <= 15; i++ ) {
                Database.insertRecipe(i, "pasta1", "Italian", "dessert", "Cook then eat", 100,
                        "15 Mins", "noodles");
            }
            */
            //Database.deleteAllRecipes();
            //Database.filterRecipes(null,null,null,null,0,"15 mins", null);
            Connection connection = DriverManager.getConnection(url);
            DSLContext create = DSL.using(connection, SQLDialect.SQLITE);
            DatabaseNew.setDslContext(create);
            //Result<org.jooq.Record> result = create.select().from(RECIPE).fetch();
            //System.out.println(result);
        }

        // For the sake of this tutorial, let's keep exception handling simple
        catch (Exception e) {
            e.printStackTrace();
        }
    }
}
