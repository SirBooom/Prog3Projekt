package database;

import static com.example.generated.Tables.SHOP;
import org.jooq.DSLContext;
import org.jooq.Record;
import org.jooq.Result;

public class ShopDatabase {
    private final DSLContext context;

    public ShopDatabase(DSLContext context) {
        if (context == null) {
            throw new IllegalArgumentException("DSLContext cannot be null");
        }
        this.context = context;
    }

    public Result<Record> showItems() {
        return context.select().from(SHOP).fetch();
    }
}
