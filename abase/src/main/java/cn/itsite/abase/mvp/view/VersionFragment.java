package cn.itsite.abase.mvp.view;

import android.support.annotation.NonNull;

import cn.itsite.abase.mvp.contract.VersionContract;
import cn.itsite.abase.mvp.presenter.VersionPresenter;
import cn.itsite.abase.mvp.view.base.BaseFragment;

/**
 * Author：leguang on 2016/10/9 0009 15:49
 * Email：langmanleguang@qq.com
 */
public class VersionFragment extends BaseFragment<VersionContract.Presenter> implements VersionContract.View {
    private static final String TAG = VersionFragment.class.getSimpleName();

    public static VersionFragment newInstance() {
        return new VersionFragment();
    }

    @NonNull
    @Override
    protected VersionContract.Presenter createPresenter() {
        return new VersionPresenter(this);
    }

    @Override
    public void end() {

    }

    @Override
    public void error(Throwable t) {

    }
}
