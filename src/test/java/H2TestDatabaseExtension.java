import database.Database;
import org.jooq.DSLContext;
import org.jooq.impl.DSL;
import org.junit.jupiter.api.extension.*;
import java.sql.Connection;
import java.sql.DriverManager;
import org.jooq.impl.SQLDataType;

import static com.example.generated.Tables.RECIPE;

public class H2TestDatabaseExtension implements BeforeEachCallback, AfterEachCallback {
    private static final String TEST_DB_URL = "jdbc:h2:mem:testdb;DB_CLOSE_DELAY=-1";
    private Connection connection;
    private DSLContext testContext;

    @Override
    public void beforeEach(ExtensionContext context) throws Exception {
        connection = DriverManager.getConnection(TEST_DB_URL);
        testContext = DSL.using(connection);
        createTestTable(testContext);
        Database.setDslContext(testContext);
    }

    @Override
    public void afterEach(ExtensionContext context) throws Exception {
        testContext.dropTableIfExists(RECIPE).execute();
        connection.close();
    }

    private void createTestTable(DSLContext context) {
        context.createTableIfNotExists("Recipe")
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
    }
}