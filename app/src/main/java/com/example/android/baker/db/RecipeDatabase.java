package com.example.android.baker.db;

import android.arch.persistence.room.Database;
import android.arch.persistence.room.Room;
import android.arch.persistence.room.RoomDatabase;
import android.content.Context;

@Database(entities = {FavoriteRecipeEntry.class}, version = 1)
public abstract class RecipeDatabase extends RoomDatabase {
    private static final String DBNAME = "recipe_database";
    private static RecipeDatabase INSTANCE;

    public abstract FavoriteRecipeDao favoriteRecipeDao();

    public static RecipeDatabase getInstance(Context context) {
        if (INSTANCE == null) {
            INSTANCE = Room
                    .databaseBuilder(context.getApplicationContext(), RecipeDatabase.class,DBNAME)
                    .build();
        }

        return INSTANCE;
    }
}
