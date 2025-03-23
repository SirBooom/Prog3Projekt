package Balance;

import Factory.ControllerFactory;
import Template.BalanceView;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.sql.SQLException;
import java.util.Map;

public class BalanceController {
    private final BalanceView balanceView;
    private final BalanceModel balanceModel;

    public BalanceController(BalanceView balanceView, BalanceModel balanceModel){
        this.balanceView = balanceView;
        this.balanceModel = balanceModel;
        updateBalance(0);
        initializeButtonActions();
        checkBonusCooldown();
    }

    public void initializeButtonActions() {
        Map<JButton, BalanceController.BalanceAction> actions = Map.of(
                balanceView.getBonusButton(), this::handleBonusButton,
                balanceView.getBackButton(), this::handleBackToMenu
        );
        // Assign actions to buttons.
        actions.forEach(this::setButtonAction);
    }

    private void handleBackToMenu(ActionEvent event) {
        balanceView.closeView();
        try {
            ControllerFactory.getInstance().getMenuController().show();
        } catch (SQLException ex) {
            balanceView.showErrorDialog("Failed to return to the menu: " + ex.getMessage());
            ex.printStackTrace();
        }
    }

    private void handleBonusButton(ActionEvent event){
        updateBalance(1000);
        long cooldownEnd = System.currentTimeMillis() + 24 * 60 * 60 * 1000;
        balanceModel.setCooldownTime(cooldownEnd);
        startBonusCooldownIfNeeded(cooldownEnd);
    }

    private void setButtonAction(JButton button, BalanceController.BalanceAction balanceAction) {
        button.addActionListener(balanceAction::execute);
    }

    private void updateBalance(int bonus){
        int newBalance = balanceModel.getBalance() + bonus;
        balanceModel.setBalance(newBalance);
        balanceView.updateBalanceLabel(newBalance);
        if (bonus>0) {
            balanceView.showSuccessDialog(1000 + " EUR Bonus has been added to your balance");
        }
    }

    private void checkBonusCooldown() {
        long cooldownEnd = balanceModel.getCooldownTime();
        if(cooldownEnd > System.currentTimeMillis()){
            startBonusCooldownIfNeeded(cooldownEnd);
        }
    }

    private void startBonusCooldownIfNeeded(long cooldownEnd) {
        balanceView.startBonusCooldown(cooldownEnd, () -> balanceModel.setCooldownTime(0));
    }

    public void show() {
        balanceView.showView();
    }

    @FunctionalInterface
    private interface BalanceAction {
        void execute(ActionEvent event);
    }
}