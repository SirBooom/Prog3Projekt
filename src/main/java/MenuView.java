import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Font;
import java.awt.event.ActionListener;
import javax.swing.*;

public class MenuView extends JFrame {
    private final JButton recipeButton;
    private final JButton ingredientsButton;
    private final JButton balanceButton;
    public MenuView(){
        setTitle("Menu");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(800, 600);
        setLayout(new BorderLayout());
        getContentPane().setBackground(Color.WHITE);

        // Insert title at top of frame
        welcomeLabel();

        JPanel buttonPanel = new JPanel();
        buttonPanel.setLayout(new BoxLayout(buttonPanel, BoxLayout.Y_AXIS));
        buttonPanel.setBackground(Color.WHITE);

        // create buttons
        recipeButton = addButton("Recipes", buttonPanel);
        ingredientsButton = addButton("Shop", buttonPanel);
        balanceButton = addButton("Balance", buttonPanel);

        // insert/add the buttons into the button section
        add(buttonPanel, BorderLayout.CENTER);
        setVisible(true);
    }

    private void welcomeLabel(){
        JLabel welcomeLabel = new JLabel("Welcome to Recipes Paradise!", SwingUtilities.CENTER);
        welcomeLabel.setFont(new Font("Calibri", Font.BOLD, 40));
        welcomeLabel.setBorder(BorderFactory.createEmptyBorder(150, 0, 20, 0));

        add(welcomeLabel, BorderLayout.NORTH);
    }

    //creates a Button with text name and adds to buttonPanel
    private JButton addButton(String name,JPanel buttonPanel) {
        JButton button = new JButton(name);
        button.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        button.setFont(new Font("Cambria", Font.ITALIC, 30));
        button.setAlignmentX(Component.CENTER_ALIGNMENT);
        button.setOpaque(true);
        button.setBackground(Color.getHSBColor(0f,0f, 1f));
        buttonPanel.add(button);
        return button;
    }

    public void addButtonListener(JButton button, ActionListener listener){
        button.addActionListener(listener);
    }

    public JButton getRecipeButton() {
        return recipeButton;
    }

    public JButton getIngredientsButton() {
        return ingredientsButton;
    }

    public JButton getBalanceButton() {
        return balanceButton;
    }

    public void closeView(){
        this.dispose();
    }
}

