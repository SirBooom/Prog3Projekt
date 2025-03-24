import Template.RecipeView;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import javax.swing.*;
import java.lang.reflect.Field;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

public class RecipeViewTest {

    private RecipeView recipeView;

    @BeforeEach
    public void setUp() throws Exception {
        SwingUtilities.invokeAndWait(() -> {
            recipeView = new RecipeView();
        });
    }

    @AfterEach
    public void tearDown() throws Exception {
        SwingUtilities.invokeAndWait(() -> {
            if (recipeView != null) {
                recipeView.dispose();
            }
        });
    }

    @Test
    public void testComponentsNotNull() {
        assertNotNull(recipeView.getTableModel(), "Table model should not be null");
        assertNotNull(recipeView.getLoadButton(), "Load button should not be null");
        assertNotNull(recipeView.getAddRecipeButton(), "Add Recipe button should not be null");
        assertNotNull(recipeView.getDeleteRecipeButton(), "Delete Recipe button should not be null");
        assertNotNull(recipeView.getUpdateRecipeButton(), "Update Recipe button should not be null");
        assertNotNull(recipeView.getFilterRecipeButton(), "Filter Recipe button should not be null");
        assertNotNull(recipeView.getDeleteAllRecipeButton(), "Delete All Recipe button should not be null");
        assertNotNull(recipeView.getBackButton(), "Back button should not be null");
    }

    @Test
    public void testGetFormDataDefault() {
        Map<String, String> formData = recipeView.getFormData();
        assertEquals("", formData.get("id"), "Default id should be empty");
        assertEquals("", formData.get("name"), "Default name should be empty");
        assertEquals("", formData.get("cuisine"), "Default cuisine should be empty");
        assertEquals("", formData.get("category"), "Default category should be empty");
        assertEquals("", formData.get("instructions"), "Default instructions should be empty");
        assertEquals("", formData.get("nutrition"), "Default nutrition should be empty");
        assertEquals("", formData.get("cookingTime"), "Default cookingTime should be empty");
        assertEquals("", formData.get("ingredient"), "Default ingredient should be empty");
    }

    @Test
    public void testGetFormDataAfterSettingFields() throws Exception {
        setTextFieldValue(recipeView, "idField", "1");
        setTextFieldValue(recipeView, "nameField", "Spaghetti");
        setTextFieldValue(recipeView, "cuisineField", "Italian");
        setTextFieldValue(recipeView, "categoryField", "Pasta");
        setTextFieldValue(recipeView, "instructionsField", "Boil water and cook pasta");
        setTextFieldValue(recipeView, "nutritionField", "High");
        setTextFieldValue(recipeView, "cookingTimeField", "10");
        setTextFieldValue(recipeView, "ingredientField", "Pasta, Tomato Sauce");

        Map<String, String> formData = recipeView.getFormData();
        assertEquals("1", formData.get("id"));
        assertEquals("Spaghetti", formData.get("name"));
        assertEquals("Italian", formData.get("cuisine"));
        assertEquals("Pasta", formData.get("category"));
        assertEquals("Boil water and cook pasta", formData.get("instructions"));
        assertEquals("High", formData.get("nutrition"));
        assertEquals("10", formData.get("cookingTime"));
        assertEquals("Pasta, Tomato Sauce", formData.get("ingredient"));
    }

    /**
     * helper method
     */
    private void setTextFieldValue(RecipeView view, String fieldName, String value) throws Exception {
        Field field = RecipeView.class.getDeclaredField(fieldName);
        field.setAccessible(true);
        JTextField textField = (JTextField) field.get(view);
        SwingUtilities.invokeAndWait(() -> textField.setText(value));
    }
}

