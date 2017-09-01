package cn.itsite.abase.demo.main;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.chad.library.adapter.base.BaseQuickAdapter;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;
import cn.itsite.abase.demo.App;
import cn.itsite.abase.demo.LBS.LBSActivity;
import cn.itsite.abase.demo.R;
import cn.itsite.abase.demo.architecture.ArchitectureActivity;
import cn.itsite.abase.demo.bluetooth.BluetoothActivity;
import cn.itsite.abase.demo.customview.CustomViewActivity;
import cn.itsite.abase.demo.designpattern.DesignPatternActivity;
import cn.itsite.abase.demo.multimedia.MultimediaActivity;
import cn.itsite.abase.demo.optimize.OptimizeActivity;
import cn.itsite.abase.demo.payment.PaymentActivity;
import cn.itsite.abase.demo.qrcode.QRCodeActivity;
import cn.itsite.abase.demo.sensor.SensorActivity;
import cn.itsite.abase.demo.ui.UIActivity;
import cn.itsite.abase.mvp.view.base.BaseFragment;
import cn.itsite.abase.utils.ToastUtils;

/**
 * Author: LiuJia on 2017/4/27 0027 11:12.
 * Email: liujia95me@126.com
 */

public class MainFragment extends BaseFragment {
    public static final String TAG = MainFragment.class.getSimpleName();
    private static final long WAIT_TIME = 2000L;// 再点一次退出程序时间设置
    public static final String PRESS_AGAIN = "再按一次退出";
    @BindView(R.id.toolbar_title)
    TextView toolbarTitle;
    private long TOUCH_TIME = 0;
    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.recyclerView)
    RecyclerView recyclerView;
    private List<MainBean> list;
    private MainRVAdapter mAdapter;
    Unbinder unbinder;

    public static MainFragment newInstance() {
        return new MainFragment();
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_list, container, false);
        unbinder = ButterKnife.bind(this, view);
        return view;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initStateBar(toolbar);
        initData();
    }

    private void initData() {
        toolbarTitle.setText(R.string.app_name);
        list = new ArrayList<>();
        list.add(new MainBean("架构", "搜集了MVP+MVVM等架构Demo", ArchitectureActivity.class));
        list.add(new MainBean("蓝牙", "封装了传统蓝牙和低功耗蓝牙Demo", BluetoothActivity.class));
        list.add(new MainBean("自定义View", "自己练手+别人写的好的自定义View参考", CustomViewActivity.class));
        list.add(new MainBean("设计模式", "设计模式Demo", DesignPatternActivity.class));
        list.add(new MainBean("UI特效", "一些有常用的布局+酷炫动画", UIActivity.class));
        list.add(new MainBean("优化", "优化相关实践", OptimizeActivity.class));
        list.add(new MainBean("多媒体", "与音频+视频相关的Demo", MultimediaActivity.class));
        list.add(new MainBean("LBS", "与地图+定位相关的Demo", LBSActivity.class));
        list.add(new MainBean("支付", "与各个支付平台相关的Demo+适当的封装", PaymentActivity.class));
        list.add(new MainBean("二维码", "与二维码相关功能的集成和使用", QRCodeActivity.class));
        list.add(new MainBean("传感器", "与传感器相关功能的集成和使用，如摇一摇", SensorActivity.class));
        recyclerView.setLayoutManager(new LinearLayoutManager(_mActivity));
        mAdapter = new MainRVAdapter(R.layout.item_rv_main_activity, list);
        mAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                Toast.makeText(App.mContext, position + "", Toast.LENGTH_SHORT).show();
                startActivity(new Intent(_mActivity, mAdapter.getData().get(position).clazz));
            }
        });
        recyclerView.setAdapter(mAdapter);
    }

    @Override
    public boolean onBackPressedSupport() {
        if (System.currentTimeMillis() - TOUCH_TIME < WAIT_TIME) {
            //退到桌面，而不是退出应用，让用户以为退出应用，尽量保活。
            Intent intent = new Intent();
            intent.setAction(Intent.ACTION_MAIN);
            intent.addCategory(Intent.CATEGORY_HOME);
            startActivity(intent);
        } else {
            TOUCH_TIME = System.currentTimeMillis();
            ToastUtils.showToast(App.mContext, PRESS_AGAIN);
        }
        return true;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }
}
