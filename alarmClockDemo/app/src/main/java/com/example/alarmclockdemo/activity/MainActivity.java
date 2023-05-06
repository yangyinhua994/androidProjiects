package com.example.alarmclockdemo.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.os.Bundle;
import android.view.View;

import com.example.alarmclockdemo.R;
import com.example.alarmclockdemo.fragment.AlarmFragment;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // 初始化 Fragment
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.fragment_container, new ClockFragment());
        fragmentTransaction.commit();
    }

    public void switchFragment(View view) {
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();

        switch (view.getId()) {
            case R.id.alarm_button:
                fragmentTransaction.replace(R.id.fragment_container, new AlarmFragment());
                break;
            case R.id.clock_button:
                fragmentTransaction.replace(R.id.fragment_container, new ClockFragment());
                break;
            case R.id.stopwatch_button:
                fragmentTransaction.replace(R.id.fragment_container, new StopwatchFragment());
                break;
            case R.id.timer_button:
                fragmentTransaction.replace(R.id.fragment_container, new TimerFragment());
                break;
        }

        fragmentTransaction.commit();
    }
}
