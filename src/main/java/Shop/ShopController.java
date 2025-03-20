package Shop;

import Factory.ControllerFactory;
import java.awt.event.ActionEvent;
import java.sql.SQLException;
import java.util.Map;
import javax.swing.JButton;
import org.jooq.Record;
import org.jooq.Result;

public record ShopController(ShopView shopView, ShopModel shopModel) {

    public ShopController(ShopView shopView, ShopModel shopModel) {
        this.shopModel = shopModel;
        this.shopView = shopView;

        shopModel.setTableModel(shopView.getTableModel());
        initializeButtonActions();
    }

    private void initializeButtonActions() {
        Map<JButton, ShopAction> actions = Map.of(
                shopView.getLoadButton(), this::handleLoadRecipes,
                shopView.getBackButton(), this::handleBackToMenu
        );
        // assign actions to buttons.
        actions.forEach(this::setButtonAction);
    }

    private void handleLoadRecipes(ActionEvent event) {
        processModelAction(
                shopModel::reloadItems,
                "Success, congrats!","Error, try again later!"
        );
    }

    private void handleBackToMenu(ActionEvent e) {
        shopView.closeView();
        try {
            ControllerFactory.getInstance().getMenuController().show();
        } catch (SQLException ex) {
            handleUnexpectedError(ex);
        }
    }

    private void setButtonAction(JButton button, ShopAction shopAction) {
        button.addActionListener(shopAction::execute);
    }

    private void processModelAction(ShopModelAction shopModelAction, String successMessage,
            String errorMessage) {
        try {
            Result<Record> result = shopModelAction.execute();
            if (result != null) {
                shopView.loadItems(result);
                shopView.showSuccessDialog(successMessage);
            } else {
                shopView.showErrorDialog(errorMessage);
            }
        } catch (Exception ex) {
            handleUnexpectedError(ex);
        }
    }

    private void handleUnexpectedError(Exception ex) {
        ex.printStackTrace();
        shopView.showErrorDialog("An unexpected error occurred: " + ex.getMessage());
    }

    public void show() {
        shopView.setVisible(true);
    }

    @FunctionalInterface
    private interface ShopAction {

        /**
         * Führt die Aktion basierend auf einem vom Benutzer ausgelösten Ereignis aus.
         *
         * @param event Das ActionEvent, das mit der Interaktion des Benutzers verknüpft ist z.B.
         *              ein button click.
         */
        void execute(ActionEvent event);
    }

    @FunctionalInterface
    private interface ShopModelAction {

        /**
         * Führt die Datenbankoperation auf dem Model aus.
         *
         * @return ein Result<Record> Objekt, dass das Ergebnis der Operation repräsentiert z.B.
         * Abfrageergebnis.
         * @throws Exception Falls bei der Operation etwas schief läuft gibt z.B. Datenbankfehler.
         */
        Result<Record> execute() throws Exception;
    }
    
}
