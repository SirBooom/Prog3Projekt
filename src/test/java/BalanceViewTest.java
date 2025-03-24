import Template.BalanceView;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import javax.swing.*;
import java.lang.reflect.Field;

import static org.junit.jupiter.api.Assertions.*;

public class BalanceViewTest {

    private BalanceView balanceView;

    @BeforeEach
    public void setUp() throws Exception {

        SwingUtilities.invokeAndWait(() -> {
            balanceView = new BalanceView();
        });
    }

    @AfterEach
    public void tearDown() throws Exception {
        SwingUtilities.invokeAndWait(() -> {
            if (balanceView != null) {
                balanceView.dispose();
            }
        });
    }

    @Test
    public void testComponentsNotNull() {
        assertNotNull(balanceView.getBonusButton(), "Bonus button should not be null");
        assertNotNull(balanceView.getBackButton(), "Back button should not be null");
        assertNotNull(balanceView.getBalanceLabel(), "Balance label should not be null");
    }

    @Test
    public void testUpdateBalanceLabel() {
        balanceView.updateBalanceLabel(2000);
        assertEquals("<html>Your Current Balance: 2000.0   EUR </html>", balanceView.getBalanceLabel().getText());
    }

    @Test
    public void testBonusButtonCooldown() throws Exception {

        long cooldownEnd = System.currentTimeMillis() + 5000;
        balanceView.startBonusCooldown(cooldownEnd, () -> {
            assertTrue(balanceView.getBonusButton().isEnabled(), "Bonus button should be enabled after cooldown");
        });
    }

    @Test
    public void testBonusLabelTextUpdate() throws InterruptedException {
        long cooldownEnd = System.currentTimeMillis() + 5000;
        balanceView.startBonusCooldown(cooldownEnd, null);


        Thread.sleep(1000);

        String text = balanceView.getBonusLabel().getText();
        assertTrue(text.contains("Available in:"), "Bonus label should display remaining time");
    }
}
