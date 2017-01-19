package cn.itsite.abase.mvp.presenter.base;


import android.support.annotation.UiThread;

import java.lang.ref.Reference;
import java.lang.ref.WeakReference;

import cn.itsite.abase.common.RxManager;
import cn.itsite.abase.mvp.contract.base.BaseContract;
import rx.Subscriber;

/**
 * Author：leguang on 2016/10/9 0009 10:31
 * Email：langmanleguang@qq.com
 */
public abstract class BasePresenter<V extends BaseContract.View> {
    public Reference<V> mViewReference;

    //每一套mvp应该拥有一个独立的RxManager
    public RxManager mRxManager = new RxManager();

    public BasePresenter(V mView) {
        attachView(mView);
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

    @UiThread
    public void detachView() {
        mRxManager.clear();
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
