import com.example.generated.tables.Recipe;

import javax.swing.*;

public class MenuController {
    private MenuView menuView;
    public MenuController(MenuView menuView){
        this.menuView = menuView;

        menuView.addButtonListener(menuView.getRecipeButton(), e->openView(new RecipeView()));
        menuView.addButtonListener(menuView.getIngredientsButton(), e->openView(new IngredientManager()));
        menuView.addButtonListener(menuView.getBalanceButton(), e->openBalanceView());

    }

    private void openRecipeView(){
        /* todo */
    }

    private void openIngredientsView(){
        /* todo */
    }

    private void openBalanceView(){
        menuView.dispose();
        new BalanceController(new BalanceView(), new BalanceModel());
    }


    private void openView(JFrame frame){
        menuView.dispose();
    }
}
