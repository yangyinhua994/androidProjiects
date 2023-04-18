package com.example.urldemo.utils;

import android.util.Log;

public class LogUtils {

    public static void d(Object o){

        if (o != null){
            Log.d("yangyinhua", o.toString());
        }

    }

    public static void d(String TAG, Object o){

        if (TAG != null && o != null){
            Log.d(TAG, o.toString());
        }

    }

}
