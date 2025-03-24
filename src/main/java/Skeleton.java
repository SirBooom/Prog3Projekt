import org.jooq.DSLContext;
import org.jooq.SQLDialect;
import org.jooq.impl.DSL;


import java.sql.Connection;
import java.sql.DriverManager;

import static org.jooq.impl.DSL.table;

public class Skeleton {
    public static void main(String[] args) {
        try {
            Application app = new Application();
            app.start();
        } catch (Exception e) {
            System.err.println("An error occurred while starting the app: " + e.getMessage());
            e.printStackTrace();
            System.exit(1);
        }

    }
}
