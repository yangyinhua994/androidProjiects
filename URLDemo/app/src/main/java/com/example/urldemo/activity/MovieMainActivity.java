package com.example.urldemo.activity;

import android.app.Activity;
import android.net.Uri;
import android.os.Bundle;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.widget.SeekBar;
import android.widget.VideoView;

import com.example.urldemo.R;

import java.util.ArrayList;
import java.util.List;

public class MovieMainActivity extends Activity {
    private VideoView mVideoView;
    private SeekBar mSeekBar;
    private GestureDetector mGestureDetector;
    private int mCurrentVideoIndex = 0;
    private List<String> mVideoUrls = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_moves);

        // 初始化VideoView和SeekBar
        mVideoView = findViewById(R.id.videoView);
        mSeekBar = findViewById(R.id.seekBar);

        // 添加视频URL到列表中
        mVideoUrls.add("https://tucdn.wpon.cn/api-girl/videos/BMjAyMTEyMTAxNjE2NDFfMzA3NDA1NzMyXzYyNDcyMDU3NTY5XzFfMw==_b_B576e26fd5d37f56e3bb995bf786a678d.mp4");
        mVideoUrls.add("https://tucdn.wpon.cn/api-girl/videos/BMjAyMTEyMTAxNjE2NDFfMzA3NDA1NzMyXzYyNDcyMDU3NTY5XzFfMw==_b_B576e26fd5d37f56e3bb995bf786a678d.mp4");
        mVideoUrls.add("https://tucdn.wpon.cn/api-girl/videos/BMjAyMTEyMTAxNjE2NDFfMzA3NDA1NzMyXzYyNDcyMDU3NTY5XzFfMw==_b_B576e26fd5d37f56e3bb995bf786a678d.mp4");

        playVideo(mCurrentVideoIndex);
        mCurrentVideoIndex++;
        // 初始化GestureDetector并将其附加到VideoView上
        mGestureDetector = new GestureDetector(this, new GestureDetector.SimpleOnGestureListener() {
            @Override
            public boolean onScroll(MotionEvent e1, MotionEvent e2, float distanceX, float distanceY) {
                if (Math.abs(distanceY) > Math.abs(distanceX)) {
                    // 确定手势方向是上下滑动
                    if (distanceY < 0) {
                        // 播放下一个视频
                        mCurrentVideoIndex++;
                        if (mCurrentVideoIndex >= mVideoUrls.size()) {
                            mCurrentVideoIndex = 0;
                        }
                    } else {
                        // 播放上一个视频
                        mCurrentVideoIndex--;
                        if (mCurrentVideoIndex < 0) {
                            mCurrentVideoIndex = mVideoUrls.size() - 1;
                        }
                    }
                    playVideo(mCurrentVideoIndex);
                    return true;
                }
                return false;
            }
        });
        mVideoView.setOnTouchListener((view, motionEvent) -> {
            mGestureDetector.onTouchEvent(motionEvent);
            return true;
        });
    }

    // 播放指定索引的视频
    private void playVideo(int index) {
        String videoUrl = mVideoUrls.get(index);
        mVideoView.setVideoPath(videoUrl);
        mVideoView.start();
    }
}

