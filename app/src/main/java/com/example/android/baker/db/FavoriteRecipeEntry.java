package com.example.android.baker.db;
import com.example.android.baker.model.Recipe;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;

import io.reactivex.annotations.NonNull;

@Entity(tableName = "favorite_recipes")
public class FavoriteRecipeEntry {
    public FavoriteRecipeEntry(){

    }
    public FavoriteRecipeEntry(int recipeId, String name){
        this.recipeId = recipeId;
        this.name = name;
    }

    public FavoriteRecipeEntry(Recipe recipe){ this(recipe.getId(), recipe.getName());}

    @NonNull
    @PrimaryKey
    private int recipeId;
    private String name;

    public int getRecipeId() { return recipeId; }

    public void setRecipeId(int recipeId) { this.recipeId = recipeId; }

    public String getName() { return name; }

    public void setName(String name) { this.name = name; }
}
