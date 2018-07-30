package com.example.android.baker.adapters;

import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.android.baker.R;
import com.example.android.baker.model.Recipe;
import com.squareup.picasso.Picasso;

import java.util.List;

public class RecipeSummaryAdapter extends RecyclerView.Adapter{
    private class ViewHolder extends RecyclerView.ViewHolder{
        private TextView mName;
        private ImageView mPlaceholder;
        ViewHolder(View v, View.OnClickListener onClickListener){
            super(v);
            mName = v.findViewById(R.id.name);
            mPlaceholder = v.findViewById(R.id.placeholder_image);

            v.setOnClickListener(onClickListener);
        }

        public TextView getName() {
            return mName;
        }

        public ImageView getPlaceholder() {
            return mPlaceholder;
        }

        public void setRecipe(Recipe recipe){
            itemView.setTag(recipe);
            mName.setText(recipe.getName());

            if(!TextUtils.isEmpty(recipe.getImage())){
                Picasso.with(itemView.getContext())
                        .load(recipe.getImage())
                        .placeholder(R.drawable.baking_clipart)
                        .into(getPlaceholder());
            }
        }
    }

    private final List<Recipe> mRecipes;
    private final View.OnClickListener mOnClickListener;

    public RecipeSummaryAdapter(List<Recipe> recipes, View.OnClickListener onClickListener){
        mRecipes = recipes;
        mOnClickListener = onClickListener;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        final View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.recipe_summary_card, parent, false);

        return new RecipeSummaryAdapter.ViewHolder(view, mOnClickListener);
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        final Recipe recipe = mRecipes.get(position);
        final ViewHolder viewHolder = (ViewHolder)holder;
        viewHolder.setRecipe(recipe);
    }

    @Override
    public int getItemCount() {
        return mRecipes.size();
    }
}
