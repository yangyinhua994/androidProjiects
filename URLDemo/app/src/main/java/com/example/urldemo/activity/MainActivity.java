package com.example.urldemo.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;

import com.example.urldemo.utils.LogUtils;
import com.example.urldemo.R;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    private Button mMainBt1;
    private Button mMainBt2;
    private Button mMainBt3;
    public static final String BAI_DU_URL = "https://www.baidu.com";
    public static final String BING_URL = "https://www.bing.com";
    public static final String MY_URL = " http://localhost:8080/hello";
    private static String url = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mMainBt1 = findViewById(R.id.bt1);
        mMainBt1.setOnClickListener(this);
        mMainBt2 = findViewById(R.id.bt2);
        mMainBt2.setOnClickListener(this);
        mMainBt3 = findViewById(R.id.bt3);
        mMainBt3.setOnClickListener(this);
        Intent intent = new Intent(this, MovieMainActivity.class);
        startActivity(intent);
        finish();
    }

    int corePoolSize = 5;
    int maximumPoolSize = 10;
    int keepAliveTime = 1000;
    ExecutorService executor = new ThreadPoolExecutor(
            corePoolSize,
            maximumPoolSize,
            keepAliveTime,
            TimeUnit.MILLISECONDS,
            new LinkedBlockingQueue<>(),
            Executors.defaultThreadFactory(),
            new ThreadPoolExecutor.AbortPolicy()
    );


    @SuppressLint("NonConstantResourceId")
    @Override
    public void onClick(View view) {

        switch (view.getId()) {
            case R.id.bt1:
                url = BAI_DU_URL;
                break;
            case R.id.bt2:
                url = BING_URL;
                break;
            case R.id.bt3:
                Intent intent = new Intent(this, MovieMainActivity.class);
                startActivity(intent);
                return;
            default:
                break;
        }
    }

}