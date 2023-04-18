package com.example.test4;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
//        2131296275    2131296276
        Log.d("yangyinhua", getResources().getInteger(R.integer.refreshRate) + "");
        Log.d("yangyinhua", R.integer.refreshRate2 + "");
    }
}