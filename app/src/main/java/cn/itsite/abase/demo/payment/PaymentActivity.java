package cn.itsite.abase.demo.payment;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.Button;

import butterknife.BindView;
import butterknife.ButterKnife;
import cn.itsite.abase.demo.R;

public class PaymentActivity extends AppCompatActivity {

    @BindView(R.id.bt_weixin)
    Button btWeixin;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_payment);
        ButterKnife.bind(this);


    }
}
