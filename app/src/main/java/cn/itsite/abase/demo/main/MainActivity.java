package cn.itsite.abase.demo.main;

import android.os.Bundle;

import cn.itsite.abase.demo.R;
import cn.itsite.abase.mvp.view.base.BaseActivity;

public class MainActivity extends BaseActivity {
    public static final String TAG = MainActivity.class.getSimpleName();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        if (savedInstanceState == null) {
            loadRootFragment(R.id.fl_main_activity, MainFragment.newInstance());
        }
    }

    @Override
    public boolean swipeBackPriority() {
        return super.swipeBackPriority();
    }
}
