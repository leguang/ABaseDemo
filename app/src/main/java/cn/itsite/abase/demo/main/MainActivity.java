package cn.itsite.abase.demo.main;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Toast;

import com.chad.library.adapter.base.BaseQuickAdapter;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import cn.itsite.abase.demo.AppApplication;
import cn.itsite.abase.demo.LBS.LBSActivity;
import cn.itsite.abase.demo.R;
import cn.itsite.abase.demo.architecture.ArchitectureActivity;
import cn.itsite.abase.demo.bluetooth.BluetoothActivity;
import cn.itsite.abase.demo.common.Constants;
import cn.itsite.abase.demo.customview.CustomViewActivity;
import cn.itsite.abase.demo.designpattern.DesignPatternActivity;
import cn.itsite.abase.demo.multimedia.MultimediaActivity;
import cn.itsite.abase.demo.optimize.OptimizeActivity;
import cn.itsite.abase.demo.payment.PaymentActivity;
import cn.itsite.abase.demo.qrcode.QRCodeActivity;
import cn.itsite.abase.demo.ui.UIActivity;
import cn.itsite.abase.mvp.view.base.BaseActivity;

public class MainActivity extends BaseActivity {
    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.recyclerview)
    RecyclerView recyclerview;
    private List<MainBean> list;
    private MainRVAdapter mAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        initData();
    }

    private void initData() {
        toolbar.setTitle(R.string.app_name);
        list = new ArrayList<>();
        list.add(new MainBean("架构", "搜集了MVP+MVVM等架构Demo"));
        list.add(new MainBean("蓝牙", "封装了传统蓝牙和低功耗蓝牙Demo"));
        list.add(new MainBean("自定义View", "自己练手+别人写的好的自定义View参考"));
        list.add(new MainBean("设计模式", "设计模式Demo"));
        list.add(new MainBean("UI特效", "一些有常用的布局+酷炫动画"));
        list.add(new MainBean("优化", "优化相关实践"));
        list.add(new MainBean("多媒体", "与音频+视频相关的Demo"));
        list.add(new MainBean("LBS", "与地图+定位相关的Demo"));
        list.add(new MainBean("支付", "与各个支付平台相关的Demo+适当的封装"));
        list.add(new MainBean("二维码", "与二维码相关功能的集成和使用"));
        recyclerview.setLayoutManager(new LinearLayoutManager(this));
        mAdapter = new MainRVAdapter(R.layout.item_rv_main_activity, list);
        mAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                Toast.makeText(AppApplication.mContext, position + "", Toast.LENGTH_SHORT).show();
                Intent mIntent = null;
                switch (position) {
                    case Constants.ARCHITECTURE:
                        mIntent = new Intent(MainActivity.this, ArchitectureActivity.class);
                        break;

                    case Constants.BLUETOOTH:
                        mIntent = new Intent(MainActivity.this, BluetoothActivity.class);
                        break;

                    case Constants.CUSTOM_VIEW:
                        mIntent = new Intent(MainActivity.this, CustomViewActivity.class);

                        break;

                    case Constants.DESIGN_PATTERN:
                        mIntent = new Intent(MainActivity.this, DesignPatternActivity.class);

                        break;

                    case Constants.UI:
                        mIntent = new Intent(MainActivity.this, UIActivity.class);

                        break;

                    case Constants.OPTIMIZE:
                        mIntent = new Intent(MainActivity.this, OptimizeActivity.class);

                        break;

                    case Constants.MULTIMEDIA:
                        mIntent = new Intent(MainActivity.this, MultimediaActivity.class);

                        break;

                    case Constants.LBS:
                        mIntent = new Intent(MainActivity.this, LBSActivity.class);

                        break;

                    case Constants.PAYMENT:
                        mIntent = new Intent(MainActivity.this, PaymentActivity.class);

                        break;

                    case Constants.QRCODE:
                        mIntent = new Intent(MainActivity.this, QRCodeActivity.class);

                        break;
                }
                startActivity(mIntent);
            }
        });
        recyclerview.setAdapter(mAdapter);
    }
}
