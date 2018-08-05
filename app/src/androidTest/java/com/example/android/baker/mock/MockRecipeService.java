package com.example.android.baker.mock;

import com.example.android.baker.model.Recipe;
import com.example.android.baker.services.RecipeService;

import java.util.ArrayList;
import java.util.List;

public class MockRecipeService implements RecipeService {
    @Override
    public List<Recipe> getAllRecipes() {
        final Recipe recipe = new MockRecipe().Get();
        final List<Recipe> recipes = new ArrayList<>();
        recipes.add(recipe);

        return recipes;
    }

    @Override
    public Recipe findById(int id) {
        return new MockRecipe().Get();
    }
}
