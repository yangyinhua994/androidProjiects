package com.example.urldemo.activity;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.view.View;
import android.widget.Toast;
import android.widget.VideoView;

import androidx.viewpager.widget.ViewPager;

import com.example.urldemo.R;
import com.example.urldemo.adapter.VideoPagerAdapter;
import com.example.urldemo.utils.ExecutorUtil;
import com.example.urldemo.utils.ExecutorUtl;
import com.example.urldemo.utils.LogUtils;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class MovieMainActivity extends Activity {

    private Context mContext;
    private final String ip = "10.0.2.2";
    private final int port = 8081;

    //测试链接
    private final String url = "http://" + ip +":" + port + "/json";
    private final String connection = "http";

    //网络链接
    private final String distalEndUrl = "https://tucdn.wpon.cn/api-girl/index.php?wpon=json";
    private final String distalEndConnection = "https";

    private String connectUrl = url;
    private String connectionMethod = connection;

    private ViewPager videoPagerView;
    private VideoPagerAdapter videoPagerAdapter;
    //当前视频列表大小
    private int currentSize = 0;
    //当前视频的索引
    private int currentVideoIndex = 0;
    //更新视频的阈值
    private final int UPDATE_VIDEO_THRESHOLD = 5;
    //每次更新视频个数
    private final int UPDATE_VIDEO_NUMBER = 2;
    //访问成功的状态码
    private final int SWITCH_VIDEO_THRESHOLD = 200;

    public final int MSG_ID_UPDATE_VIDEO_END = 1;
    public final int MSG_ID_TIME_OUT = 2;

    private final OkHttpClient client = new OkHttpClient.Builder()
            .connectTimeout(10, TimeUnit.SECONDS)
            .readTimeout(20, TimeUnit.SECONDS)
            .build();



    private final Handler mHandler = new Handler(Looper.getMainLooper()){

        @Override
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case MSG_ID_UPDATE_VIDEO_END:
                    if (msg.obj != null){
                        List<String> list = (List<String>) msg.obj;
                        videoPagerAdapter.setVideoPagerVideoUrlS(list);
                        videoPagerAdapter.notifyDataSetChanged();
                        currentSize += list.size();
                    }
                    break;
                case MSG_ID_TIME_OUT:
                    Toast.makeText(mContext, "链接超时", Toast.LENGTH_SHORT).show();
                    LogUtils.d("链接超时： " + connectUrl);
                    break;
                default:
                    super.handleMessage(msg);
            }
        }
    };


    @SuppressLint({"MissingInflatedId", "ClickableViewAccessibility"})
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        ExecutorUtl.getExecutor().execute(() ->{
            if (getUrlResponse(connectUrl) == null){
                connectUrl = distalEndUrl;
                connectionMethod = distalEndConnection;
            }
        });
        init();
        mContext = this;
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_movie_main);
        videoPagerView = findViewById(R.id.viewPager);
        videoPagerAdapter = new VideoPagerAdapter(this);
        videoPagerView.setAdapter(videoPagerAdapter);
//        videoPagerView.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
//            @Override
//            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
//                // 滑动正在发生,position表示当前页面,
//                // positionOffset表示页面偏移的百分比, positionOffsetPixels表示像素偏移量
//
//            }
//
//            @Override
//            public void onPageSelected(int position) {
//                // 当新的页面被选中时调用
//            }
//
//            @Override
//            public void onPageScrollStateChanged(int state) {
//                // 页面滚动的状态改变时调用,
//                // 状态可以是:ViewPager.SCROLL_STATE_IDLE、ViewPager.SCROLL_STATE_DRAGGING 或者 ViewPager.SCROLL_STATE_SETTLING
//                switch (state) {
//                    case ViewPager.SCROLL_STATE_IDLE:
//                        // 滚动停止
//                        break;
//                    case ViewPager.SCROLL_STATE_DRAGGING:
//                        // 页面在拖动
//                        break;
//                    case ViewPager.SCROLL_STATE_SETTLING:
//                        LogUtils.d("播放下一个视频");
//                        currentVideoIndex ++;
//                        playVideo(currentVideoIndex);
//                        break;
//                }
//            }
//        });

    }

    private void init(){
        startUpdateVideoThread();
    }

    private void startUpdateVideoThread() {
        ExecutorUtil.getExecutor().execute(() ->{
            while (true){
                int updateVideoNumber = currentSize - currentVideoIndex;
                if (updateVideoNumber <= UPDATE_VIDEO_THRESHOLD){
                    List<String> videoUrl = new ArrayList<>();
                    for (int x = UPDATE_VIDEO_NUMBER; x>0; x--){
                        Response response = getUrlResponse(connectUrl);
                        if (response == null){
                            mHandler.sendMessage(mHandler.obtainMessage(MSG_ID_TIME_OUT));
                            return;
                        }
                        JSONObject videoJson;
                        try {
                            try {
                                if (response.body() == null){
                                    return;
                                }
                                videoJson = new JSONObject(response.body().string());
                            } catch (IOException e) {
                                throw new RuntimeException(e);
                            }
                        } catch (JSONException e) {
                            throw new RuntimeException(e);
                        }
                        try {
                            if (String.valueOf(SWITCH_VIDEO_THRESHOLD).equals(videoJson.get("result").toString())){
                                videoUrl.add(connectionMethod + ":" + videoJson.get("mp4"));
                            }
                        } catch (JSONException e) {
                            throw new RuntimeException(e);
                        }
                    }
                    mHandler.sendMessage(mHandler.obtainMessage(MSG_ID_UPDATE_VIDEO_END, videoUrl));
                }
            }

        });

    }

    @Override
    protected void onPause() {
        super.onPause();
        pauseVideo();
    }


    @Override
    protected void onRestart() {
        super.onRestart();
        playVideo();
    }

    private void playVideo(){

        View view = videoPagerView.getChildAt(currentVideoIndex);
        if (view instanceof VideoView) {
            ((VideoView)view).start();
        }
    }

    private void playVideo(int videoIndex){
        LogUtils.d("播放视频的链接: " + videoPagerAdapter.getVideoList(videoIndex));
        if(videoIndex > 0 || videoIndex <= currentSize){
            View view = videoPagerView.getChildAt(videoIndex);
            if (view instanceof VideoView) {
                LogUtils.d("播放第" + (videoIndex + 1) + "视频成功");
                ((VideoView)view).start();
            }else {
                LogUtils.d("播放第" + (videoIndex + 1) + "视频失败");
            }
        }
    }

    private void pauseVideo(){
        View view = videoPagerView.getChildAt(currentVideoIndex);
        if (view instanceof VideoView) {
            ((VideoView)view).resume();
        }
    }

    private Response getUrlResponse(String url){
        Request request = new Request.Builder().url(url).build();
        Response response;
        try {
            response = client.newCall(request).execute();
        } catch (IOException e) {
            return null;
        }
        return response;
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (videoPagerView != null){
            videoPagerView.removeAllViews();
            videoPagerAdapter.clear();
        }
    }
}