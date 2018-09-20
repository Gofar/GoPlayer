package com.gofar.goplayer;

import android.content.pm.ActivityInfo;
import android.graphics.Color;
import android.graphics.SurfaceTexture;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Gravity;
import android.view.Surface;
import android.view.TextureView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageButton;
import android.widget.ImageView;

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
import com.google.android.exoplayer2.upstream.DataSource;
import com.google.android.exoplayer2.upstream.DefaultBandwidthMeter;
import com.google.android.exoplayer2.upstream.DefaultDataSourceFactory;
import com.google.android.exoplayer2.util.Util;

/**
 * @author lcf
 * @date 17/9/2018 下午 3:32
 * @since 1.0
 */
public class PlayActivity extends AppCompatActivity implements TextureView.SurfaceTextureListener {
    private static final int MODE_NORMAL = 0;
    private static final int MODE_FULL_SCREEN = 1;

    private Toolbar mToolbar;
    private FrameLayout mFlPlayer;
    private TextureView mTextureView;
    private Surface mSurface;
    private SurfaceTexture mSurfaceTexture;
    private FrameLayout mPortraitContainer;
    private FrameLayout mLandScapeContainer;
    private SimpleExoPlayer mPlayer;
    private MediaSource mMediaSource;
    private Video mVideo;
    private int mMode = MODE_NORMAL;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mVideo = getIntent().getParcelableExtra("data");
        if (mVideo == null) {
            return;
        }
        setContentView(R.layout.activity_play);
        mToolbar = findViewById(R.id.toolbar);
        setSupportActionBar(mToolbar);
        mToolbar.setTitle("player");
        mFlPlayer = findViewById(R.id.fl_player);
        mFlPlayer.setBackgroundColor(Color.BLACK);

        init();
    }

    private void init() {
        mPortraitContainer = new FrameLayout(this);
        mPortraitContainer.setBackgroundColor(Color.BLACK);
        FrameLayout.LayoutParams lp = new FrameLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.MATCH_PARENT);
        mFlPlayer.addView(mPortraitContainer, lp);

        ImageButton imageButton = new ImageButton(this);
        imageButton.setImageResource(R.drawable.ic_fullscreen_white_24dp);
        imageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mMode == MODE_FULL_SCREEN) {
                    exitFullScreen();
                } else {
                    enterFullScreen();
                }
            }
        });
        FrameLayout.LayoutParams lp1 = new FrameLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT,
                ViewGroup.LayoutParams.WRAP_CONTENT);
        lp1.gravity = Gravity.BOTTOM | Gravity.RIGHT;
        mPortraitContainer.addView(imageButton, lp1);


        ImageView ivPlayer = new ImageView(this);
        ivPlayer.setImageResource(R.drawable.ic_play_circle_filled_black_24dp);
        ivPlayer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mPlayer != null) {
                    mPlayer.setPlayWhenReady(true);
                    v.setVisibility(View.GONE);
                }
            }
        });
        FrameLayout.LayoutParams lp2 = new FrameLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT,
                ViewGroup.LayoutParams.WRAP_CONTENT);
        lp2.gravity = Gravity.CENTER;
        mFlPlayer.addView(ivPlayer, lp2);
    }


    @Override
    protected void onStart() {
        super.onStart();
        if (Util.SDK_INT > 23) {
            initializePlayer();
            addTextureView();
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (Util.SDK_INT <= 23 && mPlayer == null) {
            initializePlayer();
            addTextureView();
        }
    }

    @Override
    protected void onPause() {
        super.onPause();
        if (Util.SDK_INT > 23) {
            releasePlayer();
        }
    }

    @Override
    protected void onStop() {
        super.onStop();
        if (Util.SDK_INT <= 23) {
            releasePlayer();
        }
    }

    private void addTextureView() {
        if (mTextureView == null) {
            mTextureView = new TextureView(this);
            mTextureView.setSurfaceTextureListener(this);
        }
        mPortraitContainer.removeView(mTextureView);
        FrameLayout.LayoutParams lp = new FrameLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT
                , ViewGroup.LayoutParams.MATCH_PARENT);
        mPortraitContainer.addView(mTextureView, 0, lp);
    }

    private void initializePlayer() {
        if (mPlayer == null) {
            DefaultRenderersFactory renderersFactory = new DefaultRenderersFactory(this);
            DefaultBandwidthMeter bandwidthMeter = new DefaultBandwidthMeter();
            TrackSelection.Factory factory = new AdaptiveTrackSelection.Factory(bandwidthMeter);
            DefaultTrackSelector trackSelector = new DefaultTrackSelector(factory);
            LoadControl loadControl = new DefaultLoadControl();
            mPlayer = ExoPlayerFactory.newSimpleInstance(renderersFactory, trackSelector, loadControl);
            DataSource.Factory dataSourceFactory = new DefaultDataSourceFactory(this,
                    Util.getUserAgent(this, BuildConfig.APPLICATION_ID));
            mMediaSource = new ExtractorMediaSource.Factory(dataSourceFactory).createMediaSource(Uri.parse(mVideo.getPath()));
        }
    }

    @Override
    public void onSurfaceTextureAvailable(SurfaceTexture surface, int width, int height) {
        if (mSurfaceTexture == null) {
            mSurfaceTexture = surface;
            openMediaPlayer();
        } else {
            mTextureView.setSurfaceTexture(mSurfaceTexture);
        }
    }

    @Override
    public void onSurfaceTextureSizeChanged(SurfaceTexture surface, int width, int height) {

    }

    @Override
    public boolean onSurfaceTextureDestroyed(SurfaceTexture surface) {
        return mSurfaceTexture == null;
    }

    @Override
    public void onSurfaceTextureUpdated(SurfaceTexture surface) {

    }

    private void openMediaPlayer() {
        mPortraitContainer.setKeepScreenOn(true);
        if (mSurface == null) {
            mSurface = new Surface(mSurfaceTexture);
        }
        mPlayer.setVideoSurface(mSurface);
        mPlayer.prepare(mMediaSource);
    }

    private void stopPlayer() {
        if (mPlayer != null) {
            mPlayer.stop();
        }
    }

    private void releasePlayer() {
        if (mPlayer != null) {
            mPlayer.release();
            mPlayer = null;
        }
    }

    private void enterFullScreen() {
        mToolbar.setVisibility(View.GONE);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
        ViewGroup contentView = findViewById(android.R.id.content);
        mPortraitContainer.removeView(mTextureView);
        mFlPlayer.removeView(mPortraitContainer);
        if (mLandScapeContainer == null) {
            mLandScapeContainer = new FrameLayout(this);
            mLandScapeContainer.setBackgroundColor(Color.BLACK);
            ImageButton imageButton = new ImageButton(this);
            imageButton.setImageResource(R.drawable.ic_fullscreen_white_24dp);
            imageButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (mMode == MODE_FULL_SCREEN) {
                        exitFullScreen();
                    } else {
                        enterFullScreen();
                    }
                }
            });
            FrameLayout.LayoutParams lp = new FrameLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT,
                    ViewGroup.LayoutParams.WRAP_CONTENT);
            lp.gravity = Gravity.BOTTOM | Gravity.RIGHT;
            mLandScapeContainer.addView(imageButton, lp);
        }
        mLandScapeContainer.setSystemUiVisibility(
                View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY
                        | View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
                        | View.SYSTEM_UI_FLAG_FULLSCREEN
                        | View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                        | View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
                        | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
        );
        mLandScapeContainer.setKeepScreenOn(true);
        ViewGroup.LayoutParams lp = new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.MATCH_PARENT);
        mLandScapeContainer.addView(mTextureView, 0, lp);
        contentView.addView(mLandScapeContainer, lp);
        mMode = MODE_FULL_SCREEN;
    }

    private void exitFullScreen() {
        mToolbar.setVisibility(View.VISIBLE);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        ViewGroup contentView = findViewById(android.R.id.content);
        mLandScapeContainer.removeView(mTextureView);
        contentView.removeView(mLandScapeContainer);
        ViewGroup.LayoutParams lp = new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.MATCH_PARENT);
        mPortraitContainer.addView(mTextureView, 0, lp);
        mFlPlayer.addView(mPortraitContainer, lp);
        mMode = MODE_NORMAL;
    }
}
