package com.example.urldemo.utils;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;
import java.util.List;

/**
 * @author yangyinhua
 */
public class UrlUtil {

    public static List<String> getUrlData(String url, int index) throws IOException {

        List<String> dataList = new ArrayList<>();
        URLConnection connection;
        BufferedReader reader = null;
        while (index >= 0){
            StringBuilder sb = new StringBuilder();
            connection = new URL(url).openConnection();
            connection.connect();
            reader = new BufferedReader(new InputStreamReader(connection.getInputStream()));
            String lines;
            while ((lines = reader.readLine()) != null) {
                sb.append(lines);
            }
            dataList.add(sb.toString());
            index--;
        }
        if(reader != null){
            reader.close();
        }
        return dataList;

    }

}
