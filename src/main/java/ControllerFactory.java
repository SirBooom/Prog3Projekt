public class ControllerFactory {
    private static final ControllerFactory instance = new ControllerFactory();

    private MenuController menuController;
    private RecipeController recipeController;
    private ShopManager shopManager;
    private BalanceController balanceController;

    private ControllerFactory() {}

    public static ControllerFactory getInstance() {
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