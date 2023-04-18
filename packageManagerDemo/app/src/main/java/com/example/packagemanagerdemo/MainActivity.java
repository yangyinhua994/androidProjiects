package com.example.packagemanagerdemo;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.app.ActivityManager;
import android.app.DownloadManager;
import android.app.Fragment;
import android.app.FragmentManager;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.location.Location;
import android.location.LocationManager;
import android.net.Uri;
import android.os.Binder;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.util.Log;

import java.util.List;

public class MainActivity extends AppCompatActivity {

    @SuppressLint("MissingPermission")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
//        PackageManager packageManager = this.getPackageManager();
//        Intent intent = new Intent(Intent.ACTION_MAIN, null);
//        intent.addCategory(Intent.CATEGORY_DEFAULT);
//        List<ResolveInfo> resolveInfos = packageManager.queryIntentActivities(intent, PackageManager.MATCH_DEFAULT_ONLY);
//        for (ResolveInfo resolveInfo : resolveInfos) {
//            String appName = resolveInfo.loadLabel(packageManager).toString();
//            String packageName = resolveInfo.activityInfo.packageName;
//            String className = resolveInfo.activityInfo.name;
////            System.out.println("程序名：" + appName + " 包名:" + packageName
////                    + " 入口类名：" + className);
////            android.util.Log.i("yangyinhua", "程序名：" + appName + " 包名:" + packageName
////                    + " 入口类名：" + className);
//            Log.i("yangyinhua", "adb shell am start -n "+packageName+"/"+className+"");
//
//        }

//        ActivityManager activityManager = (ActivityManager) this.getSystemService(ACTIVITY_SERVICE);
////        for (ActivityManager.RunningAppProcessInfo runningAppProcess : activityManager.getRunningAppProcesses()) {
////            int lastTrimLevel = runningAppProcess.lastTrimLevel;
////            System.out.println("yangyinhua" + lastTrimLevel);
////            System.out.println("yangyinhua" + runningAppProcess.toString());
////        }
//        for (ActivityManager.RunningServiceInfo runningService : activityManager.getRunningServices(100)) {
//            System.out.println("yangyinhua" + runningService.toString());
////        }
//        FragmentManager fragmentManager = getFragmentManager();
//        fragmentManager.getFragment()
//        for (Fragment fragment : fragmentManager.getFragments()) {
//        }

//        DownloadManager downloadManager = (DownloadManager) getSystemService(DOWNLOAD_SERVICE);
//        DownloadManager.Request request = new DownloadManager.Request(Uri.parse("http://i.imgur.com/iXgyWbG.png"));
//        request.setNotificationVisibility(DownloadManager.Request.VISIBILITY_HIDDEN);
//        request.setVisibleInDownloadsUi(true);
//        request.setDestinationInExternalFilesDir(this, Environment.DIRECTORY_PICTURES, "iXgyWbG.png");
//        downloadManager.enqueue(request);

        LocationManager locationManager = (LocationManager) getSystemService(LOCATION_SERVICE);
        Location internet = locationManager.getLastKnownLocation("internet");
        System.out.println(internet);

        getIntent()
        new Handler();


    }
}