## HuaWeiVerifier
Fix only the crash of `Register too manager Broadcast Receivers` on Huawei's mobile phone.

## How to usage
```gradle
dependencies {
    // add dependencies
    implementation 'com.llew.huawei:verifier:1.0.2'
}
```

```java
public class SimpleApplication extends Application {

    @Override
    public void onCreate() {
        super.onCreate();
        LoadedApkHuaWei.hookHuaWeiVerifier(getBaseContext());
    }
}
```

## Blog Site
[http://blog.csdn.net/llew2011/article/details/79054457](http://blog.csdn.net/llew2011/article/details/79054457)