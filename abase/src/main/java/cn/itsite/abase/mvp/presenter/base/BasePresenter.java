package cn.itsite.abase.mvp.presenter.base;

import android.support.annotation.NonNull;
import android.support.annotation.UiThread;

import com.jakewharton.retrofit2.adapter.rxjava2.HttpException;

import org.json.JSONException;

import java.lang.ref.Reference;
import java.lang.ref.WeakReference;
import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;
import java.lang.reflect.Type;
import java.net.ConnectException;
import java.net.SocketTimeoutException;

import cn.itsite.abase.common.RxManager;
import cn.itsite.abase.log.ALog;
import cn.itsite.abase.mvp.contract.base.BaseContract;

/**
 * Author：leguang on 2016/10/9 0009 10:31
 * Email：langmanleguang@qq.com
 * <p>
 * 所有Presenter类的基类，负责调度View层和Model层的交互。
 */
public class BasePresenter<V extends BaseContract.View, M extends BaseContract.Model> implements BaseContract.Presenter {
    public final String TAG = BasePresenter.class.getSimpleName();
    private final Class<? extends BaseContract.View> mViewClass;

    public Reference<V> mViewReference;
    public M mModel;
    //每一套mvp应该拥有一个独立的RxManager
    public RxManager mRxManager = new RxManager();

    /**
     * 创建Presenter的时候就绑定View和创建model。
     *
     * @param mView 所要绑定的view层对象，一般在View层创建Presenter的时候通过this把自己传过来。
     */
    public BasePresenter(V mView) {
        mViewClass = mView.getClass();
        setView(mView);
        mModel = createModel();
    }

    public void setView(V view) {
        mViewReference = new WeakReference<V>(view);
    }

    public V getView() {
        if (mViewReference.get() != null) {
            ALog.e("getView");
            return mViewReference.get();
        }

        return (V) Proxy.newProxyInstance(mViewClass.getClassLoader(), mViewClass.getInterfaces(), new InvocationHandler() {
            @Override
            public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
                try {
                    ALog.e(args.length > 0 ? args[0] : "无参");

                    Type type = method.getReturnType();
                    if (type == boolean.class) {
                        return false;
                    } else if (type == int.class) {
                        return 0;
                    } else if (type == short.class) {
                        return (short) 0;
                    } else if (type == char.class) {
                        return (char) 0;
                    } else if (type == byte.class) {
                        return (byte) 0;
                    } else if (type == long.class) {
                        return 0L;
                    } else if (type == float.class) {
                        return 0f;
                    } else if (type == double.class) {
                        return 0D;
                    } else {
                        return null;
                    }
                } catch (Exception e) {
                    throw e.getCause();
                }
            }
        });
    }

    @UiThread
    public boolean isViewAttached() {
        return mViewReference != null && mViewReference.get() != null;
    }

    @NonNull
    protected M createModel() {
        return null;
    }

    public void setModel(@NonNull M model) {
        this.mModel = model;
    }

    /**
     * 默认实现的接口，用于P层调用。
     *
     * @param request 传一些参数给P层。
     */
    @Override
    public void start(Object request) {
        if (isViewAttached()) {
            getView().start("");
        }
    }

    /**
     * 优先释放Model层对象，避免内存泄露。
     */
    @UiThread
    @Override
    public void clear() {
        if (mModel != null) {
            mModel.clear();
            mModel = null;
        }
        if (mRxManager != null) {
            mRxManager.clear();
        }
        //释放View层对象，避免内存泄露
        if (mViewReference != null) {
            mViewReference.clear();
            mViewReference = null;
        }
    }

    /**
     * P层通用函数，用于对异常的统一处理，并调用V层显示通知用户。
     *
     * @param throwable
     */
    public void error(Throwable throwable) {
        if (!isViewAttached()) {
            return;
        }
        if (throwable == null) {
            getView().error("数据异常");
            return;
        }
        if (throwable instanceof ConnectException) {
            getView().error("网络异常");
        } else if (throwable instanceof HttpException) {
            getView().error("服务器异常");
        } else if (throwable instanceof SocketTimeoutException) {
            getView().error("连接超时");
        } else if (throwable instanceof JSONException) {
            getView().error("解析异常");
        } else {
            getView().error("数据异常");
        }
        throwable.printStackTrace();
        ALog.e(TAG, throwable);
    }

    public void complete() {
        if (isViewAttached()) {
            getView().complete(null);
        }
    }
}
