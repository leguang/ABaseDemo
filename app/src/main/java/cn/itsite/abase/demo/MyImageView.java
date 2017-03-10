package cn.itsite.abase.demo;

import android.content.Context;
import android.support.v7.widget.AppCompatImageView;

/**
 * Created by leguang on 2017/3/8 0008.
 * Email：langmanleguang@qq.com
 */

public class MyImageView extends AppCompatImageView {
    public MyImageView(Context context) {
        super(context);
    }

    @Override
    public Object clone() throws CloneNotSupportedException {
        return super.clone();
    }
}
