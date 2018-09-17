package com.gofar.goplayer;

import android.content.pm.ActivityInfo;
import android.content.res.Configuration;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.google.android.exoplayer2.DefaultLoadControl;
import com.google.android.exoplayer2.DefaultRenderersFactory;
import com.google.android.exoplayer2.ExoPlayerFactory;
import com.google.android.exoplayer2.LoadControl;
import com.google.android.exoplayer2.SimpleExoPlayer;
import com.google.android.exoplayer2.source.ExtractorMediaSource;
import com.google.android.exoplayer2.source.MediaSource;
import com.google.android.exoplayer2.trackselection.AdaptiveTrackSelection;
import com.google.android.exoplayer2.trackselection.DefaultTrackSelector;
import com.google.android.exoplayer2.trackselection.TrackSelection;
import com.google.android.exoplayer2.ui.PlayerView;
import com.google.android.exoplayer2.upstream.DataSource;
import com.google.android.exoplayer2.upstream.DefaultBandwidthMeter;
import com.google.android.exoplayer2.upstream.DefaultDataSourceFactory;
import com.google.android.exoplayer2.util.Util;

/**
 * @author lcf
 * @date 27/8/2018 下午 3:43
 * @since 1.0
 */
public class PlayDetailsActivity extends AppCompatActivity {
    private Toolbar mToolbar;
    private PlayerView mPlayerView;
    private ImageView mIvFullscreen;
    private FrameLayout mFlPlayer;
    private FrameLayout mFlPlayerContent;
    private SimpleExoPlayer mPlayer;
    private LinearLayout mLlContent;
    private MediaSource mMediaSource;
    private Video mVideo;

    private int mShowFlags;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mShowFlags=getWindow().getDecorView().getSystemUiVisibility();

        setContentView(R.layout.activity_play_details);
        mVideo = getIntent().getParcelableExtra("data");
        if (mVideo == null) {
            return;
        }

        mToolbar = findViewById(R.id.toolbar);
        mPlayerView = findViewById(R.id.player_view);
        mIvFullscreen = findViewById(R.id.iv_fullscreen);
        mFlPlayer = findViewById(R.id.fl_player);
        mLlContent = findViewById(R.id.ll_content);
        mFlPlayerContent = findViewById(R.id.fl_player_content);

        setSupportActionBar(mToolbar);
        setOrientationParams(false);

        mIvFullscreen.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (getRequestedOrientation() == ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE) {
                    setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_USER_PORTRAIT);
                    //exitFullScreen();
                } else {
                    setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
                    //enterFullScreen();
                }
            }
        });
    }

    @Override
    public void onWindowFocusChanged(boolean hasFocus) {
        super.onWindowFocusChanged(hasFocus);
        if (hasFocus && getRequestedOrientation() == ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE
                && Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                    | View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
                    | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                    | View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
                    | View.SYSTEM_UI_FLAG_FULLSCREEN
                    | View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY);
        } else {
            getWindow().getDecorView().setSystemUiVisibility(mShowFlags);
        }
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        setOrientationParams(newConfig.orientation == Configuration.ORIENTATION_LANDSCAPE);
        super.onConfigurationChanged(newConfig);
    }

    private void initializePlayer() {
        if (mPlayer == null) {
            DefaultRenderersFactory renderersFactory = new DefaultRenderersFactory(this);
            DefaultBandwidthMeter bandwidthMeter = new DefaultBandwidthMeter();
            TrackSelection.Factory factory = new AdaptiveTrackSelection.Factory(bandwidthMeter);
            DefaultTrackSelector trackSelector = new DefaultTrackSelector(factory);
            LoadControl loadControl = new DefaultLoadControl();
            mPlayer = ExoPlayerFactory.newSimpleInstance(renderersFactory, trackSelector, loadControl);
            mPlayerView.setPlayer(mPlayer);
            DataSource.Factory dataSourceFactory = new DefaultDataSourceFactory(this,
                    Util.getUserAgent(this, BuildConfig.APPLICATION_ID));
            mMediaSource = new ExtractorMediaSource.Factory(dataSourceFactory).createMediaSource(Uri.parse(mVideo.getPath()));
        }
        mPlayer.prepare(mMediaSource);
    }

    private void setOrientationParams(boolean isLandscape) {
        if (isLandscape) {
            mToolbar.setVisibility(View.GONE);
            mLlContent.setVisibility(View.GONE);
            LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT
                    , ViewGroup.LayoutParams.MATCH_PARENT);
            lp.height = getResources().getDisplayMetrics().heightPixels;
            mFlPlayer.setLayoutParams(lp);
            mPlayerView.requestFocus();
        } else {
            mToolbar.setVisibility(View.VISIBLE);
            mLlContent.setVisibility(View.VISIBLE);
            LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT
                    , ViewGroup.LayoutParams.WRAP_CONTENT);
            lp.height = getResources().getDisplayMetrics().widthPixels * 9 / 16;
            mFlPlayer.setLayoutParams(lp);
            mFlPlayer.requestFocus();
        }
    }

    private void enterFullScreen() {
        mToolbar.setVisibility(View.GONE);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
        ViewGroup contentView = findViewById(android.R.id.content);
        mFlPlayer.removeView(mFlPlayerContent);
        ViewGroup.LayoutParams lp = new ViewGroup.LayoutParams(
                ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.MATCH_PARENT);
        contentView.addView(mFlPlayerContent,lp);
    }

    private void exitFullScreen(){
        mToolbar.setVisibility(View.VISIBLE);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        ViewGroup contentView = findViewById(android.R.id.content);
        contentView.removeView(mFlPlayerContent);
        ViewGroup.LayoutParams lp = new ViewGroup.LayoutParams(
                ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.MATCH_PARENT);
        mFlPlayer.addView(mFlPlayerContent,lp);
    }

    private void releasePlayer() {
        if (mPlayer != null) {
            mPlayer.release();
            mPlayer = null;
        }
    }

    @Override
    protected void onStart() {
        super.onStart();
        if (Util.SDK_INT > 23) {
            initializePlayer();
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (Util.SDK_INT <= 23 && mPlayer == null) {
            initializePlayer();
        }
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
}
