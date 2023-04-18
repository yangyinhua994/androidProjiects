package com.example.aitest.view;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.os.Build;
import android.util.DisplayMetrics;
import android.util.Log;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.RequiresApi;

import com.example.aitest.MainActivity;
import com.example.aitest.R;
import com.example.aitest.dto.Parent;
import com.example.aitest.sqlite.MySQLite;

import java.util.Date;
import java.util.Random;

@SuppressLint("AppCompatCustomView")
public class GameMainInterfaceTextView extends TextView {

    private final Paint paint = new Paint();
    private Parent people;
    private Parent wolf;
    private Parent button;
    private int peopleWidth;
    private int peopleHeight;
    private boolean threadState = false;
    private final Long refreshThreadSleepTime = (long) getResources().getInteger(R.integer.refreshSleepTime);
    private Long peopleThreadSleepTime = (long) getResources().getInteger(R.integer.peopleThreadSleepTime);
    private Long runIndex = (long) getResources().getInteger(R.integer.runIndex);
    private final Long peopleMoveDistance = (long) getResources().getInteger(R.integer.peopleMoveDistance);
    private final DisplayMetrics dm = getResources().getDisplayMetrics();
    private int randomBooleanX;
    private int randomBooleanY;
    private MySQLite dataListMySQLite;
    private StringBuilder stringBuilder;
    private long time;
    private MainActivity mainActivity;
//    是否是学习状态
    private boolean isLearning = false;

    public void setIsLearning(boolean learning) {
        isLearning = learning;
    }

    public void setPeopleThreadSleepTime(Long peopleThreadSleepTime) {
        this.peopleThreadSleepTime = peopleThreadSleepTime;
    }

    public void setMainActivity(MainActivity mainActivity) {
        this.mainActivity = mainActivity;
    }

    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    public GameMainInterfaceTextView(Context context) {
        super(context);
        dataListMySQLite = new MySQLite(getContext(), "data_list");
        init();
        updateThread(true);
    }

    private void init(){
        Bitmap peopleBitmap = BitmapFactory.decodeResource(getResources(), R.drawable.people);
        people = new Parent(dm.widthPixels - peopleBitmap.getWidth(), dm.heightPixels / 2f,
                BitmapFactory.decodeResource(getResources(), R.drawable.people), 1);
        wolf = new Parent(100, 100,
                BitmapFactory.decodeResource(getResources(), R.drawable.wolf), 1);
        button = new Parent(100, dm.heightPixels - 400,
                BitmapFactory.decodeResource(getResources(), R.drawable.button), 1);
        peopleWidth = people.getBitmap().getWidth();
        peopleHeight = people.getBitmap().getHeight();
        stringBuilder = new StringBuilder();
        time = new Date().getTime();
    }

    @SuppressLint("DrawAllocation")
    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        if (!isLearning){
            canvas.drawBitmap(people.getBitmap(), people.getX(), people.getY(), paint);
            canvas.drawBitmap(wolf.getBitmap(), wolf.getX(), wolf.getY(), paint);
            canvas.drawBitmap(button.getBitmap(), button.getX(), button.getY(), paint);
        }
    }

    public void updateThread(boolean threadState){
        if (threadState){
//            开启线程
            startAllThread();
        }
        this.threadState = threadState;
    }

    private void startAllThread(){
        startRefreshThread();
        startMovePeopleThread();
        runTimeThread();
        startIndexThread();
    }


    private void startRefreshThread(){
        new Thread(){
            @Override
            public void run() {
                super.run();
                while (threadState){
                    postInvalidate();
                    if (!isLearning){
                        sleepThread(refreshThreadSleepTime);
                    }
                }
            }
        }.start();
    }

    private void startMovePeopleThread(){
        new Thread(){
            @Override
            public void run() {
                super.run();
                while (threadState){
                    if (people.getX() == 0){
                        people.setX(peopleMoveDistance);
                        randomBooleanX = 2;
                    }else if (people.getX() + people.getBitmap().getWidth() == dm.widthPixels){
                        people.setX(people.getX() - peopleMoveDistance);
                        randomBooleanX = 1;
                    }else {
                        if (randomBooleanX == 1){
                            if (people.getX() < peopleMoveDistance){
                                people.setX(0);
                            }else {
                                people.setX(people.getX() - peopleMoveDistance);
                            }
                        }else {
                            if ((dm.widthPixels - people.getX() - peopleWidth) < peopleMoveDistance){
                                people.setX(dm.widthPixels - peopleWidth);
                            }else {
                                people.setX(people.getX() + peopleMoveDistance);
                            }
                        }
                    }
                    if (people.getY() == 0){
                        people.setY(peopleMoveDistance);
                        randomBooleanY = 2;
                    }else if (people.getY() + people.getBitmap().getHeight() == dm.heightPixels){
                        people.setY(people.getY() - peopleMoveDistance);
                        randomBooleanY = 1;
                    }else {
                        if (randomBooleanY == 1){
                            if ((dm.heightPixels - people.getY() - peopleHeight) < peopleMoveDistance){
                                people.setY(dm.heightPixels - peopleHeight);
                            }else {
                                people.setY(people.getY() - peopleMoveDistance);
                            }
                        }else {
                            if (people.getY() < peopleMoveDistance){
                                people.setY(0);
                            }else {
                                people.setY(people.getY() + peopleMoveDistance);
                            }
                        }
                    }
                    sleepThread(peopleThreadSleepTime);
                    if (inRange(button, people.getX(), people.getX() + peopleWidth, people.getY() - peopleHeight)){
                        if (!isLearning){
                            runIndex--;
                            Log.i("yangyinhua", "还需要运行的次数为:" + runIndex);
                            dataListMySQLite.getWritableDatabase().execSQL("insert into data_list (all_step, time) values ('"+stringBuilder+"', '"+ (new Date().getTime() - time)+"')");
                            init();
                        }else {
                            updateThread(false);
                            Toast.makeText(getContext(), "学习结果运行完成", Toast.LENGTH_LONG).show();
                        }
                    }
                }
            }
        }.start();
    }

    private void sleepThread(Long millis){
        try {
            Thread.sleep(millis);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    private void runTimeThread(){
        new Thread(){
            @Override
            public void run() {
                super.run();
                if (stringBuilder.length() == 0){
                    while (threadState){
                        stringBuilder.append(randomBooleanX).append(",").append(randomBooleanY).append(".");
                        randomBooleanX = new Random().nextInt(2) + 1;
                        randomBooleanY = new Random().nextInt(2) + 1;
                        sleepThread(500L);
                    }
                }else {
                    for (String s : String.valueOf(stringBuilder).split("\\.")) {
                        randomBooleanX = Integer.parseInt(s.split(",")[0]);
                        randomBooleanY = Integer.parseInt(s.split(",")[1]);
                        sleepThread(500L);
                    }
                }
            }
        }.start();
    }

    private void startIndexThread(){
        new Thread(){
            @Override
            public void run() {
                super.run();
                while (threadState){
                    if (runIndex == 0){
                        updateThread(false);
                        Intent intent = new Intent(mainActivity, MainActivity.class);
                        mainActivity.startActivity(intent);
                    }
                }
            }
        }.start();
    }

    public static boolean inRange(Parent parent, float minX, float maxX, float y) {
        if (y >= parent.getY() && y <= parent.getY() + parent.getBitmap().getHeight()){
            if (parent.getX() >= minX && parent.getX() <= maxX) return true;
            return parent.getX() + parent.getBitmap().getWidth() >= minX && parent.getX() + parent.getBitmap().getWidth() <= maxX;
        }else {
            return false;
        }
    }

    public void runLearningResults() {
        setPeopleThreadSleepTime(10L);
        Cursor cursor = dataListMySQLite.getReadableDatabase().rawQuery("select * from data_list order by time limit 0,1", null);
        if (cursor.moveToNext()){
            stringBuilder.append(cursor.getString(cursor.getColumnIndex("all_step")));
            String time = cursor.getString(cursor.getColumnIndex("time"));
            String all_step = cursor.getString(cursor.getColumnIndex("all_step"));
        }
    }
}
