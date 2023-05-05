package com.example.urldemo.utils;

import java.io.IOException;
import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class OkhttpUtl {

    private static final OkHttpClient client = new OkHttpClient.Builder()
            .connectTimeout(10, TimeUnit.SECONDS)
            .readTimeout(20, TimeUnit.SECONDS)
            .build();

    public static Response getUrlResponse(String url){
        Request request = new Request.Builder().url(url).build();
        Response response;
        try {
            response = client.newCall(request).execute();
        } catch (IOException e) {
            return null;
        }
        return response;
    }


}
