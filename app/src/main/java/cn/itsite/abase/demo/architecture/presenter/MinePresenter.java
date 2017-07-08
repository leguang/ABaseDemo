package cn.itsite.abase.demo.architecture.presenter;


import android.support.annotation.NonNull;

import cn.itsite.abase.demo.architecture.contract.MineContract;
import cn.itsite.abase.demo.architecture.model.MineModel;
import cn.itsite.abase.log.ALog;
import cn.itsite.abase.mvp.presenter.base.BasePresenter;

/**
 * Author：leguang on 2016/10/9 0009 10:35
 * Email：langmanleguang@qq.com
 * <p>
 * 负责邻里模块Presenter层内容。
 */

public class MinePresenter extends BasePresenter<MineContract.View, MineContract.Model> implements MineContract.Presenter {
    private final String TAG = MinePresenter.class.getSimpleName();

    public MinePresenter(MineContract.View mView) {
        super(mView);
    }

    @NonNull
    @Override
    protected MineContract.Model createModel() {
        return new MineModel();
    }

    @Override
    public void start(Object request) {
        super.start(request);
        ALog.e(TAG, "start");

        mModel.start("");
        getView().start("");
    }
}