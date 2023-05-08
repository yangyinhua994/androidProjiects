package com.example.alarmclock.activity;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.ListView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.alarmclock.R;
import com.example.alarmclock.adpter.AlarmListAdapter;
import com.example.alarmclock.dto.Alarm;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {
    private ArrayList<Alarm> alarms;
    private ListView alarmsListView;
    private AlarmListAdapter adapter;
    private ImageView addAlarmClockImageView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        alarmsListView = findViewById(R.id.list_alarms);
        alarms = new ArrayList<>();
        Alarm alarm = new Alarm();
        alarms.add(alarm);
        adapter = new AlarmListAdapter(this, alarms);
        alarmsListView.setAdapter(adapter);
        addAlarmClockImageView = findViewById(R.id.add_alarm_clock);
        addAlarmClockImageView.setOnClickListener(this);

    }


    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.add_alarm_clock:
                adapter.setAlarms(new Alarm());
        }
    }
}