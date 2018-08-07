package com.gofar.goplayer;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;

import com.google.android.exoplayer2.DefaultLoadControl;
import com.google.android.exoplayer2.DefaultRenderersFactory;
import com.google.android.exoplayer2.LoadControl;
import com.google.android.exoplayer2.RenderersFactory;
import com.google.android.exoplayer2.SimpleExoPlayer;
import com.google.android.exoplayer2.trackselection.DefaultTrackSelector;
import com.google.android.exoplayer2.trackselection.TrackSelector;
import com.google.android.exoplayer2.ui.PlayerView;
import com.google.android.exoplayer2.util.Util;

import java.util.UUID;

/**
 * @author lcf
 * @date 1/8/2018 下午 4:02
 * @since 1.0
 */
public class PlayerActivity extends AppCompatActivity {
    private PlayerView mPlayerView;
    private SimpleExoPlayer mPlayer;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_player);

        mPlayerView = findViewById(R.id.my_player);
        mPlayerView.setPlayer(mPlayer);
    }

    private void init(){
        if (mPlayer==null){
            RenderersFactory factory=new DefaultRenderersFactory(this);
            TrackSelector trackSelector=new DefaultTrackSelector();
            LoadControl loadControl=new DefaultLoadControl.Builder().createDefaultLoadControl();
            String drmScheme="";
            UUID uuid= Util.getDrmUuid(drmScheme);
            //DrmSessionManager<FrameworkMediaCrypto> drmSessionManager=new DefaultDrmSessionManager<FrameworkMediaCrypto>(uuid,);
            //mPlayer=new SimpleExoPlayer(factory,trackSelector,loadControl,drmSessionManager);
        }

    }
}
