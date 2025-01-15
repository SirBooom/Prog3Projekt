import org.jooq.*;
import org.jooq.Record;
import org.jooq.impl.DSL;

import java.math.BigInteger;
import java.sql.*;
import java.util.Optional;

import static org.jooq.impl.DSL.table;
import static org.jooq.impl.SQLDataType.BIGINT;
import static org.jooq.impl.SQLDataType.VARCHAR;
import static com.example.generated.Tables.*;

public class JooqTestDrive {
    public static void main(String[] args) {
        String jdbcUrl = "jdbc:sqlite:rezeptverwaltungsdb.db";
        try {
            Connection connection  = DriverManager.getConnection(jdbcUrl);
            DSLContext context = DSL.using(connection);
            Result<Record> result = context.select().from(EXAMPLE).fetch();
            context.insertInto((RECIPE)).values(2,"rezept2").execute();

            context.createTableIfNotExists(EXAMPLE).
                    column("id", BIGINT.identity(true)).
                    column("Name", VARCHAR(25).nullable(false))
                    .constraints(DSL.constraint().primaryKey("id"))
                    .execute();
            var selected = context.select(EXAMPLE.ID, EXAMPLE.NAME)
                    .from(EXAMPLE)
                    .where(EXAMPLE.ID.eq(11))
                    .fetchOne();

            //context.insertInto(EXAMPLE)
            //        .values(11,"test").execute();

            System.out.println(selected);


            System.out.println(result);

        } catch (SQLException e) {
            System.out.println("Error: Connecting to Database");
            e.printStackTrace();
        }
    }
}
