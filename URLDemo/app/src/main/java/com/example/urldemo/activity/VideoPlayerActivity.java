package com.example.urldemo.activity;

import android.net.Uri;
import android.os.Bundle;
import android.widget.FrameLayout;
import android.widget.MediaController;
import android.widget.VideoView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.urldemo.R;

/**
 * @author yangyinhua
 */
public class VideoPlayerActivity extends AppCompatActivity {
    VideoView mVideoView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_video_player);
        mVideoView = findViewById(R.id.video_view);
        String videoUrl = getIntent().getStringExtra("video_url");
        mVideoView.setVideoURI(Uri.parse(videoUrl));
        mVideoView.setMediaController(new MediaController(this));
        mVideoView.start();

    }

    @Override
    protected void onResume() {
        super.onResume();
        if (mVideoView != null && !mVideoView.isPlaying()) {
            mVideoView.start();
        }
    }

    @Override
    protected void onPause() {
        super.onPause();
        mVideoView.pause();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mVideoView.stopPlayback();
    }
}