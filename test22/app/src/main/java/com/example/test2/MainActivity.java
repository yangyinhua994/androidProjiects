package com.example.test2;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.os.Bundle;
import android.text.format.DateUtils;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        DisplayMetrics dm = getResources().getDisplayMetrics();
        Log.i("yangyinhua", dm.heightPixels + "x" + dm.widthPixels);
        int i = px2dp(this, dm.widthPixels);
        Log.i("yangyinhua", "宽的px = " + i);
        int i1 = px2dp(this, dm.heightPixels);
        Log.i("yangyinhua", "高的px = " + i1);
    }


    public static int dp2px(Context context, float dp){
        float scale = context.getResources().getDisplayMetrics().density;
        return (int) (dp * scale + 0.5f);
    }

    public static int px2dp(Context context, float px){
        float scale = context.getResources().getDisplayMetrics().density;
        return (int) (px/scale + 035f);
    }

}