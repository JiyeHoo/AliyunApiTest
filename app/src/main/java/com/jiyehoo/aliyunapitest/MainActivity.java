package com.jiyehoo.aliyunapitest;

import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

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
    private TextView mTvRes;
    private EditText mEtArea;
    private EditText mEtType;
    private EditText mEtName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initView();
    }

    private void initView() {
        mEtArea = findViewById(R.id.et_area);
        mEtType = findViewById(R.id.et_type);
        mEtName = findViewById(R.id.et_name);
        mTvRes = findViewById(R.id.tv_res);
        findViewById(R.id.btn_get).setOnClickListener(this);
    }

    private void startGet(String area, String page, String type, String name) {
        Log.d(TAG, "开始请求");
        if (TextUtils.isEmpty(page)) {
            Log.e(TAG, "startGet pram is null");
            return;
        }

        String host = "https://scenicinfo.market.alicloudapi.com";
        String path = "/scenicInfo";
        String appcode = "a1c63ab9f2944bef99d643f6dc861a6c";

        Map<String, String> queryMap = new HashMap<>();
        queryMap.put("area", area);
        queryMap.put("page", page);
        queryMap.put("type", type);
        queryMap.put("name", name);

        HttpUtil.INSTANCE.get(host + path, appcode, queryMap, new Callback() {
            @Override
            public void onFailure(@NonNull Call call, @NonNull IOException e) {
                Log.d(TAG, "失败");
            }

            @Override
            public void onResponse(@NonNull Call call, @NonNull Response response) throws IOException {
                String res = Objects.requireNonNull(response.body()).string();
                Log.d(TAG, "成功:" + res);
                runOnUiThread(() -> mTvRes.setText(res));
            }
        });
    }

    @Override
    public void onClick(View view) {
        if (view.getId() == R.id.btn_get) {
            String area = mEtArea.getText().toString();
            String page = "1";
            String type = mEtType.getText().toString(); // 【4】请求参数，详见文档描述
            String name= mEtName.getText().toString(); // 【4】请求参数，详见文档描述
            startGet(area, page, type, name);
        }
    }
}



