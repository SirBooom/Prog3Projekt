import org.jooq.*;
import org.jooq.Record;
import org.jooq.impl.DSL;

import java.math.BigInteger;
import java.sql.*;
import java.util.Optional;

import static org.jooq.impl.DSL.table;
import static org.jooq.impl.SQLDataType.BIGINT;
import static org.jooq.impl.SQLDataType.VARCHAR;

public class JooqTestDrive {
    public static void main(String[] args) {
        String jdbcUrl = "jdbc:sqlite:rezeptverwaltungsdb.db";
        try {
            Connection connection  = DriverManager.getConnection(jdbcUrl);
            DSLContext context = DSL.using(connection);
            Result<Record> result = context.select().from().fetch();
            //context.insertInto(table("recipe")).values(2,"rezept2").execute();

            context.createTableIfNotExists("example").
                    column("id", BIGINT.identity(true)).
                    column("Name", VARCHAR(25).nullable(false))
                    .constraints(DSL.constraint().primaryKey("id"))
                    .execute();

            context.insertInto(table("example")).
                    values(11,"test").execute();
            for (Record record : result) {
                System.out.println("Record: " + record);

            }
        } catch (SQLException e) {
            System.out.println("Error: Connecting to Database");
            e.printStackTrace();
        }
    }
}
