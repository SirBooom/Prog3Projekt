import org.jooq.DSLContext;
import org.jooq.SQLDialect;
import org.jooq.impl.DSL;


import java.sql.Connection;
import java.sql.DriverManager;

import static org.jooq.impl.DSL.table;

public class Skeleton {
    public static void main(String[] args) {
        String userName = "root";
        String password = "";
        String url = "jdbc:SQLite:rezeptverwaltungsdb.db";

        try {
            Application app = new Application();
            app.start();
        } catch (Exception e) {
            System.err.println("An error occurred while starting the app: " + e.getMessage());
            e.printStackTrace();
            System.exit(1);
        }

        System.setProperty("org.jooq.no-logo", "true");
        System.setProperty("org.jooq.no-tips", "true");

        // Connection is the only JDBC resource that we need
        // PreparedStatement and ResultSet are handled by jOOQ, internally
        try {
            Connection connection = DriverManager.getConnection(url);
            DSLContext create = DSL.using(connection, SQLDialect.SQLITE);
            //DatabaseNew.setDslContext(create);
            //Result<org.jooq.Record> result = create.select().from(RECIPE).fetch();
            //System.out.println(result);
        }

        // For the sake of this tutorial, let's keep exception handling simple
        catch (Exception e) {
            e.printStackTrace();
        }
    }
}
