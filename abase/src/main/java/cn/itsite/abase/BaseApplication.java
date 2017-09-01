package cn.itsite.abase;

import android.content.Context;
import android.support.multidex.MultiDexApplication;

import cn.itsite.abase.exception.AppExceptionHandler;
import cn.itsite.abase.log.ALog;
import me.yokeyword.fragmentation.Fragmentation;

/**
 * Author：leguang on 2016/10/9 0009 15:49
 * Email：langmanleguang@qq.com
 */
public class BaseApplication extends MultiDexApplication {
    public static final String TAG = BaseApplication.class.getSimpleName();
    public static Context mContext;
//    private RefWatcher mRefWatcher;

    @Override
    public void onCreate() {
        super.onCreate();
        mContext = this;
        initData();//数据的初始化.
    }

    private void initData() {
        if (BuildConfig.DEBUG) {
            Fragmentation.builder()
                    .stackViewMode(Fragmentation.BUBBLE)
                    .install();
            //初始化内存泄露监听
//        mRefWatcher = LeakCanary.install(this);

            // 初始化卡顿监听
//        BlockCanary.install(this, new AppContext()).start();

            ALog.init(true, "ABase");
        } else {
            ALog.init(false, "ABase");//在release版中禁止打印log。
            Thread.setDefaultUncaughtExceptionHandler(AppExceptionHandler.getInstance(this));//在release版中处理全局异常。
        }
    }
}
