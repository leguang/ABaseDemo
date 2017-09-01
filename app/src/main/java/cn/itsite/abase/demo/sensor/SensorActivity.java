package cn.itsite.abase.demo.sensor;

import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import cn.itsite.abase.demo.R;
import cn.itsite.abase.demo.common.ShakeHelper;
import cn.itsite.abase.mvp.view.base.BaseActivity;

public class SensorActivity extends BaseActivity {
    public static final String TAG = SensorActivity.class.getSimpleName();
    @BindView(R.id.toolbar_title)
    TextView toolbarTitle;
    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.bt_open)
    Button btOpen;
    @BindView(R.id.bt_close)
    Button btClose;
    private ShakeHelper shakeHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sensor);
        ButterKnife.bind(this);
        initToolbar();
        shakeHelper = new ShakeHelper(this);
    }

    private void initToolbar() {
        initStateBar(toolbar);
        toolbarTitle.setText("LBS服务");
        toolbar.setNavigationIcon(R.drawable.ic_chevron_left_white_24dp);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (shakeHelper != null) {
            shakeHelper.stop();
        }
    }

    @OnClick({R.id.bt_open, R.id.bt_close})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.bt_open:
                shakeHelper.start();
                break;
            case R.id.bt_close:
                shakeHelper.stop();
                break;
        }
    }
}
