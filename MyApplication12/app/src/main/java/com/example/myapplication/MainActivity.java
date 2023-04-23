package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;

import java.lang.reflect.Method;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        String pkgName = "com.android.settings";
        String clsName = "SettingsActivity";
        // 获取ClassLoader
        ClassLoader classLoader = getClassLoader();
        Class<?> aClass;
        try {
            aClass = classLoader.loadClass(pkgName + "." + clsName);
            for (Method method : aClass.getMethods()) {
                Log.i("yangyinhua", "method.getName() = " + method.getName());
            }
        } catch (ClassNotFoundException e) {
            setContentView(R.layout.activity_main);
            Log.i("yangyinhua",  e.toString());
        }
        setContentView(R.layout.activity_main);
//        Log.i("yangyinhua", MainActivity.class.toString());
    }
}