package com.example.widget.widget;

import android.annotation.SuppressLint;
import android.app.PendingIntent;
import android.appwidget.AppWidgetManager;
import android.appwidget.AppWidgetProvider;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.widget.RemoteViews;
import android.widget.Toast;

import com.example.widget.R;

@SuppressLint("all")
public class SimpleWidgetProvider extends AppWidgetProvider {
    private static final String CLICK_ACTION = "com.example.widget.CLICK";
    @Override
    public void onReceive(Context context, Intent intent) {
        super.onReceive(context, intent);
        AppWidgetManager appWidgetManager = AppWidgetManager.getInstance(context);
        String action = intent.getAction();
        if (action.equals(CLICK_ACTION)) {
            Toast.makeText(context,"click",Toast.LENGTH_SHORT).show();
            context.startActivity(new Intent(context, R));
        }
    }

    @Override
    public void onEnabled(Context context) {
        super.onEnabled(context);
        Toast.makeText(context,"创建成功",Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onAppWidgetOptionsChanged(Context context, AppWidgetManager appWidgetManager, int appWidgetId, Bundle newOptions) {
        super.onAppWidgetOptionsChanged(context, appWidgetManager, appWidgetId, newOptions);
    }

    @Override
    public void onUpdate(Context context, AppWidgetManager appWidgetManager, int[] appWidgetIds) {
        super.onUpdate(context, appWidgetManager, appWidgetIds);
        for (int appWidgetId : appWidgetIds) {
            //创建一个远程view，绑定我们要操控的widget布局文件
            RemoteViews remoteViews = new RemoteViews(context.getPackageName(),R.layout.layout_widget);
            Intent intentClick = new Intent();
            //这个必须要设置，不然点击效果会无效
            intentClick.setClass(context,SimpleWidgetProvider.class);
            intentClick.setAction(CLICK_ACTION);
            //PendingIntent表示的是一种即将发生的意图，区别于Intent它不是立即会发生的
            PendingIntent pendingIntent = PendingIntent.getBroadcast(context,0,intentClick,PendingIntent.FLAG_UPDATE_CURRENT);
            //为布局文件中的按钮设置点击监听
            remoteViews.setOnClickPendingIntent(R.id.line1, pendingIntent);
            remoteViews.setOnClickPendingIntent(R.id.line2, pendingIntent);
            remoteViews.setOnClickPendingIntent(R.id.line3, pendingIntent);
            //告诉AppWidgetManager对当前应用程序小部件执行更新
            appWidgetManager.updateAppWidget(appWidgetId,remoteViews);
        }
        RemoteViews remoteViews = new RemoteViews(context.getPackageName(),R.layout.layout_widget);
        remoteViews.setTextViewText(R.id.textview1, "500");
        remoteViews.setTextViewText(R.id.textview2, "400");
        remoteViews.setTextViewText(R.id.textview3, "300");
        appWidgetManager.updateAppWidget(new ComponentName(context,SimpleWidgetProvider.class),remoteViews);
    }

    //当 widget 被删除时回调
    @Override
    public void onDeleted(Context context, int[] appWidgetIds) {
        super.onDeleted(context, appWidgetIds);
    }

    //当最后一个widget实例被删除时回调.
    @Override
    public void onDisabled(Context context) {
        super.onDisabled(context);
    }
}