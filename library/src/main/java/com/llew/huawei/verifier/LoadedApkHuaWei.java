package com.llew.huawei.verifier;

import android.app.Application;
import android.content.Context;
import android.util.Log;

import com.llew.reflect.FieldUtils;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;

/**
 * Fix crash only on HuaWei's device
 * <pre class="prettyprint">
 * java.lang.AssertionError:<font color='red'>Register too many Broadcast Receivers</font>
 *      android.app.LoadedApk.checkRecevierRegisteredLeakLocked(LoadedApk.java:1010)
 *      android.app.LoadedApk.getReceiverDispatcher(LoadedApk.java:1038)
 *      android.app.ContextImpl.registerReceiverInternal(ContextImpl.java:1476)
 *      android.app.ContextImpl.registerReceiver(ContextImpl.java:1456)
 *      android.app.ContextImpl.registerReceiver(ContextImpl.java:1450)
 *      android.content.ContextWrapper.registerReceiver(ContextWrapper.java:586)
 * </pre>
 *
 * @author llew
 * @date 2018/1/14
 */

public class LoadedApkHuaWei {

    private static final HuaWeiVerifier IMPL;

    static {
        final int version = android.os.Build.VERSION.SDK_INT;
        if (version >= 26) {
            IMPL = new V26VerifierImpl();
        } else if (version >= 24) {
            IMPL = new V24VerifierImpl();
        } else {
            IMPL = new BaseVerifierImpl();
        }
    }

    public static void hookHuaWeiVerifier(Application application) {
        try {
            if (null != application) {
                IMPL.verifier(application.getBaseContext());
            } else {
                Log.w(LoadedApkHuaWei.class.getSimpleName(), "application is null ！！！");
            }
        } catch (Throwable ignored) {
            // ignore it
        }
    }

    private static class V26VerifierImpl extends BaseVerifierImpl {

        private static final String WHITE_LIST = "mWhiteListMap";

        @Override
        public void verifier(Context baseContext) throws Throwable {
            Object whiteListMapObject = getWhiteListObject(baseContext, WHITE_LIST);
            if (whiteListMapObject instanceof Map) {
                Map whiteListMap = (Map) whiteListMapObject;
                List whiteList = (List) whiteListMap.get(0);
                if (null == whiteList) {
                    whiteList = new ArrayList<>();
                    whiteListMap.put(0, whiteList);
                }
                whiteList.add(baseContext.getPackageName());
            }
        }
    }

    private static class V24VerifierImpl extends BaseVerifierImpl {

        private static final String WHITE_LIST = "mWhiteList";

        @Override
        public void verifier(Context baseContext) throws Throwable {
            Object whiteListObject = getWhiteListObject(baseContext, WHITE_LIST);
            if (whiteListObject instanceof List) {
                List whiteList = (List) whiteListObject;
                whiteList.add(baseContext.getPackageName());
            }
        }
    }

    private static class BaseVerifierImpl implements HuaWeiVerifier {

        private static final String WHITE_LIST = "mWhiteList";

        @Override
        public void verifier(Context baseContext) throws Throwable {
            Object receiverResourceObject = getWhiteListObject(baseContext, WHITE_LIST);
            if (receiverResourceObject instanceof String[]) {
                String[] whiteList = (String[]) receiverResourceObject;
                List<String> newWhiteList = new ArrayList<>();
                newWhiteList.add(baseContext.getPackageName());
                Collections.addAll(newWhiteList, whiteList);
                FieldUtils.writeField(receiverResourceObject, WHITE_LIST, newWhiteList.toArray(new String[newWhiteList.size()]));
            }
        }

        Object getWhiteListObject(Context baseContext, String whiteList) throws Throwable {
            Field receiverResourceField = FieldUtils.getDeclaredField("android.app.LoadedApk", "mReceiverResource", true);
            if (null != receiverResourceField) {
                Field packageInfoField = FieldUtils.getDeclaredField("android.app.ContextImpl", "mPackageInfo", true);
                if (null != packageInfoField) {
                    Object packageInfoObject = FieldUtils.readField(packageInfoField, baseContext);
                    if (null != packageInfoObject) {
                        Object receivedResource = FieldUtils.readField(receiverResourceField, packageInfoObject, true);
                        if (null != receivedResource) {
                            return FieldUtils.readField(receivedResource, whiteList);
                        }
                    }
                }
            }
            return null;
        }
    }

    private interface HuaWeiVerifier {
        void verifier(Context baseContext) throws Throwable;
    }
}
