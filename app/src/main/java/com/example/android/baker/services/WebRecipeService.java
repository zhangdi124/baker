package com.example.android.baker.services;

import android.util.Log;

import com.example.android.baker.model.Recipe;
import com.google.gson.Gson;

import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class WebRecipeService extends RestServiceBase implements RecipeService  {
    private static final String RECIPE_URL = "http://go.udacity.com/android-baking-app-json";
    private static List<Recipe> mAllRecipes = null;
    private static Map<Integer, Recipe> mRecipeMap = null;

    @Override
    public List<Recipe> getAllRecipes() {
        if(mAllRecipes == null || mAllRecipes.size() == 0){
            mAllRecipes = DownloadRecipes();
            mRecipeMap = new HashMap<>();
            for(Recipe recipe : mAllRecipes){
                mRecipeMap.put(recipe.getId(), recipe);
            }
        }

        return mAllRecipes;
    }

    @Override
    public Recipe findById(int id) {
        if(mRecipeMap == null || !mRecipeMap.containsKey(id))
            return null;

        return mRecipeMap.get(id);
    }

    private List<Recipe> DownloadRecipes(){
        try{
            final String json = getJSON(RECIPE_URL);
            final Recipe[] recipes = new Gson().fromJson(json, Recipe[].class);

            return Arrays.asList(recipes);
        }catch(Exception e){
            Log.e("WebRecipeService", "Could not download recipes", e);
            return Collections.emptyList();
        }
    }
}
