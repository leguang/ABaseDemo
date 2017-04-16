package cn.itsite.abase.mvp.presenter.base;

import android.support.annotation.NonNull;
import android.support.annotation.UiThread;


import java.lang.ref.Reference;
import java.lang.ref.WeakReference;

import cn.itsite.abase.log.ALog;
import cn.itsite.abase.mvp.contract.base.BaseContract;
import cn.itsite.abase.mvp.model.base.BaseModel;
import rx.Subscriber;

/**
 * Author：leguang on 2016/10/9 0009 10:31
 * Email：langmanleguang@qq.com
 * <p>
 * 所有Presenter类的基类，负责调度View层和Model层的交互。
 */
public abstract class BasePresenter<V extends BaseContract.View, M extends BaseContract.Model> {
    private final String TAG = BaseModel.class.getSimpleName();

    public Reference<V> mViewReference;
    public M mModel;

    /**
     * 创建Presenter的时候就绑定View和创建model。
     *
     * @param mView 所要绑定的view层对象，一般在View层创建Presenter的时候通过this把自己传过来。
     */
    public BasePresenter(V mView) {
        attachView(mView);
        mModel = createModel();
    }

    public void attachView(V view) {
        mViewReference = new WeakReference<V>(view);
    }

    public V getView() {
        return mViewReference == null ? null : mViewReference.get();
    }

    @UiThread
    public boolean isViewAttached() {
        return mViewReference != null && mViewReference.get() != null;
    }

    @NonNull
    protected M createModel() {
        return null;
    }

    public M getModel() {
        return mModel;
    }

    public void setPresenter(@NonNull M model) {
        this.mModel = model;
    }

    @UiThread
    public void clear() {
        ALog.e(TAG + "clear()");

        //释放Model层对象，避免内存泄露
        if (mModel != null) {
            mModel.clear();
            mModel = null;
        }

        //释放View层对象，避免内存泄露
        if (mViewReference != null) {
            mViewReference.clear();
            mViewReference = null;
        }
    }

    public abstract class RxSubscriber<T> extends Subscriber<T> {

        @Override
        public void onStart() {
            super.onStart();
            getView().start();
        }

        @Override
        public void onNext(T t) {
            _onNext(t);
        }


        @Override
        public void onCompleted() {
            getView().end();
        }

        @Override
        public void onError(Throwable e) {
            e.printStackTrace();
            //此处不考虑错误类型，笼统的以错误来介绍
            getView().error(e);
        }

        public abstract void _onNext(T t);

    }
}
