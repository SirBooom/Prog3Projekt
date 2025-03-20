package Shop;

import database.Database;
import database.ShopDatabase;
import java.util.Map;
import javax.swing.table.DefaultTableModel;
import org.jooq.Record;
import org.jooq.Result;

public class ShopModel {
    private ShopDatabase shopDatabase;
    private DefaultTableModel tableModel;

    public ShopModel() {
        try {
            shopDatabase = new ShopDatabase(Database.getDslContext());
        } catch (Exception e) {
            e.printStackTrace();
        }
        this.tableModel = null;
    }

    public Result<Record> reloadItems() {
        tableModel.setRowCount(0);
        try {
            return shopDatabase.showItems();
        } catch (Exception ex) {
            ex.printStackTrace();
            return null;
        }
    }

    public Result<Record> buyItem(Map<String, String> data) {
        try {
            return shopDatabase.buyItem(
                    parseIntegerOrNull(data.get("Item ID")),
                    parseIntegerOrNull(data.get("Desired Quantity"))
                    );
        } catch (Exception ex) {
            ex.printStackTrace();
            return null;
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
