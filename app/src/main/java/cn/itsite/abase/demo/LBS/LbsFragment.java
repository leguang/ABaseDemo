package cn.itsite.abase.demo.LBS;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;
import cn.itsite.abase.demo.R;
import cn.itsite.abase.mvp.view.base.BaseFragment;

/**
 * Author: LiuJia on 2017/4/27 0027 11:12.
 * Email: liujia95me@126.com
 */

public class LbsFragment extends BaseFragment {
    public static final String TAG = LbsFragment.class.getSimpleName();
    @BindView(R.id.bt_select_address)
    Button btSelectAddress;
    @BindView(R.id.bt_location)
    Button btLocation;
    @BindView(R.id.bt_map)
    Button btMap;
    @BindView(R.id.toolbar_title)
    TextView toolbarTitle;
    @BindView(R.id.toolbar)
    Toolbar toolbar;
    private Unbinder unbinder;

    public static LbsFragment newInstance() {
        return new LbsFragment();
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_lbs, container, false);
        unbinder = ButterKnife.bind(this, view);
        return view;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initStateBar(toolbar);
    }

    @OnClick({R.id.bt_select_address, R.id.bt_location, R.id.bt_map})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.bt_select_address:
                start(LoacationFragment.newInstance());
                break;
            case R.id.bt_location:
                break;
            case R.id.bt_map:
                break;
        }
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }
}
