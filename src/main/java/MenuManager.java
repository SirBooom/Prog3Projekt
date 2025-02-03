import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;

public class MenuManager extends JFrame {
    /*
    private JTextField idField, nameField, cuisineField, categoryField, instructionsField,
            nutritionField, cookingTimeField, ingredientField;


    public MenuManager(IngredientManager im, RecipeManager rm, BalanceManager bm) {
        recipeManager = rm;
        ingredientManager = im;
        balanceManager = bm;
    }
    */
    private JPanel buttonPanel;

    public MenuManager(){
        setTitle("Menu");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(800, 600);
        setLayout(new BorderLayout());
        getContentPane().setBackground(Color.WHITE);


        // Insert title at top of frame
        welcomeLabel();

        // create section for buttons
        buttonPanel = new JPanel();
        buttonPanel.setLayout(new BoxLayout(buttonPanel, BoxLayout.Y_AXIS));
        buttonPanel.setBackground(Color.WHITE);

        // create buttons
        JButton rb = createRecipeButton(buttonPanel);
        JButton ib = createIngredientButton(buttonPanel);
        JButton bb = createBalanceButton(buttonPanel);

        // insert/add the buttons into the button section
        add(buttonPanel, BorderLayout.CENTER);
        setVisible(true);
        //ingredientsButton.setPreferredSize(buttonSize);
        //balanceButton.setPreferredSize(buttonSize);

        //ingredientsButton.setAlignmentX(Component.CENTER_ALIGNMENT);
        //balanceButton.setAlignmentX(Component.CENTER_ALIGNMENT);
        // add buttons to the panel + add space between buttons and panel
        //buttonPanel.add(Box.createVerticalStrut(50));
        //buttonPanel.add(Box.createVerticalStrut(50));
        //buttonPanel.add(ingredientsButton);
        //buttonPanel.add(Box.createVerticalStrut(50));  // space between buttons
        //buttonPanel.add(balanceButton);
        //buttonPanel.add(Box.createVerticalStrut(50));

        pressIngredientButton(ib);
        pressRecipeButton(rb);
        pressBalanceButton(bb);

    }

    private void welcomeLabel(){
        //add(Box.createVerticalStrut(10));
        JLabel welcomeLabel = new JLabel("Welcome to Recipes Paradise!", SwingUtilities.CENTER);
        welcomeLabel.setFont(new Font("Calibri", Font.BOLD, 40));
        welcomeLabel.setBorder(BorderFactory.createEmptyBorder(150, 0, 20, 0));
        //add(Box.createVerticalStrut(20));
        //welcomeLabel.add(Box.createVerticalStrut(20));
        //welcomeLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        //welcomeLabel.setAlignmentY(Component.CENTER_ALIGNMENT);
        add(welcomeLabel, BorderLayout.NORTH);
    }

    private JButton createIngredientButton(JPanel buttonPanel) {
        JButton ib = new JButton("Ingredients");
        ib.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        ib.setFont(new Font("Cambria", Font.ITALIC, 30));
        ib.setAlignmentX(Component.CENTER_ALIGNMENT);
        ib.setOpaque(true);
        ib.setBackground(Color.getHSBColor(0f,0f, 1f));
        buttonPanel.add(ib);
        return ib;
    }

    private JButton createBalanceButton(JPanel buttonPanel) {
        JButton bb = new JButton("Balance");
        bb.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        bb.setFont(new Font("Cambria", Font.ITALIC, 30));
        bb.setAlignmentX(Component.CENTER_ALIGNMENT);
        bb.setOpaque(true);
        bb.setBackground(Color.getHSBColor(0f,0f, 1f));
        buttonPanel.add(bb);
        return bb;
    }

    private JButton createRecipeButton(JPanel buttonPanel){
        JButton rb = new JButton("Recipes");
        rb.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        rb.setFont(new Font("Cambria", Font.ITALIC, 30));
        rb.setAlignmentX(Component.CENTER_ALIGNMENT);
        rb.setOpaque(true);
        rb.setBackground(Color.getHSBColor(0f,0f, 1f));
        buttonPanel.add(rb);
        return rb;
    }

    private void pressRecipeButton(JButton rb) {
        rb.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                dispose();

                SwingUtilities.invokeLater(RecipeView::new);
            }
        });
    }

    private void pressIngredientButton(JButton ib) {
        ib.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                // Close the main frame immediately
                dispose();

                // Open the Recipes JFrame on the EDT (Event Dispatch Thread) to ensure responsiveness
                SwingUtilities.invokeLater(IngredientManager::new);
            }
        });
    }

    private void pressBalanceButton(JButton bb) {
        bb.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                // Close the main frame immediately
                dispose();

                // Open the Recipes JFrame on the EDT (Event Dispatch Thread) to ensure responsiveness
                SwingUtilities.invokeLater(BalanceManager::new);
            }
        });
    }

    private void addButton (JPanel panel, String buttonText){
        JButton button = new JButton(buttonText);
        //button.addActionListener(action);
        panel.add(button);
    }

}
