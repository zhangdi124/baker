package com.example.android.baker.listeners;

import android.view.View;

import com.example.android.baker.RecipeActivityBase;
import com.example.android.baker.model.Recipe;

public class RecipeOnClickListener implements View.OnClickListener {
    private final RecipeActivityBase mRecipeActivity;

    public RecipeOnClickListener(RecipeActivityBase recipeActivity){
        mRecipeActivity = recipeActivity;
    }

    @Override
    public void onClick(View view) {
        final Recipe recipe = (Recipe)view.getTag();
        mRecipeActivity.navigateToRecipe(recipe);
    }
}
