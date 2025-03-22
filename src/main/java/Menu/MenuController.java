package Menu;

import Factory.ControllerFactory;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.sql.SQLException;
import java.util.Map;

public record MenuController(MenuView menuView) {

    public MenuController(MenuView menuView) {
        this.menuView = menuView;
        
        initializeButtonActions();
       
    }

    public void initializeButtonActions() {
        Map<JButton, MenuAction> actions = Map.of(
                menuView.getRecipeButton(), this::handleRecipeButton,
                menuView.getShopButton(), this::handleShopButton,
                menuView.getBalanceButton(), this::handleBalanceButton);
        
        actions.forEach(this::setButtonAction);
    }
    
    private void handleRecipeButton(ActionEvent event) {
        try {
            openRecipeView();
        } catch (SQLException ex) {
            handleUnexpectedError("Failed to open Recipe View", ex);
        }
    }

    private void handleShopButton(ActionEvent event) {
        try {
            openShopView();
        } catch (SQLException ex) {
            handleUnexpectedError("Failed to open Ingredients View", ex);
        }
    }

    private void handleBalanceButton(ActionEvent event) {
        try {
            openBalanceView();
        } catch (SQLException ex) {
            handleUnexpectedError("Failed to open Balance View", ex);
        }
    }

    /**
     * Adds an ActionListener to a button.
     *
     * @param button   The button to which the listener will be added.
     * @param menuAction The Action to be executed upon button press.
     */
    private void setButtonAction(JButton button, MenuAction menuAction) {
        button.addActionListener(menuAction::execute);
    }

    public void handleUnexpectedError(String message, Exception ex) {
        menuView.showError(message);
    }

    private void openRecipeView() throws SQLException {
        menuView.closeView();
        ControllerFactory.getInstance().getRecipeController().show();
    }

    private void openShopView() throws SQLException {
        menuView.closeView();
        ControllerFactory.getInstance().getShopController().show();
    }

    private void openBalanceView() throws SQLException {
        menuView.closeView();
        ControllerFactory.getInstance().getBalanceController().show();
    }

    public void show() {
        menuView.showView();
    }

    /**
     * Funktionales Interface, das eine Aktion repräsentiert, die durch eine Benutzerinteraktion in
     * dem view ausgelöst wird, z.B. click auf einen Button. Die Aktion ist dazu gedacht, das
     * Ereignis im Controller zu verarbeiten.
     */
    @FunctionalInterface
    private interface MenuAction {

        /**
         * Führt die Aktion basierend auf einem vom Benutzer ausgelösten Ereignis aus.
         *
         * @param event Das ActionEvent, das mit der Interaktion des Benutzers verknüpft ist z.B.
         *              ein button click.
         */
        void execute(ActionEvent event);
    }

}