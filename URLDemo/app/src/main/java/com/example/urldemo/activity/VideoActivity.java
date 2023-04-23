package com.example.urldemo.activity;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.view.MotionEvent;
import android.view.View;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.VideoView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.ViewPager;

import com.example.urldemo.MessageEnum;
import com.example.urldemo.R;
import com.example.urldemo.adapter.MovieAdapter;
import com.example.urldemo.adapter.VideoAdapter;
import com.example.urldemo.dto.Movie;
import com.example.urldemo.dto.Video;
import com.example.urldemo.utils.ExecutorUtl;
import com.example.urldemo.utils.HandlerUtil;
import com.example.urldemo.utils.LogUtils;
import com.example.urldemo.utils.UrlUtil;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * @author yangyinhua
 */
public class VideoActivity extends Activity {
    ViewPager viewPager;
    ImageView playButton;
    private String url = "https://tucdn.wpon.cn/api-girl/index.php?wpon=json";
    private int updates = 10;
    private VideoAdapter videoAdapter;
    private Context mContext;
    String mp4Url = "https://tucdn.wpon.cn/api-girl/videos/BMjAyMTEyMTAxNjE2NDFfMzA3NDA1NzMyXzYyNDcyMDU3NTY5XzFfMw==_b_B576e26fd5d37f56e3bb995bf786a678d.mp4";

    private final Handler mHandler = new Handler(Looper.getMainLooper()){

        @Override
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case HandlerUtil.MSG_ID_UPDATE_MOVIE:
                    if (msg.obj != null) {
                        if (videoAdapter == null) {
                            videoAdapter = new VideoAdapter(mContext);
                        }
                        videoAdapter.setMp4UrlList((List<String>) msg.obj);
                        videoAdapter.notifyDataSetChanged();
                    }
                    break;
                default:
                    super.handleMessage(msg);
            }
        }
    };

    @SuppressLint("ClickableViewAccessibility")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        videoAdapter = new VideoAdapter(this);
        mContext = this;
        updateMp4Url();

        setContentView(R.layout.activity_video);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        viewPager = findViewById(R.id.viewPager);
        viewPager.setAdapter(videoAdapter);
        viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                LogUtils.d("onPageSelected");
                playButton.setImageResource(R.drawable.ic_play);
                VideoView videoView = (VideoView) viewPager.getChildAt(position);
                if (videoView.isPlaying()) {
                    videoView.pause();
                } else {
                    videoView.start();
                }
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });


        // 处理手势切换页面
        viewPager.setOnTouchListener(new View.OnTouchListener() {

            float startX;
            float endX;

            @Override
            public boolean onTouch(View v, MotionEvent event) {
                switch (event.getAction()) {
                    case MotionEvent.ACTION_DOWN:
                        startX = event.getX();
                        break;
                    case MotionEvent.ACTION_UP:
                        endX = event.getX();
                        if (startX - endX > 1) {
                            viewPager.setCurrentItem(viewPager.getCurrentItem() - 1);
                        } else if (endX - startX > 1) {
                            viewPager.setCurrentItem(viewPager.getCurrentItem() + 1);
                        }
                        startX = 0;
                        endX = 0;
                        break;
                }
                return false;
            }
        });

    }

    private void updateMp4Url(){
        ExecutorUtl.getExecutor().execute(() -> {
            List<String> list = new ArrayList<>();
            try {
                List<String> urlDataList = UrlUtil.getUrlData(url, updates);
                for (String urlData : urlDataList) {
                    JSONObject jsonObject = new JSONObject(urlData);
                    if (jsonObject.get("result").toString().equals("200")){
                        list.add("https:" + jsonObject.get("mp4"));
                        LogUtils.d("mp4 = " + "https:" + jsonObject.get("mp4"));
                    }else {
                        LogUtils.d("更新数据失败，失败原因为： " + jsonObject.get("error"));
                    }
                }
            } catch (IOException e) {
                e.printStackTrace();
                LogUtils.d(e);
            } catch (JSONException e) {
                e.printStackTrace();
            }
            HandlerUtil.sendMes(HandlerUtil.MSG_ID_UPDATE_MP4_LIST, list);
        });

    }

}