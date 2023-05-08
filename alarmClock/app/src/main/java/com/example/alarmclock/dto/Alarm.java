package com.example.alarmclock.dto;

import android.widget.Switch;

public class Alarm {

    //响铃时间
    private String ringingTime = "07:20";
    //上午或下午
    private String morningAfternoon = "上午";
    //剩余响铃时间
    private String ringTimeDifference = "法定工作日 | 11小时28分钟后响铃";

    private boolean checked = true;

    public String getRingingTime() {
        return ringingTime;
    }

    public void setRingingTime(String ringingTime) {
        this.ringingTime = ringingTime;
    }

    public String getMorningAfternoon() {
        return morningAfternoon;
    }

    public void setMorningAfternoon(String morningAfternoon) {
        this.morningAfternoon = morningAfternoon;
    }

    public String getRingTimeDifference() {
        return ringTimeDifference;
    }

    public void setRingTimeDifference(String ringTimeDifference) {
        this.ringTimeDifference = ringTimeDifference;
    }

    public boolean isChecked() {
        return checked;
    }

    public void setChecked(boolean checked) {
        this.checked = checked;
    }
}
