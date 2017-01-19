package cn.itsite.abase.mvp.presenter;


import cn.itsite.abase.mvp.contract.VersionContract;
import cn.itsite.abase.mvp.presenter.base.BasePresenter;
import retrofit2.Call;

/**
 * Author：leguang on 2016/10/9 0009 15:49
 * Email：langmanleguang@qq.com
 */
public class VersionPresenter extends BasePresenter<VersionContract.View> implements VersionContract.Presenter {

    private static final String TAG = VersionPresenter.class.getSimpleName();
    private Call mCall;


    public VersionPresenter(VersionContract.View mView) {
        super(mView);
    }


    @Override
    public void start() {

    }
}
