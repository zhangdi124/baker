package com.example.android.baker.adapters;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;

import com.example.android.baker.R;

import com.example.android.baker.model.Recipe;
import com.example.android.baker.model.RecipeStep;

import java.util.List;

public class RecipeStepAdapter  extends ArrayAdapter<RecipeStep> {
    private Recipe mRecipe;

    public RecipeStepAdapter(Context context, Recipe recipe){
        super(context, R.layout.recipe_step, recipe.getSteps());
        mRecipe = recipe;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        final RecipeStep recipeStep = mRecipe.getSteps().get(position);

        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.recipe_step, parent, false);
        }

        Button button = convertView.findViewById(R.id.button);
        button.setText(String.format("%d - %s", (position + 1), recipeStep.getShortDescription()));

        return convertView;
    }
}
