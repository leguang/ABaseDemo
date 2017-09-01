package cn.itsite.abase.demo.LBS;

import android.os.Bundle;

import cn.itsite.abase.demo.R;
import cn.itsite.abase.demo.main.MainFragment;
import cn.itsite.abase.mvp.view.base.BaseActivity;

public class LBSActivity extends BaseActivity {
    public static final String TAG = LBSActivity.class.getSimpleName();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        if (savedInstanceState == null) {
            loadRootFragment(R.id.fl_main_activity, LbsFragment.newInstance());
        }
    }
}
