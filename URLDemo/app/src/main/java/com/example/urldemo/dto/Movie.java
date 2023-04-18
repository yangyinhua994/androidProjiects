package com.example.urldemo.dto;

import android.util.Log;

import java.util.List;
import java.util.Map;

/**
 * @author yangyinhua
 */
public class Movie {

    private String title;
    private String imageUrl;
    private String playUrl;
    private String duration;
    private String description;
    private Map<String, List<Map<String, String>>> people;
    private List<String> starringList;

    private List<String> categories;
    private Long doc_id;
    private Long tvId;

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public String getPlayUrl() {
        return playUrl;
    }

    public void setPlayUrl(String playUrl) {
        this.playUrl = playUrl;
    }

    public String getDuration() {
        return duration;
    }

    public void setDuration(String duration) {
        this.duration = duration;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Map<String, List<Map<String, String>>> getPeople() {
        return people;
    }

    public void setPeople(Map<String, List<Map<String, String>>> people) {
        this.people = people;
    }

    public List<String> getStarringList() {
        return starringList;
    }

    public void setStarringList(List<String> starringList) {
        this.starringList = starringList;
    }

    public List<String> getCategories() {
        return categories;
    }

    public void setCategories(List<String> categories) {
        this.categories = categories;
    }

    public Long getDoc_id() {
        return doc_id;
    }

    public void setDoc_id(Long doc_id) {
        this.doc_id = doc_id;
    }

    public Long getTvId() {
        return tvId;
    }

    public void setTvId(Long tvId) {
        this.tvId = tvId;
    }
}
