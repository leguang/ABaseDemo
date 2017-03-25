package cn.itsite.abase.demo.designpattern;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import cn.itsite.abase.demo.R;
import cn.itsite.abase.mvp.view.base.BaseActivity;

public class DesignPatternActivity extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_design_pattern);
    }
}
