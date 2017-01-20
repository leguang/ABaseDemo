package cn.itsite.abase.demo;

import android.app.Application;
import android.content.Context;

import cn.itsite.abase.exception.AppExceptionHandler;


/**
 * Author：leguang on 2016/10/9 0009 15:49
 * Email：langmanleguang@qq.com
 */
public class BaseApplication extends Application {
    private static final String TAG = BaseApplication.class.getSimpleName();
    public static Context mContext;

    @Override
    public void onCreate() {
        super.onCreate();
        mContext = this;
        Thread.setDefaultUncaughtExceptionHandler(AppExceptionHandler.getInstance(this));

    }
}
