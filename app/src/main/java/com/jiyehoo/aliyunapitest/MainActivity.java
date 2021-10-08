package com.jiyehoo.aliyunapitest;

import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Response;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    private final String TAG = "###MainActivity";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        findViewById(R.id.btn_get).setOnClickListener(this);
    }

    private void startGet(String city, String page, String province, String spot) {
        if (TextUtils.isEmpty(city) || TextUtils.isEmpty(city)
                || TextUtils.isEmpty(city) || TextUtils.isEmpty(city)) {
            Log.e(TAG, "startGet pram is null");
            return;
        }

        String host = "https://scenicspot.market.alicloudapi.com";
        String path = "/lianzhuo/scenicspot";
        String appcode = "a1c63ab9f2944bef99d643f6dc861a6c";

        Map<String, String> queryMap = new HashMap<>();
        queryMap.put("city", city);
        queryMap.put("page", page);
        queryMap.put("province", province);
        queryMap.put("spot", spot);

        HttpUtil.INSTANCE.get(host + path, appcode, queryMap, new Callback() {
            @Override
            public void onFailure(@NonNull Call call, @NonNull IOException e) {
                Log.d(TAG, "失败");
            }

            @Override
            public void onResponse(@NonNull Call call, @NonNull Response response) throws IOException {
                Log.d(TAG, "成功:" + response.toString() + Objects.requireNonNull(response.body()).string());
            }
        });
    }

    @Override
    public void onClick(View view) {
        if (view.getId() == R.id.btn_get) {
            String city = "济南";
            String page = "1";
            String province = "山东";
            String spot = "五龙潭";
            startGet(city, page, province, spot);
        }
    }
}



