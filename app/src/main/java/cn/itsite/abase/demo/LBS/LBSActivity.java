package cn.itsite.abase.demo.LBS;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import cn.itsite.abase.demo.R;
import cn.itsite.abase.mvp.view.base.BaseActivity;

public class LBSActivity extends BaseActivity {
    public static final String TAG = LBSActivity.class.getSimpleName();
    @BindView(R.id.bt_select_address)
    Button btSelectAddress;
    @BindView(R.id.bt_location)
    Button btLocation;
    @BindView(R.id.bt_map)
    Button btMap;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lbs);
        ButterKnife.bind(this);
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
}
