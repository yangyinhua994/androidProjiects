package com.example.urldemo.dto;

import java.util.List;

/**
 * @author yangyinhua
 */
public class Msg {

    private int state;
    private List<Movie> data;

    public int getState() {
        return state;
    }

    public void setState(int state) {
        this.state = state;
    }

    public List<Movie> getData() {
        return data;
    }

    public void setData(List<Movie> data) {
        this.data = data;
    }
}
