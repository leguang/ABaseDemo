package cn.itsite.abase.demo.architecture.view;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;
import cn.itsite.abase.demo.R;
import cn.itsite.abase.demo.architecture.contract.MineContract;
import cn.itsite.abase.demo.architecture.presenter.MinePresenter;
import cn.itsite.abase.log.ALog;
import cn.itsite.abase.mvp.view.base.BaseFragment;


/**
 * Author: LiuJia on 2017/4/21 14:25.
 * Email: liujia95me@126.com
 */
public class MineFragment extends BaseFragment<MineContract.Presenter> implements MineContract.View {
    private static final String TAG = MineFragment.class.getSimpleName();
    @BindView(R.id.tv)
    TextView tv;
    Unbinder unbinder;

    public static MineFragment newInstance() {
        return new MineFragment();
    }

    @NonNull
    @Override
    protected MineContract.Presenter createPresenter() {
        return new MinePresenter(this);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_mine, container, false);
        unbinder = ButterKnife.bind(this, view);
        return attachToSwipeBack(view);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initData();
    }

    private void initData() {
        mPresenter.start("");
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }

    @Override
    public void start(Object response) {
        super.start(response);
        ALog.e(TAG, "start");
    }
}
