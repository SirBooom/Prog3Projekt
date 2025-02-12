import javax.swing.*;

public class MenuController {
    private final MenuView menuView;


    public MenuController(MenuView menuView){
        this.menuView = menuView;

        menuView.addButtonListener(menuView.getRecipeButton(), e-> openRecipeView());
        menuView.addButtonListener(menuView.getIngredientsButton(), e-> openShopView());
        menuView.addButtonListener(menuView.getBalanceButton(), e-> openBalanceView());

    }

    private void openRecipeView() {
        menuView.closeView();
        ControllerFactory.getInstance().getRecipeController().show();
    }

    private void openShopView() {
        menuView.closeView();
        //ControllerFactory.getInstance().getShopController().show();
        new ShopManager();
    }


    private void openBalanceView() {
        menuView.closeView();
        ControllerFactory.getInstance().getBalanceController().show();
    }


    public void show(){
        menuView.setVisible(true);
    }

}

