package com.example.urldemo.dto;

import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class MyMessage implements Serializable {
    public static final Long MY = 0L;
    public static final Long AI = 1L;
    private String message;
    private boolean mine;
    private Date time;
    private Long type;

    public MyMessage(Object message, Long who) {
        this.message = message.toString();
        this.time = new Date();
        this.type = who;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getMessage() {
        return message;
    }

    public boolean isMine() {
        return mine;
    }

    public Long getType() {
        return type;
    }

    public void setType(Long type) {
        this.type = type;
    }

    public Date getTime() {
        return time;
    }

    public String getFormattedTime() {
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.getDefault());
        return dateFormat.format(time);
    }
}
