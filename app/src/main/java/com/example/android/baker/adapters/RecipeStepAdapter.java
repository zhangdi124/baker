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

        public ViewHolder(View view, View.OnClickListener onClickListener){
            super(view);

            view.setOnClickListener(onClickListener);
            mButton = view.findViewById(R.id.button);
        }

        public Button getButton() {
            return mButton;
        }

        public void setStep(int step){
            itemView.setTag(step);
        }
    }

    private List<RecipeStep> mRecipeSteps;
    private View.OnClickListener mOnClickListener;

    public RecipeStepAdapter(List<RecipeStep> recipeSteps, View.OnClickListener onClickListener){
        mRecipeSteps = recipeSteps;
        mOnClickListener = onClickListener;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        final View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.recipe_step, parent, false);

        return new RecipeStepAdapter.ViewHolder(view, mOnClickListener);
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        final RecipeStep recipeStep = mRecipeSteps.get(position);

        ViewHolder viewHolder = ((RecipeStepAdapter.ViewHolder)holder);
        final Button button = viewHolder.getButton();
        button.setText(String.format("%d) %s", (position + 1), recipeStep.getShortDescription()));

        viewHolder.setStep(position);
    }

    @Override
    public int getItemCount() {
        return mRecipeSteps.size();
    }
}
