import Factory.ControllerFactory;
import Menu.MenuController;
import database.Database;
import database.RecipeDatabase;
import database.ShopDatabase;
import org.jooq.DSLContext;

public class Application {

    MenuController menuController;

    public void start() throws Exception {

        System.out.println("Initializing database connection...");

        ControllerFactory controllerFactory = ControllerFactory.getInstance();

        System.out.println("Database connection initialized successfully.");

        menuController = controllerFactory.getMenuController();

        menuController.show();

    }
}
