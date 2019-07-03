package com.cogitech.lependu.Activities.asynctask;

import android.util.Log;

import com.squareup.okhttp.OkHttpClient;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.Response;

public class OkHttpUtils {

    public static Response sendGetOkHttpRequest(String url) throws Exception{
        Log.v("TAG_URL",url);
        OkHttpClient client = new OkHttpClient();

        //Creation de la requette
        Request request = new Request.Builder().url(url).build();

        //execution de la requette
    return client.newCall(request).execute();
    }
}
