package com.example.android.baker.viewmodel;

import android.annotation.SuppressLint;
import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;
import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;
import android.os.AsyncTask;
import android.support.annotation.NonNull;

import com.example.android.baker.model.Recipe;
import com.example.android.baker.services.RecipeService;
import com.example.android.baker.services.WebRecipeService;

import java.util.Collections;
import java.util.List;

public class MainViewModel extends AndroidViewModel {
    final private MutableLiveData<List<Recipe>> mRecipes;
    final private RecipeService mRecipeService;

    public MainViewModel(@NonNull Application application) {
        super(application);

        mRecipes = new MutableLiveData<>();
        mRecipes.setValue(Collections.<Recipe>emptyList());

        mRecipeService = new WebRecipeService();
    }

    public LiveData<List<Recipe>> getRecipes(){
        return mRecipes;
    }

    @SuppressLint("StaticFieldLeak")
    public void fetchRecipes() {
        new AsyncTask<Void, Void, List<Recipe>>() {

            @Override
            protected List<Recipe> doInBackground(Void... voids) {
                return mRecipeService.getAllRecipes();
            }

            @Override
            protected void onPostExecute(List<Recipe> recipes) {
                mRecipes.setValue(recipes);
            }
        }.execute();
    }
}
