package com.example.android.baker.services;

import android.content.Context;

import com.example.android.baker.R;
import com.example.android.baker.model.Recipe;
import com.google.gson.Gson;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.Arrays;
import java.util.List;

public class JsonRecipeService implements RecipeService {
    private final Context mContext;

    public JsonRecipeService(Context context){
        mContext = context;
    }

    @Override
    public List<Recipe> GetAllRecipes() {
        final InputStream is = mContext.getResources().openRawResource(R.raw.baking);
        final BufferedReader reader = new BufferedReader(new InputStreamReader(is));

        final Gson gson = new Gson();
        final Recipe[] recipes = gson.fromJson(reader, Recipe[].class);
        return Arrays.asList(recipes);
    }
}