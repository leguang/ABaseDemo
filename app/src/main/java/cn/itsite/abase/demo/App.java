package cn.itsite.abase.demo;

import android.app.Activity;
import android.app.Application;
import android.os.Bundle;

import cn.itsite.abase.BaseApplication;
import cn.itsite.abase.log.ALog;


/**
 * Author：leguang on 2016/10/9 0009 15:49
 * Email：langmanleguang@qq.com
 */
public class App extends BaseApplication implements Application.ActivityLifecycleCallbacks {
    private static final String TAG = App.class.getSimpleName();

    @Override
    public void onCreate() {
        super.onCreate();
        registerActivityLifecycleCallbacks(this);
    }

    @Override
    public void onActivityCreated(Activity activity, Bundle savedInstanceState) {
        ALog.e("onActivityCreated");
    }

    @Override
    public void onActivityStarted(Activity activity) {
        ALog.e("onActivityStarted");
    }

    @Override
    public void onActivityResumed(Activity activity) {
        ALog.e("onActivityResumed");
    }

    @Override
    public void onActivityPaused(Activity activity) {
        ALog.e("onActivityPaused");
    }

    @Override
    public void onActivityStopped(Activity activity) {
        ALog.e("onActivityStopped");
    }

    @Override
    public void onActivitySaveInstanceState(Activity activity, Bundle outState) {
        ALog.e("onActivitySaveInstanceState");
    }

    @Override
    public void onActivityDestroyed(Activity activity) {
        ALog.e("onActivityDestroyed");
    }
}
