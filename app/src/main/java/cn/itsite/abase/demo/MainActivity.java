package cn.itsite.abase.demo;

import android.os.Bundle;
import android.support.v7.widget.Toolbar;

import cn.itsite.abase.demo.test.Service;
import cn.itsite.abase.mvp.view.base.BaseActivity;

public class MainActivity extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);


    }
}
