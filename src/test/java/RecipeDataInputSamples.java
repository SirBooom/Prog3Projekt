import java.util.Map;

public class RecipeDataInputSamples {



    public static Map<String,String> emptyInput() {
        return Map.of(
                "id", "",
                "name", "",
                "cuisine", "",
                "category", "",
                "instructions", "",
                "nutrition", "",
                "cookingTime", "",
                "ingredient", ""
        );
    }

    public static Map<String,String> fullRecipe1() {
        return Map.of(
                "id", "1",
                "name", "Margherita Pizza",
                "cuisine", "Italian",
                "category", "Main Course",
                "instructions", "Bake the pizza until the cheese melts.",
                "nutrition", "800",
                "cookingTime", "15",
                "ingredient", "Dough, Tomato Sauce, Mozzarella Cheese, Basil"
        );
    }

    public static Map<String,String> fullRecipe1DifferentCuisine() {
        return Map.of(
                "id", "1",
                "name", "Margherita Pizza",
                "cuisine", "Spanish",
                "category", "Main Course",
                "instructions", "Bake the pizza until the cheese melts.",
                "nutrition", "800",
                "cookingTime", "15",
                "ingredient", "Dough, Tomato Sauce, Mozzarella Cheese, Basil"
        );
    }

    public static Map<String,String> fullRecipe2() {
        return Map.of(
                "id", "2",
                "name", "Caesar Salad",
                "cuisine", "American",
                "category", "Appetizer",
                "instructions", "Mix lettuce with Caesar dressing and sprinkle croutons.",
                "nutrition", "250",
                "cookingTime", "10",
                "ingredient", "Romaine Lettuce, Caesar Dressing, Croutons, Parmesan Cheese"
        );
    }

    public static Map<String,String> fullRecipe3() {
        return Map.of(
                "id", "3",
                "name", "Grilled Cheese Sandwich",
                "cuisine", "American",
                "category", "Snack",
                "instructions", "Grill the sandwich.",
                "nutrition", "300",
                "cookingTime", "5",
                "ingredient", "Bread, Cheese"
        );
    }

    public static Map<String,String> IdInput() {
        return Map.of(
                "id", "4",
                "name", "",
                "cuisine", "",
                "category", "",
                "instructions", "",
                "nutrition", "",
                "cookingTime", "",
                "ingredient", ""
        );
    }

    public static Map<String,String> nameInput() {
        return Map.of(
                "id", "1",
                "name", "Spaghetti",
                "cuisine", "",
                "category", "",
                "instructions", "",
                "nutrition", "",
                "cookingTime", "",
                "ingredient", ""
        );
    }

    public static Map<String,String> cuisineInput() {
        return Map.of(
                "id", "2",
                "name", "",
                "cuisine", "Italien",
                "category", "",
                "instructions", "",
                "nutrition", "",
                "cookingTime", "",
                "ingredient", ""
        );
    }

    public static Map<String,String> categoryInput() {
        return Map.of(
                "id", "3",
                "name", "",
                "cuisine", "",
                "category", "Main Course",
                "instructions","",
                "nutrition", "",
                "cookingTime", "",
                "ingredient", ""
        );
    }

    public static Map<String,String> instructionsInput() {
        return Map.of(
                "id", "4",
                "name", "",
                "cuisine", "",
                "category", "",
                "instructions", """
                        1. Preheat the oven to 475°F (245°C).
                        2. Roll out the pizza dough on a floured surface.
                        3. Spread the tomato sauce evenly over the dough.
                        4. Add fresh mozzarella slices and a few basil leaves.
                        5. Bake in the preheated oven for 10-15 minutes or until the cheese is melted and bubbly.
                        """,
                "nutrition", "",
                "cookingTime", "",
                "ingredient", ""
        );
    }

    public static Map<String,String> nutritionInput() {
        return Map.of(
                "id", "1",
                "name", "",
                "cuisine", "",
                "category", "",
                "instructions", "",
                "nutrition", "500 Kcal",
                "cookingTime", "",
                "ingredient", ""
        );
    }

    public static Map<String,String> cookingTimeInput() {
        return Map.of(
                "id", "2",
                "name", "",
                "cuisine", "",
                "category", "",
                "instructions", "",
                "nutrition", "",
                "cookingTime", "30 minutes",
                "ingredient", ""
        );
    }

    public static Map<String,String> ingredientInput() {
        return Map.of(
                "id", "3",
                "name", "",
                "cuisine", "",
                "category", "",
                "instructions", "",
                "nutrition", "",
                "cookingTime", "",
                "ingredient", "1 ball (250g) of Dough\n 1/4 cup Tomato Sauce\n 150g Fresh Mozzarella\n 4-5 Fresh Basil Leaves\n 1 tbsp Olive Oil\n A pinch of Salt"
        );
    }

    public static Map<String,String> existingID() {
        return Map.of(
                "id", "3",
                "name", "",
                "cuisine", "",
                "category", "",
                "instructions", "",
                "nutrition", "",
                "cookingTime", "",
                "ingredient", ""
        );
    }

    public static Map<String,String> IDAndNameInput() {
        return Map.of(
                "id", "6",
                "name", "Pasta",
                "cuisine", "",
                "category", "",
                "instructions", "",
                "nutrition", "",
                "cookingTime", "",
                "ingredient", ""
        );
    }

    public static Map<String,String> negativeID() {
        return Map.of(
                "id", "-6",
                "name", "",
                "cuisine", "",
                "category", "",
                "instructions", "",
                "nutrition", "",
                "cookingTime", "",
                "ingredient", ""
        );
    }

}
