import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;

public class MenuManager extends JFrame {
    private static final Font BUTTON_FONT = new Font("Cambria", Font.ITALIC, 30);
    private static final Color BUTTON_COLOR = Color.getHSBColor(0f, 0f, 1f);
    private JPanel buttonPanel;

    public MenuManager() {
        setTitle("Menu");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(800, 600);
        setLayout(new BorderLayout());
        getContentPane().setBackground(Color.WHITE);

        addWelcomeLabel();
        addButtonPanel();

        createButton("Recipes", RecipeController::new);
        createButton("Shop", ShopManager::new);
        createButton("Balance", BalanceManager::new);

        add(buttonPanel, BorderLayout.CENTER);
        setVisible(true);
    }

    private void addWelcomeLabel() {
        JLabel welcomeLabel = new JLabel("Welcome to Recipes Paradise!", SwingUtilities.CENTER);
        welcomeLabel.setFont(new Font("Calibri", Font.BOLD, 40));
        welcomeLabel.setBorder(BorderFactory.createEmptyBorder(150, 0, 20, 0));
        add(welcomeLabel, BorderLayout.NORTH);
    }

    private void addButtonPanel() {
        buttonPanel = new JPanel();
        buttonPanel.setLayout(new BoxLayout(buttonPanel, BoxLayout.Y_AXIS));
        buttonPanel.setBackground(Color.WHITE);
    }

    private void createButton(String text, Runnable onClickAction) {
        JButton button = new JButton(text);
        button.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        button.setFont(BUTTON_FONT);
        button.setAlignmentX(Component.CENTER_ALIGNMENT);
        button.setOpaque(true);
        button.setBackground(BUTTON_COLOR);
        button.addActionListener(e -> {
            dispose();
            SwingUtilities.invokeLater(onClickAction);
        });
        buttonPanel.add(button);
    }
}