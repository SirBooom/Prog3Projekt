package Balance;

import MVC.View;

import javax.swing.*;
import java.awt.*;

public class BalanceView extends View {
    private JButton bonusButton;
    private JLabel balanceLabel;
    private JLabel bonusLabel;
    private Timer cooldownTimer;
    private Runnable onCooldownFinished;

    public BalanceView(){

        setupFrame();

        // create buttons for user to interact with
        createButtons();
    }

    ///////////////////////////////////// --- Construct The Shop View --- /////////////////////////////////////

    @Override
    protected void setupFrame(){
        setTitle("Balance Manager");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(800, 600);
        setLayout(new BorderLayout());
    }

    @Override
    protected void createButtons() {
        // top panel for back button
        JPanel backPanel = new JPanel();
        backPanel.setLayout(new BoxLayout(backPanel, BoxLayout.X_AXIS));
        backPanel.setPreferredSize(new Dimension(0, 30));
        backPanel.setBackground(Color.WHITE);
        add(backPanel, BorderLayout.NORTH);

        JPanel buttonPanel = new JPanel();
        buttonPanel.setLayout(new GridLayout(2,2));
        add(buttonPanel, BorderLayout.CENTER);

        // create panel for balance and bonus button
        JPanel balancePanel = createPanel(buttonPanel);
        JPanel bonusPanel = createPanel(buttonPanel);

        balanceLabel = createBalanceLabel();
        balancePanel.add(balanceLabel);

        bonusButton = addButton(bonusPanel, "Daily Bonus");
        setBonusLabel(bonusPanel);

        backButton = addButton(backPanel, "Back");
        backPanel.add(backButton);
    }

    public JPanel createPanel(JPanel panel){
        JPanel jPanel = new JPanel(new GridBagLayout());
        jPanel.setBackground(Color.WHITE);
        panel.add(jPanel);
        return jPanel;
    }

    public JLabel createBalanceLabel(){
        balanceLabel = new JLabel("<html>Your Current Balance: " + "1000" + " EUR </html>");
        balanceLabel.setPreferredSize(new Dimension(300, 250));
        balanceLabel.setFont(new Font("Cambria", Font.BOLD, 35));
        return balanceLabel;
    }

    ///////////////////////////////////// --- Obtain The Visual Data --- /////////////////////////////////////

    public JButton getBonusButton() {
        return this.bonusButton;
    }

    ////////////////////////////////// --- View Methods --- ////////////////////////////////////////

    public void startBonusCooldown(long cooldownEnd, Runnable onCooldownFinished) {
        this.onCooldownFinished = onCooldownFinished;
        bonusButton.setEnabled(false);
        cooldownTimer = new Timer(10, e -> {
            long remainingTime = (cooldownEnd - System.currentTimeMillis()) / 1000;
            if (remainingTime <= 0) {
                cooldownTimer.stop();
                bonusLabel.setText("Bonus available!");
                bonusButton.setEnabled(true);
                // informs to controller when cooldown finished
                if (this.onCooldownFinished != null) {
                    this.onCooldownFinished.run();
                }
            } else {
                updateBonusLabel(cooldownEnd - System.currentTimeMillis());
            }
        });
        cooldownTimer.start();
    }

    private void updateBonusLabel(long remainingMillis){
        long hours = (remainingMillis / (1000 * 60 * 60)) % 24;
        long minutes = (remainingMillis / (1000 * 60)) % 60;
        long seconds = (remainingMillis / 1000) % 60;
        bonusLabel.setText(String.format("Available in: %02dh %02dm %02ds", hours, minutes, seconds));
    }

    public void setBonusLabel(JPanel panel){
        // Bonus-Button mit Cooldown
        bonusLabel = new JLabel("Bonus available!");
        bonusLabel.setFont(new Font("Cambria", Font.BOLD, 25));
        // GridBagConstraints für Available in
        GridBagConstraints gbcBonusText = new GridBagConstraints();
        gbcBonusText.gridx = 0;
        gbcBonusText.gridy = 1;
        gbcBonusText.insets = new Insets(10, 10, 10, 10);
        panel.add(bonusLabel, gbcBonusText);
    }

    public void updateBalanceLabel(float newBalance){
        balanceLabel.setText("<html>Your Current Balance: " + newBalance + "   EUR </html>");
    }


}
