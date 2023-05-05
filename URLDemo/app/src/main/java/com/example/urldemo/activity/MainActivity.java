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

/**
 * @author yangyinhua
 */
public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    private Button aiBt;
    private Button videoBt;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        aiBt = findViewById(R.id.ai);
        aiBt.setOnClickListener(this);
        videoBt = findViewById(R.id.video);
        videoBt.setOnClickListener(this);
    }

    @SuppressLint("NonConstantResourceId")
    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.ai:
                Intent intent = new Intent(this, AiActivity.class);
                startActivity(intent);
                break;
            case R.id.video:
                intent = new Intent(this, MovieMainActivity.class);
                startActivity(intent);
                break;
            default:
                break;
        }
    }

}