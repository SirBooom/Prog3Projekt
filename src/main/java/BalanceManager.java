import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.*;

public class BalanceManager extends JFrame {
    private static final int COOLDOWN_HOURS = 24;
    private static final long COOLDOWN_MILLIS = COOLDOWN_HOURS * 60 * 60 * 1000;
    private static int balance;
    private static int cashAvailable;
    private static JLabel balanceLabel;
    private static JButton bonusButton;
    private JLabel bonusLabel;
    private Timer cooldownTimer;
    private static final String COOLDOWN_FILE = "cooldown.txt";
    public BalanceManager() {
        setTitle("BalanceManager");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(800, 600);
        setLayout(new BorderLayout());

        //load balance
        balance = loadData("balance.txt",1000);

        //load cashAvailable
        cashAvailable = loadData("cashAvailable.txt",10000);

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

        addBalanceLabel(leftTopPanel);
        addButton(rightTopPanel,"Withdraw Money", new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                withdrawMoneyMenu();
            }
        });

        setBonusLabelAndButton(leftBottomPanel);

        setVisible(true);

    }

    //creates and returns a new panel and adds it to panel
    public JPanel createPanel(JPanel panel){
        JPanel jPanel = new JPanel(new GridBagLayout());
        jPanel.setBackground(Color.WHITE);
        panel.add(jPanel);
        return jPanel;
    }

    // adds a balance Label with the text 'Your Current Balance: ... '
    public static void addBalanceLabel(JPanel jPanel){
        balanceLabel = new JLabel("<html>Your Current Balance: " + balance + "   EUR </html>");
        balanceLabel.setPreferredSize(new Dimension(300, 250));
        balanceLabel.setFont(new Font("Cambria", Font.BOLD, 35));
        jPanel.add(balanceLabel);
    }

    public void setBonusLabelAndButton(JPanel leftBottomPanel){
        // Bonus-Button mit Cooldown
        bonusLabel = new JLabel("Bonus available!");
        bonusLabel.setFont(new Font("Cambria", Font.BOLD, 25));
        // GridBagConstraints für Available in
        GridBagConstraints gbcBonusText = new GridBagConstraints();
        gbcBonusText.gridx = 0;
        gbcBonusText.gridy = 1;
        gbcBonusText.insets = new Insets(10, 10, 10, 10);
        leftBottomPanel.add(bonusLabel, gbcBonusText);

        bonusButton = addButton(leftBottomPanel, "Daily Bonus", e->bonusButtonClicked());

        checkBonusCooldown();

    }

    //opens a frame when button "withdraw money" is clicked
    public static void withdrawMoneyMenu() {
        JFrame withdrawFrame = new JFrame("Withdraw Money");
        withdrawFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        withdrawFrame.setSize(400,100);
        withdrawFrame.setLayout(new FlowLayout());
        JLabel text = new JLabel("<html> Enter amount you want to withdraw.  Available: " + cashAvailable + "</html>");
        withdrawFrame.add(text);
        JTextField inputField = new JTextField(20);
        withdrawFrame.add(inputField);
        JButton enterButton = new JButton("Enter");
        withdrawFrame.add(enterButton);
        enterButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String userInput = inputField.getText();
                JOptionPane.showMessageDialog(withdrawFrame, userInput + " EUR has been added to your balance");
                updateBalance(userInput);
                updateCashAvailable(userInput,text);
            }
        });
        withdrawFrame.setVisible(true);

    }

    private static void updateBalance(String userInput){
        balance = balance + Integer.parseInt(userInput);
        saveData("balance.txt",balance);
        balanceLabel.setText("<html>Your Current Balance: " + balance + "   EUR </html>");
    }

    private static void updateCashAvailable(String userInput, JLabel text){
        cashAvailable = cashAvailable - Integer.parseInt(userInput);
        saveData("cashAvailable.txt",cashAvailable);
        text.setText("<html> Enter amount you want to withdraw.  Available: " + cashAvailable + "</html>");
    }

    private JButton addButton(JPanel panel, String buttonText, ActionListener action) {
        JButton button = new JButton(buttonText);
        button.setBorder(BorderFactory.createEmptyBorder(25, 25, 25, 25));
        button.setFont(new Font("Cambria", Font.ITALIC, 35));
        button.setOpaque(true);
        button.setBackground(Color.getHSBColor(0f, 0f, 1f));
        button.addActionListener(action);
        panel.add(button);
        return button;
    }

    private JButton addBackButton(JPanel panel, String buttonText, ActionListener action) {
        JButton button = new JButton(buttonText);
        button.addActionListener(action);
        panel.add(button);
        return button;
    }

    // saves the value to fileName
    private static void saveData(String fileName, int value){
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(fileName))) {
            writer.write(String.valueOf(value));
        } catch (IOException e){
            e.printStackTrace();
        }
    }

    // loads data from path exists and assigns to value otherwise assigns initialValue
    private static int loadData(String pathName, int initialValue) {
        File file = new File(pathName);
        int value = initialValue;
        if(!file.exists()){
            return value;
        }
        try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
            String line = reader.readLine();
            if(line != null){
                value = Integer.parseInt(line);
            }
        } catch (IOException | NumberFormatException e) {
            value = initialValue;
            e.printStackTrace();
        }
        return value;
    }

    private void backToMenu(ActionEvent e) {
        dispose();
        //new MenuManager();
    }

    private void giveBonus(){
        balance = balance + 1000;
        saveData("balance.txt",balance);
        balanceLabel.setText("<html>Your Current Balance: " + balance + "   EUR </html>");
        JOptionPane.showMessageDialog(this,  "1000 EUR Bonus has been added to your balance");
    }

    public void bonusButtonClicked(){
        giveBonus();
        startBonusCooldown();
    }

    private void startBonusCooldown() {
        long cooldownEnd = System.currentTimeMillis() + COOLDOWN_MILLIS;
        saveCooldownTime(cooldownEnd);
        updateCooldownUI(cooldownEnd);
    }

    private void updateCooldownUI(long cooldownEnd) {
        bonusButton.setEnabled(false);
        cooldownTimer = new Timer(10, e -> {
            long remainingTime = (cooldownEnd - System.currentTimeMillis()) / 1000;
            if (remainingTime <= 0) {
                cooldownTimer.stop();
                bonusLabel.setText("Bonus available!");
                bonusButton.setEnabled(true);
                saveCooldownTime(0);
            } else {
                long remainingMillis = cooldownEnd - System.currentTimeMillis();
                updateBonusLabel(remainingMillis);
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

    private void checkBonusCooldown() {
        long cooldownEnd = loadCooldownTime();
        if (cooldownEnd > System.currentTimeMillis()) {
            updateCooldownUI(cooldownEnd);
        }
    }

    // saves cooldownTime to cooldown.txt
    private void saveCooldownTime(long time) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(COOLDOWN_FILE))) {
            writer.write(String.valueOf(time));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    // loads cooldownTime from cooldown.txt
    private long loadCooldownTime() {
        File file = new File(COOLDOWN_FILE);
        if (!file.exists()) return 0;
        try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
            return Long.parseLong(reader.readLine());
        } catch (IOException | NumberFormatException e) {
            e.printStackTrace();
        }
        return 0;
    }
}

