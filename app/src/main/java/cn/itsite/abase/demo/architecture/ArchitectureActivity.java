package cn.itsite.abase.demo.architecture;

import android.os.Bundle;

import cn.itsite.abase.demo.R;
import cn.itsite.abase.demo.architecture.mvp.view.MineFragment;
import cn.itsite.abase.mvp.view.base.BaseActivity;

public class ArchitectureActivity extends BaseActivity {
    public final String TAG = ArchitectureActivity.class.getSimpleName();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_architecture);
        if (savedInstanceState == null) {
            loadRootFragment(R.id.fl_architecture_activity, MineFragment.newInstance());
        }
    }
}
