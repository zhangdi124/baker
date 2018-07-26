package com.example.android.baker;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;

import com.example.android.baker.model.Recipe;
import com.google.gson.Gson;

public abstract class RecipeActivityBase extends AppCompatActivity {
    final protected static String RECIPE = "recipe";
    final protected static String STEP = "mStep";

    protected Recipe mRecipe = null;

    @Override
    protected void onResume() {
        super.onResume();
        final Recipe recipe = getRecipe();
        if(recipe != null)
            setTitle(recipe.getName());
    }

    protected Recipe getRecipe(){
        if(mRecipe == null){
            final Intent intent = getIntent();
            final String json = intent.getStringExtra(RECIPE);

            mRecipe = new Gson().fromJson(json, Recipe.class);
        }

        return mRecipe;
    }

    protected int getRecipeStep(){
        final Intent intent = getIntent();
        return intent.getIntExtra(STEP, 0);
    }

    public void navigateToRecipe(Recipe recipe){
        final Intent intent = new Intent(this, RecipeDetailsActivity.class);
        putRecipe(intent, recipe);

        startActivity(intent);
    }

    public void navigateToRecipeStep(Recipe recipe, int step){
        final Intent intent = new Intent(this, RecipeStepDetailActivity.class);
        putRecipe(intent, recipe);
        intent.putExtra(STEP, step);

        startActivity(intent);
    }

    protected void putRecipe(Intent intent, Recipe recipe){
        final String json = new Gson().toJson(recipe);
        intent.putExtra(RECIPE, json);
    }
}
