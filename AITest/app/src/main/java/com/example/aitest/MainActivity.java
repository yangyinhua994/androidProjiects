package com.example.aitest;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import android.database.Cursor;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Toast;

import com.example.aitest.sqlite.MySQLite;
import com.example.aitest.view.GameMainInterfaceTextView;

public class MainActivity extends AppCompatActivity {

    private GameMainInterfaceTextView gameMainInterfaceTextView;
    private MySQLite dataListMySQLite;

    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        this.getString()
        super.onCreate(savedInstanceState);
        dataListMySQLite = new MySQLite(this, "data_list");
        Cursor cursor = dataListMySQLite.getReadableDatabase().rawQuery("select * from data_list order by time limit 0,1", null);
        if (cursor.moveToNext()){
            setContentView(R.layout.activity_main);
        }else {
            gameMainInterfaceTextView = new GameMainInterfaceTextView(this);
            gameMainInterfaceTextView.setMainActivity(this);
            setContentView(gameMainInterfaceTextView);
        }
    }

    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    public void onClick1(View view) {
        dataListMySQLite.getWritableDatabase().execSQL("delete from data_list");
        gameMainInterfaceTextView = new GameMainInterfaceTextView(this);
        gameMainInterfaceTextView.setMainActivity(this);
        setContentView(gameMainInterfaceTextView);
    }

    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    public void onClick2(View view) {
//        运行学习的结果
        Cursor cursor = dataListMySQLite.getReadableDatabase().rawQuery("select * from data_list order by time limit 0,1", null);
        if (cursor.moveToNext()){
            gameMainInterfaceTextView = new GameMainInterfaceTextView(this);
            gameMainInterfaceTextView.setMainActivity(this);
            gameMainInterfaceTextView.runLearningResults();
            setContentView(gameMainInterfaceTextView);
        }else {
            Toast.makeText(this, "没有学习记录", Toast.LENGTH_SHORT).show();
        }

    }

}