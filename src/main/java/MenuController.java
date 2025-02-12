import javax.swing.*;

public class MenuController {
    private final MenuView menuView;

    public MenuController(MenuView menuView){
        this.menuView = menuView;

        menuView.addButtonListener(menuView.getRecipeButton(), e-> openRecipeView());
        menuView.addButtonListener(menuView.getIngredientsButton(), e-> openShopView());
        menuView.addButtonListener(menuView.getBalanceButton(), e-> openBalanceView());

    }

    private void openRecipeView(){
        menuView.dispose();
        new RecipeController();
    }

    private void openShopView(){
        menuView.dispose();
        new ShopManager();
        /* todo */
    }

    private void openBalanceView(){
        menuView.dispose();
        new BalanceController(new BalanceView(), new BalanceModel());
    }
}

