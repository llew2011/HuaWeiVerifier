package com.llew.huawei.verifier;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

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
        setContentView(com.llew.huawei.verifier.demo.R.layout.activity_main);
    }

    public void register(View view) {
        for (int i = 1; i <= 2000; i++) {
            IntentFilter filter = new IntentFilter();
            filter.addAction("hook index : " + i);
            registerReceiver(new BroadcastReceiver() {
                @Override
                public void onReceive(Context context, Intent intent) {
                }
            }, filter);
            Log.e(getClass().getName(), "index：" + i);
        }
    }

    public void hook(View view) {
        // LoadedApkHuaWei.hookHuaWeiVerifier(SimpleApplication.INSTANCE);
        LoadedApkHuaWei.hookHuaWeiVerifier(SimpleApplication.INSTANCE, new LoadedApkHuaWei.TooManyBroadcastCallback() {
            @Override
            public void tooManyBroadcast(int currentIndex, int totalCount) {
                Toast.makeText(getApplicationContext(), "最多只能注册" + totalCount + "个广播接收器", Toast.LENGTH_SHORT).show();
            }
        });
    }
}
