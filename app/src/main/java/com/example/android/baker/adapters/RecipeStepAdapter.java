package com.example.android.baker.adapters;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.RecyclerView.Adapter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.example.android.baker.R;
import com.example.android.baker.model.RecipeStep;

import java.util.List;


public class RecipeStepAdapter extends RecyclerView.Adapter{
    private class ViewHolder extends RecyclerView.ViewHolder{
        Button mButton;

        public ViewHolder(View view){
            super(view);

            mButton = view.findViewById(R.id.button);
        }

        public Button getButton() {
            return mButton;
        }
    }

    private List<RecipeStep> mRecipeSteps;

    public RecipeStepAdapter(List<RecipeStep> recipeSteps){
        mRecipeSteps = recipeSteps;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        final View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.recipe_step, parent, false);

        return new RecipeStepAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        final RecipeStep recipeStep = mRecipeSteps.get(position);

        final Button button = ((RecipeStepAdapter.ViewHolder)holder).getButton();
        button.setText(String.format("%d) %s", (position + 1), recipeStep.getShortDescription()));
    }

    @Override
    public int getItemCount() {
        return mRecipeSteps.size();
    }
}
