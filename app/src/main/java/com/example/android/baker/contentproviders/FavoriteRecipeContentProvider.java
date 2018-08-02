package com.example.android.baker.contentproviders;

import android.content.ContentProvider;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import com.example.android.baker.db.FavoriteRecipeDao;
import com.example.android.baker.db.RecipeDatabase;
import com.example.android.baker.model.Recipe;

public final class FavoriteRecipeContentProvider extends ContentProvider{
    private FavoriteRecipeDao mFavoriteRecipeDao = null;

    @Override
    public boolean onCreate() {
        final Context context = getContext();
        if(context == null){
            return false;
        }

        mFavoriteRecipeDao = RecipeDatabase
                .getInstance(context)
                .favoriteRecipeDao();

        return true;
    }

    @Nullable
    @Override
    public Cursor query(@NonNull Uri uri,
                        @Nullable String[] projection, @Nullable String selection,
                        @Nullable String[] selectionArgs, @Nullable String sortOrder){
        if(mFavoriteRecipeDao == null)
            return null;

        return mFavoriteRecipeDao.selectAll();
    }

    @Nullable
    @Override
    public String getType(@NonNull Uri uri) {
        return FavoriteRecipeContract.MIME_TYPE;
    }

    @Nullable
    @Override
    public Uri insert(@NonNull Uri uri, @Nullable ContentValues contentValues) {
        return null;
    }

    @Override
    public int delete(@NonNull Uri uri, @Nullable String s, @Nullable String[] strings) {
        return 0;
    }

    @Override
    public int update(@NonNull Uri uri, @Nullable ContentValues contentValues, @Nullable String s, @Nullable String[] strings) {
        return 0;
    }
}
