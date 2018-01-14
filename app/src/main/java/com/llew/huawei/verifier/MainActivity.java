package com.llew.huawei.verifier;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;

/**
 * <br/><br/>
 *
 * @author llew
 * @date 2018/1/14
 */

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public void register(View view) {
        LoadedApkHuaWei.hookHuaWeiVerifier(getBaseContext());
        for (int i = 1; i <= 1000; i++) {
            IntentFilter filter = new IntentFilter();
            filter.addAction("test index : " + i);
            registerReceiver(new BroadcastReceiver() {
                @Override
                public void onReceive(Context context, Intent intent) {
                }
            }, filter);
            Log.e(getClass().getName(), "当前注册了：" + i + " 个广播接收器");
        }
    }
}
