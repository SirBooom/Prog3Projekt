import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class BalanceView extends JFrame{
    private JButton bonusButton;
    private JLabel balanceLabel;
    private JLabel bonusLabel;
    private Timer cooldownTimer;
    private Runnable onCooldownFinished;
    public BalanceView(){
        setTitle("BalanceManager");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(800, 600);
        setLayout(new BorderLayout());

        // top panel for back button
        JPanel topPanel = new JPanel();
        topPanel.setLayout(new BoxLayout(topPanel, BoxLayout.X_AXIS));
        topPanel.setPreferredSize(new Dimension(0, 30));
        topPanel.setBackground(Color.WHITE);
        add(topPanel, BorderLayout.NORTH);

        // rest panel for dividing into 4 panels
        JPanel bottomPanel = new JPanel();
        bottomPanel.setLayout(new GridLayout(2,2));
        add(bottomPanel, BorderLayout.CENTER);

        // divide bottomPanel into leftTop -, rightTop - , leftBottom - , rightBottom panel
        JPanel leftTopPanel = createPanel(bottomPanel);
        JPanel rightTopPanel = createPanel(bottomPanel);
        JPanel leftBottomPanel = createPanel(bottomPanel);
        JPanel rightBottomPanel = createPanel(bottomPanel);

        // adding backButton to the TopPanel
        addBackButton(topPanel, "Back", this::backToMenu);

        balanceLabel = createBalanceLabel();
        leftTopPanel.add(balanceLabel);

        setBonusLabel(rightTopPanel);
        addBonusButton(rightTopPanel);

        setVisible(true);

    }

    private JButton addButton(JPanel panel, String buttonText) {
        JButton button = new JButton(buttonText);
        button.setBorder(BorderFactory.createEmptyBorder(25, 25, 25, 25));
        button.setFont(new Font("Cambria", Font.ITALIC, 35));
        button.setOpaque(true);
        button.setBackground(Color.getHSBColor(0f, 0f, 1f));
        panel.add(button);
        return button;
    }

    private JButton addBackButton(JPanel panel, String buttonText, ActionListener action) {
        JButton button = new JButton(buttonText);
        button.addActionListener(action);
        panel.add(button);
        return button;
    }

    public void updateBalanceLabel(int newBalance){
        balanceLabel.setText("<html>Your Current Balance: " + newBalance + "   EUR </html>");
    }

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

    //creates and returns a new panel and adds it to panel
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

    public void addBonusButton(JPanel panel){
        bonusButton = addButton(panel, "Daily Bonus");
    }

    private void backToMenu(ActionEvent e) {
        dispose();
        new MenuController(new MenuView());
    }

    public JButton getBonusButton() {
        return this.bonusButton;
    }
}
