package com.example.android.baker.adapters;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.example.android.baker.R;
import com.example.android.baker.model.Ingredient;
import com.example.android.baker.model.Recipe;

import java.util.List;

public class RecipeIngredientAdapter extends ArrayAdapter<Ingredient> {
    private List<Ingredient> mIngredients;
    private Context mContext;

    public RecipeIngredientAdapter(Context context, List<Ingredient> ingredients){
        super(context, R.layout.recipe_ingredient, ingredients);
        mContext = context;
        mIngredients = ingredients;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        final Ingredient ingredient = mIngredients.get(position);

        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.recipe_ingredient, parent, false);
        }

        final TextView tv = convertView.findViewById(R.id.caption);
        final String caption = ingredient.toString();
        tv.setText(caption);

        return convertView;
    }
}
