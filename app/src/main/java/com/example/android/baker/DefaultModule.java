package com.example.android.baker;

import com.example.android.baker.services.RecipeService;
import com.example.android.baker.services.WebRecipeService;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

@Module
public class DefaultModule {
    @Provides
    @Singleton
    static RecipeService provideRecipeService() {
        return new WebRecipeService();
    }
}
