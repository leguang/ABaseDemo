package cn.itsite.abase.mvp.view.base;

import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.view.View;
import android.view.WindowManager;

import com.jakewharton.retrofit2.adapter.rxjava2.HttpException;

import org.json.JSONException;

import java.net.ConnectException;
import java.net.SocketTimeoutException;

import cn.itsite.abase.common.ActivityManager;
import cn.itsite.abase.common.DialogHelper;
import cn.itsite.abase.log.ALog;
import cn.itsite.abase.mvp.contract.base.BaseContract;
import cn.itsite.abase.utils.ScreenUtils;
import cn.itsite.adialog.dialog.LoadingDialog;
import me.yokeyword.fragmentation.anim.DefaultNoAnimator;
import me.yokeyword.fragmentation.anim.FragmentAnimator;
import me.yokeyword.fragmentation_swipeback.SwipeBackActivity;


/**
 * Author：leguang on 2016/10/9 0009 15:49
 * Email：langmanleguang@qq.com
 */
public abstract class BaseActivity<P extends BaseContract.Presenter> extends SwipeBackActivity implements BaseContract.View {
    public final String TAG = BaseActivity.class.getSimpleName();
    public P mPresenter;
    private LoadingDialog loadingDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initActivity();
        initStateBar();
        mPresenter = createPresenter();
    }

    private void initStateBar() {
        if (Build.VERSION_CODES.KITKAT <= Build.VERSION.SDK_INT) {
            // 透明状态栏
            getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
            //实现透明导航栏
//            getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_NAVIGATION);
        }
    }

    @NonNull
    protected P createPresenter() {
        return null;
    }

    private void initActivity() {
        //把每一个Activity加入栈中
        ActivityManager.getInstance().addActivity(this);

        //一旦启动某个Activity就打印Log，方便找到该类
        ALog.e(TAG);
    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    @Override
    protected void onPause() {
        super.onPause();
    }

    @Override
    protected void onDestroy() {

        if (mPresenter != null) {
            mPresenter.clear();
            mPresenter = null;
        }
        //把每一个Activity弹出栈
        ActivityManager.getInstance().removeActivity(this);
        super.onDestroy();
    }

    @Override
    public FragmentAnimator onCreateFragmentAnimator() {
        return new DefaultNoAnimator();
    }

    public void initStateBar(View view) {
        if (Build.VERSION_CODES.KITKAT <= Build.VERSION.SDK_INT) {
            view.setPadding(view.getPaddingLeft(),
                    view.getPaddingTop() + ScreenUtils.getStatusBarHeight(this),
                    view.getPaddingRight(), view.getPaddingBottom());
        }
    }

    public void showLoading() {
        if (loadingDialog == null) {
            loadingDialog = new LoadingDialog(this);
        }
        loadingDialog.show();
    }

    public void dismissLoading() {
        if (loadingDialog != null) {
            loadingDialog.dismiss();
        }
    }

    /**
     * 用于被P层调用的通用函数。
     *
     * @param response
     */
    @Override
    public void start(Object response) {
        ALog.e(TAG, "start");
        showLoading();
    }

    /**
     * 用于被P曾调用的通用函数。
     *
     * @param errorMessage P层传递过来的错误信息显示给用户。
     */
    @Override
    public void error(String errorMessage) {
        dismissLoading();
        DialogHelper.warningSnackbar(getWindow().getDecorView(), errorMessage);
    }

    public void error(Throwable throwable) {
        if (throwable == null) {
            DialogHelper.errorSnackbar(getWindow().getDecorView(), "数据异常");
            return;
        }
        if (throwable instanceof ConnectException) {
            DialogHelper.errorSnackbar(getWindow().getDecorView(), "网络异常");
        } else if (throwable instanceof HttpException) {
            DialogHelper.errorSnackbar(getWindow().getDecorView(), "服务器异常");
        } else if (throwable instanceof SocketTimeoutException) {
            DialogHelper.errorSnackbar(getWindow().getDecorView(), "连接超时");
        } else if (throwable instanceof JSONException) {
            DialogHelper.errorSnackbar(getWindow().getDecorView(), "解析异常");
        } else {
            DialogHelper.errorSnackbar(getWindow().getDecorView(), "数据异常");
        }
        throwable.printStackTrace();
        ALog.e(TAG, throwable);
    }

    @Override
    public void complete(Object response) {
        dismissLoading();
    }
}
