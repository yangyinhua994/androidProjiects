package com.example.urldemo.activity;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.view.ViewGroup;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.urldemo.R;
import com.example.urldemo.utils.ExecutorUtil;

import java.util.concurrent.Executor;

/**
 * @author yangyinhua
 */

public class BaseActivity extends AppCompatActivity {

    public static final Executor executor = ExecutorUtil.getExecutor();
    public static int MSG_ID_UPDATE_URL = 1;
    public static final int MSG_ID_UPDATE_MOVIE = 2;
    public static final int MSG_ID_PLAY_VIDEO = 3;



}
