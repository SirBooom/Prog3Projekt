package database;

import static com.example.generated.Tables.RECIPE;
import static com.example.generated.Tables.SHOP;
import org.jooq.Condition;
import org.jooq.DSLContext;
import org.jooq.Record;
import org.jooq.Result;
import org.jooq.impl.DSL;

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

    public Result<Record> buyItem(Integer id, Integer amount) {

        if (id == null || id <= 0) {
            throw new IllegalArgumentException("Invalid item ID.");
        }
        if (amount == null || amount <= 0) {
            throw new IllegalArgumentException("Invalid amount to buy.");
        }

        // check if the item exists and the requested amount is available
        Record item = context.select(SHOP.AMOUNT_AVAILABLE)
                .from(SHOP)
                .where(SHOP.ID.eq(id))
                .fetchOne();

        if (item == null) {
            throw new IllegalArgumentException("Item ID does not exist.");
        }

        int availableAmount = item.get(SHOP.AMOUNT_AVAILABLE);

        if (availableAmount < amount) {
            throw new IllegalArgumentException("Not enough items available.");
        }

        // update the shop

        context.update(SHOP)
                .set(SHOP.AMOUNT_AVAILABLE, availableAmount - amount)
                .where(SHOP.ID.eq(id))
                .execute();

        return showItems();
    }
}
