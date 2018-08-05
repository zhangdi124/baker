package com.example.android.baker;

import android.content.Intent;
import android.support.test.espresso.NoMatchingViewException;
import android.support.test.espresso.ViewAssertion;
import android.support.test.espresso.contrib.RecyclerViewActions;
import android.support.test.espresso.intent.Intents;
import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.TextView;

import com.example.android.baker.mock.MockRecipe;
import com.example.android.baker.mock.MockRecipeService;
import com.example.android.baker.model.Recipe;
import com.example.android.baker.services.RecipeService;
import com.google.gson.Gson;

import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.util.ArrayList;
import java.util.List;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static android.support.test.espresso.intent.Intents.intended;
import static android.support.test.espresso.intent.matcher.IntentMatchers.hasComponent;

import static android.support.test.espresso.matcher.ViewMatchers.isAssignableFrom;
import static android.support.test.espresso.matcher.ViewMatchers.isDisplayed;
import static android.support.test.espresso.matcher.ViewMatchers.withChild;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static android.support.test.espresso.matcher.ViewMatchers.withParent;
import static android.support.test.espresso.matcher.ViewMatchers.withText;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.AllOf.allOf;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class RecipeDetailsActivityTest {
    @Rule
    public ActivityTestRule<RecipeDetailsActivity> mActivityTestRule
            = new ActivityTestRule<>(RecipeDetailsActivity.class, false, false);

    @Before
    public void beforeTests(){
        BakerApplication.register(new MockRecipeService(), RecipeService.class);
        Intents.init();
    }

    @Test
    public void TitleBar_MatchesRecipeName(){
        final Recipe recipe = startWithMockRecipe();
        onView(allOf(isAssignableFrom(TextView.class), withParent(isAssignableFrom(Toolbar.class))))
                .check(matches(withText(recipe.getName())));
    }

    @Test
    public void ingredientCount_MatchesRecipeIngredientCount(){
        final Recipe recipe = startWithMockRecipe();
        onView(withId(R.id.ingredients))
                .check(new ViewAssertion() {
                    @Override
                    public void check(View view, NoMatchingViewException noViewFoundException) {
                        RecyclerView rv = (RecyclerView)view;
                        RecyclerView.Adapter adapter = rv.getAdapter();

                        assertThat(adapter.getItemCount(), is(recipe.getIngredients().size()));
                    }
                });
    }

    @Test
    public void stepCount_MatchesRecipeStepCount(){
        final Recipe recipe = startWithMockRecipe();
        onView(withId(R.id.steps))
                .check(new ViewAssertion() {
                    @Override
                    public void check(View view, NoMatchingViewException noViewFoundException) {
                        RecyclerView rv = (RecyclerView)view;
                        RecyclerView.Adapter adapter = rv.getAdapter();

                        assertThat(adapter.getItemCount(), is(recipe.getSteps().size()));
                    }
                });
    }
    @Test
    public void clickStepCard_StartStepDetailActivity(){
        startWithMockRecipe();
        onView(withId(R.id.steps))
                .perform(RecyclerViewActions.actionOnItemAtPosition(0, click()));

        intended(hasComponent(RecipeStepDetailActivity.class.getName()));
    }

    @After
    public void afterTests(){
        Intents.release();
    }

    private Recipe startWithMockRecipe(){
        final Recipe recipe = new MockRecipe().Get();
        final String json = new Gson().toJson(recipe);

        Intent i = new Intent();
        i.putExtra(RecipeActivityBase.RECIPE, json);
        mActivityTestRule.launchActivity(i);

        return recipe;
    }
}
