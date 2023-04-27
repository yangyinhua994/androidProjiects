package com.example.urldemo.adapter;

import android.app.Activity;
import android.media.MediaPlayer;
import android.os.Handler;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.VideoView;

import androidx.annotation.NonNull;
import androidx.viewpager.widget.PagerAdapter;
import androidx.viewpager.widget.ViewPager;

import com.example.urldemo.dto.Video;
import com.example.urldemo.utils.LogUtils;

import java.util.ArrayList;
import java.util.List;

public class VideoPagerAdapter extends PagerAdapter {

    //当前视频的索引
    private int currentVideoIndex = -1;
    //视频是否加载完成
    private boolean isPaused = false;
    private Activity activity;
    private List<String> videoList = new ArrayList<>();
    private boolean isOne = true;

    public String getVideoList(int index) {
        return videoList.get(index);
    }


    //加载到几秒播放
    private final int loadSecondsPlay = 1;
    Handler mHandler = new Handler();


    public VideoPagerAdapter(Activity activity) {
        this.activity = activity;

    }

    public void setVideoPagerVideoUrlS(List<String> videoUrl) {
        videoList.addAll(videoUrl);
    }

    @Override
    public int getCount() {
        return videoList.size();
    }

    @Override
    public boolean isViewFromObject(@NonNull View view, @NonNull Object object) {
        return view == object;
    }

    @NonNull
    @Override
    public Object instantiateItem(@NonNull ViewGroup container, int position) {
        VideoView videoView;
        if (currentVideoIndex == position){
            videoView = (VideoView) container.getChildAt(position);
        }else if (currentVideoIndex < position){
            VideoView previousVideoView = (VideoView) container.getChildAt(currentVideoIndex);
            if (previousVideoView != null){
                previousVideoView.pause();
            }
            //后滑
            videoView = new VideoView(activity);
            String videoUrl = videoList.get(position);
            videoView.setVideoPath(videoUrl);
            LogUtils.d("第" + (position + 1) + "个视频处理完成");
            setLoadTime(videoView);
            container.addView(videoView);
        }else {
            //前滑
            videoView = (VideoView) container.getChildAt(position);
            videoView.resume();
        }
        currentVideoIndex = position;
        return videoView;
    }

    @Override
    public void destroyItem(@NonNull ViewGroup container, int position, @NonNull Object object) {

    }

    private void setLoadTime(VideoView videoView){
        videoView.setOnPreparedListener(mediaPlayer -> {
            mHandler.postDelayed(videoView::start, loadSecondsPlay * 1000);
        });

    }

    public void clear() {
        videoList.clear();
    }

}