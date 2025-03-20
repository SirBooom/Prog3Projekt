package Shop;

import static com.example.generated.Tables.SHOP;
import FileData.FileHandler;
import java.awt.BorderLayout;
import java.awt.Font;
import java.awt.GridLayout;
import java.util.HashMap;
import java.util.Map;
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
import javax.swing.SwingUtilities;
import javax.swing.table.DefaultTableModel;
import org.jooq.Record;
import org.jooq.Result;

public class ShopView extends JFrame  {

    private DefaultTableModel tableModel;

    private JButton loadButton;
    private JButton buyButton;
    private JButton backButton;

    private JTextField idField, amountField;

    public ShopView() {

        setupFrame();

        // create layout table for items
        setupItemTable();

        // show balance label
        addBalanceLabel();

        // create buttons for user to interact with
        createButtons();

        // display the frame
        setVisible(true);
    }

    ///////////////////////////////////// --- Construct The Shop View --- /////////////////////////////////////

    private void setupFrame() {
        setTitle("Shop");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(1000, 800);
        setLayout(new BorderLayout());
    }

    private void setupItemTable() {
        tableModel = new DefaultTableModel(
                new Object[]{"ID", "Name", "Amount", "PRICE(€)", "Nutrition(Kcal)"}, 0);
        JTable ingredientTable = new JTable(tableModel);
        add(new JScrollPane(ingredientTable), BorderLayout.CENTER);
        ingredientTable.setFont(new Font("Camibri", Font.BOLD, 15));
    }

    private void createButtons() {

        JPanel buttonPanel = new JPanel();
        //buttonPanel.setLayout(new BoxLayout(buttonPanel, BoxLayout.Y_AXIS));

        loadButton = addButton(buttonPanel, "Load ingredients");
        buyButton = addButton(buttonPanel, "Buy Item");

        Box centeredBox = new Box(BoxLayout.Y_AXIS);
        centeredBox.add(Box.createVerticalGlue()); // Fill vertical space to push the button to center
        centeredBox.add(buttonPanel);  // Add the button panel
        centeredBox.add(Box.createVerticalGlue());
        centeredBox.setBorder(BorderFactory.createEmptyBorder(20,0,20,0));
        add(centeredBox, BorderLayout.SOUTH);


        //backButton
        JPanel backPanel = new JPanel();
        backPanel.setLayout(new BoxLayout(backPanel, BoxLayout.X_AXIS));

        backButton = addButton(backPanel, "Back");

        backPanel.add(Box.createVerticalStrut(40));
        add(backPanel, BorderLayout.NORTH);
    }

    public void displayItemDialog() {
        JPanel panel = new JPanel(new GridLayout(2, 2, 10, 10)); // 2 rows, 2 columns, 10px gap

        JLabel idLabel = new JLabel("Item ID:");
        idField = new JTextField();
        JLabel amountLabel = new JLabel("Desired Quantity:");
        amountField = new JTextField();

        panel.add(idLabel);
        panel.add(idField);
        panel.add(amountLabel);
        panel.add(amountField);

        showItemDialog(panel);

        }

    public void addBalanceLabel(){
        JLabel balanceLabel = new JLabel("<html>Your Current Balance: " + FileHandler.loadBalance() + "   EUR </html>",
                SwingUtilities.RIGHT);
        balanceLabel.setBorder(BorderFactory.createEmptyBorder(400, 0, 20, 0));
        balanceLabel.setFont(new Font("Cambria", Font.BOLD, 20));
        add(balanceLabel, BorderLayout.SOUTH);
        setVisible(true);
    }

    private JButton addButton(JPanel panel, String buttonText) {
        JButton button = new JButton(buttonText);
        panel.add(button);
        return button;
    }

    ///////////////////////////////////// --- Obtain The Visual Data --- /////////////////////////////////////

    public DefaultTableModel getTableModel() {
        return tableModel;
    }

    public JButton getLoadButton() {
        return loadButton;
    }

    public JButton getBackButton() {
        return backButton;
    }

    public JButton getBuyButton() {
        return buyButton;
    }

    public Map<String, String> getFormData() {
        Map<String, String> formData = new HashMap<>();
        formData.put("Item ID", idField.getText());
        formData.put("Desired Quantity", amountField.getText());
        return formData;
    }

    ///////////////////////////////////// --- View Methods --- /////////////////////////////////////

    public void loadItems (Result<Record> result){
        tableModel.setRowCount(0);
        result.forEach(record -> {
            tableModel.addRow(new Object[]{
                    record.getValue(SHOP.ID),
                    record.getValue(SHOP.NAME),
                    record.getValue(SHOP.AMOUNT_AVAILABLE),
                    record.getValue(SHOP.PRICE),
                    record.getValue(SHOP.NUTRITION),
            });
        });
    }

    public void closeView() {
        this.dispose();
    }

    public void showErrorDialog(String message) {
        JOptionPane.showMessageDialog(this, message);
    }

    public void showSuccessDialog(String message) {
        JOptionPane.showMessageDialog(this, message);
    }

    private void showItemDialog(JPanel panel){
        JOptionPane.showConfirmDialog(this, panel,"Buy an Item",
                JOptionPane.OK_CANCEL_OPTION, JOptionPane.PLAIN_MESSAGE);
    }

}
