package com.example.android.baker;

import android.app.Application;

import com.example.android.baker.services.RecipeService;
import com.example.android.baker.services.WebRecipeService;

import java.util.HashMap;
import java.util.Map;

public class BakerApplication extends Application {
    private static boolean isTestConfiguration = false;

    private static Map<Class, Object> IMPL_MAP = new HashMap<>();
    private static Map<Class, Object> TEST_IMPL_MAP = new HashMap<>();

    static{
        register(new WebRecipeService(), RecipeService.class);
    }

    @Override
    public void onCreate() {
        super.onCreate();
    }

    public static <T> void register(T impl, Class<T> classOfT){
        IMPL_MAP.put(classOfT, impl);
    }

    public static <T> T getService(Class<T> classOfT){
        if(IMPL_MAP.containsKey(classOfT)){
            return (T)IMPL_MAP.get(classOfT);
        }

        return null;
    }
}
