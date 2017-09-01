package cn.itsite.abase.demo.qrcode;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import cn.itsite.abase.demo.R;
import cn.itsite.abase.mvp.view.base.BaseActivity;

public class QRCodeActivity extends BaseActivity {
    public static final String TAG = QRCodeActivity.class.getSimpleName();
    @BindView(R.id.toolbar_title)
    TextView toolbarTitle;
    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.bt_scan_qrcode)
    Button btScanQrcode;
    @BindView(R.id.bt_generate_qrcode)
    Button btGenerateQrcode;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_qrcode);
        ButterKnife.bind(this);
        initToolbar();
    }

    @OnClick({R.id.bt_scan_qrcode,
            R.id.bt_generate_qrcode})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.bt_scan_qrcode:
                startActivity(new Intent(this, ScanQRCodeActivity.class));
                break;
            case R.id.bt_generate_qrcode:
                startActivity(new Intent(this, GenerateQRCodeActivity.class));
                break;
        }
    }

    protected void initToolbar() {
        initStateBar(toolbar);
        toolbarTitle.setText("二维码");
        toolbar.setNavigationIcon(R.drawable.ic_arrow_back_white_24dp);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressedSupport();
            }
        });
    }
}
