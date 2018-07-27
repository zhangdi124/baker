package com.example.android.baker.adapters;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import com.example.android.baker.R;
import com.example.android.baker.model.Ingredient;
import java.util.List;


public class RecipeIngredientAdapter extends RecyclerView.Adapter{
    private class ViewHolder extends RecyclerView.ViewHolder{
        TextView mCaption;

        public ViewHolder(View view){
            super(view);

            mCaption = view.findViewById(R.id.caption);
        }

        public TextView getCaption(){
            return mCaption;
        }
    }

    private List<Ingredient> mIngredients;

    public RecipeIngredientAdapter(List<Ingredient> ingredients){
        mIngredients = ingredients;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        final View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.recipe_ingredient, parent, false);

        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        final Ingredient ingredient = mIngredients.get(position);
        ((ViewHolder)holder).getCaption().setText(ingredient.toString());
    }

    @Override
    public int getItemCount() {
        return mIngredients.size();
    }
}
