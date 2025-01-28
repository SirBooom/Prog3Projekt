import static com.example.generated.Tables.INGREDIENT;
import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.table.DefaultTableModel;

public class IngredientManager extends JFrame {

    private DefaultTableModel tableModel;
    private JTable ingredientTable;

    public IngredientManager() {
        setTitle("Ingredients");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(1000, 800);
        setLayout(new BorderLayout());

        tableModel = new DefaultTableModel(
                new Object[]{"ID", "Name", "RECIPE_ID", "PRICE", "NUTRITION"}, 0);
        ingredientTable = new JTable(tableModel);
        add(new JScrollPane(ingredientTable), BorderLayout.CENTER);
        ingredientTable.setFont(new Font("Camibri", Font.ITALIC, 15));


        createBackButton();
        createLoadIngredientButton();
        setVisible(true);
    }

    private void createLoadIngredientButton() {
        JPanel buttonPanel = new JPanel();
        buttonPanel.setLayout(new BoxLayout(buttonPanel, BoxLayout.X_AXIS));
        JButton loadButton = addButton(buttonPanel, "Load ingredients",this::loadIngredients);
        loadButton.setFont(new Font("Arial", Font.BOLD, 20));

        Box centeredBox = new Box(BoxLayout.Y_AXIS);
        centeredBox.add(Box.createVerticalGlue()); // Fill vertical space to push the button to center
        centeredBox.add(buttonPanel);  // Add the button panel
        centeredBox.add(Box.createVerticalGlue());
        centeredBox.setBorder(BorderFactory.createEmptyBorder(20,0,20,0));

        add(centeredBox, BorderLayout.SOUTH);
    }

    private void createBackButton() {
        JPanel backButton = new JPanel();
        backButton.setLayout(new BoxLayout(backButton, BoxLayout.X_AXIS));
        addButton(backButton, "Back", this::backToMenu);
        backButton.add(Box.createVerticalStrut(40));
        add(backButton, BorderLayout.NORTH);
    }

    private JButton addButton(JPanel panel, String buttonText, ActionListener action) {
        JButton button = new JButton(buttonText);
        button.addActionListener(action);
        panel.add(button);
        return button;
    }

    private void backToMenu(ActionEvent e) {
        dispose();
        new MenuManager();
    }

    private void loadIngredients (ActionEvent e){
        try {
            tableModel.setRowCount(0);
            Database.getConnection()
                    .select()
                    .from(INGREDIENT)
                    .fetch()
                    .forEach(record -> tableModel.addRow(new Object[]{
                            record.getValue(INGREDIENT.ID),
                            record.getValue(INGREDIENT.NAME),
                            record.getValue(INGREDIENT.RECIPE_ID),
                            record.getValue(INGREDIENT.PRICE),
                            record.getValue(INGREDIENT.NUTRITION),
                    }));
        } catch (Exception ex) {
            ex.printStackTrace();
            JOptionPane.showMessageDialog(this, "Error loading Ingredients.");
        }
    }
}
