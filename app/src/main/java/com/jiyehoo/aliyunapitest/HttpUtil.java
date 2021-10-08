package com.jiyehoo.aliyunapitest;

import android.text.TextUtils;
import android.util.Log;

import java.util.Map;
import java.util.Objects;
import java.util.concurrent.TimeUnit;

import okhttp3.Callback;
import okhttp3.HttpUrl;
import okhttp3.OkHttpClient;
import okhttp3.Request;

/**
 * TODO feature
 *
 * @author aobing <a href="mailto:aobing.hu@tuya.com"/>
 * @since 2021/10/8 3:01 下午
 */
public enum HttpUtil {

    INSTANCE;

    private static final String TAG = "###HttpUtil";
    private final OkHttpClient client;

    HttpUtil() {
        client = new OkHttpClient().newBuilder()
                .connectTimeout(10, TimeUnit.SECONDS)
                .readTimeout(30, TimeUnit.SECONDS)
                .build();
    }

    public void get(String url, String appcode, Map<String,String> params, Callback responseCallback) {
        if (TextUtils.isEmpty(url) || TextUtils.isEmpty(appcode) || null == responseCallback) {
            Log.e(TAG, "HttpUtil get url params is null");
            return;
        }
        HttpUrl.Builder httpBuilder = Objects.requireNonNull(HttpUrl.parse(url)).newBuilder();
        if (params != null) {
            for(Map.Entry<String, String> param : params.entrySet()) {
                httpBuilder.addQueryParameter(param.getKey(),param.getValue());
            }
        }
        Request request = new Request.Builder()
                .header("Authorization", "APPCODE " + appcode)
                .url(httpBuilder.build())
                .build();
        client.newCall(request).enqueue(responseCallback);
    }

}
