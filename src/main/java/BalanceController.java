import javax.swing.*;

public class BalanceController {
    private final BalanceView balanceView;
    private final BalanceModel balanceModel;

    public BalanceController(BalanceView balanceView, BalanceModel balanceModel){
        this.balanceView = balanceView;
        this.balanceModel = balanceModel;
        updateBalance(0);

        balanceView.getBonusButton().addActionListener(_ ->bonusButtonClicked());

        checkBonusCooldown();
    }

    private void bonusButtonClicked(){
        updateBalance(1000);
        long cooldownEnd = System.currentTimeMillis() + 24 * 60 * 60 * 1000;
        balanceModel.setCooldownTime(cooldownEnd);
        startBonusCooldownIfNeeded(cooldownEnd);
    }

    private void updateBalance(int bonus){
        int newBalance = balanceModel.getBalance() + bonus;
        balanceModel.setBalance(newBalance);
        balanceView.updateBalanceLabel(newBalance);
        if (bonus>0) {
            JOptionPane.showMessageDialog(balanceView, bonus + " EUR Bonus has been added to your balance");
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
        balanceView.setVisible(true);
    }
}