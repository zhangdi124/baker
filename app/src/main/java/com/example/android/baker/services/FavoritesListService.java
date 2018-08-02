package com.example.android.baker.services;

import android.appwidget.AppWidgetManager;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.os.Binder;
import android.util.Log;
import android.widget.RemoteViews;
import android.widget.RemoteViewsService;

import com.example.android.baker.R;
import com.example.android.baker.contentproviders.FavoriteRecipeContract;
import com.example.android.baker.db.FavoriteRecipeDao_Impl;
import com.example.android.baker.db.FavoriteRecipeEntry;
import com.example.android.baker.db.RecipeDatabase;
import com.example.android.baker.model.Ingredient;
import com.example.android.baker.model.Recipe;

import java.util.ArrayList;
import java.util.List;

public class FavoritesListService extends RemoteViewsService {
    @Override
    public RemoteViewsFactory onGetViewFactory(Intent intent) {
        return new FavoritesRemoteViewsFactory(this, intent);
    }
}

class FavoritesRemoteViewsFactory implements RemoteViewsService.RemoteViewsFactory{
    final private Context mContext;
    final private int mAppWidgetId;

    final private RecipeService mRecipeService;

    private List<Recipe> mFavoriteRecipes = null;

    public FavoritesRemoteViewsFactory(Context context, Intent intent){
        mContext = context;
        mAppWidgetId = intent.getIntExtra(AppWidgetManager.EXTRA_APPWIDGET_ID,
                AppWidgetManager.INVALID_APPWIDGET_ID);

        mRecipeService = new WebRecipeService();
    }

    @Override
    public void onCreate() {

    }

    @Override
    public void onDataSetChanged() {
        fetchFavoriteRecipes();
    }

    @Override
    public void onDestroy() {
        if(mFavoriteRecipes != null)
            mFavoriteRecipes.clear();
    }

    @Override
    public int getCount() {
        if(mFavoriteRecipes == null)
            return 0;

        return mFavoriteRecipes.size();
    }

    @Override
    public RemoteViews getViewAt(int i) {
        final Recipe recipe = mFavoriteRecipes.get(i);
        final RemoteViews rv = new RemoteViews(mContext.getPackageName(), R.layout.favorite_recipe_entry);
        rv.setTextViewText(R.id.caption, recipe.getName());

        final StringBuilder sb = new StringBuilder();
        for(Ingredient ingredient: recipe.getIngredients()){
            if(sb.length() > 0)
                sb.append(", ");

            sb.append(ingredient.getName());
        }
        rv.setTextViewText(R.id.ingredients, sb.toString());

        return rv;
    }

    @Override
    public RemoteViews getLoadingView() {
        return null;
    }

    @Override
    public int getViewTypeCount() {
        return 1;
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public boolean hasStableIds() {
        return true;
    }

    private void fetchFavoriteRecipes(){
        final List<Recipe> recipes = mRecipeService.getAllRecipes();
        final long identityToken = Binder.clearCallingIdentity();

        final Cursor cursor = mContext.getContentResolver()
                .query(FavoriteRecipeContract.CONTENT_URI, null,null, null, null);

        final List<Integer> favoriteEntries = new ArrayList<>();

        if(cursor.moveToFirst()){
            do{
                int entry = cursor.getInt(cursor.getColumnIndex("recipeId"));
                favoriteEntries.add(entry);
            }while(cursor.moveToNext());
        }

        mFavoriteRecipes = new ArrayList<>();
        for(Recipe recipe : recipes){
            for(int entry : favoriteEntries){
                if(entry == recipe.getId())
                    mFavoriteRecipes.add(recipe);
            }
        }
    }
}
