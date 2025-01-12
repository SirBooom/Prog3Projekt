import org.jooq.DSLContext;
import org.jooq.Result;
import org.jooq.Table;
import org.jooq.impl.DSL;
import java.sql.*;
import org.jooq.Record;

import static org.jooq.impl.DSL.table;

public class JooqTestDrive {
    public static void main(String[] args) {
        String jdbcUrl = "jdbc:sqlite:rezeptverwaltungsdb.db";
        try {
            Connection connection  = DriverManager.getConnection(jdbcUrl);
            DSLContext context = DSL.using(connection);
            Table<?> recipe = table("recipe");
            Result<Record> result = context.select().from(recipe).fetch();

            for (Record record : result) {
                System.out.println("Record: " + record);

            }
        } catch (SQLException e) {
            System.out.println("Error: Connecting to Database");
            e.printStackTrace();
        }
    }
}
