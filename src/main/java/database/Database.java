package database;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import org.jooq.DSLContext;
import org.jooq.impl.DSL;

public class Database {
    private static DSLContext dslContext;
    private static final String URL = "jdbc:sqlite:rezeptverwaltungsdb.db";

    private Database() { throw new UnsupportedOperationException("Utility class");
    }

    public static DSLContext getDslContext() throws SQLException {
        if (dslContext == null) {
            Connection connection = DriverManager.getConnection(URL);
            dslContext = DSL.using(connection);
        }
        return dslContext;
    }

    public static void setDslContext(DSLContext context) {
        dslContext = context;
    }
}
