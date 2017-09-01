package cn.itsite.abase.demo.sensor;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import cn.itsite.abase.demo.R;
import cn.itsite.abase.demo.common.ShakeHelper;

public class SensorActivity extends AppCompatActivity {
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
