package Menu;

import Factory.ControllerFactory;

import java.sql.SQLException;

public record MenuController(MenuView menuView) {

    public MenuController(MenuView menuView) {
        this.menuView = menuView;

        menuView.addButtonListener(menuView.getRecipeButton(), e -> handleRecipeButton());
        menuView.addButtonListener(menuView.getIngredientsButton(), e -> handleIngredientsButton());
        menuView.addButtonListener(menuView.getBalanceButton(), e -> handleBalanceButton());
    }


    private void handleRecipeButton() {
        try {
            openRecipeView();
        } catch (SQLException ex) {
            handleError("Failed to open Recipe View", ex);
        }
    }

    private void handleIngredientsButton() {
        try {
            openShopView();
        } catch (SQLException ex) {
            handleError("Failed to open Ingredients View", ex);
        }
    }

    private void handleBalanceButton() {
        try {
            openBalanceView();
        } catch (SQLException ex) {
            handleError("Failed to open Balance View", ex);
        }
    }

    private void handleError(String message, Exception ex) {
        menuView.showError(message);
        //ex.printStackTrace();
    }

    private void openRecipeView() throws SQLException {
        menuView.setVisible(false);
        ControllerFactory.getInstance().getRecipeController().show();
    }

    private void openShopView() throws SQLException {
        menuView.setVisible(false);
        ControllerFactory.getInstance().getShopController().show();
    }

    private void openBalanceView() throws SQLException {
        menuView.setVisible(false);
        ControllerFactory.getInstance().getBalanceController().show();
    }

    // show menu view
    public void show() {
        menuView.setVisible(true);
    }

    public void closeView() {
        menuView.setVisible(false);
    }

}