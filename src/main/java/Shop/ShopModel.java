package Shop;

import database.Database;
import database.ShopDatabase;
import java.util.Map;
import javax.swing.table.DefaultTableModel;

import org.jooq.DSLContext;
import org.jooq.Record;
import org.jooq.Result;

public class ShopModel {
    private ShopDatabase shopDatabase;
    private DefaultTableModel tableModel;

    public ShopModel(DSLContext context) {
        try {
            shopDatabase = new ShopDatabase(context);
        } catch (Exception e) {
            throw new RuntimeException("Error initializing ShopModel: " + e.getMessage(), e);
        }
        this.tableModel = null;
    }

    public Result<Record> reloadItems() {
        tableModel.setRowCount(0);
        try {
            return shopDatabase.showItems();
        } catch (Exception ex) {
            throw new RuntimeException("Error reloading shop items: " + ex.getMessage(), ex);
        }
    }

    public Result<Record> buyItem(Map<String, String> data) {
        try {
            return shopDatabase.buyItem(
                    parseIntegerOrNull(data.get("Item ID")),
                    parseIntegerOrNull(data.get("Desired Quantity"))
            );
        } catch (Exception ex) {
            throw new RuntimeException("Error buying item: " + ex.getMessage(), ex);
        }
    }

    private Integer parseIntegerOrNull(String value) {
        try {
            return value == null || value.trim().isEmpty() ? null : Integer.parseInt(value.trim());
        } catch (NumberFormatException ex) {
            return null;
        }
    }

    public void setTableModel(DefaultTableModel tableModel) {
        this.tableModel = tableModel;
    }

}