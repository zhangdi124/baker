package com.example.android.baker.db;

import android.arch.lifecycle.LiveData;
import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;
import android.database.Cursor;

import java.util.List;
import static android.arch.persistence.room.OnConflictStrategy.REPLACE;


@Dao
public interface FavoriteRecipeDao {

    @Query("SELECT * FROM favorite_recipes")
    LiveData<List<FavoriteRecipeEntry>> getFavoriteRecipes();

    @Query("SELECT * FROM favorite_recipes")
    Cursor selectAll();

    @Query("SELECT * FROM favorite_recipes WHERE recipeId = :id")
    FavoriteRecipeEntry getFavoriteRecipe(int id);

    @Insert(onConflict = REPLACE)
    void addFavoriteRecipe(FavoriteRecipeEntry entry);

    @Delete
    void delete(FavoriteRecipeEntry entry);
}
