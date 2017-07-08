package cn.itsite.abase.demo.architecture.mvp.presenter;


import cn.itsite.abase.demo.architecture.mvp.contract.MineContract;
import cn.itsite.abase.mvp.presenter.base.BasePresenter;

/**
 * Author：leguang on 2016/10/9 0009 10:35
 * Email：langmanleguang@qq.com
 * <p>
 * 负责邻里模块Presenter层内容。
 */

public class MinePresenter extends BasePresenter<MineContract.View, MineContract.Model> implements MineContract.Presenter<String> {
    private final String TAG = MinePresenter.class.getSimpleName();

    public MinePresenter(MineContract.View mView) {
        super(mView);
    }

    /**
     * Presenter的生命周期开始。
     *
     * @param s
     */
    @Override
    public String start(String s) {
        return null;
    }
}