package cn.itsite.abase.demo.sensor;

import android.os.Bundle;

import cn.itsite.abase.demo.R;
import cn.itsite.abase.demo.common.ShakeHelper;
import cn.itsite.abase.mvp.view.base.BaseActivity;

public class SensorActivity extends BaseActivity {
    public static final String TAG = SensorActivity.class.getSimpleName();
    private ShakeHelper shakeHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sensor);
        shakeHelper = new ShakeHelper(this);
        shakeHelper.start();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (shakeHelper != null) {
            shakeHelper.Stop();
        }
    }
}
