package com.example.android.baker;

import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.example.android.baker.adapters.RecipeIngredientAdapter;
import com.example.android.baker.adapters.RecipeStepAdapter;
import com.example.android.baker.listeners.RecipeStepOnClickListener;
import com.example.android.baker.model.Recipe;

public class RecipeDetailsActivity extends RecipeActivityBase {
    private  RecyclerView mIngredientsRV;
    private RecyclerView mStepsRV;
    private RecyclerView.Adapter mIngredientsAdapter;
    private RecyclerView.Adapter mStepsAdapter;
    private RecyclerView.LayoutManager mIngredientsLayoutManager;
    private RecyclerView.LayoutManager mStepsLayoutManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.recipe_details);

        setupActionToolbar();

        mIngredientsRV = findViewById(R.id.ingredients);
        mIngredientsRV.setHasFixedSize(true);
        mIngredientsLayoutManager = new LinearLayoutManager(this);
        mIngredientsRV.setLayoutManager(mIngredientsLayoutManager);

        mStepsRV = findViewById(R.id.steps);
        mStepsRV.setHasFixedSize(true);
        mStepsLayoutManager = new LinearLayoutManager(this);
        mStepsRV.setLayoutManager(mStepsLayoutManager);
    }

    @Override
    protected void onResume() {
        super.onResume();
        update();
    }

    private void update(){
        final Recipe recipe = getRecipe();
        mIngredientsAdapter = new RecipeIngredientAdapter(recipe.getIngredients());
        mIngredientsRV.setAdapter(mIngredientsAdapter);

        final View.OnClickListener stepClickListener = new RecipeStepOnClickListener(this, recipe);
        mStepsAdapter = new RecipeStepAdapter(recipe.getSteps(), stepClickListener);
        mStepsRV.setAdapter(mStepsAdapter);

    }
}
