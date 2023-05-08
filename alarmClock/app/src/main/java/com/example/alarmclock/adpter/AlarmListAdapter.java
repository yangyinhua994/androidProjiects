package com.example.alarmclock.adpter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Switch;
import android.widget.TextView;

import com.example.alarmclock.R;
import com.example.alarmclock.dto.Alarm;

import java.util.ArrayList;

public class AlarmListAdapter extends BaseAdapter {
    private ArrayList<Alarm> alarms;
    private LayoutInflater inflater;

    public AlarmListAdapter(Context context, ArrayList<Alarm> alarms) {
        this.alarms = alarms;
        this.inflater = LayoutInflater.from(context);
    }

    public void setAlarms(Alarm alarm){
        alarms.add(alarm);
        notifyDataSetChanged();
    }

    @Override
    public int getCount() {
        return alarms.size();
    }

    @Override
    public Object getItem(int position) {
        return alarms.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View view = convertView;
        ViewHolder holder;
        if (view == null) {
            view = inflater.inflate(R.layout.aclarm_clock_list_view, parent, false);
            holder = new ViewHolder();
            holder.ringingTimeTextView = view.findViewById(R.id.ringing_time);
            holder.morningAfternoonTextView = view.findViewById(R.id.morning_afternoon);
            holder.ringTimeDifferenceTextView = view.findViewById(R.id.ring_time_difference);
            holder.customSwitch = view.findViewById(R.id.custom_switch_button);
            view.setTag(holder);
        } else {
            holder = (ViewHolder) view.getTag();
        }
        Alarm alarm = alarms.get(position);
        holder.ringingTimeTextView.setText(alarm.getRingingTime());
        holder.morningAfternoonTextView.setText(alarm.getMorningAfternoon());
        holder.ringTimeDifferenceTextView.setText(alarm.getRingTimeDifference());
        holder.customSwitch.setChecked(alarm.isChecked());
        return view;
    }

    private static class ViewHolder {
        TextView ringingTimeTextView;
        TextView morningAfternoonTextView;
        TextView ringTimeDifferenceTextView;
        @SuppressLint("UseSwitchCompatOrMaterialCode")
        Switch customSwitch;
    }
}
