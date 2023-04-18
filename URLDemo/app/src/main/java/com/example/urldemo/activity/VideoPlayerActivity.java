package com.example.urldemo.activity;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.ViewGroup;
import android.widget.VideoView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.example.urldemo.R;
import com.example.urldemo.adapter.MovieAdapter;
import com.example.urldemo.dto.Movie;
import com.example.urldemo.utils.M3U8PlayerUtil;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;

import java.io.IOException;
import java.util.List;
import java.util.concurrent.Executor;

/**
 * @author yangyinhua
 */
@SuppressLint("HandlerLeak")
public class VideoPlayerActivity extends BaseActivity {

    String videoUrl;
    private ViewGroup parentView;
    M3U8PlayerUtil m3U8PlayerUtil;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_video_player);
        parentView = findViewById(R.id.parent_view);
        m3U8PlayerUtil = new M3U8PlayerUtil(this, parentView);
        m3U8PlayerUtil.start("https://hot.qoqkkhy.com/20230414/rKU3dY4q/1000kb/hls/index.m3u8");

    }

    @Override
    protected void onStop() {
        super.onStop();
        m3U8PlayerUtil.stop();
    }
}
