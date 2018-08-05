package com.example.android.baker;

import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.example.android.baker.adapters.RecipeSummaryAdapter;
import com.example.android.baker.listeners.RecipeOnClickListener;
import com.example.android.baker.model.Recipe;
import com.example.android.baker.viewmodel.MainViewModel;


import java.util.List;

public class MainActivity extends RecipeActivityBase {
    private MainViewModel mViewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        setupActionToolbar();

        mViewModel = ViewModelProviders.of(this).get(MainViewModel.class);

        final int gridSpan = getResources().getInteger(R.integer.recipe_summary_gridspan);
        final RecyclerView recipeRV = findViewById(R.id.recipes);
        final RecyclerView.LayoutManager layoutManager = new GridLayoutManager(this, gridSpan);
        recipeRV.setLayoutManager(layoutManager);

        mViewModel.getRecipes().observe(MainActivity.this, new Observer<List<Recipe>>() {
            @Override
            public void onChanged(@Nullable List<Recipe> recipes) {
                final RecipeOnClickListener onClickListener = new RecipeOnClickListener(MainActivity.this);
                final RecipeSummaryAdapter adapter = new RecipeSummaryAdapter(recipes, onClickListener);
                recipeRV.setAdapter(adapter);
            }

        });

        mViewModel.fetchRecipes();

    }

}
