package com.example.urldemo.utils;

import android.os.Build;
import android.webkit.URLUtil;

import androidx.annotation.RequiresApi;

import com.example.urldemo.dto.Movie;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.jsoup.Connection;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MovieUtil {

    public static boolean isInMyPhone = false;

    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    public static List<Movie> getMoviesFromServer(String movieUrl) throws IOException, JSONException {
        List<Movie> movies = new ArrayList<>();
        URL url = new URL(movieUrl);
        URLConnection connection = url.openConnection();
        connection.connect();
        BufferedReader reader = new BufferedReader(new InputStreamReader(connection.getInputStream()));
        StringBuilder sb = new StringBuilder();
        String lines;
        while ((lines = reader.readLine()) != null) {
            sb.append(lines);
        }
        JSONObject json = new JSONObject(sb.toString());
        if(!"A00000".equals(json.get("code"))){
            return null;
        }
        JSONArray lists = json.getJSONObject("data").getJSONArray("list");
        for (int x = 0; x<lists.length(); x++){
            Movie movie = new Movie();
            Object list = lists.get(x);
            JSONObject jsonObject = new JSONObject(list.toString());
            movie.setTitle((String) jsonObject.get("title"));
            movie.setImageUrl((String) jsonObject.get("imageUrl"));
            movie.setPlayUrl((String) jsonObject.get("playUrl"));
            movie.setDuration((String) jsonObject.get("duration"));
            movie.setDescription((String) jsonObject.get("description"));
            JSONArray categories = jsonObject.getJSONArray("categories");
            List<String> categoryList = new ArrayList<>();
            for (int i = 0; i < categories.length(); i++) {
                String category = categories.getString(i);
                categoryList.add(category);
            }
            movie.setCategories(categoryList);
            ArrayList<String> starringList = new ArrayList<>();
            JSONObject peopleJson = jsonObject.getJSONObject("people");
            JSONArray starringJson = peopleJson.getJSONArray("main_charactor");
            for (int i = 0; i < starringJson.length(); i++) {
                String name = (String) starringJson.getJSONObject(i).get("name");
                starringList.add(name);
            }
            movie.setStarringList(starringList);
            movies.add(movie);
        }
        return movies;
    }

}
