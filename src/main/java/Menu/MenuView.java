package Menu;

import java.awt.*;
import javax.swing.*;

public class MenuView extends JFrame {

    // buttons for menu
    private final JButton recipeButton;
    private final JButton shopButton;
    private final JButton balanceButton;

    public MenuView() {
        // construct the frame
        setupFrame();

        // add the welcome title at the top
        createWelcomeLabel();

        // panel for buttons
        JPanel buttonPanel = createButtonPanel();

        // create and add buttons
        recipeButton = createAndAddButton("Recipe", buttonPanel);
        shopButton = createAndAddButton("Shop", buttonPanel);
        balanceButton = createAndAddButton("Balance", buttonPanel);

        // add the button panel to the center of the frame
        add(buttonPanel, BorderLayout.CENTER);

    }

    /**
     * Sets up the main JFrame configurations.
     */
    private void setupFrame() {
        setTitle("Menu");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(800, 600);
        setLayout(new BorderLayout());
        getContentPane().setBackground(Color.WHITE);
    }

    /**
     * Adds a welcome label to the top of the Menu.MenuView.
     */
    private void createWelcomeLabel() {
        JLabel welcomeLabel = new JLabel("Welcome to Recipes Paradise!", SwingUtilities.CENTER);
        welcomeLabel.setFont(new Font("Calibri", Font.BOLD, 40));
        welcomeLabel.setBorder(BorderFactory.createEmptyBorder(150, 0, 20, 0));
        add(welcomeLabel, BorderLayout.NORTH);
    }

    /**
     * Creates a vertical panel (BoxLayout) for buttons.
     *
     * @return A JPanel to hold buttons in the center of the frame.
     */
    private JPanel createButtonPanel() {
        JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
        panel.setBackground(Color.WHITE);
        return panel;
    }

    /**
     * Creates a button with specified text and adds it to the provided panel.
     *
     * @param text   The button text.
     * @param panel  The panel to which the button will be added.
     * @return The created JButton.
     */
    private JButton createAndAddButton(String text, JPanel panel) {
        JButton button = createButton(text);
        panel.add(button);
        return button;
    }

    /**
     * Create a well-styled button.
     *
     * @param text The text for the button.
     * @return The styled JButton instance.
     */
    private JButton createButton(String text) {
        JButton button = new JButton(text);
        button.setFont(new Font("Cambria", Font.ITALIC, 30));
        button.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        button.setAlignmentX(Component.CENTER_ALIGNMENT);
        button.setOpaque(true);
        button.setBackground(Color.getHSBColor(0f, 0f, 1f));
        return button;
    }


    /**
     * Displays an error message in a dialog.
     *
     * @param message The error message to display.
     */
    public void showError(String message) {
        JOptionPane.showMessageDialog(this, message, "Error", JOptionPane.ERROR_MESSAGE);
    }

    public JButton getRecipeButton() {
        return recipeButton;
    }

    public JButton getShopButton() {
        return shopButton;
    }

    public JButton getBalanceButton() {
        return balanceButton;
    }

    public void closeView(){
        this.setVisible(false);
    }

    public void showView(){
        this.setVisible(true);
    }
}