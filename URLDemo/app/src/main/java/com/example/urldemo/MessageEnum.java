package com.example.urldemo;

public enum MessageEnum {
    UPDATE_URL(1),
    UPDATE_MOVIE(2),
    PLAY_VIDEO(3),
    UPDATE_MP4_URL(4),
    UPDATE_MP4_LIST(5);

    private int value;

    MessageEnum(int value) {
        this.value = value;
    }

    public int getValue() {
        return value;
    }
}
