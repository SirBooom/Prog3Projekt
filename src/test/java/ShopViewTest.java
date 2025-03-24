import Template.ShopView;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import javax.swing.*;
import java.lang.reflect.Field;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

public class ShopViewTest {

    private ShopView shopView;

    @BeforeEach
    public void setUp() throws Exception {
        SwingUtilities.invokeAndWait(() -> {
            shopView = new ShopView();
        });
    }

    @AfterEach
    public void tearDown() throws Exception {
        SwingUtilities.invokeAndWait(() -> {
            if (shopView != null) {
                shopView.dispose();
            }
        });
    }

    @Test
    public void testComponentsNotNull() {
        assertNotNull(shopView.getTableModel(), "Table model should not be null");
        assertNotNull(shopView.getLoadButton(), "Load button should not be null");
        assertNotNull(shopView.getBuyButton(), "Buy button should not be null");
        assertNotNull(shopView.getBackButton(), "Back button should not be null");
    }

    @Test
    public void testGetFormDataDefault() {
        Map<String, String> formData = shopView.getFormData();
        assertEquals("", formData.get("Item ID"), "Default Item ID should be empty");
        assertEquals("", formData.get("Desired Quantity"), "Default Desired Quantity should be empty");
    }

    @Test
    public void testGetFormDataAfterSettingFields() throws Exception {
        setTextFieldValue(shopView, "idField", "5");
        setTextFieldValue(shopView, "amountField", "10");

        Map<String, String> formData = shopView.getFormData();
        assertEquals("5", formData.get("Item ID"));
        assertEquals("10", formData.get("Desired Quantity"));
    }

    private void setTextFieldValue(ShopView view, String fieldName, String value) throws Exception {
        Field field = ShopView.class.getDeclaredField(fieldName);
        field.setAccessible(true);
        JTextField textField = (JTextField) field.get(view);
        SwingUtilities.invokeAndWait(() -> textField.setText(value));
    }
}
