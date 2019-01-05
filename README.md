## HuaWeiVerifier
Fix only the crash of `Register too many Broadcast Receivers` on Huawei's mobile phone.

## How to usage
```gradle
dependencies {
    // add dependencies
    implementation 'com.llew.huawei:verifier:1.1.0'
}
```

```java
public class SimpleApplication extends Application {

    @Override
    public void onCreate() {
        super.onCreate();
        LoadedApkHuaWei.hookHuaWeiVerifier(this);
    }
}
```
##### or with callback like this:
```java
public class SimpleApplication extends Application {

    public static SimpleApplication INSTANCE;

    @Override
    public void onCreate() {
        super.onCreate();
        INSTANCE = this;
        LoadedApkHuaWei.hookHuaWeiVerifier(this, new LoadedApkHuaWei.TooManyBroadcastCallback() {
            @Override
            public void tooManyBroadcast(int currentIndex, int totalCount) {
                Toast.makeText(SimpleApplication.INSTANCE, "too many broadcast registed " + totalCount, Toast.LENGTH_SHORT).show();
            }
        });
    }
}
```

## Blog Site
[http://blog.csdn.net/llew2011/article/details/79054457](http://blog.csdn.net/llew2011/article/details/79054457)