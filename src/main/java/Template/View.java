package Template;

import javax.swing.*;

public abstract class View extends JFrame {

    protected JButton backButton;
    protected JButton loadButton;


    protected abstract void setupFrame();

    protected abstract void createButtons();

    protected JButton addButton(JPanel panel, String buttonText) {
        JButton button = new JButton(buttonText);
        panel.add(button);
        return button;
    }

    public void closeView(){
        this.setVisible(false);
    }

    public void showView(){
        this.setVisible(true);
    }

    public void showErrorDialog(String message) {
        JOptionPane.showMessageDialog(this, message);
    }

    public void showSuccessDialog(String message) {
        JOptionPane.showMessageDialog(this, message);
    }

    public JButton getBackButton() {
        return backButton;
    }

    public JButton getLoadButton() {
        return loadButton;
    }
}
