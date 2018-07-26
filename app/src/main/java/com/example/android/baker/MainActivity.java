package com.example.android.baker;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.GridView;

import com.example.android.baker.adapters.RecipeSummaryAdapter;
import com.example.android.baker.model.Recipe;
import com.example.android.baker.services.JsonRecipeService;
import com.example.android.baker.services.RecipeService;

import java.util.List;

public class MainActivity extends RecipeActivityBase {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        RecipeService rs = new JsonRecipeService(this);
        List<Recipe> recipes = rs.GetAllRecipes();

        GridView gv = (GridView)findViewById(R.id.grid_view);
        gv.setAdapter(new RecipeSummaryAdapter(this, recipes));


    }
}
