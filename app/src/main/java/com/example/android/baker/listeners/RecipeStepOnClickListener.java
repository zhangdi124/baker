package com.example.android.baker.listeners;

import android.view.View;

import com.example.android.baker.RecipeActivityBase;
import com.example.android.baker.model.Recipe;

public class RecipeStepOnClickListener implements View.OnClickListener{
    private final RecipeActivityBase mRecipeActivity;
    private final Recipe mRecipe;

    public RecipeStepOnClickListener(RecipeActivityBase recipeActivity, Recipe recipe){
        mRecipeActivity = recipeActivity;
        mRecipe = recipe;
    }

    @Override
    public void onClick(View view) {
        int step = (int)view.getTag();
        mRecipeActivity.navigateToRecipeStep(mRecipe, step);
    }
}
