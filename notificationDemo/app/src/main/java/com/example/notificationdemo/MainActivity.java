package com.example.notificationdemo;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.Context;
import android.graphics.BitmapFactory;
import android.os.Build;
import android.os.Bundle;
import android.os.Parcelable;

import java.util.HashMap;

public class MainActivity extends AppCompatActivity {


    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Context context=getApplicationContext();
        String channelId = "测试渠道";
        Notification notification = new Notification.Builder(context,channelId)
                .setContentTitle("测试通知标题")
                .setContentText("测试通知文本")
                .setWhen(System.currentTimeMillis())
                .setSmallIcon(R.drawable.icon1)
                .setLargeIcon(BitmapFactory.decodeResource(getResources(),R.drawable.icon1))
                .build();
        NotificationManager notificationManager = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
        NotificationChannel notificationChannel = new NotificationChannel(channelId, "测试渠道名称", NotificationManager.IMPORTANCE_DEFAULT);
        notificationManager.createNotificationChannel(notificationChannel);
        notificationManager.notify(1122, notification);
        HashMap<Object, Object> hashMap = new HashMap<>();


    }
}