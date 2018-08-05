package com.example.android.baker;

import android.content.Intent;
import android.support.test.espresso.NoMatchingViewException;
import android.support.test.espresso.ViewAssertion;
import android.support.test.espresso.intent.Intents;
import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.example.android.baker.mock.MockRecipe;
import com.example.android.baker.model.Recipe;
import com.google.android.exoplayer2.Player;
import com.google.android.exoplayer2.SimpleExoPlayer;
import com.google.android.exoplayer2.ui.SimpleExoPlayerView;
import com.google.gson.Gson;

import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.matcher.ViewMatchers.isAssignableFrom;
import static android.support.test.espresso.matcher.ViewMatchers.isDescendantOfA;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static android.support.test.espresso.matcher.ViewMatchers.withParent;
import static android.support.test.espresso.matcher.ViewMatchers.withParentIndex;
import static android.support.test.espresso.matcher.ViewMatchers.withText;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.AllOf.allOf;
import static org.hamcrest.core.IsNot.not;

@RunWith(AndroidJUnit4.class)
public class RecipeStepDetailTest {
    @Rule
    public ActivityTestRule<RecipeStepDetailActivity> mActivityTestRule
            = new ActivityTestRule<>(RecipeStepDetailActivity.class, false, false);

    @Test
    public void stepVideoPlays_WhenActivityStarts(){
        startWithMockIntent();
        onView(withId(R.id.player_view))
                .check(new ViewAssertion() {
                    @Override
                    public void check(View view, NoMatchingViewException noViewFoundException) {
                        final SimpleExoPlayerView playerView = (SimpleExoPlayerView)view;
                        assertThat(playerView.getPlayer().getPlaybackState(), not(Player.STATE_IDLE));
                    }
                });
    }

    @Before
    public void beforeTests(){
        Intents.init();
    }

    @After
    public void afterTests(){
        Intents.release();
    }

    private void startWithMockIntent(){
        final Recipe recipe = new MockRecipe().Get();
        final String json = new Gson().toJson(recipe);

        Intent i = new Intent();
        i.putExtra(RecipeActivityBase.RECIPE, json);
        i.putExtra(RecipeActivityBase.STEP, 0);
        mActivityTestRule.launchActivity(i);
    }
}
