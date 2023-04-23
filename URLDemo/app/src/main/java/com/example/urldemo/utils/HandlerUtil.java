package com.example.urldemo.utils;


import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;

import com.example.urldemo.activity.VideoActivity;
import com.example.urldemo.activity.VideoingActivity;

public class HandlerUtil extends Activity {

    private static Context mContext;
    public static int MSG_ID_UPDATE_URL = 1;
    public static final int MSG_ID_UPDATE_MOVIE = 2;
    public static final int MSG_ID_PLAY_VIDEO = 3;
    public static final int MSG_ID_LOADED_ING = 4;
    public static final int MSG_ID_LOADED_SUCCESSFULLY = 5;
    public static final int MSG_ID_UPDATE_MAP4_URL = 6;
    public static final int MSG_ID_UPDATE_MP4_LIST = 7;

    public static final android.os.Handler mHandler = new Handler(Looper.getMainLooper()){

        @Override
        public void handleMessage(Message msg) {

            switch (msg.what){
                case MSG_ID_LOADED_ING:
                    Intent intent = new Intent(mContext, VideoingActivity.class);
                    mContext.startActivity(intent);
                    break;
                case MSG_ID_LOADED_SUCCESSFULLY:
                    intent = new Intent(mContext, VideoActivity.class);
                    mContext.startActivity(intent);
                    break;
                case MSG_ID_UPDATE_MP4_LIST:

                default:
                    super.handleMessage(msg);
            }


        }
    };

    public HandlerUtil() {
        mContext = this;
    }

    public static Handler getHandler(){
        return mHandler;
    }

    public static void sendMes(int what, Object mes){
        Message message = mHandler.obtainMessage(what, mes);
        mHandler.sendMessage(message);
    }


}
