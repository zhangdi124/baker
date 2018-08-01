package com.example.android.baker;

import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;

import com.example.android.baker.adapters.RecipeIngredientAdapter;
import com.example.android.baker.adapters.RecipeStepAdapter;
import com.example.android.baker.listeners.RecipeStepOnClickListener;
import com.example.android.baker.model.Recipe;
import com.example.android.baker.viewmodel.RecipeDetailsViewModel;

public class RecipeDetailsActivity extends RecipeActivityBase {
    private RecipeDetailsViewModel mViewModel;

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

        mViewModel = ViewModelProviders.of(this).get(RecipeDetailsViewModel.class);

        setupActionToolbar();

        //Setup the View components

        mIngredientsRV = findViewById(R.id.ingredients);
        mIngredientsRV.setHasFixedSize(true);
        mIngredientsLayoutManager = new LinearLayoutManager(this);
        mIngredientsRV.setLayoutManager(mIngredientsLayoutManager);

        mStepsRV = findViewById(R.id.steps);
        mStepsRV.setHasFixedSize(true);
        mStepsLayoutManager = new LinearLayoutManager(this);
        mStepsRV.setLayoutManager(mStepsLayoutManager);


        //bind to view model
        mViewModel.getRecipe().observe(RecipeDetailsActivity.this, new Observer<Recipe>() {
            @Override
            public void onChanged(@Nullable Recipe recipe) {
                update(recipe);
            }
        });

        mViewModel.getIsFavorite().observe(RecipeDetailsActivity.this, new Observer<Boolean>() {
            @Override
            public void onChanged(@Nullable Boolean isFavorite) {
                onFavoriteChanged(isFavorite);
            }
        });

        //click events
        findViewById(R.id.favorite_button)
                .setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        RecipeDetailsActivity.this.mViewModel.toggleFavorite();
                    }
                });
    }

    @Override
    protected void onResume() {
        super.onResume();

        final Recipe recipe = getRecipe();
        mViewModel.loadRecipe(recipe.getId());
    }

    private void update(Recipe recipe){
        mIngredientsAdapter = new RecipeIngredientAdapter(recipe.getIngredients());
        mIngredientsRV.setAdapter(mIngredientsAdapter);

        final View.OnClickListener stepClickListener = new RecipeStepOnClickListener(this, recipe);
        mStepsAdapter = new RecipeStepAdapter(recipe.getSteps(), stepClickListener);
        mStepsRV.setAdapter(mStepsAdapter);
    }

    private void onFavoriteChanged(boolean isFavorite){
        final ImageButton favoriteButton = findViewById(R.id.favorite_button);

        final int resId = isFavorite ? R.drawable.ic_favorite_black_24dp : R.drawable.ic_favorite_border_black_24dp;
        favoriteButton.setImageResource(resId);
    }
}
