package com.example.android.baker.mock;

import com.example.android.baker.model.Ingredient;
import com.example.android.baker.model.Recipe;
import com.example.android.baker.model.RecipeStep;

import java.util.ArrayList;
import java.util.List;

public class MockRecipe extends MockBase<Recipe> {
    @Override
    public Recipe Get() {
        final Recipe recipe = new Recipe();
        recipe.setId(0);
        recipe.setName("Bittermelon Beef");

        List<Ingredient> ingredients = new ArrayList<>();
        ingredients.add(new Ingredient("Bittermelon", "LB", 1.0D));
        ingredients.add(new Ingredient("Beef", "LB", 2.0D));
        ingredients.add(new Ingredient("Soy Sauce", "Tablespoon", 3.0D));
        recipe.setIngredients(ingredients);

        List<RecipeStep> steps = new ArrayList<>();
        steps.add(new RecipeStep(0, "Cut Bittermelon", "Lorem ipsum dolor", "https://d17h27t6h515a5.cloudfront.net/topher/2017/April/58ffd9a6_2-mix-sugar-crackers-creampie/2-mix-sugar-crackers-creampie.mp4", ""));
        steps.add(new RecipeStep(1, "Cut Beef", "Lorem ipsum dolor", "https://d17h27t6h515a5.cloudfront.net/topher/2017/April/58ffd9a6_2-mix-sugar-crackers-creampie/2-mix-sugar-crackers-creampie.mp4", ""));
        steps.add(new RecipeStep(2, "Marinate Beef", "Lorem ipsum dolor", "https://d17h27t6h515a5.cloudfront.net/topher/2017/April/58ffd9a6_2-mix-sugar-crackers-creampie/2-mix-sugar-crackers-creampie.mp4", ""));
        recipe.setSteps(steps);

        return recipe;
    }
}
