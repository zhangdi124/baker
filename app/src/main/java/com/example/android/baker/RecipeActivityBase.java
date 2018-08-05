package com.example.android.baker;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;

import com.example.android.baker.model.Recipe;
import com.google.gson.Gson;

public abstract class RecipeActivityBase extends AppCompatActivity {
    final public static String RECIPE = "recipe";
    final public static String STEP = "mStep";

    protected Recipe mRecipe = null;

    //For toolbars and related UI
    protected DrawerLayout mDrawerLayout;

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

    protected void setupActionToolbar(){
        final Toolbar toolbar = findViewById(R.id.toolbar);
        if(toolbar != null){
            setSupportActionBar(toolbar);
            final ActionBar actionbar = getSupportActionBar();
            actionbar.setDisplayHomeAsUpEnabled(true);
            actionbar.setHomeAsUpIndicator(R.drawable.ic_menu);

            setupDrawer();
        }
    }

    protected void setupDrawer(){
        mDrawerLayout = findViewById(R.id.drawer_layout);
        if(mDrawerLayout == null)
            return;

        final ActionBarDrawerToggle actionBarDrawerToggle
                = new ActionBarDrawerToggle(this,
                mDrawerLayout,
                R.string.open,
                R.string.close);

        mDrawerLayout.addDrawerListener(actionBarDrawerToggle);
        actionBarDrawerToggle.syncState();
    }

    public boolean onOptionsItemSelected(MenuItem item){
        switch (item.getItemId()) {
            case android.R.id.home:
                if(mDrawerLayout != null)
                    mDrawerLayout.openDrawer(GravityCompat.START);
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    //MENU CALLBACKS
    public void onHomeClick(MenuItem item) {
        if(this instanceof MainActivity){
            return;
        }

        final Intent intent = new Intent(this, MainActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(intent);
    }
}
