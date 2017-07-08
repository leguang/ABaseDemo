package cn.itsite.abase.widget.Dialog;

import android.app.Dialog;
import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.support.annotation.NonNull;
import android.view.Window;
import android.widget.TextView;

import cn.itsite.abase.R;


/**
 * 加载中Dialog
 *
 * @author lexyhp
 */
public class LoadingDialog extends Dialog {

    private TextView tvLoading;

    public LoadingDialog(@NonNull Context context) {
        super(context);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        setContentView(R.layout.dialog_loading);
        tvLoading = (TextView) findViewById(R.id.tv_loading);
        setCancelable(false);
        setCanceledOnTouchOutside(false);
    }

    public LoadingDialog setText(String s) {
        tvLoading.setText(s);
        return this;
    }
}
