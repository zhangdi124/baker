package com.example.android.baker.viewmodel;

import android.annotation.SuppressLint;
import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;
import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MediatorLiveData;
import android.arch.lifecycle.MutableLiveData;
import android.arch.lifecycle.Observer;
import android.os.AsyncTask;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import com.example.android.baker.BakerApplication;
import com.example.android.baker.db.FavoriteRecipeDao;
import com.example.android.baker.db.FavoriteRecipeEntry;
import com.example.android.baker.db.RecipeDatabase;
import com.example.android.baker.model.Recipe;
import com.example.android.baker.services.RecipeService;
import com.example.android.baker.services.WebRecipeService;

import java.util.List;

public class RecipeDetailsViewModel extends AndroidViewModel {
    private final RecipeService mRecipeService;
    private final LiveData<List<FavoriteRecipeEntry>> mFavoriteRecipes;

    private final MutableLiveData<Recipe> mRecipe;
    public LiveData<Recipe> getRecipe(){ return mRecipe; }

    private final MediatorLiveData<Boolean> mIsFavorite;
    public LiveData<Boolean> getIsFavorite(){
        return mIsFavorite;
    }
    private final FavoriteRecipeDao mFavoriteRecipeDao;

    public RecipeDetailsViewModel(@NonNull Application application) {
        super(application);

        mRecipe = new MutableLiveData<>();
        mRecipeService = BakerApplication.getService(RecipeService.class);

        mFavoriteRecipeDao = RecipeDatabase.getInstance(application)
                .favoriteRecipeDao();

        mFavoriteRecipes = mFavoriteRecipeDao.getFavoriteRecipes();

        mIsFavorite = new MediatorLiveData<>();
        mIsFavorite.addSource(mFavoriteRecipes, new Observer<List<FavoriteRecipeEntry>>() {
            @Override
            public void onChanged(@Nullable List<FavoriteRecipeEntry> favoriteRecipeEntries) {
                mIsFavorite.setValue(determineIfFavorite(mRecipe.getValue(), favoriteRecipeEntries));
            }
        });

        mIsFavorite.addSource(mRecipe, new Observer<Recipe>() {
            @Override
            public void onChanged(@Nullable Recipe recipe) {
                mIsFavorite.setValue(determineIfFavorite(recipe, mFavoriteRecipes.getValue()));
            }
        });
    }

    @SuppressLint("StaticFieldLeak")
    public void loadRecipe(int id){
        new AsyncTask<Integer, Void, Recipe>(){

            @Override
            protected Recipe doInBackground(Integer... params) {
                return mRecipeService.findById(params[0]);
            }

            @Override
            protected void onPostExecute(Recipe recipe) {
                mRecipe.setValue(recipe);
            }
        }.execute(id);
    }

    public void toggleFavorite(){
        if(mRecipe.getValue() == null || mFavoriteRecipes.getValue()== null)
            return;

        final Recipe recipe = mRecipe.getValue();

        for(FavoriteRecipeEntry entry : mFavoriteRecipes.getValue()){
            if(entry.getRecipeId() == recipe.getId()){
                unfavoriteRecipe(entry);
                return;
            }
        }

        FavoriteRecipeEntry recipeEntry = new FavoriteRecipeEntry();
        recipeEntry.setName(recipe.getName());
        recipeEntry.setRecipeId(recipe.getId());

        favoriteRecipe(recipeEntry);
    }

    private boolean determineIfFavorite(Recipe recipe, List<FavoriteRecipeEntry> favoriteRecipeEntries){
        if(recipe == null || favoriteRecipeEntries == null)
            return false;

        final int id = recipe.getId();

        for(FavoriteRecipeEntry entry : favoriteRecipeEntries){
            if(entry.getRecipeId() == id)
                return true;
        }

        return false;
    }

    @SuppressLint("StaticFieldLeak")
    private void favoriteRecipe(FavoriteRecipeEntry recipeEntry){
        new AsyncTask<FavoriteRecipeEntry, Void, Void>(){

            @Override
            protected Void doInBackground(FavoriteRecipeEntry... params) {
                mFavoriteRecipeDao.addFavoriteRecipe(params[0]);
                return null;
            }
        }.execute(recipeEntry);
    }

    @SuppressLint("StaticFieldLeak")
    private void unfavoriteRecipe(FavoriteRecipeEntry recipeEntry){
        new AsyncTask<FavoriteRecipeEntry, Void, Void>(){

            @Override
            protected Void doInBackground(FavoriteRecipeEntry... params) {
                mFavoriteRecipeDao.delete(params[0]);
                return null;
            }
        }.execute(recipeEntry);
    }
}
