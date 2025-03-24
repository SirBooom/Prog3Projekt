import Shop.ShopModel;
import org.jooq.DSLContext;
import org.jooq.Record;
import org.jooq.Result;
import org.jooq.SQLDialect;
import org.jooq.impl.DSL;
import org.junit.jupiter.api.*;

import javax.swing.table.DefaultTableModel;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;

import static com.example.generated.Tables.SHOP;
import static org.junit.jupiter.api.Assertions.*;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class ShopModelTest {

    private DSLContext context;
    private ShopModel shopModel;

    @BeforeAll
    void setUpAll() throws SQLException {
        context = createDSLContext();
        context.createTable(SHOP)
                .columns(SHOP.ID, SHOP.NAME, SHOP.AMOUNT_AVAILABLE, SHOP.PRICE, SHOP.NUTRITION)
                .execute();

        context.insertInto(SHOP, SHOP.ID, SHOP.NAME, SHOP.AMOUNT_AVAILABLE, SHOP.PRICE, SHOP.NUTRITION)
                .values(1, "Apple", 10, 1.50f, 300)
                .execute();

        shopModel = new ShopModel(context);
        DefaultTableModel tableModel = new DefaultTableModel();
        shopModel.setTableModel(tableModel);
    }

    @Test
    void testReloadItems() {
        Result<Record> result = shopModel.reloadItems();
        assertNotNull(result, "Result should not be null");
        assertFalse(result.isEmpty(), "Result should not be empty");
    }

    @Test
    void testBuyItemSuccessful() throws NullPointerException{
        Map<String, String> data = new HashMap<>();
        data.put("Item ID", "1");
        data.put("Desired Quantity", "2");

        Result<Record> result = shopModel.buyItem(data);
        assertNotNull(result, "Result should not be null");

        Record remainingAmountAsRecord = context.select(SHOP.AMOUNT_AVAILABLE)
                .from(SHOP)
                .where(SHOP.ID.eq(1))
                .fetchOne();
        assertNotNull(remainingAmountAsRecord);
        int remainingAmount = remainingAmountAsRecord.into(int.class);
        assertEquals(8, remainingAmount, "Remaining amount should be 8 after buying 2 items");
    }

    @Test
    void testBuyItemFailsForNonExistentItem() {
        Map<String, String> data = new HashMap<>();
        data.put("Item ID", "99");
        data.put("Desired Quantity", "2");

        assertThrows(RuntimeException.class, ()->shopModel.buyItem(data));
    }

    private DSLContext createDSLContext() throws SQLException {
        Connection connection = DriverManager.getConnection("jdbc:h2:mem:testdb");
        return DSL.using(connection, SQLDialect.H2);
    }

    @AfterAll
    void tearDownAll(){
        context.dropTableIfExists(SHOP).execute();
    }
}
