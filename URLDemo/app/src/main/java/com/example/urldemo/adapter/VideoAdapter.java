package com.example.urldemo.adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.VideoView;

import androidx.annotation.NonNull;
import androidx.viewpager.widget.PagerAdapter;

import com.example.urldemo.utils.ExecutorUtl;
import com.example.urldemo.utils.LogUtils;
import com.example.urldemo.utils.UrlUtil;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class VideoAdapter extends PagerAdapter {

    private List<String> mp4UrlList;
    private Context mContext;
    private int Updates = 10;

    private String url = "https://tucdn.wpon.cn/api-girl/index.php?wpon=json";

    public VideoAdapter(Context context) {
        mContext = context;
        this.mp4UrlList = new ArrayList<>();

    }

    public void setMp4UrlList(List<String> list){
        mp4UrlList.addAll(list);
    }

    @Override
    public int getCount() {
        return mp4UrlList.size();
    }

    @Override
    public boolean isViewFromObject(@NonNull View view, @NonNull Object object) {
        return view == object;
    }

    @NonNull
    @Override
    public Object instantiateItem(@NonNull ViewGroup container, int position) {
        LogUtils.d("instantiateItem");
        if (mp4UrlList.isEmpty()) return null;
        VideoView videoView = new VideoView(container.getContext());
        videoView.setVideoPath(mp4UrlList.get(position));
        videoView.setOnPreparedListener(mp -> videoView.start());
        container.addView(videoView);
        return videoView;
    }

    @Override
    public void destroyItem(@NonNull ViewGroup container, int position, @NonNull Object object) {
        VideoView videoView = (VideoView) object;
        videoView.stopPlayback();
        container.removeView(videoView);
    }
}