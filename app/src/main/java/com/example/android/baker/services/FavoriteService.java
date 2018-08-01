package com.example.android.baker.services;

import com.example.android.baker.model.Recipe;

import java.util.List;

public interface FavoriteService {
    boolean isFavoriteRecipe(int id);
    List<Recipe> getFavoriteRecipes();
    void addFavoriteRecipe (Recipe recipe);
    void unFavoriateRecipe (Recipe recipe);
}
