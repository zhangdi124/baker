package com.example.android.baker.listeners;

import android.view.View;

import com.example.android.baker.RecipeActivityBase;
import com.example.android.baker.model.Recipe;

public class RecipeStepOnClickListener  implements View.OnClickListener {
    private final RecipeActivityBase mActivity;
    private final Recipe mRecipe;
    private final int mStep;

    public RecipeStepOnClickListener(RecipeActivityBase mActivity, Recipe recipe, int step){
        this.mActivity = mActivity;
        mRecipe = recipe;
        mStep = step;
    }

    @Override
    public void onClick(View view) {
        mActivity.navigateToRecipeStep(mRecipe, mStep);
    }
}
