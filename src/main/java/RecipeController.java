import java.awt.event.ActionListener;
import javax.swing.*;
import java.awt.event.ActionEvent;



public class RecipeController {

    public RecipeController(RecipeView recipeView) {
        pressLoadRecipeButton(recipeView.getLoadButton());
        pressAddRecipeButton(recipeView.getAddRecipeButton());
        pressDeleteRecipeButton(recipeView.getDeleteRecipeButton());
        pressFilterRecipeButton(recipeView.getFilterRecipeButton());
        pressUpdateRecipeButton(recipeView.getUpdateRecipeButton());
        pressDeleteAllRecipeButton(recipeView.getDeleteAllRecipeButton());
        pressBackButton(recipeView.getBackButton());
    }

    private void pressDeleteAllRecipeButton(JButton deleteAllRecipeButton) {
        deleteAllRecipeButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                RecipeModel.deleteAllRecipes(e);
            }
        });
    }

    private void pressFilterRecipeButton(JButton filterRecipeButton) {
        filterRecipeButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                RecipeModel.filterRecipes(e);
            }
        });
    }

    private void pressUpdateRecipeButton(JButton updateRecipeButton) {
        updateRecipeButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                RecipeModel.updateRecipe(e);
            }
        });
    }

    private void pressDeleteRecipeButton(JButton deleteRecipeButton) {
        deleteRecipeButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                RecipeModel.deleteRecipe(e);
            }
        });
    }

    private void pressLoadRecipeButton(JButton loadButton) {
        loadButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                RecipeModel.loadRecipes(e);
            }
        });
    }

    private void pressAddRecipeButton(JButton addRecipeButton) {
        addRecipeButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                RecipeModel.addRecipe(e);
            }
        });
    }

    private void pressBackButton(JButton backButton) {
        backButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                RecipeModel.backToMenu(e);
            }
        });
    }


}