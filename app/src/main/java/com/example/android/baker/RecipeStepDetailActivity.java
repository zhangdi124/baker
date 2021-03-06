package com.example.android.baker;

import android.net.Uri;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.example.android.baker.adapters.RecipeStepAdapter;
import com.example.android.baker.model.Recipe;
import com.example.android.baker.model.RecipeStep;
import com.google.android.exoplayer2.C;
import com.google.android.exoplayer2.DefaultLoadControl;
import com.google.android.exoplayer2.ExoPlayerFactory;
import com.google.android.exoplayer2.LoadControl;
import com.google.android.exoplayer2.SimpleExoPlayer;
import com.google.android.exoplayer2.extractor.DefaultExtractorsFactory;
import com.google.android.exoplayer2.extractor.ExtractorsFactory;
import com.google.android.exoplayer2.source.ExtractorMediaSource;
import com.google.android.exoplayer2.source.MediaSource;
import com.google.android.exoplayer2.trackselection.AdaptiveTrackSelection;
import com.google.android.exoplayer2.trackselection.DefaultTrackSelector;
import com.google.android.exoplayer2.trackselection.TrackSelection;
import com.google.android.exoplayer2.trackselection.TrackSelector;
import com.google.android.exoplayer2.ui.SimpleExoPlayerView;
import com.google.android.exoplayer2.upstream.BandwidthMeter;
import com.google.android.exoplayer2.upstream.DataSource;
import com.google.android.exoplayer2.upstream.DefaultBandwidthMeter;
import com.google.android.exoplayer2.upstream.DefaultDataSourceFactory;
import com.google.android.exoplayer2.util.Util;
import com.squareup.picasso.Picasso;

public class RecipeStepDetailActivity extends RecipeActivityBase implements View.OnClickListener{
    final private static String BUNDLE_STEP = "STEP";
    final private static String BUNDLE_VIDEOTHUMB = "VIDEOTHUMB";
    final private static String BUNDLE_PLAYERSTATE = "PLAYERSTATE";
    protected SimpleExoPlayer mPlayer;
    protected int mStep = -1;
    protected long mVideoThumb = C.TIME_UNSET;
    protected boolean mPlayWhenReady;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.step_detail);

        setupActionToolbar();

        if(savedInstanceState != null) {
            mStep = savedInstanceState.getInt(BUNDLE_STEP, -1);
            mVideoThumb = savedInstanceState.getLong(BUNDLE_VIDEOTHUMB, C.TIME_UNSET);
            mPlayWhenReady = savedInstanceState.getBoolean(BUNDLE_PLAYERSTATE, true);
        }else{
            mVideoThumb = C.TIME_UNSET;
            mPlayWhenReady = true;
        }

        findViewById(R.id.next)
            .setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    RecipeStepDetailActivity.this.onNext();
                }
            });

        findViewById(R.id.back)
            .setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    RecipeStepDetailActivity.this.onPrevious();
                }
            });

        setupMediaPlayer();
    }

    @Override
    protected void onResume() {
        super.onResume();
        update();
    }

    @Override
    protected void onPause() {
        super.onPause();
        if (Util.SDK_INT <= 23) {
            releasePlayer();
        }
    }

    @Override
    protected void onStop() {
        super.onStop();
        if (Util.SDK_INT > 23) {
            releasePlayer();
        }
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);

        if(mStep >= 0){
            outState.putInt(BUNDLE_STEP, mStep);
        }
        if (mPlayer != null) {
            outState.putLong(BUNDLE_VIDEOTHUMB, mPlayer.getCurrentPosition());
            outState.putBoolean(BUNDLE_PLAYERSTATE, mPlayer.getPlayWhenReady());
        }
    }

    @Override
    public void onBackPressed() {
        if(--mStep < 0) {
            super.onBackPressed();
        }else{
            update();
        }
    }

    @Override
    protected int getRecipeStep() {
        if(mStep < 0)
            mStep = super.getRecipeStep();

        return mStep;
    }

    private void update(){
        final Recipe recipe = getRecipe();
        final int stepIndex = getRecipeStep();
        final RecipeStep step = recipe.getSteps().get(stepIndex);

        findViewById(R.id.back).setEnabled(stepIndex > 0);
        findViewById(R.id.next).setEnabled(stepIndex < recipe.getSteps().size() - 1);

        final TextView stepInstructions = findViewById(R.id.step_instructions);
        stepInstructions.setText(step.getDescription());

        final String videoUrl = step.getVideoURL();
        if(TextUtils.isEmpty(videoUrl)){
            findViewById(R.id.player_view).setVisibility(View.GONE);
        }else{
            findViewById(R.id.player_view).setVisibility(View.VISIBLE);
            playMedia(step.getVideoURL());
        }

        final RecyclerView steps = findViewById(R.id.steps);
        if(steps != null){
            final RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this);
            steps.setLayoutManager(layoutManager);
            steps.setAdapter(new RecipeStepAdapter(recipe.getSteps(), this));
        }

        final ImageView placeholder = findViewById(R.id.placeholder_image);
        if(placeholder != null){
            final String thumbnailUrl = step.getThumbnailURL();
            if(thumbnailUrl != null && thumbnailUrl.length() > 0){
                Picasso.with(this)
                        .load(step.getThumbnailURL())
                        .placeholder(R.drawable.baking_clipart)
                        .into(placeholder);
            }
        }
    }

    private void onNext(){
        final Recipe recipe = getRecipe();
        if(recipe == null)
            return;

        if(mStep < recipe.getSteps().size() - 1){
            ++mStep;
            update();
        }
    }

    private void onPrevious(){
        if(mStep > 0){
            --mStep;
            update();
        }
    }

    private void setupMediaPlayer(){
        final BandwidthMeter bandwidthMeter = new DefaultBandwidthMeter();
        final TrackSelection.Factory videoTrackSelectionFactory =
                new AdaptiveTrackSelection.Factory(bandwidthMeter);

        final TrackSelector trackSelector =
                new DefaultTrackSelector(videoTrackSelectionFactory);

        mPlayer = ExoPlayerFactory.newSimpleInstance(this, trackSelector);

        SimpleExoPlayerView playerView = findViewById(R.id.player_view);
        playerView.setPlayer(mPlayer);
        playerView.setKeepScreenOn(true);
    }

    private void playMedia(String path){
        final DataSource.Factory dataSourceFactory = new DefaultDataSourceFactory(this,
                Util.getUserAgent(this, "ExoPlayer"));
        final ExtractorsFactory extractorsFactory = new DefaultExtractorsFactory();

        final MediaSource videoSource = new ExtractorMediaSource(Uri.parse(path),
                dataSourceFactory, extractorsFactory, null, null);

        mPlayer.prepare(videoSource);
        mPlayer.setPlayWhenReady(mPlayWhenReady);
        mPlayer.seekTo(mVideoThumb);
    }

    private void releasePlayer(){
        if(mPlayer != null){
            mPlayer.release();
            mPlayer = null;
        }

        mPlayWhenReady = true;
        mVideoThumb = C.TIME_UNSET;
    }

    @Override
    public void onClick(View view) {
        mStep = (int)view.getTag();
        update();
    }
}
