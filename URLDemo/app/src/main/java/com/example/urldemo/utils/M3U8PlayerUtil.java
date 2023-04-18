package com.example.urldemo.utils;

import android.content.Context;
import android.net.Uri;
import android.os.Handler;
import android.os.Looper;
import android.view.SurfaceView;
import android.view.ViewGroup;
import android.widget.FrameLayout;

import androidx.annotation.NonNull;

import com.google.android.exoplayer2.DefaultLoadControl;
import com.google.android.exoplayer2.DefaultRenderersFactory;
import com.google.android.exoplayer2.ExoPlayer;
import com.google.android.exoplayer2.ExoPlayerFactory;
import com.google.android.exoplayer2.SimpleExoPlayer;
import com.google.android.exoplayer2.extractor.DefaultExtractorsFactory;
import com.google.android.exoplayer2.source.ExtractorMediaSource;
import com.google.android.exoplayer2.source.MediaSource;
import com.google.android.exoplayer2.trackselection.AdaptiveTrackSelection;
import com.google.android.exoplayer2.trackselection.DefaultTrackSelector;
import com.google.android.exoplayer2.trackselection.TrackSelection;
import com.google.android.exoplayer2.trackselection.TrackSelector;
import com.google.android.exoplayer2.ui.PlayerView;
import com.google.android.exoplayer2.upstream.BandwidthMeter;
import com.google.android.exoplayer2.upstream.DefaultBandwidthMeter;
import com.google.android.exoplayer2.upstream.DefaultHttpDataSourceFactory;
import com.google.android.exoplayer2.util.Util;

/**
 * @author yangyinhua
 */
public class M3U8PlayerUtil {

    private final Context context;
    private final FrameLayout parentView;
    private final Handler handler = new Handler(Looper.getMainLooper());

    private PlayerView playerView;
    private SimpleExoPlayer player;

    public M3U8PlayerUtil(@NonNull Context context, @NonNull ViewGroup parentView) {
        this.context = context;
        this.parentView = new FrameLayout(context);
        parentView.addView(this.parentView);
    }

    public void start(@NonNull String url) {
        if (player == null) {
            Handler mainHandler = new Handler();

            BandwidthMeter bandwidthMeter = new DefaultBandwidthMeter();

            TrackSelection.Factory videoTrackSelectionFactory = new AdaptiveTrackSelection.Factory(bandwidthMeter);

            TrackSelector trackSelector = new DefaultTrackSelector(videoTrackSelectionFactory);
            player = ExoPlayerFactory.newSimpleInstance(context, trackSelector);
            // Create a new instance of SimpleExoPlayer
//            player = new SimpleExoPlayer.Builder(context, new DefaultRenderersFactory(context))
//                    .setTrackSelector(new DefaultTrackSelector(context))
//                    .setLoadControl(new DefaultLoadControl())
//                    .build();

            // Bind the player to a PlayerView for video playback
            playerView = new PlayerView(context);
            playerView.setLayoutParams(new FrameLayout.LayoutParams(
                    ViewGroup.LayoutParams.MATCH_PARENT,
                    ViewGroup.LayoutParams.MATCH_PARENT));
            parentView.addView(playerView);
            playerView.setPlayer(player);

            // Prepare the MediaSource
            MediaSource mediaSource = new ExtractorMediaSource.Factory(
                    new DefaultHttpDataSourceFactory(Util.getUserAgent(context, context.getPackageName())))
                    .setExtractorsFactory(new DefaultExtractorsFactory())
                    .createMediaSource(Uri.parse(url));

            // Start playing the video
            player.prepare(mediaSource);
            player.setPlayWhenReady(true);
        }
    }

    public void stop() {
        if (player != null) {
            player.stop();
            player.release();
            player = null;
            handler.post(() -> {
                parentView.removeView(playerView);
                playerView = null;
            });
        }
    }
}



