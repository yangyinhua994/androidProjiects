package com.example.layoutinflatertest;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.os.Bundle;
import android.os.HandlerThread;
import android.view.LayoutInflater;
import android.view.View;

/**
 * @author yangyinhua
 */
public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        LayoutInflater inflater = (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View inflate = inflater.inflate(R.layout.test, null, false);
        View viewById = inflate.findViewById(R.id.button);
        new HandlerThread()
    }
}