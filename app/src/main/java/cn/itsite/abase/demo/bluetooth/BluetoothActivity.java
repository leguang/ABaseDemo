package cn.itsite.abase.demo.bluetooth;

import android.os.Bundle;

import cn.itsite.abase.demo.R;
import cn.itsite.abase.mvp.view.base.BaseActivity;

public class BluetoothActivity extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bluetooth);
        getSupportFragmentManager()
                .beginTransaction()
//                .replace(R.id.fl_bluetooth_activity, BluetoothFragment.newInstance())
                .commit();
    }
}
