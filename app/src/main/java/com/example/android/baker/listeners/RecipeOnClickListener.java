package com.example.android.baker.listeners;

import android.content.Intent;
import android.view.View;

import com.example.android.baker.RecipeActivityBase;
import com.example.android.baker.RecipeDetailsActivity;
import com.example.android.baker.model.Recipe;
import com.google.gson.Gson;

public class RecipeOnClickListener implements View.OnClickListener {
    private final RecipeActivityBase mActivity;
    private final Recipe mRecipe;

    public RecipeOnClickListener(RecipeActivityBase mActivity, Recipe recipe){
        this.mActivity = mActivity;
        mRecipe = recipe;
    }

    @Override
    public void onClick(View view) {
        mActivity.navigateToRecipe(mRecipe);
    }
}
