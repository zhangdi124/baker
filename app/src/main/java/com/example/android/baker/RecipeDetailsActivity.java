package com.example.android.baker;

import android.os.Bundle;
import android.os.PersistableBundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import com.example.android.baker.adapters.RecipeIngredientAdapter;
import com.example.android.baker.adapters.RecipeStepAdapter;
import com.example.android.baker.model.Recipe;

public class RecipeDetailsActivity extends RecipeActivityBase {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.recipe_details);
    }

    @Override
    protected void onResume() {
        super.onResume();
        update();
    }

    private void update(){
        final Recipe recipe = getRecipe();
        final ListView ingredients = findViewById(R.id.ingredients);
        ingredients.setAdapter(new RecipeIngredientAdapter(this, recipe.getIngredients()));

        final ListView steps = findViewById(R.id.steps);
        steps.setAdapter(new RecipeStepAdapter(this, recipe));
        steps.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                RecipeDetailsActivity.this.navigateToRecipeStep(recipe, i);
            }
        });
    }
}
