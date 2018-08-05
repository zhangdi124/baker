package com.example.android.baker;

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

import com.example.android.baker.mock.MockRecipeService;
import com.example.android.baker.services.RecipeService;

import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.matcher.ViewMatchers.isDescendantOfA;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static android.support.test.espresso.intent.Intents.intended;
import static android.support.test.espresso.intent.matcher.IntentMatchers.hasComponent;

import static android.support.test.espresso.matcher.ViewMatchers.isAssignableFrom;
import static android.support.test.espresso.matcher.ViewMatchers.withParent;
import static android.support.test.espresso.matcher.ViewMatchers.withParentIndex;
import static android.support.test.espresso.matcher.ViewMatchers.withText;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.AllOf.allOf;

@RunWith(AndroidJUnit4.class)
public class MainActivityTest {
    @Rule
    public ActivityTestRule<MainActivity> mActivityTestRule
            = new ActivityTestRule<>(MainActivity.class);

    @Before
    public void beforeTests(){
        BakerApplication.register(new MockRecipeService(), RecipeService.class);
        Intents.init();
    }

    @Test
    public void mainTitle_IsBaker(){
        final String title = "Baker";
        onView(allOf(isAssignableFrom(TextView.class), withParent(isAssignableFrom(Toolbar.class))))
                .check(matches(withText(title)));
    }

    @Test
    public void clickRecipeCard_StartRecipeDetailActivity(){
        onView(withId(R.id.recipes))
                .perform(RecyclerViewActions.actionOnItemAtPosition(0, click()));

        intended(hasComponent(RecipeDetailsActivity.class.getName()));
    }

    @Test
    public void recipeCount_MatchesService(){
        onView(withId(R.id.recipes))
                .check(new ViewAssertion() {
                    @Override
                    public void check(View view, NoMatchingViewException noViewFoundException) {
                        RecyclerView rv = (RecyclerView)view;
                        RecyclerView.Adapter adapter = rv.getAdapter();

                        assertThat(adapter.getItemCount(), is(1));
                    }
                });
    }

    @Test
    public void recipeCardTitles_MatchRecipeTitles(){
        onView(allOf(isAssignableFrom(TextView.class), withParent(withParentIndex(0)), isDescendantOfA(withId(R.id.recipes))))
                .check(matches(withText("Bittermelon Beef")));
    }

    @After
    public void afterTests(){
        Intents.release();
    }
    //    @Test
//    public void clickHomeButton_ShowRecipeSummaryCard() {
//        //find the view
//        //perform action on the view
//        onView(withId(R.id.home_page)).perform(click());
//        //check if the view does what you expected
//        onView(withId(R.id.recipe_summary_page)).check(matches(withId(R.id.recipe_summary_page)));
//    }
}
