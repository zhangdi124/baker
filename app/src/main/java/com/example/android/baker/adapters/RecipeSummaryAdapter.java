package com.example.android.baker.adapters;

import android.app.Activity;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.example.android.baker.R;
import com.example.android.baker.RecipeActivityBase;
import com.example.android.baker.listeners.RecipeOnClickListener;
import com.example.android.baker.model.Recipe;

import java.util.List;

public class RecipeSummaryAdapter extends ArrayAdapter<Recipe>{
    final List<Recipe> mRecipes;
    final RecipeActivityBase mActivity;

    public RecipeSummaryAdapter(RecipeActivityBase activity, List<Recipe> recipes){
        super(activity, R.layout.recipe_summary_card, recipes);
        mActivity = activity;
        mRecipes = recipes;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        final Recipe recipe = mRecipes.get(position);

        if(convertView == null){
            LayoutInflater inflater = LayoutInflater.from(getContext());
            convertView = inflater.inflate(R.layout.recipe_summary_card, parent, false);
        }

        TextView tvName = convertView.findViewById(R.id.name);
        tvName.setText(recipe.getName());

        convertView.setOnClickListener(new RecipeOnClickListener(mActivity, recipe));

        return convertView;
    }
}
