package Factory;

import Balance.BalanceController;
import Balance.BalanceModel;
import Balance.BalanceView;
import Menu.MenuController;
import Menu.MenuView;
import Recipe.RecipeController;
import Recipe.RecipeModel;
import Recipe.RecipeView;
import Shop.ShopManager;
import database.Database;
import java.sql.SQLException;
import org.jooq.DSLContext;

public class ControllerFactory {


    private static  ControllerFactory instance;
    //private final database.RecipeDatabase recipeDatabase;


    private MenuController menuController;
    private RecipeController recipeController;
    private ShopManager shopManager;
    private BalanceController balanceController;

    private ControllerFactory() throws SQLException {
        DSLContext dbContext = Database.getDslContext();
        //this.recipeDatabase = new database.RecipeDatabase(dbContext);
    }

    public static ControllerFactory getInstance() throws SQLException {
            if (instance == null) {
                instance = new ControllerFactory();
            }
            return instance;
    }

    public MenuController getMenuController() {
        if (menuController == null) {
            menuController = new MenuController(new MenuView());
        }
        return menuController;
    }

    public RecipeController getRecipeController() {
        if (recipeController == null) {
            recipeController = new RecipeController(new RecipeView(), new RecipeModel());
        }
        return recipeController;
    }

    public ShopManager getShopController() {
        if (shopManager == null) {
            shopManager = new ShopManager();
        }
        return shopManager;
    }

    public BalanceController getBalanceController() {
        if (balanceController == null) {
            balanceController = new BalanceController(new BalanceView(), new BalanceModel());
        }
        return balanceController;
    }
}