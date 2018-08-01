package com.example.android.baker.services;

import android.app.Application;
import android.arch.lifecycle.LiveData;

import com.example.android.baker.db.FavoriteRecipeDao;
import com.example.android.baker.db.FavoriteRecipeEntry;
import com.example.android.baker.db.RecipeDatabase;
import com.example.android.baker.model.Recipe;

import java.util.LinkedList;
import java.util.List;

public class SQLiteFavoritesService implements FavoriteService {
    private final Application mApplication;

    public SQLiteFavoritesService(Application application) {
        mApplication= application;

    }

    @Override
    public boolean isFavoriteRecipe(int id) {
        FavoriteRecipeEntry entry = getFavoriteRecipeDao()
                .getFavoriteRecipe(id);
        return  entry != null;
    }


    @Override
    public List<Recipe> getFavoriteRecipes() {
//        LiveData<List<FavoriteRecipeEntry>> LiveData = getFavoriteRecipeDao()
//                .getFavoriteRecipes();
//
//        List<FavoriteRecipeEntry> favoriteRecipeEntries = LiveData.getValue();
//
//        List<Recipe> favoriteRecipes = new LinkedList<>();
//        for (FavoriteRecipeEntry entry : favoriteRecipeEntries) {
//            Recipe r = mRecipeService.findById(entry.getRecipeId());
//            favoriteRecipes.add(r);
//        }
//        return favoriteRecipes;
        return null;
    }

    @Override
    public void addFavoriteRecipe(Recipe recipe) {
        FavoriteRecipeEntry entry = new FavoriteRecipeEntry(recipe);
        getFavoriteRecipeDao().addFavoriteRecipe(entry);
    }

    @Override
    public void unFavoriateRecipe(Recipe recipe) {
        FavoriteRecipeEntry entry = new FavoriteRecipeEntry(recipe);
        getFavoriteRecipeDao().delete(entry);
    }

    private FavoriteRecipeDao getFavoriteRecipeDao() {
        return RecipeDatabase
                .getInstance(mApplication)
                .favoriteRecipeDao();
    }
}
