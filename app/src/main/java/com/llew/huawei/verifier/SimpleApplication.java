package com.llew.huawei.verifier;

import android.app.Application;

/**
 * <br/><br/>
 *
 * @author llew
 * @date 2018/1/14
 */

public class SimpleApplication extends Application {

    @Override
    public void onCreate() {
        super.onCreate();
        // LoadedApkHuaWei.hookHuaWeiVerifier(getBaseContext());
    }
}
